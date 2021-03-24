package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class NoteTab {
    @FindBy(css="#addNoteButton")
    private WebElement addNoteButton;
    private WebDriverWait wait;


    @FindBy(css="#note-title")
    private WebElement noteTitle;

    @FindBy(css="#note-description")
    private WebElement noteDescription;

    @FindBy(css="#saveNoteChanges")
    private WebElement saveNoteChanges;

    @FindBy(css="#userTable")
    private WebElement userTable;

    @FindBy(css="#noteTable > tbody > tr > td:nth-child(1) > a")
    private List<WebElement> noteDeleteButtons;

    @FindBy(css="#noteTable > tbody > tr > td:nth-child(1) > button")
    private List<WebElement> noteEditButtons;

    @FindBy(css="#noteTable > tbody > tr > th")
    private List<WebElement> noteTitleCols;

    @FindBy(css=" #noteTable > tbody > tr > td:nth-child(3)")
    private List<WebElement> noteDescriptionCols;

    private WebDriver webDriver;

    public List<WebElement> getNoteTitleCols() {
        return noteTitleCols;
    }

    public List<WebElement> getNoteDescriptionCols() {
        return noteDescriptionCols;
    }

    public List<WebElement> getNoteDeleteButtons() {
        return noteDeleteButtons;
    }

    public List<WebElement> getNoteEditButtons() {
        return noteEditButtons;
    }

    public NoteTab(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        wait = new WebDriverWait(webDriver, 15, 50);
        this.webDriver = webDriver;
    }

    public  void clickAddNoteButton() throws InterruptedException {
        addNoteButton.click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOf(this.noteTitle));
        wait.until(ExpectedConditions.elementToBeClickable(this.saveNoteChanges));
    }

    public  void clickEditNoteButton(Integer index){
        getNoteEditButtons().get(index).click();
        wait.until(ExpectedConditions.visibilityOf(this.noteTitle));
        wait.until(ExpectedConditions.elementToBeClickable(this.saveNoteChanges));
    }


    public void addNote(String noteTitle, String noteDescription) throws InterruptedException {

        this.noteTitle.clear();
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.clear();
        this.noteDescription.sendKeys(noteDescription);
        this.saveNoteChanges.click();
        Thread.sleep(1000);

    }



}
