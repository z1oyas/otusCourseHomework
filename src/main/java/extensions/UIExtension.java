package extensions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import components.CatalogNavigationComponent;
import factory.WebDriverFactory;
import modules.GuiceComponentsModule;
import modules.GuicePagesModule;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;

public class UIExtension implements BeforeEachCallback, AfterEachCallback {
  private Injector injector = null;

  @Override
  public void afterEach(ExtensionContext context) {
    WebDriver driver = injector.getInstance(WebDriver.class);
    if (driver != null) driver.quit();
  }

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    WebDriverFactory factory = new WebDriverFactory();
    WebDriver driver = factory.create();
    String baseUrl = factory.getBasePageUrl();
    injector = Guice.createInjector(new GuicePagesModule(driver,baseUrl), new GuiceComponentsModule(driver, baseUrl));
    injector.injectMembers(context.getTestInstance().isPresent() ? context.getTestInstance().get() : null);
  }
}
