package main.otus.steps;

import components.CatalogNavigationComponent;
import components.CategoriesNavigationComponent;
import components.CourseCategoriesComponent;
import components.SearchBar;
import io.cucumber.java.ru.Если;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import jakarta.inject.Inject;
import org.openqa.selenium.WebElement;
import pages.CoursePage;
import pages.MainCoursePage;
import scope.ScenarScope;
import java.time.LocalDate;
import java.util.Map;

public class CoursesPageSteps {

  @Inject
  private MainCoursePage mainCoursePage;
  @Inject
  private SearchBar searchBar;

  @Inject
  private CatalogNavigationComponent catalogComponent;

  @Inject
  private ScenarScope scope;

  @Inject
  private CategoriesNavigationComponent categoriesNavigationComponent;

  @Пусть("Открыта страница каталога курсов")
  public void openCoursesPage() {
    mainCoursePage.open();
  }

  @Если("Набрать в строке поиска имя курса и кликнуть по 1му курсу")
  public void sendKeysToSearchBar() {
    WebElement myCourse = searchBar
                              .searchInCourseBar("Java QA Engineer. Professional")
                              .findCourseInCatalog("Java QA Engineer. Professional");
    CoursePage coursePage = catalogComponent.clickOnCourse(myCourse);
    scope.setStorageValue("coursePage", coursePage);
  }

  @Затем("найти курсы, которые стартуют раньше и позже всех")
  public void findMinMaxCourses() {
    Map<String, LocalDate> extremumDateCoursesMap = catalogComponent
                                                        .findCoursesInCatalog()
                                                        .findExtremumDateCourse();
    scope.setStorageValue("extremumDateCoursesMap", extremumDateCoursesMap);
  }

  @Тогда("Проверить, что на карточке верное название курса и дата его начала")
  public void checkCourseNameAndDate() {
    Map<String, LocalDate> extremumDateCoursesMap = scope.getStorageValue("extremumDateCoursesMap");
    for (Map.Entry<String, LocalDate> entry : extremumDateCoursesMap.entrySet()) {
      WebElement course = catalogComponent.findCourseInCatalog(entry.getKey());
      catalogComponent.assertCourseName(course, entry.getKey());
      catalogComponent.assertCourseDate(course, entry.getValue());
    }
  }
  @Тогда("Проверить, что в списке категорий отмечена верная")
  public void checkCheckBoxCategory() {
    WebElement checkedCategory = categoriesNavigationComponent.findActiveCategory();
    String categoryNameInList = categoriesNavigationComponent.getCourseCategoryName(checkedCategory);
    categoriesNavigationComponent
        .assertCourseName(categoryNameInList, ((CourseCategoriesComponent.CategoryData)(scope.getStorageValue("category"))).getKey());
  }
}
