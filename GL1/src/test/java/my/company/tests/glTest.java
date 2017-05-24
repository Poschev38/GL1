package my.company.tests;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import my.company.steps.WebDriverSteps;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class glTest 
{

    private WebDriverSteps steps;

    @Before
    public void setUp() throws Exception
    {
        ChromeDriverManager.getInstance().setup();
        steps = new WebDriverSteps(new ChromeDriver());
    }
    
    @RunWith(Parameterized.class)
    public class RUnMany {
    	
    
    @Parameterized.Parameters
    public List<Object[]> data() {
        return Arrays.asList(new Object[10][0]);
    }
    
    @Test
    public void test() throws Exception
    {	
        steps.openMainPage();
        steps.script();
        steps.Screen();
        steps.quit();
    }
   }
    
}



