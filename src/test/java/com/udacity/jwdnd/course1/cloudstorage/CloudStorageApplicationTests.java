package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.juli.logging.Log;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.junit.FixMethodOrder;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
	@Order(1)
	public void test01_UnauthorizedAccessRestriction(){
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/result");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void test02_ValidLogin(){
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

	private static Stream<Arguments> provideParameters() {
		return Stream.of(
				Arguments.of("michael", "michael")
		);
	}

	@ParameterizedTest
	@Order(3)
	@MethodSource("provideParameters")
	public void test03_NoteTest_Add(String username, String password) throws InterruptedException {
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
	}

	@ParameterizedTest
	@Order(4)
	@MethodSource("provideParameters")
	public void test04_NoteTest_Edit(String username, String password) throws InterruptedException {
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.clickNoteTab();
		NoteTab noteTab = new NoteTab(driver);
		noteTab.clickEditNoteButton(0);
		noteTab.addNote("short memory updated", "this is my updated description");
		driver.get(baseURL + "/home");
		homePage.clickNoteTab();
		noteTab = new NoteTab(driver);
		Assertions.assertEquals("short memory updated", noteTab.getNoteTitleCols().get(0).getText());

	}

	@ParameterizedTest
	@Order(5)
	@MethodSource("provideParameters")
	public void test05_NoteTest_Delete(String username, String password) throws InterruptedException {
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.clickNoteTab();
		NoteTab noteTab = new NoteTab(driver);
		noteTab.getNoteDeleteButtons().get(0).click();
		noteTab = new NoteTab(driver);
		Assertions.assertEquals(0, noteTab.getNoteTitleCols().size());
	}


	@ParameterizedTest
	@Order(6)
	@MethodSource("provideParameters")
	public void test06_CredentialTest_Add(String username, String password) throws InterruptedException {
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
	}

	@ParameterizedTest
	@Order(7)
	@MethodSource("provideParameters")
	public void test07_CredentialTest_Edit(String username, String password) throws InterruptedException {
		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.clickCredntialsTab();
		CredentialTab credentialTab = new CredentialTab(driver);
		credentialTab.clickEditCredentialButton(0);
		credentialTab.addCredential("http://www.google.com/gmail", "michael_ipdate", "password_update");
		driver.get(baseURL + "/home");
		homePage.clickCredntialsTab();
		credentialTab = new CredentialTab(driver);
		Assertions.assertEquals("http://www.google.com/gmail", credentialTab.getCredentialUrlCols().get(0).getText());
	}

	@ParameterizedTest
	@Order(8)
	@MethodSource("provideParameters")
	public void test08_CredentialTest_Delete(String username, String password) throws InterruptedException {
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);
		homePage.clickCredntialsTab();
		CredentialTab credentialTab = new CredentialTab(driver);
		credentialTab.getCredentialDeleteButtons().get(0).click();
		credentialTab = new CredentialTab(driver);
		Assertions.assertEquals(0, credentialTab.getCredentialUrlCols().size());
	}

}
