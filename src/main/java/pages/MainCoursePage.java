package pages;

import annatations.Path;
import components.popups.Popup;
import jakarta.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

@Path("/catalog/courses")
public class MainCoursePage extends ABasePage {

  @Inject
  public MainCoursePage(WebDriver driver, String baseUrl) {
    super(driver, baseUrl);
    PageFactory.initElements(driver, this);
  }

  public WebElement findElement(By locator) {
    return driver.findElement(locator);

  }
}

