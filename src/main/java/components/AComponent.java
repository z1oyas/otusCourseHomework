package components;

import annatations.Component;
import common.ACommon;
import jakarta.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class AComponent extends ACommon {

  private WebElement component;
  protected String baseUrl;

  @Inject
  public AComponent(WebDriver driver, String baseUrl) {
    super(driver);
    this.baseUrl = baseUrl;

    waiters.waitElementShouldBePresent(getComponentEntry());
  }

  public WebElement getComponent() {
    try {
      return driver.findElement(getComponentEntry());
    } catch (Exception ignore) {
      // do nothing
    }
    return null;
  }

  private By getComponentEntry() {
    Component component = getClass().getAnnotation(Component.class);
    if (component != null) {
      String[] locatorMethod = (component.value()).split(";");
      if (locatorMethod[0].equals("css")) {
        return By.cssSelector(locatorMethod[1]);
      } else if (locatorMethod[0].equals("xpath")) {
        return By.xpath(locatorMethod[1]);
      }
    }
    return null;
  }
}
