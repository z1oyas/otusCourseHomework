package components;

import annatations.Component;
import jakarta.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import scope.ScenarScope;

@Component("xpath;//div/input[@type=\"search\"]")
public class SearchBar extends AComponent {
  private final ScenarScope scope;

  @Inject
  public SearchBar(ScenarScope scope) {
    super(scope);
    this.scope=scope;
    PageFactory.initElements(driver, this);
  }

  @FindBy(xpath = "//div/input[@type=\"search\"]")
  private WebElement searchCourseBar;

  @FindBy(xpath = "//div/input[@type=\"search\"]/following-sibling::div")
  private WebElement searchButton;

  public CatalogNavigationComponent searchInCourseBar(String keys) {
    searchCourseBar.click();
    searchCourseBar.sendKeys(keys);
    return new CatalogNavigationComponent(scope);
  }
}
