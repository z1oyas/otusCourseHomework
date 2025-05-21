package components;

import annatations.Component;
import jakarta.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Component("xpath;//div/input[@type=\"search\"]")
public class SearchBar extends AComponent {

  @Inject
  public SearchBar(WebDriver driver, String baseUrl) {
    super(driver, baseUrl);
    PageFactory.initElements(driver, this);
  }

  @FindBy(xpath = "//div/input[@type=\"search\"]")
  WebElement searchCourseBar;

  @FindBy(xpath = "//div/input[@type=\"search\"]/following-sibling::div")
  WebElement searchButton;

  public CatalogNavigationComponent searchInCourseBar(String keys) {
    searchCourseBar.click();
    searchCourseBar.sendKeys(keys);
    return new CatalogNavigationComponent(driver, baseUrl);
  }
}
