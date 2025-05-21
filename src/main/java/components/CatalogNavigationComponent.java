package components;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import annatations.Component;
import components.popups.BannerPopup;
import components.popups.Popup;
import jakarta.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.CoursePage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component("xpath;//parent::h1/parent::div//following-sibling::div/div[a]//img")
public class CatalogNavigationComponent extends AComponent {

  //protected Popup popupButton;

  protected ShowMoreButton button;

  //protected BannerPopup bannerPopup;

  protected Map<String, LocalDate> courseData;

  @Inject
  public CatalogNavigationComponent(WebDriver driver, String baseUrl) {
    super(driver, baseUrl);
    PageFactory.initElements(driver, this);
//    popupButton = new Popup(driver);
//    bannerPopup = new BannerPopup(driver);
    button = new ShowMoreButton(driver, baseUrl);
  }

  @FindBy(xpath = "//parent::h1/parent::div//following-sibling::div/div[a]")
  private WebElement catalog;

  // было .//a[@href]/h6, надо поменять так, чтобы искалась карточка а не уже заголовок
  private By catalogItemLocator = By.xpath(".//a[@href]");

  private By courseCardFromRoot = By.xpath("//parent::h1/parent::div//following-sibling::div/div[a]/a[@href]/h6/div");

  private By courseCardDataStart = By.xpath(".//following-sibling::div/div/div");

  private WebElement targetCourse;
  private List<WebElement> courseList;

  public CoursePage clickOnCourse(WebElement targetCourse) {
    targetCourse.findElement(By.tagName("h6")).click();
    return new CoursePage(driver, baseUrl);
  }

  public WebElement findCourseInCatalog(String courseName) {
    By courseNameLocator = By.xpath(".//div[contains(text(),'" + courseName + "')]");
    waiters.waitElementShouldBePresent(courseNameLocator);

    WebElement myCourse = catalog.findElements(catalogItemLocator)
                              .stream()
                              .filter(x -> x.findElement(By.tagName("h6")).getText().equals(courseName))
                              .findFirst()
                              .orElse(null);
    scrollToElement(myCourse);
    if (myCourse != null) {
      return myCourse;
    }
    else {
      return null;
    }
  }

  public CatalogNavigationComponent findCoursesInCatalog() {
    Map<String, String> courseDataRaw = getCoursesMap();
    courseData = dataParser.formattingData(courseDataRaw);

    return this;
  }

  public Map<String, LocalDate> findExtremumDateCourse() {
    Map<String, LocalDate> extremumDateCoursesMap = dataParser.LatestDataCourses(courseData);
    extremumDateCoursesMap.putAll(dataParser.EarliestDataCourses(courseData));
    return extremumDateCoursesMap;
  }

  private Map<String, String> getCoursesMap() {
    //прокликать всю страницу курсов
    int catalogSize = 0;
    while (true) {
      try {
        catalogSize = catalog.findElements(catalogItemLocator).size();
        button.clickOnButton();
        waiters.waitForCondition(ExpectedConditions.numberOfElementsToBeMoreThan(courseCardFromRoot, catalogSize));

      } catch (Exception e) {
        System.out.println(catalogSize + " всего курсов в каталоге");
        break;
      }
    }
    Map<String, String> courseData = dataParser.getAllPageHtml();
    return courseData;
  }

  public CoursePage ClickOnEveryCourse(Map.Entry<String, LocalDate> entry) {
      By locator = By.xpath(".//a[@href]/h6/div[contains(text(),'"+entry.getKey()+"')]");
      clickItemInListByPredicate(locator, x -> x.getText().equals(entry.getKey()));
    return new CoursePage(driver, baseUrl);
  }

  public CatalogNavigationComponent assertCourseName(WebElement element, String text) {
    assertThat(element.findElement(By.tagName("h6")).getText()).isEqualTo(text);
    return this;
  }

    public CatalogNavigationComponent assertCourseDate(WebElement element, LocalDate expectedDate) {
    String dataOnCard = element.findElement(By.xpath(".//h6/following-sibling::div/div/div")).getText();
      dataOnCard = dataOnCard.substring(0,dataOnCard.indexOf("·")-1);
      Locale ru = new Locale("ru", "RU");
      DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMMM, yyyy", ru);
      String fullDate = dataOnCard;
      LocalDate dataOnCardForrmated = LocalDate.parse(fullDate, format);
      assertThat(dataOnCardForrmated.getDayOfMonth()).isEqualTo(expectedDate.getDayOfMonth());
      assertThat(dataOnCardForrmated.getMonth()).isEqualTo(expectedDate.getMonth());
      return this;
  }
}

