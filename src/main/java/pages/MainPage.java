package pages;

import annatations.Path;
import jakarta.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Path("")
public class MainPage extends ABasePage{

  By PageTitle = By.tagName("h1");
  ////main//div/a[contains(text(),'Все')]/preceding-sibling::a

  @Inject
  public MainPage(WebDriver driver, String baseUrl) {
    super(driver,baseUrl);
    PageFactory.initElements(driver, this);
  }

}
