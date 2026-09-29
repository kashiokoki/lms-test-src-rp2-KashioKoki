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
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 研修日の一覧を取得
		List<WebElement> sectionRows = webDriver.findElements(By.cssSelector("table.sctionList tr"));
		for (WebElement row : sectionRows) {
			// 行を少しスクロールして表示
			scrollBy("50");
			// 行のセルを取得
			List<WebElement> cellsElements = row.findElements(By.cssSelector("td"));

			// 「提出済み」の研修日を探す
			if (cellsElements.size() >= 3
					&& "提出済み".equals(cellsElements.get(2).getText())) {
				// 「詳細」ボタンを押下
				row.findElement(By.cssSelector("input[value='詳細']")).click();
				break;
			}
		}
		// セクション詳細画面への遷移を待つ
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.urlToBe(
				"http://localhost:8080/lms/section/detail"));

		getEvidence(new Object() {
		});
		// セクション詳細画面に遷移したことを確認
		assertEquals(
				"http://localhost:8080/lms/section/detail",
				webDriver.getCurrentUrl());
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 提出済みの日報の「確認する」ボタンを押下
		webDriver.findElement(
				By.cssSelector("input[value='提出済み日報【デモ】を確認する']"))
				.click();
		// レポート登録画面への遷移を待つ
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.urlToBe(
				"http://localhost:8080/lms/report/regist"));

		getEvidence(new Object() {
		});

		// レポート登録画面に遷移したことを確認
		assertEquals(
				"http://localhost:8080/lms/report/regist",
				webDriver.getCurrentUrl());
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		// 報告内容の入力欄を取得
		WebElement reportElement = webDriver.findElement(By.className("form-control"));

		reportElement.clear();
		reportElement.sendKeys("テスト修正");

		// 修正前の証跡を取得
		getEvidence(new Object() {
		}, "01");

		// 「提出する」ボタンを押下
		webDriver.findElement(
				By.cssSelector("button[type='submit']")).click();
		// セクション詳細画面への遷移を待つ
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.urlContains("/lms/section/detail"));

		// 修正後の証跡を取得
		getEvidence(new Object() {
		}, "02");
		// セクション詳細画面に遷移したことを確認
		assertTrue(
				webDriver.getCurrentUrl().contains("/lms/section/detail"));
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		// ユーザー詳細画面へのリンクを押下
		webDriver.findElement(
				By.cssSelector("a[href='/lms/user/detail']")).click();
		// ユーザー詳細画面への遷移を待つ
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.urlToBe(
				"http://localhost:8080/lms/user/detail"));

		getEvidence(new Object() {
		});
		// ユーザー詳細画面に遷移したことを確認
		assertEquals(
				"http://localhost:8080/lms/user/detail",
				webDriver.getCurrentUrl());
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// レポート一覧を取得
		List<WebElement> sectionRows = webDriver.findElements(By.cssSelector("table.table-hover tr"));
		for (WebElement row : sectionRows) {
			// 行を少しスクロールして表示
			scrollBy("50");
			// 行のセルを取得
			List<WebElement> cellsElements = row.findElements(By.cssSelector("td"));
			// データ行ではない場合はスキップ
			if (cellsElements.size() < 5) {
				continue;
			}
			// 2022年10月1日のレポートを探す
			if ("2022年10月1日(土)".equals(
					cellsElements.get(0).getText())) {
				// 該当レポートの「詳細」ボタンを押下
				row.findElement(
						By.cssSelector("input[value='詳細']")).click();
				break;
			}
		}
		// レポート詳細画面への遷移を待つ
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.urlContains("/lms/report/detail"));

		getEvidence(new Object() {
		});

		// 修正した内容が表示されていることを確認
		assertTrue(
				webDriver.findElement(By.cssSelector("body")).getText().contains("テスト修正"));
	}
}