package ui;

import core.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * Created by artem_skrypnyk on 10/1/14.
 */
public class CalcMainScreen {

    private static final By FIELD_A_LOCATOR                = By.xpath("//android.widget.EditText[1]");
    private static final By FIELD_B_LOCATOR                = By.xpath("//android.widget.EditText[2]");
    private static final By FIELD_A_LABEL_LOCATOR          = By.xpath("//android.widget.TextView[2]");
    private static final By FIELD_B_LABEL_LOCATOR          = By.xpath("//android.widget.TextView[3]");
    private static final By RESULTS_FIELD_LOCATOR          = By.xpath("//android.widget.TextView[4]");
    private static final By MULTIPLY_BUTTON_LOCATOR        = By.xpath("//android.widget.Button[1]");

    public CalcMainScreen setFieldAValue(String number){
        WebElement fieldA = Driver.findElement(FIELD_A_LOCATOR);
        //Workaround for known issue
        clearFieldA(fieldA);
        fieldA.sendKeys(number);
        //Workaround for known issue
        Driver.deleteLastSymbolFromField(fieldA);
        return this;
    }

    public CalcMainScreen setFieldBValue(String number){
        WebElement fieldB = Driver.findElement(FIELD_B_LOCATOR);
        clearField(fieldB);
        fieldB.sendKeys(number);
        return this;
    }

    public CalcMainScreen verifyFieldAValue(String expectedText){
        WebElement fieldA = Driver.findElement(FIELD_A_LOCATOR);
        Assert.assertTrue(expectedText.equalsIgnoreCase(fieldA.getText()), "Expected value is '" + expectedText + "', but found '" + fieldA.getText() + "'.");
        return this;
    }

    public CalcMainScreen verifyFieldBValue(String expectedText){
        WebElement fieldB = Driver.findElement(FIELD_B_LOCATOR);
        Assert.assertTrue(expectedText.equalsIgnoreCase(fieldB.getText()), "Expected value is '" + expectedText + "', but found '" + fieldB.getText() + "'.");
        return this;
    }

    public CalcMainScreen verifyFieldALabel(String expectedText){
        WebElement fieldALabel = Driver.findElement(FIELD_A_LABEL_LOCATOR);
        Assert.assertTrue(expectedText.equalsIgnoreCase(fieldALabel.getText()), "Expected label value is '" + expectedText + "', but found '" + fieldALabel.getText() + "'.");
        return this;
    }

    public CalcMainScreen verifyFieldBLabel(String expectedText){
        WebElement fieldBLabel = Driver.findElement(FIELD_B_LABEL_LOCATOR);
        Assert.assertTrue(expectedText.equalsIgnoreCase(fieldBLabel.getText()), "Expected label value is '" + expectedText + "', but found '" + fieldBLabel.getText() + "'.");
        return this;
    }

    public CalcMainScreen tapMultiplyButton(){
        WebElement multiplyButton = Driver.findElement(MULTIPLY_BUTTON_LOCATOR);
        multiplyButton.click();
        return this;
    }

    public CalcMainScreen verifyResult(String expectedResult){
        expectedResult = "Result: " + expectedResult;
        WebElement result = Driver.findElement(RESULTS_FIELD_LOCATOR);
        Assert.assertTrue(expectedResult.equalsIgnoreCase(result.getText()), "Expected result is '" + expectedResult + "', but found '" + result.getText() + "'.");
        return this;
    }

    private void clearField(WebElement field){
        Driver.clearTextField(field);
    }

    private void clearFieldA(WebElement field){
        Driver.moveToTheBeginningOfTheField(field);
        while (!field.getText().equalsIgnoreCase("0")) {
            Driver.pressDeleteKey();
        }
    }



}
