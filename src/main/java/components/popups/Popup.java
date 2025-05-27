package components.popups;

import common.ACommon;
import jakarta.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import scope.ScenarScope;

public class Popup extends ACommon implements IPopup {

  private By popupButton = By.xpath("//div/div/button/div[contains(text(),'OK')]");

  @Inject
  public Popup(ScenarScope scope) {
    super(scope);
  }

  @Override
  public void popupShouldNotBeVisible() {
    waiters.waitElementShouldNotBePresent(popupButton);
  }

  @Override
  public void popupShouldBeVisible() {
    waiters.waitElementShouldBePresent(popupButton);
  }

  @Override
  public void clickOnButton() {
    try {
      popupShouldBeVisible();
      driver.findElement(popupButton).click();
    } catch (Exception e) {
      System.out.println("Popup button not found in time — skipping click");
    }

  }
}
