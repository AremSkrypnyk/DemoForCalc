package core;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 * Created by artem_skrypnyk on 10/1/14.
 */
public class Driver {

    private static final int CODE_OF_BACKSPACE_BUTTON = 67;

    private static WebDriver driver;

    public static WebDriver get() {
        return driver;
    }

    public static void set(WebDriver driverInput){
        driver = driverInput;
    }

    public static boolean isInitialized() {
        return driver != null;
    }

    public static File getScreenshot(){
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    }

    public static void init() {
        WebDriver driverInput = initAndroidPhoneDriver();
        Driver.set(driverInput);
    }

    private static WebDriver initAndroidPhoneDriver(){
        WebDriver androidPhoneDriver;
        DesiredCapabilities androidPhoneCapabilities = new DesiredCapabilities();
        androidPhoneCapabilities.setCapability("appium-version", "1.0");
        androidPhoneCapabilities.setCapability("platformName", "Android");
        androidPhoneCapabilities.setCapability("deviceName", "4d00794f5cc1a0ff");
        androidPhoneCapabilities.setCapability("app", "/Users/artem_skrypnyk/Downloads/SDETcalc_test.apk");
        androidPhoneCapabilities.setCapability("appPackage", "com.test.mq.calctest");
        androidPhoneCapabilities.setCapability("appActivity", "com.test.mq.calctest.CalcScreen");
        try {
            androidPhoneDriver =  new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), androidPhoneCapabilities);
        } catch (MalformedURLException e) {
            throw new Error("Unexpected error during remote WebDriver setup: " + e.getMessage(), e);
        }
        return androidPhoneDriver;
    }

    public static void tearDown() {
        if (Driver.isInitialized()) {
            Driver.get().quit();
        } else {
            throw new Error("Driver was not initialized and cannot be terminated.");
        }
    }

    public static void moveToTheBeginningOfTheField(WebElement element) {
        double duration = 0.1;
        int touchCount = 1;
        int xOffset = 5;
        int yOffset = 3;
        double x = element.getLocation().getX() + element.getSize().width - xOffset;
        double y = element.getLocation().getY() + ((double) element.getSize().height / yOffset);
        preciseTap(x, y, duration, touchCount);
    }

    public static void clearTextField(WebElement element) {
        moveToTheBeginningOfTheField(element);
        while (!element.getText().isEmpty()) {
            pressDeleteKey();
        }
    }

    public static void deleteLastSymbolFromField(WebElement element) {
        moveToTheBeginningOfTheField(element);
        pressDeleteKey();
    }

    public static void preciseTap(double x, double y, double duration, int touchCount) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.get();
        HashMap<String, Double> tapObject = new HashMap<String, Double>();
        tapObject.put("x", x);
        tapObject.put("y", y);
        tapObject.put("touchCount", (double)touchCount);
        tapObject.put("duration", duration);
        js.executeScript("mobile: tap", tapObject);
    }

    public static void pressDeleteKey() {
        ((AppiumDriver)Driver.get()).sendKeyEvent(CODE_OF_BACKSPACE_BUTTON);
    }

    public static WebElement findElement(By by){
        List<WebElement> elements;
        Assert.assertNotNull(by, "Cannot find element: locator in null");
        Assert.assertNotNull(get(), "Driver is not initialized");
        elements = get().findElements(by);
        if (elements.isEmpty())
            throw new Error("No element was found.");
        if (elements.size() > 1)
            throw new Error("More than 1 element for found using giving locator.");
        return elements.get(0);
    }
}
