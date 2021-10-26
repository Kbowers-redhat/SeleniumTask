import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SlovnikFramework {

    public SlovnikFramework() {}

    public List<String> getLanguagesFromDropdown(WebDriver webDriver) {
        webDriver.findElement(By.xpath("//*[@id=\"__next\"]/div/header/div[2]/form/div[2]/div[1]/button/span")).click();
        List<WebElement> dropdownLanguagesElement  = webDriver.findElements(By.className("Form-element-selectDropdown-item"));
        List dropdownLanguages = new ArrayList();
        for (int i = 0;i<dropdownLanguagesElement.size(); i++) {
            dropdownLanguages.add(dropdownLanguagesElement.get(i).getText());
        }
        System.out.println(dropdownLanguages);
        return dropdownLanguages;
    }

    public List<String> getLanguagesFromBottom(WebDriver webDriver) {
        webDriver.get("https://slovnik.seznam.cz/");
        List<WebElement> footerLanguagesElement  = webDriver.findElements(By.className("Footer-text-link"));
        List footerLanguages = new ArrayList();
        for (int i = 0;i<7; i++) {
            footerLanguages.add(footerLanguagesElement.get(i).getText());
        }
        return footerLanguages;
    }
}
