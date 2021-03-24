package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.juli.logging.Log;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void UnauthorizedAccessRestriction(){
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/result");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void ValidLogin(){
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		String username = "michael";
		String password = "michael";
		String messageText = "Hello!";


		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("michael", "li", username, password);

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.logout();
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}



	@Test
	public void NoteTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		String username = "michael";
		String password = "michael";
		String messageText = "Hello!";
		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("michael", "li", username, password);

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		driver.get(baseURL + "/home");

		HomePage homePage = new HomePage(driver);
		homePage.clickNoteTab();

		NoteTab noteTab = new NoteTab(driver);
		noteTab.clickAddNoteButton();
		noteTab.addNote("short memory", "this is my description");


		driver.get(baseURL + "/home");
		homePage.clickNoteTab();

		noteTab = new NoteTab(driver);
		Assertions.assertEquals("short memory", noteTab.getNoteTitleCols().get(0).getText());


		noteTab.clickEditNoteButton(0);

		noteTab.addNote("short memory updated", "this is my updated description");
		driver.get(baseURL + "/home");
		homePage.clickNoteTab();
		noteTab = new NoteTab(driver);
		Assertions.assertEquals("short memory updated", noteTab.getNoteTitleCols().get(0).getText());

		noteTab.getNoteDeleteButtons().get(0).click();
		noteTab = new NoteTab(driver);
		Assertions.assertEquals(0, noteTab.getNoteTitleCols().size());

	}

	@Test
	public void CredentialTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		String username = "michael";
		String password = "michael";
		String messageText = "Hello!";
		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("michael", "li", username, password);

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		driver.get(baseURL + "/home");

		HomePage homePage = new HomePage(driver);
		homePage.clickCredntialsTab();

		CredentialTab credentialTab = new CredentialTab(driver);
		credentialTab.clickAddCredentialButton();
		credentialTab.addCredential("http://www.google.com", "michael", "password");



		driver.get(baseURL + "/home");
		homePage.clickCredntialsTab();

		credentialTab = new CredentialTab(driver);
		Assertions.assertEquals("http://www.google.com", credentialTab.getCredentialUrlCols().get(0).getText());


		credentialTab.clickEditCredentialButton(0);

		credentialTab.addCredential("http://www.google.com/gmail", "michael_ipdate", "password_update");
		driver.get(baseURL + "/home");
		homePage.clickCredntialsTab();
		credentialTab = new CredentialTab(driver);
		Assertions.assertEquals("http://www.google.com/gmail", credentialTab.getCredentialUrlCols().get(0).getText());
		credentialTab.getCredentialDeleteButtons().get(0).click();
		credentialTab = new CredentialTab(driver);
		Assertions.assertEquals(0, credentialTab.getCredentialUrlCols().size());

	}

}
