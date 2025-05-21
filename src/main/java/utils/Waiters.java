package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Waiters {
  private WebDriver driver;
  private int waitersTimeout;

  public Waiters(WebDriver driver) {
    this.driver = driver;
    try {
      this.waitersTimeout = Integer.parseInt(System.getProperty("timeout", "10"));
    } catch (NumberFormatException e) {
      this.waitersTimeout = 10; // значение по умолчанию
    }
  }

  public boolean waitForCondition(ExpectedCondition condition) {
    try {
      new WebDriverWait(driver, Duration.ofSeconds(waitersTimeout)).until(condition);
      return true;
    } catch (TimeoutException e) {
      return false;
    }
  }

  public boolean justWait() {
    try {
      new WebDriverWait(driver, Duration.ofSeconds(4))
          .until(driver1 -> true);
      return true;
    } catch (TimeoutException e) {
      return false;
    }
  }

  public boolean waitElementShouldBePresent(By locator) {
    return waitForCondition(ExpectedConditions.presenceOfElementLocated(locator));
  }

  public boolean waitElementShouldBeVisible(WebElement element) {
    return waitForCondition(ExpectedConditions.visibilityOf(element));
  }

  public boolean waitElementShouldNotBePresent(By locator) {
    return waitForCondition(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(locator)));
  }

}
