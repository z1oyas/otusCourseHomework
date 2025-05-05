package components;

import annatations.Component;
import components.popups.BannerPopup;
import components.popups.Popup;
import jakarta.inject.Inject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.MainCoursePage;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component("xpath;//section/button")
public class CourseCategoriesComponent extends AComponent {


  By buttonMenu = By.xpath("//section/button");

  By LearningCategoryButton = By.xpath("//div/button[contains(text(),'Обучение')]");

  By LearningCategoryChooseButton = By.xpath("//div/div[p[contains(text(),'Все курсы')]]/div/a");


  @Inject
  public CourseCategoriesComponent(WebDriver driver, String baseUrl) {
    super(driver, baseUrl);
    PageFactory.initElements(driver, this);
  }

  public By getButtonMenu() {
    return buttonMenu;
  }
public By getLearningCategoryButton() {
    return LearningCategoryButton;
}
public By getLearningCategoryChooseButton() {
    return LearningCategoryChooseButton;
}


  public CourseCategoriesComponent clickOnButton(By button) {
    waiters.waitForCondition(ExpectedConditions.elementToBeClickable(button));
    //waiters.waitElementShouldBeVisible(f(button));
    //waiters.waitElementShouldBePresent(By.tagName("h1"));
    WebElement menuButton = driver.findElement(button);
    System.out.println("Visible? " + menuButton.isDisplayed());
    System.out.println("Enabled? " + menuButton.isEnabled());
    System.out.println(menuButton.getRect());
    System.out.println(((JavascriptExecutor) driver).executeScript(
        "return document.elementFromPoint(arguments[0], arguments[1]);",
        menuButton.getLocation().getX(), menuButton.getLocation().getY()
    ));
    Point location = menuButton.getLocation();
    JavascriptExecutor js = (JavascriptExecutor) driver;

// Получаем смещение прокрутки
    long scrollY = (Long) js.executeScript("return window.pageYOffset;");
    long scrollX = (Long) js.executeScript("return window.pageXOffset;");

    Object elementAtPoint = js.executeScript(
        "return document.elementFromPoint(arguments[0], arguments[1]);",
        location.getX() - scrollX,
        location.getY() - scrollY
    );

    System.out.println("Button: " + menuButton);
    System.out.println("Element at point: " + elementAtPoint);

    String buttonHtml = (String) js.executeScript("return arguments[0].outerHTML;", menuButton);
    String elementHtml = (String) js.executeScript("return arguments[0].outerHTML;", elementAtPoint);

    System.out.println("Same HTML? " + buttonHtml.equals(elementHtml));


    menuButton.click();
    //((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(button));
    return this;
  }
  public MainCoursePage clickOnCourse(By courseLocator) {
    WebElement category = driver.findElement(courseLocator);
    category.getText();
    waiters.waitElementShouldBeVisible(category);
    category.click();
    return new MainCoursePage(driver, baseUrl);
  }

  public List<Object> getRandomCategory() {
    List<WebElement> categories = ff(LearningCategoryChooseButton);
    int randomIndex = (int) (Math.random() * categories.size());
    By value = By.xpath(LearningCategoryChooseButton +"["+randomIndex + 1+"]");
    String keyName = f(value).getText();
    String key = keyName.substring(0,keyName.indexOf("(")-1);
    return List.of(key,keyName);

  }
}
