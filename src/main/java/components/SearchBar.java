package components;

import annatations.Component;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.inject.Inject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import scope.ScenarScope;

@Component("xpath;//div/input[@type=\"search\"]")
public class SearchBar extends AComponent {
  private final ScenarScope scope;

  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "scope управляется DI, и его изменять извне не предполагается")
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
