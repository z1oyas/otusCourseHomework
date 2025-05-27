package pages;

import annatations.Path;
import jakarta.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import scope.ScenarScope;

@Path("/catalog/courses")
public class MainCoursePage extends ABasePage {

  @Inject
  public MainCoursePage(ScenarScope scope, String baseUrl) {
    super(scope, baseUrl);
    PageFactory.initElements(driver, this);
  }

  public WebElement findElement(By locator) {
    return driver.findElement(locator);

  }
}

