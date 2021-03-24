package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CredentialTab{
    @FindBy(css="#addCredentialButton")
    private WebElement addCredentialButton;
    private WebDriverWait wait;


    @FindBy(css="#credential-url")
    private WebElement credentialUrl;

    @FindBy(css="#credential-username")
    private WebElement credentialUserName;

    @FindBy(css="#credential-password")
    private WebElement credentialPassword;


    @FindBy(css="#saveCredentialChanges")
    private WebElement saveCredentialChanges;


    @FindBy(linkText="Delete")
    private List<WebElement> CredentialDeleteButtons;


    @FindBy( css="#credentialTable > tbody > tr > td:nth-child(1) > button")
    private List<WebElement> CredentialEditButtons;

    @FindBy(css="#credentialTable > tbody > tr > th")
    private List<WebElement> CredentialUrlCols;

    @FindBy(css="#CredentialTable > tbody > tr > td:nth-child(3)")
    private List<WebElement> CredentialUserNameCols;


    @FindBy(css="#credentialTable > tbody > tr > td:nth-child(4)")
    private List<WebElement> CredentialPasswordCols;

    private WebDriver webDriver;

    public List<WebElement> getCredentialUrlCols() {
        return CredentialUrlCols;
    }

    public List<WebElement> getCredentialPasswordCols() {
        return CredentialPasswordCols;
    }

    public List<WebElement> getCredentialDeleteButtons() {
        return CredentialDeleteButtons;
    }

    public List<WebElement> getCredentialEditButtons() {
        return CredentialEditButtons;
    }

    public CredentialTab(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        wait = new WebDriverWait(webDriver, 15, 50);
        this.webDriver = webDriver;
    }

    public  void clickAddCredentialButton() throws InterruptedException {
        addCredentialButton.click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOf(this.credentialUrl));
        wait.until(ExpectedConditions.elementToBeClickable(this.saveCredentialChanges));
    }

    public  void clickEditCredentialButton(Integer index){
        getCredentialEditButtons().get(index).click();
        wait.until(ExpectedConditions.visibilityOf(this.credentialUrl));
        wait.until(ExpectedConditions.elementToBeClickable(this.saveCredentialChanges));
    }


    public void addCredential(String CredentialUrl, String CredentialUserName, String CredentialPassword) throws InterruptedException {

        this.credentialUrl.clear();
        this.credentialUrl.sendKeys(CredentialUrl);
        this.credentialUserName.clear();
        this.credentialUserName.sendKeys(CredentialUserName);
        this.credentialPassword.clear();
        this.credentialPassword.sendKeys(CredentialPassword);
        this.saveCredentialChanges.click();
        Thread.sleep(2000);

    }

}
