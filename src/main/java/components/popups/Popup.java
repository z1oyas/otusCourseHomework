package components.popups;

import common.ACommon;
import jakarta.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Popup extends ACommon implements IPopup {

  private By popupButton = By.xpath("//div/div/button/div[contains(text(),'OK')]");

  @Inject
  public Popup(WebDriver driver) {
    super(driver);
    //PageFactory.initElements(driver, this);
  }

  @Override
  public void popupShouldNotBeVisible() {
    waiters.waitElementShouldNotBePresent(popupButton);
  }

  @Override
  public void PopupShouldBeVisible() {
    waiters.waitElementShouldBePresent(popupButton);
  }

  @Override
  public void clickOnButton() {
    try {
      PopupShouldBeVisible();
      driver.findElement(popupButton).click();
    }
    catch (Exception e){
      System.out.println("Popup button not found in time — skipping click");
    }

  }
}
