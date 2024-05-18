import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class WebDriverSeleniumTests {
    private static WebDriver DRIVER;

    @BeforeMethod(alwaysRun = true)
    public void browserSetup(){
        DRIVER = new ChromeDriver();
        DRIVER.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
    }

    @AfterMethod(alwaysRun = true)
    public void browserTearDown(){
        DRIVER.quit();
    }

    @Test
    public void getLessMoneyThanAllowedFromBankAccount() throws InterruptedException {
        //Customer Login
        WebElement searchCustomerLoginButton = waitForElementLocatedBy("//button[text()='Customer Login']");
        searchCustomerLoginButton.click();
        //Choice User
        WebElement searchSelectUser = waitForElementLocatedBy("//select[@id='userSelect']");
        searchSelectUser.click();
        Select selectUser = new Select(searchSelectUser);
        selectUser.selectByVisibleText("Hermoine Granger");
        //Login
        WebElement searchLogIn = waitForElementLocatedBy("//button[text()='Login']");
        searchLogIn.click();
        //WithdrawlMenu
        WebElement searchWithdrawlMenuButton = waitForElementLocatedBy("//button[@ng-class='btnClass3']");
        searchWithdrawlMenuButton.click();
        //input amount
        WebElement searchWithdrawlInput = waitForElementLocatedBy("//input[@placeholder='amount']");
        searchWithdrawlInput.sendKeys("1");
        //WithDraw
        WebElement searchWithdrawButton = waitForElementLocatedBy("//button[text()='Withdraw']");
        searchWithdrawButton.click();
        //Assert
        WebElement s = waitForElementLocatedBy("//span[@class='error ng-binding']");
        Assert.assertEquals(s.getText(),"Transaction successful");
    }
    private static WebElement waitForElementLocatedBy(String XPath) {
        return new WebDriverWait(DRIVER, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(new By.ByXPath(XPath)));
    }
    private static List<WebElement> waitForElementsLocatedBy(String XPath) {
        return new WebDriverWait(DRIVER, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(new By.ByXPath(XPath)));
    }
}
