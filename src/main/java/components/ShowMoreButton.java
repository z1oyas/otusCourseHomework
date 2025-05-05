package components;

import annatations.Component;
import jakarta.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Component("xpath;//button[contains(text(),'Показать')]")
public class ShowMoreButton extends AComponent {
  By button = By.xpath("//button[contains(text(),'Показать')]");

  @Inject
  public ShowMoreButton(WebDriver driver, String baseUrl) {
    super(driver, baseUrl);
  }

  public void clickOnButton() {
    waiters.waitElementShouldBePresent(button);
    driver.findElement(button).click();
  }

  public void buttonShouldBeVisible() {
    waiters.waitElementShouldBePresent(button);
  }
}
