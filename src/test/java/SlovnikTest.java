
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.logging.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SlovnikTest {

    private String baseUrl = "https://www.seznam.cz/";
    private String pathToChromeDriver = "/home/kbowers/Downloads/chromedriver_linux64/chromedriver";
    private WebDriver webDriver;
    private SlovnikFramework slovnikFramework = new SlovnikFramework();

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
        webDriver = new ChromeDriver();
        webDriver.get(baseUrl);
        Logger logger = Logger.getLogger(SlovnikTest.class.getName());

    }

    @Test
    public void simpleTest() throws Exception {
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

    @After
    public void tearDown() {
        webDriver.close();
    }

    private void clickSlovnikTab() {
        webDriver.findElement(By.xpath("//*[@id=\"hp-app\"]/div/div[1]/div[2]/div/div/div[1]/header/div/div/div[2]/ul/li[6]/button/span[1]")).click();
    }

    private void translate(String wordForTranslation) throws Exception {
        WebElement searchBar = webDriver.findElement(By.xpath("//*[@id=\"slovnik-input\"]"));
        searchBar.sendKeys(wordForTranslation);
        WebElement textbox = webDriver.findElement(By.xpath("//*[@id=\"hp-app\"]/div/div[1]/div[2]/div/div/div[1]/header/div/div/div[2]/div[1]/form/button"));
        textbox.sendKeys(Keys.ENTER);
    }
    private void isPronunciationDisplayed() {
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Boolean pronunciationDisplayed = !webDriver.findElements(By.className("TranslatePage-word--pronunciation")).isEmpty();
        assertThat(pronunciationDisplayed).isTrue();
    }
    private void getLanguagesFromDropdownTest(List dropdown) {
        Assert.assertEquals(dropdown.size(), 7);
    }
    private void verifyDropdownAndBottomSame(List dropdown, List bottom) {
        assertThat(dropdown).hasSameElementsAs(bottom);
    }
    private void printAriaLabelValue() {
        String ariaLabel = webDriver.findElement(By.id("loginBadgeEl")).getAttribute("aria-label");
        System.out.println(ariaLabel);
    }
}
