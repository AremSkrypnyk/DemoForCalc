package core;

import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by artem_skrypnyk on 10/6/14.
 */

public class TestListener extends TestListenerAdapter {

    private static final String SCREENSHOT_TIMESTAMP_FORMAT = "MM-dd_HH-mm-ss_sss";
    private static final String REPORTS_DIR_DATE_FORMAT = "MM-dd-yy__HH-mm";

    @Override
    public void onTestFailure(ITestResult tr) {
         makeReportsDir("DEFAULT SUITE");
         postScreenshot("ScreenShot On Failure", "[SCREENSHOT]");
    }

    private static void postScreenshot(String screenshotName, String messagePrefix){
        if (Driver.isInitialized()) {
            File inputFile = Driver.getScreenshot();
            String outputDir = makeReportsDir("DEFAULT SUITE") + "/screenshots";
            new File(outputDir).mkdirs();

            String outputFileName = getScreenshotFileName(screenshotName);
            String outputPath = outputDir + "/" + outputFileName;

            File outputFile = new File(outputPath);
            try {
                FileUtils.copyFile(inputFile, outputFile);
                System.out.println(messagePrefix + outputFile.getCanonicalPath());
            } catch (IOException e) {
                throw new Error("Failed to save a screenshot named " + outputFileName, e);
            }
        } else {
            throw new Error(messagePrefix + "Failed to save a screenshot named "+screenshotName+": driver is not initialized.");
        }
    }

    private static String makeReportsDir(String suiteName) {
        DateFormat dateFormat = new SimpleDateFormat(REPORTS_DIR_DATE_FORMAT);
        String pathForReport = "reports/" + suiteName + "_" + dateFormat.format(new Date()).toString();
        new File(pathForReport).mkdirs();
        return pathForReport;
    }

    private static String getScreenshotFileName(String screenshotName) {
        String uniqueSuffix = System.nanoTime() + "";
        int filenameLength = 240 - uniqueSuffix.length();
        DateFormat dateFormat = new SimpleDateFormat(SCREENSHOT_TIMESTAMP_FORMAT);

        String formattedName = screenshotName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_").replaceAll("_+", "_");
        formattedName = dateFormat.format(new Date()) + "-" + formattedName;
        if (formattedName.length() > filenameLength) {
            formattedName = formattedName.substring(0, filenameLength - 1);
        }
        formattedName = formattedName + "-" + uniqueSuffix + ".png";
        return formattedName;
    }



}

