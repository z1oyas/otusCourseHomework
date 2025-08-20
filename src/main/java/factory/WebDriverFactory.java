package factory;

import exeptions.BrowserNotSupportedExeption;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import utils.HighlightListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WebDriverFactory {
  //  <systemPropertyVariables>
  //                        <browser>chrome</browser>
  //                        <base.url>https://otus.ru</base.url>
  //                        <remote>false</remote>
  //                        <selenoid.url>http://localhost/wd/hub</selenoid.url>
  //                        <browser.version>127.0</browser.version>
  //                        <enable.video>true</enable.video>
  //                        <enable.vnc>true</enable.vnc>
  //                    </systemPropertyVariables>
  private final String browserName = System.getProperty("browser", "chrome");
  private final String url = System.getProperty("base.url", "https://otus.ru");
  private final String remote = System.getProperty("remote", "false");
  private final String browserVersion = System.getProperty("browser.version", "127.0");
  private final boolean videoEnable = Boolean.parseBoolean(System.getProperty("enable.video", "false"));
  private final boolean enableVNC = Boolean.parseBoolean(System.getProperty("enable.vnc", "true"));
  private final String selenoidUrl = System.getProperty("selenoid.url", "http://localhost/wd/hub");
  private final URL urlSelenoid;

  public WebDriverFactory() {
    URL tempUrl = null;
    try {
      tempUrl = new URL(selenoidUrl);
    } catch (MalformedURLException e) {
      System.err.println("Invalid Selenoid URL: " + selenoidUrl);
    }
    this.urlSelenoid = tempUrl;
  }


  public WebDriver create() {
    switch (remote) {
      case "true": {
        Capabilities capabilities;
        switch (browserName) {
          case "chrome": {
            ChromeOptions options = new ChromeOptions();
            options.setCapability("timeouts", Map.of("implicit", 5000, "pageLoad", 180000, "script", 30000));
            options.setCapability("browserVersion", browserVersion);
            options.setCapability("selenoid:options", new HashMap<String, Object>() {{
                put("name", "Test badge...");
                put("sessionTimeout", "15m");
                put("env", new ArrayList<String>() {{
                    add("TZ=UTC");
                  }});
                put("labels", new HashMap<String, Object>() {{
                    put("manual", "true");
                  }});
                put("enableVideo", videoEnable);
                put("enableVNC", enableVNC);
              }});
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.merge(options);
            capabilities = caps;
            break;
          }
          case "firefox": {
            FirefoxOptions options = new FirefoxOptions();
            options.setCapability("timeouts", Map.of("implicit", 5000, "pageLoad", 180000, "script", 30000));
            options.setCapability("browserVersion", browserVersion);
            options.setCapability("selenoid:options", new HashMap<String, Object>() {{
                put("name", "Test badge...");
                put("sessionTimeout", "15m");
                put("env", new ArrayList<String>() {{
                    add("TZ=UTC");
                  }});
                put("labels", new HashMap<String, Object>() {{
                    put("manual", "true");
                  }});
                put("enableVideo", videoEnable);
                put("enableVNC", enableVNC);
              }});
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.merge(options);
            capabilities = caps;
            break;
          }
          default:
            throw new BrowserNotSupportedExeption(browserName);
        }

        RemoteWebDriver driver = new RemoteWebDriver(urlSelenoid, capabilities);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60)); // таймаут перехода по ссылке
        HighlightListener listener = new HighlightListener(driver);
        System.out.println("Session started: " + driver.getSessionId());
        return new EventFiringDecorator<>(listener).decorate(driver);
      }

      case "false": {
        WebDriver rawDriver;
        switch (browserName) {
          case "chrome": {
            WebDriverManager.chromedriver().setup();
            rawDriver = new ChromeDriver();
            break;
          }
          case "firefox": {
            WebDriverManager.firefoxdriver().setup();
            rawDriver = new FirefoxDriver();
            break;
          }
          default:
            throw new BrowserNotSupportedExeption(browserName);
        }
        HighlightListener listener = new HighlightListener(rawDriver);
        return new EventFiringDecorator<>(listener).decorate(rawDriver);
      }

      default:
        throw new RuntimeException("remote must be 'true' or 'false'");
    }
  }

  public String getBasePageUrl() {
    if (url == null) {
      throw new IllegalStateException("System property 'base.url' is not set.");
    }
    return url;
  }
}
