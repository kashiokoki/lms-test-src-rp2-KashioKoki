package jp.co.sss.lms.service;

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
class LoginServiceTest {

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
	void testLogin_WrongId() {
		// 1. ログイン画面を開く
		driver.get("http://localhost:8080/lms/");

		// 2. 誤った情報を入力
		driver.findElement(By.id("loginId")).sendKeys("kashiokoki");
		driver.findElement(By.id("password")).sendKeys("kashiokoki");

		// 3. ログインボタンをクリック
		driver.findElement(By.cssSelector(".btn.btn-primary")).click();

		// 4. 検証：エラーメッセージが表示されているか
		WebElement errorMsg = driver.findElement(By.cssSelector(".help-inline.error"));

		// メッセージの内容が正しいか
		assertEquals("* ログインに失敗しました。", errorMsg.getText());

		// 検証：画面が遷移していない（タイトルが変わっていない）ことの確認
		assertEquals("ログイン | LMS", driver.getTitle());

	}

}
