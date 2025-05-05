package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import components.CatalogNavigationComponent;
import org.openqa.selenium.WebDriver;

public class GuiceComponentsModule extends AbstractModule {
  public WebDriver driver;
  public String url;

  public GuiceComponentsModule(WebDriver driver,String url) {
    this.driver = driver;
    this.url =url;
  }


  @Provides
  @Singleton
  public CatalogNavigationComponent getCatalogNavigationComponent(){
    return new CatalogNavigationComponent(driver, url);
  }
}
