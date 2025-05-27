package main.otus.steps;

import components.CatalogNavigationComponent;
import components.SearchBar;
import io.cucumber.java.ru.Если;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import jakarta.inject.Inject;
import org.openqa.selenium.WebElement;
import pages.MainCoursePage;
import scope.ScenarScope;

public class CoursesPageSteps {

  @Inject
  private MainCoursePage mainCoursePage;
  @Inject
  private SearchBar searchBar;

  @Inject
  private CatalogNavigationComponent catalogComponent;

  @Inject
  private ScenarScope scope;

  @Пусть("Открыта страница каталога курсов")
  public void openCoursesPage() {
    mainCoursePage.open();
  }

  @Если("Набрать в строке поиска имя курса и кликнуть по 1му курсу")
  public void sendKeysToSearchBar() {
    WebElement myCourse = searchBar
                              .searchInCourseBar("Java QA Engineer. Professional")
                              .findCourseInCatalog("Java QA Engineer. Professional");

    scope.setStorageValue("CourseElement", myCourse);
  }

  @Тогда("Страница выбранного курса успешно открыта")
  public void rightCoursePageOpen(){

    catalogComponent.clickOnCourse(scope.getStorageValue("CourseElement"))
        .assertCourseName("Java QA Engineer. Professional");

  }
}
