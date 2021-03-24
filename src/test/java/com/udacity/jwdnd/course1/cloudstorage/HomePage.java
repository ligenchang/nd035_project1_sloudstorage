package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class HomePage {
    @FindBy(css="#logout")
    private WebElement logoutButton;

    @FindBy(css="#submit-button")
    private WebElement submitButton;

    @FindBy(css="#nav-notes-tab")
    private  WebElement noteTab;

    @FindBy(css="#nav-credentials-tab")
    private  WebElement credentialsTab;

    private WebDriverWait wait;
    WebDriver webDriver;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        wait = new WebDriverWait(webDriver, 15, 50);
        this.webDriver = webDriver;

    }

    public void logout(){
        logoutButton.click();
        wait.until(ExpectedConditions.visibilityOf(submitButton));
    }

    public void clickNoteTab() throws InterruptedException {
        noteTab.click();
        Thread.sleep(2000);
    }

    public void clickCredntialsTab() throws InterruptedException {
        credentialsTab.click();
        Thread.sleep(3000);
    }
}
