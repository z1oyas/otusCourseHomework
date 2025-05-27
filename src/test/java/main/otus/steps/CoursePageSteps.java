package main.otus.steps;

import components.CourseCategoriesComponent;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import jakarta.inject.Inject;
import pages.CoursePage;
import pages.MainPage;
import scope.ScenarScope;

public class CoursePageSteps {

  @Inject
  private ScenarScope scope;

  @Тогда("Открыт курс с названием, которое мы ввели")
  public void rightCoursePageOpen() {
    ((CoursePage)scope.getStorageValue("coursePage"))
                                .assertCourseName("Java QA Engineer. Professional");

  }
}
