package jp.co.sss.lms.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {

	private WebDriver driver;

	@BeforeEach
	void setUp() {
		// Chromeの起動オプション設定
		ChromeOptions options = new ChromeOptions();
		driver = new ChromeDriver(options);
	}

	@AfterEach
	void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	void testIndex() {
		driver.get("http://localhost:8080/lms/");

		assertEquals("ログイン | LMS", driver.getTitle());

		WebElement loginButton = driver.findElement(By.cssSelector("input[value='ログイン']"));

		assertEquals("ログイン", loginButton.getAttribute("value"));
	}

}
