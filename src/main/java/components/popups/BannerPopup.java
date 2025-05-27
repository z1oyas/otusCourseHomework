package components.popups;

import common.ACommon;
import io.cucumber.guice.ScenarioScoped;
import jakarta.inject.Inject;
import org.openqa.selenium.*;
import scope.ScenarScope;

public class BannerPopup extends ACommon implements IPopup {
  private By bannerPopup = By.xpath("//div[@class='sticky-banner__close js-sticky-banner-close']");

  @Inject
  public BannerPopup(ScenarScope scope) {
    super(scope);
  }

  @Override
  public void popupShouldNotBeVisible() {
    waiters.waitElementShouldNotBePresent(bannerPopup);
  }

  @Override
  public void popupShouldBeVisible() {
    waiters.waitElementShouldBePresent(bannerPopup);
  }

  @Override
  public void clickOnButton() {
    try {
      popupShouldBeVisible();
      WebElement element = driver.findElement(bannerPopup);
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    } catch (Exception e) {
      System.out.println("Popup button not found in time — skipping click");
    }
  }
}
