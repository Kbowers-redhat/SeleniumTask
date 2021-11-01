import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SlovnikFramework {

    public SlovnikFramework() {
    }

    /**
     * Returns a list of Strings containing the languages from a dropdown from the following URL
     * https://slovnik.seznam.cz/.
     *
     * @param webDriver
     * @return the list of languages from the specified dropdown
     */
    public List<String> getLanguagesFromDropdown(WebDriver webDriver) {
        webDriver.findElement(By.className("Form-element-select-value")).click();
        List<WebElement> dropdownLanguagesElement = webDriver.findElements(By.className("Form-element-selectDropdown-item"));
        List<String> dropdownLanguages = new ArrayList<>();
        dropdownLanguagesElement.forEach((element) -> {
            dropdownLanguages.add(element.getText());
        });
        System.out.println(dropdownLanguages);
        return dropdownLanguages;
    }

    /**
     * Returns a list of Strings containing the languages from the footer of the following URL:
     * https://slovnik.seznam.cz/.
     *
     * @param webDriver
     * @return the list of languages from the footer of the slovnik URL.
     */
    public List<String> getLanguagesFromBottom(WebDriver webDriver) {
        webDriver.get("https://slovnik.seznam.cz/");
        List<WebElement> footerLanguagesElement = webDriver.findElements(By.cssSelector("a[href*='preklad']"));
        List<String> footerLanguages = new ArrayList<>();
        footerLanguagesElement.forEach((element) -> {
            footerLanguages.add(element.getText());
        });
        return footerLanguages;
    }
}
