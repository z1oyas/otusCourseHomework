package components;

import annatations.Component;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.inject.Inject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.MainCoursePage;
import scope.ScenarScope;
import java.util.List;

@Component("xpath;//nav")
public class CourseCategoriesComponent extends AComponent {


  private By buttonMenu = By.xpath("//section/button");

  private By learningCategoryButton = By.xpath("//span[contains(text(),'Обучение')]");

  private String learningCategoryChooseButton = "//div/div[p[contains(text(),'Все курсы')]]/div/a";

  private final ScenarScope scope;

  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "scope управляется DI, и его изменять извне не предполагается")
  @Inject
  public CourseCategoriesComponent(ScenarScope scope) {
    super(scope);
    this.scope = scope;
    PageFactory.initElements(driver, this);
  }

  public By getButtonMenu() {
    return buttonMenu;
  }

  public By getLearningCategoryButton() {
    return learningCategoryButton;
  }

  public By getLearningCategoryChooseButton() {
    return By.xpath(learningCategoryChooseButton);
  }


  public CourseCategoriesComponent clickOnButton(By button) {
    waiters.waitForCondition(ExpectedConditions.elementToBeClickable(button));
    WebElement menuButton = driver.findElement(button);

    menuButton.click();
    return this;
  }

  public MainCoursePage clickOnCourse(By courseLocator) {
    WebElement category = driver.findElement(courseLocator);
    category.getText();
    waiters.waitElementShouldBeVisible(category);
    category.click();
    return new MainCoursePage(scope);
  }


  public CategoryData getRandomCategory() {
    List<WebElement> categories = ff(getLearningCategoryChooseButton());
    int randomIndex = (int) (Math.random() * categories.size()) + 1;

    By locator = By.xpath(learningCategoryChooseButton + "[" + randomIndex + "]");
    String fullText = f(locator).getText();
    String key = fullText.substring(0, fullText.indexOf("(") - 1);

    return new CategoryData(key, fullText, locator);
  }

  public static class CategoryData {
    private final String key;
    private final String fullText;
    private final By locator;

    public CategoryData(String key, String fullText, By locator) {
      this.key = key;
      this.fullText = fullText;
      this.locator = locator;
    }

    public String getKey() {
      return key;
    }

    public String getFullText() {
      return fullText;
    }

    public By getLocator() {
      return locator;
    }
  }
}
