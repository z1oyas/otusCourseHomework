package pages;

import annatations.Path;
import components.popups.Popup;
import jakarta.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

@Path("/catalog/courses")
//@Urls({@UrlTemplate(name ="MainPage",value = "/$1/$1")})
public class MainCoursePage extends ABasePage{

  private String placeHolder;
  @FindBy(xpath = "//section/div/h1/div[contains(text(),\"Каталог\")]")
  WebElement courseNameHeader;

  @FindBy(xpath= "//div/input[@type=\"search\"]")
  WebElement searchCourseBar;

  @FindBy(xpath = "//a/h6/div[contains(text(),\"Java QA Engineer. Professional\")]")
  WebElement MyCourseCardName;

  By courseCardInCatalog = By.xpath("//parent::h1/parent::div//following-sibling::div/div[a]/a[@href]/h6/div");

  By CourseCardInCatalogData = By.xpath(".parent::h6/following-sibling::div/div/div");

  By CourseCardName = By.xpath(String.format("//a/h6/div[contains(text(),\"'%s'\")]",placeHolder));

  By CourseNameHeader = By.tagName("h1");

  By button = By.xpath("//button[contains(text(),'Показать')]");

  By popupButton = By.xpath("//div/div/button/div[contains(text(),'OK')]");

  By allCourses = By.xpath("//parent::h1/parent::div//following-sibling::div/div[a]");

  protected Popup popup;
@Inject
  public MainCoursePage(WebDriver driver, String baseUrl) {
    super(driver,baseUrl);
    PageFactory.initElements(driver, this);
  }

  public void searchInCourseBar(String keys) {
     searchCourseBar.sendKeys(keys);
  }

  public void clickOnMyCourseCardName() {
    MyCourseCardName.click();
  }

  public void clickOnAnyCourseCardName(String placeHolder)
  {
    driver.findElement(CourseCardName).click();
  }

  public List<WebElement> getAllCourses(){
    return driver.findElements(courseCardInCatalog);
  }

  public void getCourseCardInCatalogData(WebElement element){
    driver.findElement(courseCardInCatalog).findElement(CourseCardInCatalogData);
  }

  public WebElement getCourse(){
    return driver.findElement(courseCardInCatalog);
  }
   public void clickOnFirstCourseCard(){
    driver.findElement(courseCardInCatalog).click();
    }

    public WebElement findElement(By locator){
    return driver.findElement(locator);

    }
    public void clickOnButton(By button){
      driver.findElement(button).click();
    }
}

