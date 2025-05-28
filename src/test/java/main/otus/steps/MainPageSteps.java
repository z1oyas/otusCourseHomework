package main.otus.steps;

import components.CourseCategoriesComponent;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.Пусть;
import jakarta.inject.Inject;
import pages.MainPage;
import scope.ScenarScope;

public class MainPageSteps {
  @Inject
  private MainPage mainPage;

  @Inject
  private CourseCategoriesComponent categoryComponent;

  @Inject
  private ScenarScope scope;

  @Пусть("Открыта главная страница")
  public void openMainPage() {
    mainPage.open();
  }
  @Затем("В заголовке страницы открыть меню «Обучение» и выбрать случайную категорию курсов")
  public void clickOnRandonCategoryInCatalog() {
    categoryComponent.clickOnButton(categoryComponent.getLearningCategoryButton());
    CourseCategoriesComponent.CategoryData category = categoryComponent.getRandomCategory();
    categoryComponent.clickOnCourse(category.getLocator());
    scope.setStorageValue("category", category);
  }
}
