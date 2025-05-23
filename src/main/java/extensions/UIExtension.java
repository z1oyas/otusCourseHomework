package extensions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import factory.WebDriverFactory;
import modules.GuiceComponentsModule;
import modules.GuicePagesModule;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;

public class UIExtension implements BeforeEachCallback, AfterEachCallback {
  private static final ThreadLocal<Injector> INJECTOR_THREAD_LOCAL = new ThreadLocal<>();

  @Override
  public void afterEach(ExtensionContext context) {
    Injector injector = INJECTOR_THREAD_LOCAL.get();
    if (injector != null) {
      WebDriver driver = injector.getInstance(WebDriver.class);
      if (driver != null) driver.quit();
    }
    INJECTOR_THREAD_LOCAL.remove();
  }

  @Override
  public void beforeEach(ExtensionContext context) {
    WebDriverFactory factory = new WebDriverFactory();
    WebDriver driver = factory.create();
    String baseUrl = factory.getBasePageUrl();
    Injector injector = Guice.createInjector(
        new GuicePagesModule(driver, baseUrl),
        new GuiceComponentsModule(driver, baseUrl)
    );
    INJECTOR_THREAD_LOCAL.set(injector);
    injector.injectMembers(context.getTestInstance().isPresent() ? context.getTestInstance().get() : null);
  }
}
