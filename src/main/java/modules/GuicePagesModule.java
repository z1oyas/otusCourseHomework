package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.openqa.selenium.WebDriver;

public class GuicePagesModule extends AbstractModule {
  public WebDriver driver;
  public String url;

  public GuicePagesModule(WebDriver driver, String url) {
    this.driver = driver;
    this.url = url;
  }

  @Provides
  public WebDriver getDriver() {
    return driver;
  }

  //  @Provides
  //  @Singleton
  //  public MainPage getMainPage(){
  //    return new MainPage(driver);
  //  }

  @Provides
  public String provideMainPageUrl() {
    return url;
  }

}
