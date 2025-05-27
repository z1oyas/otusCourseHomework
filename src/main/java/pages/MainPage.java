package pages;

import annatations.Path;
import jakarta.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import scope.ScenarScope;

@Path("")
public class MainPage extends ABasePage {


  @Inject
  public MainPage(ScenarScope scope) {
    super(scope);
    PageFactory.initElements(driver, this);
  }

}
