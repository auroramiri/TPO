package test;

import accountCurrency.AccountCurrency;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import page.SeleniumHQLoginPage;
import stringHolder.StringHolder;


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
    public void getLessMoneyThanAllowedFromBankAccount(){
        StringHolder errorOrSuccessesMessage = new StringHolder();

         new SeleniumHQLoginPage(DRIVER)
                 .SubmitCustomerLoginButton()
                 .ChoiceUser("Hermoine Granger")
                 .SubmitLogInButton()
                 .SubmitWithdrawlMenuButton()
                 .TypeAmountWithdraw("1")
                 .SubmitWithdrawButton()
                 .WriteErrorOrSuccessesMessage(errorOrSuccessesMessage);

        Assert.assertEquals(errorOrSuccessesMessage.getValue(),"Transaction successful");
    }

    @Test
    public void OpenNewBankAccount(){
        StringHolder newAccountIdFromBankManagerPage = new StringHolder();
        StringHolder newAccountIdFromAccountPage = new StringHolder();

        new SeleniumHQLoginPage(DRIVER)
                .SubmitBankManagerLoginButton()
                .SubmitOpenAccountButton()
                .ChoiceUser("Hermoine Granger")
                .ChoiceCurrency(AccountCurrency.Dollar)
                .SubmitProcessButton()
                .MemorizeIdAccountFromAlertWindow(newAccountIdFromBankManagerPage)//!
                .SubmitOkAlertButton()
                .SubmitHomeButton()
                .SubmitCustomerLoginButton()
                .ChoiceUser("Hermoine Granger")
                .SubmitLogInButton()
                .CheckNewAccountId(newAccountIdFromBankManagerPage,newAccountIdFromAccountPage);

        Assert.assertTrue(
                newAccountIdFromAccountPage.getValue() != null && newAccountIdFromBankManagerPage.getValue() != null &&
                        !newAccountIdFromAccountPage.getValue().equals("") && !newAccountIdFromBankManagerPage.getValue().equals("") &&
                        newAccountIdFromAccountPage.getValue().equals(newAccountIdFromBankManagerPage.getValue())
        );

    }

}
