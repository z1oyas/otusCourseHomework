package factory;

import exeptions.BrowserNotSupportedExeption;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import utils.HighlightListener;

public class WebDriverFactory {
  //  private String browserName = System.getProperty("browser");
  //  private String url = System.getProperty("base.url");

  private String browserName = "chrome";
  private String url = "https://otus.ru";

  public WebDriver create() {
    switch (browserName) {
      case "chrome": {
        WebDriverManager.chromedriver().setup();
        WebDriver rawDriver = new ChromeDriver();
        HighlightListener listener = new HighlightListener(rawDriver);
        return new EventFiringDecorator<>(listener).decorate(rawDriver);
      }
    }
    throw new BrowserNotSupportedExeption(browserName);
  }

  public String getBasePageUrl() {
    if (url == null) {
      throw new IllegalStateException("System property 'base.url' is not set.");
    }
    return url;
  }
}
