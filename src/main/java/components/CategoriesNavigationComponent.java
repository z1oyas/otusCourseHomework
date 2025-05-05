package components;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import annatations.Component;
import jakarta.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

@Component("xpath;//div/div[p[contains(text(),'Направление')]]/following-sibling::div")
public class CategoriesNavigationComponent extends AComponent {
  //  //
  //  //
  By CategoryBlock = By.xpath("//div/div[p[contains(text(),'Направление')]]/following-sibling::div");

  By CategoryItemBlock = By.xpath("./div/div/div");

  By CategoryName = By.xpath(".//label");

  //By CategoryCheckBox = By.xpath(".//div/input[@type='checkbox']");

  @Inject
  public CategoriesNavigationComponent(WebDriver driver, String baseUrl) {
    super(driver, baseUrl);
    PageFactory.initElements(driver, this);
  }

  public WebElement findActiveCategory() {
    waiters.waitElementShouldBePresent(CategoryBlock);

    WebElement myCourse = f(CategoryBlock).findElements(CategoryItemBlock)
                              .stream()
                              .filter(x -> x.findElement(By.tagName("input")).isSelected())
                              .findFirst()
                              .orElse(null);
    scrollToElement(myCourse);
    if (myCourse != null) {
      return myCourse;
    }
    else {
      return null;
    }
  }

  public String getCourseCategoryName(WebElement element){
   String text =  element.findElement(CategoryName).getText();
    System.out.println(text);
    return text;
  }

  public void  assertCourseName(String text, String CourseCategory) {
    assertThat(text).isEqualTo(CourseCategory);
  }

}
