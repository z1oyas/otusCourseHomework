package scope;

import factory.WebDriverFactory;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebDriver;
import java.util.HashMap;

@ScenarioScoped
public class ScenarScope {

  private WebDriver driver = new WebDriverFactory().create();

  public WebDriver getDriver() {
    return this.driver;
  }

  private HashMap<String, Object> storage = new HashMap<>();

  public <T> void setStorageValue(String key, T value) {
    storage.put(key, value);
  }

  public <T> T getStorageValue(String name) {
    return (T) storage.get(name);
  }
}
