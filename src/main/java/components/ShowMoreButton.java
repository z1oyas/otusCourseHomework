package components;

import annatations.Component;
import jakarta.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import scope.ScenarScope;

@Component("xpath;//button[contains(text(),'Показать')]")
public class ShowMoreButton extends AComponent {
  private By button = By.xpath("//button[contains(text(),'Показать')]");

  @Inject
  public ShowMoreButton(ScenarScope scope, String baseUrl) {
    super(scope, baseUrl);
  }

  public void clickOnButton() {
    waiters.waitElementShouldBePresent(button);
    driver.findElement(button).click();
  }

  public void buttonShouldBeVisible() {
    waiters.waitElementShouldBePresent(button);
  }
}
