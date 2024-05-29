package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.AccountCurrency;
import model.User;
import org.openqa.selenium.support.ui.Select;
import util.StringHolder;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeleniumHQBankManagerPage extends  AbstractPage{

    private final By newCustomerButtonLocator = new By.ByXPath("/html/body/div/div/div[2]/div/div[1]/button[1]");
    private final By openAccountMenuButtonLocator = new By.ByXPath("/html/body/div/div/div[2]/div/div[1]/button[2]");
    private final By customersMenuButtonLocator = new By.ByXPath("/html/body/div/div/div[2]/div/div[1]/button[3]");
    private final By selectUserButtonLocator = new By.ByXPath("//*[@id='userSelect']");
    private final By selectCurrencyLocator = new By.ByXPath("//*[@id='currency']");
    private final By processButtonLocator = new By.ByXPath("//button[text()='Process']");
    private final By homeButtonLocator = new By.ByXPath("/html/body/div/div/div[1]/button[1]");
    private final By firstNameByTableLocator = new By.ByXPath("/html/body/div/div/div[2]/div/div[2]/div/div/table/tbody/tr[1]/td[1]");
    private final By lastNameByTableLocator = new By.ByXPath("/html/body/div/div/div[2]/div/div[2]/div/div/table/tbody/tr[1]/td[2]");
    private final By firstNameButtonLocator = new By.ByXPath("/html/body/div/div/div[2]/div/div[2]/div/div/table/thead/tr/td[1]/a");
    private final By lastNameButtonLocator = new By.ByXPath("/html/body/div/div/div[2]/div/div[2]/div/div/table/thead/tr/td[2]/a");

    private final By searchLineInputCustomersLocator = new By.ByXPath("/html/body/div/div/div[2]/div/div[2]/div/form/div/div/input");

    private final By firstNameInputCustomersLocator = new By.ByXPath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[1]/input");
    private final By lastNameInputCustomersLocator = new By.ByXPath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[2]/input");
    private final By postCodeInputCustomersLocator = new By.ByXPath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[3]/input");
    private final By addCustomerButtonLocator = new By.ByXPath("/html/body/div/div/div[2]/div/div[2]/div/div/form/button");



    public SeleniumHQBankManagerPage(WebDriver driver){
        super(driver);
    }

    public SeleniumHQBankManagerPage SubmitOpenAccountButton(){

        WebElement openAccountMenuButton = WaitForElementLocatedBy(openAccountMenuButtonLocator);
        openAccountMenuButton.click();
        return this;
    }

    public SeleniumHQBankManagerPage SubmitCustomersButton(){

        WebElement customersButtonButton = WaitForElementLocatedBy(customersMenuButtonLocator);
        customersButtonButton.click();
        return this;
    }

    public SeleniumHQBankManagerPage InputSearchLine(String inputString){
        WebElement searchLineInputCustomers = WaitForElementLocatedBy(searchLineInputCustomersLocator);
        searchLineInputCustomers.sendKeys(inputString);
        return this;
    }

    public SeleniumHQBankManagerPage InputNewFirstNameLine(String inputString){
        WebElement InputNewFirstNameCustomer = WaitForElementLocatedBy(firstNameInputCustomersLocator);
        InputNewFirstNameCustomer.sendKeys(inputString);
        return this;
    }

    public SeleniumHQBankManagerPage InputNewLastNameLine(String inputString){
        WebElement InputNewLastNameCustomer = WaitForElementLocatedBy(lastNameInputCustomersLocator);
        InputNewLastNameCustomer.sendKeys(inputString);
        return this;
    }

    public SeleniumHQBankManagerPage InputNewPostCodeNameLine(String inputString){
        WebElement InputNewPostCodeCustomer = WaitForElementLocatedBy(postCodeInputCustomersLocator);
        InputNewPostCodeCustomer.sendKeys(inputString);
        return this;
    }

    public SeleniumHQBankManagerPage AddNewCustomerButton(){

        WebElement addCustomerButtonButton = WaitForElementLocatedBy(newCustomerButtonLocator);
        addCustomerButtonButton.click();
        return this;
    }

    public SeleniumHQBankManagerPage ChoiceUser(User user){
        WebElement selectCurrencyButton = WaitForElementLocatedBy(selectUserButtonLocator);
        selectCurrencyButton.click();

        Select selectUser = new Select(selectCurrencyButton);
        selectUser.selectByVisibleText(user.getUserName());
        return this;
    }

    public SeleniumHQBankManagerPage ChoiceCurrency(AccountCurrency currency){
        WebElement selectCurrencyButton = WaitForElementLocatedBy(selectCurrencyLocator);
        selectCurrencyButton.click();

        Select selectUser = new Select(selectCurrencyButton);
        selectUser.selectByIndex(currency.ordinal() + 1);
        return this;
    }

    public SeleniumHQBankManagerPage SubmitProcessButton(){
        WebElement processButton = WaitForElementLocatedBy(processButtonLocator);
        processButton.click();

        return this;
    }

    public SeleniumHQBankManagerPage MemorizeIdAccountFromAlertWindow(StringHolder newAccountId){
        Alert alert = driver.switchTo().alert();
        Pattern pattern = Pattern.compile(":([0-9]+)");
        Matcher matcher = pattern.matcher(alert.getText());
        if (matcher.find()) {
            newAccountId.setValue(matcher.group(1));
        }
        return this;
    }

    public SeleniumHQBankManagerPage MemorizeIdCustomerFromAlertWindow(StringHolder newCustomerId){
        try {
            // Ожидание появления alert
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.alertIsPresent());

            Alert alert = driver.switchTo().alert();
            Pattern pattern = Pattern.compile(":([0-9]+)");
            Matcher matcher = pattern.matcher(alert.getText());
            if (matcher.find()) {
                newCustomerId.setValue(matcher.group(1));
            }
        } catch (NoAlertPresentException e) {
            // Обработка исключения, если alert не появился
        }
        return this;
    }


    public SeleniumHQBankManagerPage MemorizeFirstNameByList(StringHolder FirstName){
        WebElement firstNameByTable = WaitForElementLocatedBy(firstNameByTableLocator);
        FirstName.setValue(firstNameByTable.getText());
        return this;
    }

    public SeleniumHQBankManagerPage MemorizeLastNameByList(StringHolder LastName){
        WebElement lastNameByTable = WaitForElementLocatedBy(lastNameByTableLocator);
        LastName.setValue(lastNameByTable.getText());
        return this;
    }

    public SeleniumHQBankManagerPage MemorizeFullNameByList(StringHolder FullName){
        StringHolder FirstName = new StringHolder();
        StringHolder LastName = new StringHolder();
        try {
            MemorizeFirstNameByList(FirstName);
            MemorizeLastNameByList(LastName);
            FullName.setValue(FirstName.getValue() + LastName.getValue());
            return this;
        }catch (Exception exception){
            FullName.setValue(null);
            return this;
        }

    }

    public SeleniumHQBankManagerPage SubmitOkAlertButton(){
        Alert alert = driver.switchTo().alert();
        alert.accept();
        return this;
    }

    public SeleniumHQLoginPage SubmitHomeButton(){
        WebElement homeButton = WaitForElementLocatedBy(homeButtonLocator);
        homeButton.click();
        return new SeleniumHQLoginPage(this.driver);
    }

    public SeleniumHQBankManagerPage SubmitFirstNameButton(){
        WebElement firstNameButton = WaitForElementLocatedBy(firstNameButtonLocator);
        firstNameButton.click();
        return this;
    }

    public SeleniumHQBankManagerPage SubmitLastNameButton(){
        WebElement firstNameButton = WaitForElementLocatedBy(lastNameButtonLocator);
        firstNameButton.click();
        return this;
    }

    public SeleniumHQBankManagerPage SubmitAddNewCustomerButton(){
        WebElement newCustomerButton = WaitForElementLocatedBy(addCustomerButtonLocator);
        newCustomerButton.click();
        return this;
    }




}
