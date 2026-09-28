package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		WebDriverUtils.goTo("http://localhost:8080/lms/");

		assertEquals("http://localhost:8080/lms/", webDriver.getCurrentUrl());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("StudentAA001");

		WebElement loginButton = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		loginButton.click();
		//画面遷移後待機時間
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.urlToBe(
				"http://localhost:8080/lms/course/detail"));

		assertEquals("http://localhost:8080/lms/course/detail", webDriver.getCurrentUrl());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		//未提出の研修日を探す
		List<WebElement> rows = webDriver.findElements(By.cssSelector("table.sctionList tbody tr"));
		for (WebElement row : rows) {
			if (row.getText().contains("未提出")) {
				//未提出の研修日の詳細をクリック
				row.findElement(By.cssSelector("input[value='詳細']")).click();
				break;
			}
		}

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.urlContains("/lms/section/detail"));

		//セクション詳細画面に遷移するかのテスト
		assertTrue(webDriver.getCurrentUrl().contains("/lms/section/detail"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		//提出するボタンクリック
		WebElement submitButton = webDriver.findElement(
				By.cssSelector("input[value='日報【デモ】を提出する']"));
		submitButton.click();

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.urlToBe("http://localhost:8080/lms/report/regist"));

		assertEquals("http://localhost:8080/lms/report/regist", webDriver.getCurrentUrl());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		// 報告内容を入力
		WebElement reportContent = webDriver.findElement(By.id("content_0"));
		reportContent.sendKeys("テスト");

		// 「提出する」ボタンを押下
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.urlContains("/lms/section/detail"));

		// 「提出済み日報【デモ】を確認する」に更新されたことを確認
		WebElement submittedButton = webDriver.findElement(
				By.cssSelector("input[value='提出済み日報【デモ】を確認する']"));

		assertEquals("提出済み日報【デモ】を確認する", submittedButton.getAttribute("value"));

		getEvidence(new Object() {
		});
	}

}
