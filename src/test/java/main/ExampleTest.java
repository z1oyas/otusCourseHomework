package main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import components.*;
import extensions.UIExtension;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.MainCoursePage;
import pages.MainPage;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@ExtendWith(UIExtension.class)
public class ExampleTest {

  @Inject
  public MainCoursePage mainCoursePage;
  @Inject
  public CatalogNavigationComponent catalogComponent;
  @Inject
  public SearchBar searchBar;
  @Inject
  public MainPage mainPage;
  @Inject
  public CourseCategoriesComponent categoryComponent;
  @Inject
  public CategoriesNavigationComponent categoriesNavigationComponent;


  @Test
  @DisplayName("1. Get course by name")
  public void getCourseTest() {
    //Шаг 1: Открыть страницу каталога курсов
    mainCoursePage.open();
    //Шаг 2: Найти курс по имени (имя курса должно передаваться как данные в тесте)
    WebElement myCourse = searchBar
        .searchInCourseBar("Java QA Engineer. Professional")
        .findCourseInCatalog("Java QA Engineer. Professional");
    //Кликнуть по плитке курса и проверить, что открыта страница верного курса
        catalogComponent.clickOnCourse(myCourse)
        .assertCourseName("Java QA Engineer. Professional");
  }

  //todo доделать по дате
  @Test
  @DisplayName("2. Get earliest and latest courses from catalog")
  public void getEarliestAndLatestCourseTest() throws InterruptedException {
    //шаг 1: открыть страницу каталога
    mainCoursePage.open();
    // шаг 2: найти курсы, которые стартуют раньше и позже всех
    Map<String, LocalDate> extremumDateCoursesMap = catalogComponent
        .findCoursesInCatalog()
        .findExtremumDateCourse();
    // шаг 3: проверить, что на карточке самого раннего/позднего курсов отображается верное название курса и дата его начала
    for(Map.Entry<String, LocalDate> entry : extremumDateCoursesMap.entrySet()) {
      WebElement course = catalogComponent.findCourseInCatalog(entry.getKey());
      catalogComponent.assertCourseName(course,entry.getKey());
      //catalogComponent.assertCourseDate(course,entry.getValue());
    }
  }


  @Test
  @DisplayName("3. Get random courseCategory")
  public void getRandomCourseCategory() throws InterruptedException {
    //Шаг 1: Открыть главную страницу
    mainPage.open();
    //Шаг 2: В заголовке страницы открыть меню «Обучение» и выбрать случайную категорию курсов
        categoryComponent.clickOnButton(categoryComponent.getLearningCategoryButton());
    CourseCategoriesComponent.CategoryData category = categoryComponent.getRandomCategory();
    //Шаг 3: Проверить, что открыт каталог курсов верной категории`
    categoryComponent.clickOnCourse(category.getLocator());
    WebElement checkedCategory = categoriesNavigationComponent.findActiveCategory();
    String categoryNameInList = categoriesNavigationComponent.getCourseCategoryName(checkedCategory);
    categoriesNavigationComponent.assertCourseName(categoryNameInList, category.getKey());
  }
}
