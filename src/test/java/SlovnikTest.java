import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SlovnikTest {

    private String baseUrl = "<urlFromTheAssignment>";
    private String pathToChromeDriver = "<pathToYourChromeDriver>";
    private WebDriver webDriver;
    private SlovnikFramework slovnikFramework = new SlovnikFramework();

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
        webDriver = new ChromeDriver();
    }

    @Test
    public void simpleTest() {
        clickSlovnikTab();
        translate("test");
    }

    @After
    public void tearDown() {
        webDriver.get(baseUrl);
        webDriver.close();
    }

    private void clickSlovnikTab() {
        // TODO: implement me
    }

    private void translate(String wordForTranslation) {
        // TODO: implement me
    }
}
