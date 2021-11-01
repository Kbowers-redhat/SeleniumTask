
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;

import java.util.logging.*;

import static org.assertj.core.api.Assertions.*;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SlovnikTest {

    private String baseUrl = "https://www.seznam.cz/";
    private WebDriver webDriver;
    private SlovnikFramework slovnikFramework = new SlovnikFramework();

    /**
     * Sets up the ChromeDriver and goes to the specified URL
     */
    @Before
    public void setUp() {
        ChromeDriverManager.getInstance().setup();
        webDriver = new ChromeDriver();
        webDriver.get(baseUrl);
    }

    /**
     * Runs the specified tests and returns to the base URL.
     */
    @Test
    public void simpleTest() {
        clickSlovnikTab();
        translate("test");
        isPronunciationDisplayed();
        List dropdown = slovnikFramework.getLanguagesFromDropdown(webDriver);
        List bottom = slovnikFramework.getLanguagesFromBottom(webDriver);
        getLanguagesFromDropdownTest(dropdown);
        verifyDropdownAndBottomSame(dropdown, bottom);
        Logger logger = Logger.getLogger(SlovnikTest.class.getName());
        logger.log(Level.WARNING, "The test is almost over");
        printAriaLabelValue();
        webDriver.get(baseUrl);
    }

    /**
     * Closes the webdriver.
     */
    @After
    public void tearDown() {
        webDriver.close();
    }

    /**
     * Clicks on the slovnik tab from the baseUrl.
     */
    private void clickSlovnikTab() {
        List<WebElement> li = webDriver.findElements(By.className("tab-button__title-inactive"));
        li.get(5).click();
    }

    /**
     * Enters a word in the textbox to be translated and presses enter.
     *
     * @param wordForTranslation the word that will be entered into the search box and translated
     */
    private void translate(String wordForTranslation) {
        WebElement searchBar = webDriver.findElement(By.id("slovnik-input"));
        searchBar.sendKeys(wordForTranslation);
        WebElement textbox = webDriver.findElement(By.cssSelector("[data-dot=search-button]"));
        textbox.sendKeys(Keys.ENTER);
    }

    /**
     * Checks whether there is a pronunciation element on the webpage.
     */
    private void isPronunciationDisplayed() {
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Boolean pronunciationDisplayed = !webDriver.findElements(By.className("TranslatePage-word--pronunciation")).isEmpty();
        assertThat(pronunciationDisplayed).isTrue();
    }

    /**
     * Tests whether the dropdown list contains 7 languages.
     *
     * @param dropdown contains a list of languages from the dropdown
     */
    private void getLanguagesFromDropdownTest(List dropdown) {
        Assert.assertEquals(dropdown.size(), 7);
    }

    /**
     * Tests whether the languages from the dropdown and the footer are the same.
     *
     * @param dropdown contains a list of languages from the dropdown
     * @param bottom   contains a list of  languages from the footer
     */
    private void verifyDropdownAndBottomSame(List dropdown, List bottom) {
        assertThat(dropdown).hasSameElementsAs(bottom);
    }

    /**
     * prints out the ariaLabel of the element with id loginBadgeEl
     */
    private void printAriaLabelValue() {
        String ariaLabel = webDriver.findElement(By.id("loginBadgeEl")).getAttribute("aria-label");
        System.out.println(ariaLabel);
    }
}
