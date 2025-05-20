package utils;

import jakarta.inject.Inject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;


public class HighlightListener implements WebDriverListener {

  WebDriver driver;
  @Inject
  public HighlightListener(WebDriver driver) {
    this.driver = driver;
  }
  @Override
  public void beforeClick(WebElement element) {
    highlightElement(element);
  }

  @Override
  public void afterClick(WebElement element) {
    turnOffElement(element);
  }

  private void highlightElement(WebElement element) {
    try {
      ((JavascriptExecutor) driver)
          .executeScript("arguments[0].style.border='3px solid red'", element);
    }
    catch (Exception ignore) {
    }
  }

  private void turnOffElement(WebElement element) {
    try {
      ((JavascriptExecutor) driver)
          .executeScript("arguments[0].style.border=''", element);
    }
    catch (Exception ignore) {
    }
  }
}
