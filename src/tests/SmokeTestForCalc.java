package tests;

import ui.CalcMainScreen;
import core.BaseTest;
import org.testng.annotations.Test;

import java.math.BigInteger;

/**
 * Created by artem_skrypnyk on 10/1/14.
 */
public class SmokeTestForCalc extends BaseTest {

    @Test
    public void verifyDefaultLabels(){
        String fieldALabel = "A:";
        String fieldBLabel = "B:";
        String fieldADefaultValue = "0";
        String fieldBDefaultValue = "0";
        String expectedResult = "0";

        CalcMainScreen mainScreen = new CalcMainScreen();
        mainScreen
                .verifyFieldALabel(fieldALabel)
                .verifyFieldAValue(fieldADefaultValue)
                .verifyFieldBLabel(fieldBLabel)
                .verifyFieldBValue(fieldBDefaultValue)
                .verifyResult(expectedResult);
    }

    @Test
    public void positiveCase(){
        String fieldAValue = "10";
        String fieldBValue = "10";
        String expectedResult = "100";

        CalcMainScreen mainScreen = new CalcMainScreen();
        mainScreen
                .setFieldAValue(fieldAValue)
                .verifyFieldAValue(fieldAValue)
                .setFieldBValue(fieldBValue)
                .verifyFieldBValue(fieldBValue)
                .tapMultiplyButton()
                .verifyResult(expectedResult);
    }

    //It was no specific requirement that app can multiply only positive numbers
    @Test
    public void negativeNumbersMultiplyingCase(){
        String fieldAValue = "10";
        String fieldBValue = "-10";
        String expectedResult = "-100";

        CalcMainScreen mainScreen = new CalcMainScreen();
        mainScreen
                .setFieldAValue(fieldAValue)
                .verifyFieldAValue(fieldAValue)
                .setFieldBValue(fieldBValue)
                .verifyFieldBValue(fieldBValue)
                .tapMultiplyButton()
                .verifyResult(expectedResult);
    }

    //It was no specific requirement that app can multiply only integers
    @Test
    public void fractionalNumbersMultiplyingCase(){
        String fieldAValue = "0.1";
        String fieldBValue = "0.1";
        String expectedResult = "0.01";

        CalcMainScreen mainScreen = new CalcMainScreen();
        mainScreen
                .setFieldAValue(fieldAValue)
                .verifyFieldAValue(fieldAValue)
                .setFieldBValue(fieldBValue)
                .verifyFieldBValue(fieldBValue)
                .tapMultiplyButton()
                .verifyResult(expectedResult);
    }

    @Test
    public void fractionalNumberForFieldACase(){
        String fieldAValue = "0.1";
        String fieldBValue = "1";
        String expectedResult = "0.01";

        CalcMainScreen mainScreen = new CalcMainScreen();
        mainScreen
                .setFieldAValue(fieldAValue)
                .verifyFieldAValue(fieldAValue)
                .setFieldBValue(fieldBValue)
                .verifyFieldBValue(fieldBValue)
                .tapMultiplyButton()
                .verifyResult(expectedResult);
    }

    @Test
    public void fractionalNumberForFieldBCase(){
        String fieldAValue = "1";
        String fieldBValue = "0.1";
        String expectedResult = "0.01";

        CalcMainScreen mainScreen = new CalcMainScreen();
        mainScreen
                .setFieldAValue(fieldAValue)
                .verifyFieldAValue(fieldAValue)
                .setFieldBValue(fieldBValue)
                .verifyFieldBValue(fieldBValue)
                .tapMultiplyButton()
                .verifyResult(expectedResult);
    }

    //As stated known issue - app should works correctly with numbers less or equal than MAX_LONG, so it was chosen as edge case for field A
    @Test
    public void edgeCaseForFieldA(){
        String fieldAValue = BigInteger.valueOf(Long.MAX_VALUE) + "";
        String fieldBValue = Long.MAX_VALUE + "";
        BigInteger result = BigInteger.valueOf(Long.MAX_VALUE).multiply(BigInteger.valueOf(Long.MAX_VALUE));
        String expectedResult = result + "";

        CalcMainScreen mainScreen = new CalcMainScreen();
        mainScreen
                .setFieldAValue(fieldAValue)
                .verifyFieldAValue(fieldAValue)
                .setFieldBValue(fieldBValue)
                .verifyFieldBValue(fieldBValue)
                .tapMultiplyButton()
                .verifyResult(expectedResult);
    }

    //It was no specific requirement about max value for field B, so (MAX_LONG+1) was chosen as edge case for field B
    @Test
    public void edgeCaseForFieldB(){
        String fieldAValue = Long.MAX_VALUE + "";
        String fieldBValue = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE) + "";
        BigInteger result = BigInteger.valueOf(Long.MAX_VALUE).multiply(BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE));
        String expectedResult = result + "";

        CalcMainScreen mainScreen = new CalcMainScreen();
        mainScreen
                .setFieldAValue(fieldAValue)
                .verifyFieldAValue(fieldAValue)
                .setFieldBValue(fieldBValue)
                .verifyFieldBValue(fieldBValue)
                .tapMultiplyButton()
                .verifyResult(expectedResult);
    }

    @Test
    public void zeroCaseForFieldB(){
        String fieldAValue = Long.MAX_VALUE + "";
        String fieldBValue = "0";
        String expectedResult = "0";

        CalcMainScreen mainScreen = new CalcMainScreen();
        mainScreen
                .setFieldAValue(fieldAValue)
                .verifyFieldAValue(fieldAValue)
                .setFieldBValue(fieldBValue)
                .verifyFieldBValue(fieldBValue)
                .tapMultiplyButton()
                .verifyResult(expectedResult);
    }

    @Test
    public void zeroCaseForFieldA(){
        String fieldAValue = "0";
        String fieldBValue = Long.MAX_VALUE + "";
        String expectedResult = "0";

        CalcMainScreen mainScreen = new CalcMainScreen();
        mainScreen
                .setFieldAValue(fieldAValue)
                .verifyFieldAValue(fieldAValue)
                .setFieldBValue(fieldBValue)
                .verifyFieldBValue(fieldBValue)
                .tapMultiplyButton()
                .verifyResult(expectedResult);
    }

    //Test only for field B as field A cannot be empty (according to known issue)
    @Test
    public void emptyFieldBCase(){
        String fieldAValue = "1";
        String fieldBValue = "";
        String expectedResult = "";

        CalcMainScreen mainScreen = new CalcMainScreen();
        mainScreen
                .setFieldAValue(fieldAValue)
                .verifyFieldAValue(fieldAValue)
                .setFieldBValue(fieldBValue)
                .verifyFieldBValue(fieldBValue)
                .tapMultiplyButton()
                .verifyResult(expectedResult);
    }

    @Test
    public void attemptToInputTextCase(){
        String fieldAValue = "test";
        String fieldBValue = "test";

        //according to known issues
        String expectedFieldAValue = "0";
        String expectedFieldBValue = "";

        CalcMainScreen mainScreen = new CalcMainScreen();
        mainScreen
                .setFieldAValue(fieldAValue)
                .verifyFieldAValue(expectedFieldAValue)
                .setFieldBValue(fieldBValue)
                .verifyFieldBValue(expectedFieldBValue);
    }
}
