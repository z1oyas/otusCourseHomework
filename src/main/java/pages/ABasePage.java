package pages;

import annatations.Path;
import common.ACommon;
import components.popups.BannerPopup;
import components.popups.Popup;
import jakarta.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.lang.annotation.Annotation;


public abstract class ABasePage extends ACommon {

  private String baseUrl;
  public By body = By.tagName("body");

  protected Popup popupButton;

  protected BannerPopup bannerPopup;

  private <T extends Annotation> Annotation getAnnotationInstance(Class<T> annatation, boolean isException) {
    Class clazz = getClass();

    if (clazz.isAnnotationPresent(annatation)) {
      return clazz.getDeclaredAnnotation(annatation);
    }
    if (isException)
      throw new RuntimeException("annotation is absent for " + clazz.getCanonicalName());

    return null;
  }

  @Inject
  public ABasePage(WebDriver driver, String baseUrl) {
    super(driver);
    this.baseUrl = baseUrl;
  }

  private String getPath() {

    return ((Path) getAnnotationInstance(Path.class, true)).value();
  }

  public void open() {
    driver.get(baseUrl + getPath());
    driver.manage().window().maximize();
    waiters.waitElementShouldBePresent(body);
    popupButton = new Popup(driver);
    bannerPopup = new BannerPopup(driver);

    popupButton.clickOnButton();
    bannerPopup.clickOnButton();
  }
}
