package my.company.steps;

import com.google.common.base.Predicate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;


public class WebDriverSteps 
{

    private WebDriver driver;
    	public WebDriverSteps(WebDriver driver) 
    {
        this.driver = driver;
    }
	
    @Step
    public void openMainPage() 
    {
    	driver.get("https://grill-man.ru/catalog");
    }

    @Step
    public void script() throws InterruptedException 
    {
    	PrintWriter file = null;
        try
        {
            file = new PrintWriter(new FileOutputStream("combinations.txt", true));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Ошибка открытия файла combinations.txt");
            System.exit(0);
        }
    	
    	WebElement catalog = driver.findElement(By.id("content"));
  		 List<WebElement> list = catalog.findElements(By.cssSelector("div.node-catalog"));
  		 
  		 int sumprice =  0;

  		 chosemeals: for (WebElement foods : list)  
  		 {
  			 WebElement name  = foods.findElement(By.cssSelector("div.left-col h2"));
  			 WebElement price = foods.findElement(By.cssSelector("div.field-name-commerce-price"));
  			 WebElement offer = foods.findElement(By.cssSelector("input.form-submit"));
  			 
  			 if(Math.random() > 0.8)
  			 {
  				 offer.click();
  				 System.out.println(String.format("Блюдо: %s\n по цене: %s", name.getText(), price.getText()));
  				 	file.println(String.format("Блюдо: %s\n по цене: %s", name.getText(), price.getText()));
  				 Thread.sleep(2000);
  				 
  				 WebElement finalprice = driver.findElement(By.cssSelector("div.final-price"));
  				 System.out.println(String.format("Итоговая цена: %s\n", finalprice.getText()));
  				 	file.println(String.format("Итоговая цена: %s\n", finalprice.getText()));
  				 
  				 sumprice =  Integer.parseInt((finalprice.getText()).replaceAll(" ", ""));
  				 
  				 if( sumprice > 1000) break chosemeals;
  				 
  				 
  			 }
  			
  		 }		 
  		 
  		 if( sumprice > 1000)
  		 {
  			 System.out.println("Итоговая цена больше 1000 руб. - делаем заказ...\n");
  			 WebElement checkout1 = driver.findElement(By.cssSelector("a.checkout-button"));
  			 checkout1.click();
  			 
  			 WebDriverWait waitfor = new WebDriverWait(driver, 5, 1000);
  			 waitfor.withMessage("Элемент не был найден!");
  			 waitfor.until(ExpectedConditions.presenceOfElementLocated(By.id("edit-customer-profile-billing-field-phone-und-0-value")));

  			 driver.findElement(By.id("edit-customer-profile-billing-field-phone-und-0-value")).clear();
  			 driver.findElement(By.id("edit-customer-profile-billing-field-phone-und-0-value")).sendKeys("88005553535");
  			 driver.findElement(By.id("edit-continue")).click();
  			 
  			 waitfor.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.ctools-modal-content")));
  			 WebElement bonus = driver.findElement(By.cssSelector("div.ctools-modal-content"));
  			 System.out.println(String.format("Бонус к заказу: %s\n", bonus.getText()));
  			 
  			file.println(String.format("Бонус к заказу: %s\n", bonus.getText()));
  			file.println(" ");
  			file.close();
  			 
  			
  		 }
   	 }
        
        @Attachment
        @Step("Make screen shot of results page")
        public byte[] Screen() throws InterruptedException 
        {
        	Thread.sleep(1500);
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
        
        

        public void quit() 
        {
            driver.quit();
        }
         
}
