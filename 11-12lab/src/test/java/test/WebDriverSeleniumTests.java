package test;

import service.TestDataReader;
import service.UserCreator;
import util.AccountCurrency;
import driver.DriverSingleton;
import model.User;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import page.SeleniumHQLoginPage;
import util.StringHolder;
import util.TestListener;

@Listeners({TestListener.class})
public class WebDriverSeleniumTests {
    private static WebDriver DRIVER;
    private static TestDataReader testDataReader = new TestDataReader();

    @BeforeMethod(alwaysRun = true)
    public void browserSetup(){
        DRIVER = DriverSingleton.getDriver();
        DRIVER.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
    }

    @AfterMethod(alwaysRun = true)
    public void browserTearDown(){
        DriverSingleton.closeDriver();
    }

    @Test
    public void GetLessMoneyThanAllowedFromBankAccount(){
        StringHolder errorOrSuccessesMessage = new StringHolder();

         new SeleniumHQLoginPage(DRIVER)
                 .SubmitCustomerLoginButton()
                 .ChoiceUser(UserCreator.WithCredentialsFromProperty())
                 .SubmitLogInButton()
                 .SubmitWithdrawlMenuButton()
                 .TypeAmountWithdraw(TestDataReader.GetAmountWithdraw())
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
                .ChoiceUser(UserCreator.WithCredentialsFromProperty())
                .ChoiceCurrency(AccountCurrency.Dollar)
                .SubmitProcessButton()
                .MemorizeIdAccountFromAlertWindow(newAccountIdFromBankManagerPage)//!
                .SubmitOkAlertButton()
                .SubmitHomeButton()
                .SubmitCustomerLoginButton()
                .ChoiceUser(UserCreator.WithCredentialsFromProperty())
                .SubmitLogInButton()
                .CheckNewAccountId(newAccountIdFromBankManagerPage,newAccountIdFromAccountPage);

        Assert.assertTrue(
                newAccountIdFromAccountPage.getValue() != null && newAccountIdFromBankManagerPage.getValue() != null &&
                        !newAccountIdFromAccountPage.getValue().equals("") && !newAccountIdFromBankManagerPage.getValue().equals("") &&
                        newAccountIdFromAccountPage.getValue().equals(newAccountIdFromBankManagerPage.getValue())
        );
    }

    @Test
    public void GetMoreMoneyThanAllowedFromBankAccount(){
        StringHolder errorOrSuccessesMessage = new StringHolder();

        new SeleniumHQLoginPage(DRIVER)
                .SubmitCustomerLoginButton()
                .ChoiceUser(UserCreator.WithCredentialsFromProperty())
                .SubmitLogInButton()
                .SubmitWithdrawlMenuButton()
                .TypeAmountWithdraw(TestDataReader.GetAmountWithdraw() + "00000")
                .SubmitWithdrawButton()
                .WriteErrorOrSuccessesMessage(errorOrSuccessesMessage);

        Assert.assertEquals(errorOrSuccessesMessage.getValue(),"Transaction Failed. You can not withdraw amount more than the balance.");
    }

    @Test
    public void CheckingUserSearchByPostCode(){
        StringHolder postCodeByList = new StringHolder();

        new SeleniumHQLoginPage(DRIVER)
                .SubmitBankManagerLoginButton()
                .SubmitCustomersButton()
                .InputSearchLine(UserCreator.WithCredentialsFromProperty().getUserCode())
                .MemorizeLastNameByList(postCodeByList);

        Assert.assertTrue(
                postCodeByList.getValue() != null &&
                        !postCodeByList.getValue().equals("") &&
                        postCodeByList.getValue().equals(UserCreator.WithCredentialsFromProperty().getUserName().split(" ")[1])
        );
    }

    @Test
    public void AddNewCustomer(){
        User newUser = new User("Aurora Miria", "12345");
        StringHolder postCodeByList = new StringHolder();

        new SeleniumHQLoginPage(DRIVER)
                .SubmitBankManagerLoginButton()
                .AddNewCustomerButton()
                .InputNewFirstNameLine("Aurora")
                .InputNewLastNameLine("Miria")
                .InputNewPostCodeNameLine("12345")
                .SubmitAddNewCustomerButton()
                .SubmitOkAlertButton()
                .SubmitHomeButton()
                .SubmitBankManagerLoginButton()
                .SubmitCustomersButton()
                .InputSearchLine(newUser.getUserCode())
                .MemorizeLastNameByList(postCodeByList);

        Assert.assertTrue(
                postCodeByList.getValue() != null &&
                        !postCodeByList.getValue().equals("") &&
                        postCodeByList.getValue().equals(newUser.getUserName().split(" ")[1])
        );
    }

}
