package pages;

import annatations.Path;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import jakarta.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Path("/lessons/")
public class CoursePage extends ABasePage{

  By CourseNameHeader = By.tagName("h1");

  @Inject
  public CoursePage(WebDriver driver,String baseUrl) {
    super(driver,baseUrl);
    PageFactory.initElements(driver, this);
  }

  private String getCourseName(){
    waiters.waitElementShouldBePresent(CourseNameHeader);
    return driver.findElement(CourseNameHeader).getText();
  }

  public CoursePage assertCourseName(String text){
    assertThat(getCourseName()).isEqualTo(text);
    return this;
  }

}
