package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

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

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
		webDriver.get("http://localhost:8080/lms/");

		assertEquals("ログイン | LMS", webDriver.getTitle());

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		//機能プルダウンクリック
		webDriver.findElement(By.cssSelector("li.dropdown > a.dropdown-toggle")).click();

		//ヘルプボタンクリック
		webDriver.findElement(By.linkText("ヘルプ")).click();

		assertEquals("http://localhost:8080/lms/help", webDriver.getCurrentUrl());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		//現在のウィンドウ取得
		String currentWindow = webDriver.getWindowHandle();

		webDriver.findElement(By.linkText("よくある質問")).click();

		//新しく開いたタブに切り替える
		for (String window : webDriver.getWindowHandles()) {
			if (!window.equals(currentWindow)) {
				webDriver.switchTo().window(window);
				break;
			}
		}

		assertEquals("http://localhost:8080/lms/faq", webDriver.getCurrentUrl());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		//カテゴリ【研修関係】
		webDriver.findElement(By.linkText("【研修関係】")).click();

		assertEquals("http://localhost:8080/lms/faq?frequentlyAskedQuestionCategoryId=1",
				webDriver.getCurrentUrl());

		String result = webDriver.findElement(
				By.cssSelector("table.sortabletable")).getText();
		assertTrue(result.contains("Q.キャンセル料・途中退校について"));
		assertTrue(result.contains("Q.研修の申し込みはどのようにすれば良いですか？"));

		//カテゴリ【人材開発支援助成金】
		webDriver.findElement(By.linkText("【人材開発支援助成金】")).click();

		assertEquals("http://localhost:8080/lms/faq?frequentlyAskedQuestionCategoryId=2",
				webDriver.getCurrentUrl());

		result = webDriver.findElement(
				By.cssSelector("table.sortabletable")).getText();
		assertTrue(result.contains("Q.セルフ・キャリアドック制度とは何か"));
		assertTrue(result.contains("Q.事業所が変わった場合、何かしら手続きをする必要がありますか？"));
		assertTrue(result.contains("Q.助成金書類の作成方法が分かりません"));

		//カテゴリ【遠隔研修】
		webDriver.findElement(By.linkText("【遠隔研修】")).click();

		assertEquals("http://localhost:8080/lms/faq?frequentlyAskedQuestionCategoryId=3",
				webDriver.getCurrentUrl());

		result = webDriver.findElement(
				By.cssSelector("table.sortabletable")).getText();
		assertEquals("検索結果\n"
				+ "データが登録されていません。", result);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {

	}

}
