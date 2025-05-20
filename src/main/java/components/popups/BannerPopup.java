package components.popups;

import common.ACommon;
import jakarta.inject.Inject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

public class BannerPopup extends ACommon implements IPopup {
  protected By bannerPopup = By.xpath("//div[@class='sticky-banner__close js-sticky-banner-close']");

  @Inject
  public BannerPopup(WebDriver driver) {
    super(driver);
    //PageFactory.initElements(driver, this);
  }

  @Override
  public void popupShouldNotBeVisible() {
    waiters.waitElementShouldNotBePresent(bannerPopup);
  }

  @Override
  public void PopupShouldBeVisible() {
    waiters.waitElementShouldBePresent(bannerPopup);
  }

  @Override
  public void clickOnButton() {
    try {
      PopupShouldBeVisible();
      WebElement element = driver.findElement(bannerPopup);
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
    catch (Exception e){
      System.out.println("Popup button not found in time — skipping click");
    }
  }
}
