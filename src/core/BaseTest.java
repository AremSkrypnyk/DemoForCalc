package core;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

/**
 * Created by artem_skrypnyk on 10/1/14.
 */

@Listeners(TestListener.class)
public class BaseTest  {

    @BeforeMethod
    public void init(){
        Driver.init();
    }

    @AfterMethod
    public void cleanup() {
        Driver.tearDown();
    }
}
