package main.otus.steps.hooks;

import io.cucumber.java.After;
import jakarta.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import scope.ScenarScope;

public class Hooks {

  @Inject
  private ScenarScope scope;

  @After
  public void  after(){
    WebDriver driver = scope.getDriver();
    if (driver!=null) driver.close();
  }
}
