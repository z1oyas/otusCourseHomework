package pages;

import annatations.Path;
import common.ACommon;
import components.popups.BannerPopup;
import components.popups.Popup;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.inject.Inject;
import org.openqa.selenium.By;
import scope.ScenarScope;
import java.lang.annotation.Annotation;


public abstract class ABasePage extends ACommon {

  private String baseUrl;
  private By body = By.tagName("body");

  protected Popup popupButton;

  protected BannerPopup bannerPopup;

  private final ScenarScope scope;

  private <T extends Annotation> Annotation getAnnotationInstance(Class<T> annatation, boolean isException) {
    Class clazz = getClass();

    if (clazz.isAnnotationPresent(annatation)) {
      return clazz.getDeclaredAnnotation(annatation);
    }
    if (isException)
      throw new RuntimeException("annotation is absent for " + clazz.getCanonicalName());

    return null;
  }

  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "scope управляется DI, и его изменять извне не предполагается")
  @Inject
  public ABasePage(ScenarScope scope) {
    super(scope);
    this.scope = scope;
    this.baseUrl = scope.getBaseUrl();
  }

  private String getPath() {

    return ((Path) getAnnotationInstance(Path.class, true)).value();
  }

  public void open() {
    driver.get(baseUrl + getPath());
    driver.manage().window().maximize();
    waiters.waitElementShouldBePresent(body);
    popupButton = new Popup(scope);
    bannerPopup = new BannerPopup(scope);

    popupButton.clickOnButton();
    bannerPopup.clickOnButton();
  }
}
