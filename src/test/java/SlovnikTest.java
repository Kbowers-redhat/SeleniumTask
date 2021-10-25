
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
        getLanguagesFromDropdown();
        verifyDropdownAndBottomSame();
        getLanguagesFromBottom();
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
    private void getLanguagesFromDropdown() {
        webDriver.findElement(By.xpath("//*[@id=\"__next\"]/div/header/div[2]/form/div[2]/div[1]/button/span")).click();
        List<WebElement> dropdownLanguages  = webDriver.findElements(By.className("Form-element-selectDropdown-item"));
        for (int i = 0;i<dropdownLanguages.size(); i++) {
            System.out.println(dropdownLanguages.get(i).getText());
        }
        Assert.assertEquals(dropdownLanguages.size(), 7);
    }
    private void getLanguagesFromBottom() {
        webDriver.get("https://slovnik.seznam.cz/");
        List<WebElement> footerLanguages  = webDriver.findElements(By.className("Footer-text-link"));
        for (int i = 0;i<7; i++) {
            System.out.println(footerLanguages.get(i).getText());
        }
    }
    private void verifyDropdownAndBottomSame() {
        List<WebElement> footerLanguages  = webDriver.findElements(By.className("Footer-text-link"));
        List<WebElement> dropdownLanguages  = webDriver.findElements(By.className("Form-element-selectDropdown-item"));
        for (int i = 0;i<dropdownLanguages.size(); i++) {
            for (int j = 0; j < dropdownLanguages.size(); j++) {
                if (dropdownLanguages.get(i).getText().equals(footerLanguages.get(j).getText())) {
                    Assert.assertEquals(dropdownLanguages.get(i).getText(), footerLanguages.get(j).getText());
                    break;
                }
            }
        }

    }
    private void printAriaLabelValue() {
        String ariaLabel = webDriver.findElement(By.id("loginBadgeEl")).getAttribute("aria-label");
        System.out.println(ariaLabel);
    }





}
