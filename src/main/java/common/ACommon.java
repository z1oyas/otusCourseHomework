package common;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import scope.ScenarScope;
import utils.DataParser;
import utils.Waiters;
import java.util.List;
import java.util.function.Predicate;

public abstract class ACommon {

  protected WebDriver driver;

  protected Waiters waiters;

  protected DataParser dataParser;


  public ACommon(ScenarScope scope) {
    this.driver = scope.getDriver();
    this.waiters = new Waiters(scope);
    this.dataParser = new DataParser(driver);
  }

  public WebElement f(By locator) {
    return driver.findElement(locator);
  }

  public List<WebElement> ff(By locator) {

    return driver.findElements(locator);
  }

  protected void clickItemInListByPredicate(By locator, Predicate<WebElement> predicate) {
    ff(locator).stream()
        .filter(predicate)
        .findAny().get()
        .click();
  }

  protected void scrollToElement(WebElement element) {
    new Actions(driver)
        .scrollToElement(element)
        .perform();

    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].style.outline='3px solid yellow';", element
    );
  }
}
