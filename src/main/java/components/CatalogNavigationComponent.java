package components;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import annatations.Component;
import jakarta.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.CoursePage;
import scope.ScenarScope;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

@Component("xpath;//parent::h1/parent::div//following-sibling::div/div[a]//img")
public class CatalogNavigationComponent extends AComponent {

  protected ShowMoreButton button;

  protected Map<String, LocalDate> courseData;

  private final ScenarScope scope;

  @Inject
  public CatalogNavigationComponent(ScenarScope scope) {
    super(scope);
    this.scope = scope;
    PageFactory.initElements(scope.getDriver(), this);
    button = new ShowMoreButton(scope);
  }

  @FindBy(xpath = "//parent::h1/parent::div//following-sibling::div/div[a]")
  private WebElement catalog;

  private By catalogItemLocator = By.xpath(".//a[@href]");

  private By courseCardFromRoot = By.xpath("//parent::h1/parent::div//following-sibling::div/div[a]/a[@href]/h6/div");

  private WebElement targetCourse;

  public CoursePage clickOnCourse(WebElement targetCourse) {
    targetCourse.findElement(By.tagName("h6")).click();
    return new CoursePage(scope);
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
    } else {
      return null;
    }
  }

  public CatalogNavigationComponent findCoursesInCatalog() {
    Map<String, String> courseDataRaw = getCoursesMap();
    courseData = dataParser.formattingData(courseDataRaw);

    return this;
  }

  public Map<String, LocalDate> findExtremumDateCourse() {
    Map<String, LocalDate> extremumDateCoursesMap = dataParser.latestDataCourses(courseData);
    extremumDateCoursesMap.putAll(dataParser.earliestDataCourses(courseData));
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

  public CoursePage clickOnEveryCourse(Map.Entry<String, LocalDate> entry) {
    By locator = By.xpath(".//a[@href]/h6/div[contains(text(),'" + entry.getKey() + "')]");
    clickItemInListByPredicate(locator, x -> x.getText().equals(entry.getKey()));
    return new CoursePage(scope);
  }

  public CatalogNavigationComponent assertCourseName(WebElement element, String text) {
    assertThat(element.findElement(By.tagName("h6")).getText()).isEqualTo(text);
    return this;
  }

  public CatalogNavigationComponent assertCourseDate(WebElement element, LocalDate expectedDate) {
    String dataOnCard = element.findElement(By.xpath(".//h6/following-sibling::div/div/div")).getText();
    dataOnCard = dataOnCard.substring(0, dataOnCard.indexOf("·") - 1);
    Locale ru = new Locale("ru", "RU");
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMMM, yyyy", ru);
    String fullDate = dataOnCard;
    LocalDate dataOnCardForrmated = LocalDate.parse(fullDate, format);
    assertThat(dataOnCardForrmated.getDayOfMonth()).isEqualTo(expectedDate.getDayOfMonth());
    assertThat(dataOnCardForrmated.getMonth()).isEqualTo(expectedDate.getMonth());
    return this;
  }
}

