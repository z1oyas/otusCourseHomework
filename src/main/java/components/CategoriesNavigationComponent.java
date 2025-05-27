package components;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import annatations.Component;
import jakarta.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import scope.ScenarScope;

@Component("xpath;//div/div[p[contains(text(),'Направление')]]/following-sibling::div")
public class CategoriesNavigationComponent extends AComponent {
  private By categoryBlock = By.xpath("//div/div[p[contains(text(),'Направление')]]/following-sibling::div");

  private By categoryItemBlock = By.xpath("./div/div/div");

  private By categoryName = By.xpath(".//label");

  @Inject
  public CategoriesNavigationComponent(ScenarScope scope, String baseUrl) {
    super(scope, baseUrl);
    PageFactory.initElements(driver, this);
  }

  public WebElement findActiveCategory() {
    waiters.waitElementShouldBePresent(categoryBlock);

    WebElement myCourse = f(categoryBlock).findElements(categoryItemBlock)
                              .stream()
                              .filter(x -> x.findElement(By.tagName("input")).isSelected())
                              .findFirst()
                              .orElse(null);
    scrollToElement(myCourse);
    if (myCourse != null) {
      return myCourse;
    } else {
      return null;
    }
  }

  public String getCourseCategoryName(WebElement element) {
    String text = element.findElement(categoryName).getText();
    System.out.println(text);
    return text;
  }

  public void assertCourseName(String text, String courseCategory) {
    assertThat(text).isEqualTo(courseCategory);
  }

}
