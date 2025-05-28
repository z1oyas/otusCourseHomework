package pages;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import annatations.Path;
import jakarta.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import scope.ScenarScope;

@Path("/lessons/")
public class CoursePage extends ABasePage {

  private By courseNameHeader = By.tagName("h1");

  @Inject
  public CoursePage(ScenarScope scope) {
    super(scope);
    PageFactory.initElements(driver, this);
  }

  private String getCourseName() {
    waiters.waitElementShouldBePresent(courseNameHeader);
    return driver.findElement(courseNameHeader).getText();
  }

  public CoursePage assertCourseName(String text) {
    assertThat(getCourseName()).isEqualTo(text);
    return this;
  }

}
