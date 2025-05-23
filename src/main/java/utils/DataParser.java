package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class DataParser {

  protected WebDriver driver;

  public DataParser(WebDriver driver) {
    this.driver = driver;
  }

  public Map<String, LocalDate> formattingData(Map<String, String> courseData) {
    Locale ru = new Locale("ru", "RU");
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMMM, yyyy", ru);
    Map<String, LocalDate> formattedCourseData = courseData.entrySet().stream().map(x -> {
      try {
        String val = x.getValue();
        val = val.substring(0, val.indexOf("·") - 1);
        LocalDate date = LocalDate.parse(val, format);
        return Map.entry(x.getKey(), date);
      } catch (Exception ignore) {
        return null;
      }
    }).filter(Objects::nonNull)
                                                     .collect(Collectors.toMap(
                                                         Map.Entry::getKey,
                                                         Map.Entry::getValue
                                                     ));
    return formattedCourseData;
  }

  public Map<String, String> getAllPageHtml() {
    String html = driver.getPageSource();
    Document doc = Jsoup.parse(html);

    // поиск ссылок на курсы
    Elements allCoursesNode = doc.select("div>section>div>div:has(a)>a[href]");

    Map<String, String> courseData = allCoursesNode.stream()
                                         .filter(x -> x.select("a > div:last-child > div > div").text().contains("месяц"))
                                         .collect(Collectors.toMap(
                                             (x -> x.select("a > h6 > div").text()),
                                             (x -> x.select("a > div:last-child > div > div").text())));
    return courseData;
  }


  public Map<String, LocalDate> latestDataCourses(Map<String, LocalDate> formattedCourseData) {
    Optional<LocalDate> maxDateOrdered = formattedCourseData.values().stream()
                                             .max(Comparator.naturalOrder());

    Map<String, LocalDate> latestDataCoursesMap = findAllEquals(maxDateOrdered, formattedCourseData);
    latestDataCoursesMap.forEach((key, value) -> System.out.println("Начинается позже всех курсов: " + key + ", дата начала: " + value));

    return latestDataCoursesMap;
  }

  public Map<String, LocalDate> earliestDataCourses(Map<String, LocalDate> formattedCourseData) {
    Optional<LocalDate> minDateOrdered = formattedCourseData.values().stream()
                                             .min(Comparator.naturalOrder());
    Map<String, LocalDate> earliestDataCoursesMap = findAllEquals(minDateOrdered, formattedCourseData);
    earliestDataCoursesMap.forEach((key, value) -> System.out.println("Начинается раньше всех курсов: " + key + ", дата начала: " + value));
    return earliestDataCoursesMap;
  }

  private Map<String, LocalDate> findAllEquals(Optional<LocalDate> dateEqual, Map<String, LocalDate> map) {
    return dateEqual.map(date ->
                             map.entrySet().stream()
                                 .filter(x -> x.getValue().equals(date))
                                 .collect(Collectors.toMap(
                                     Map.Entry::getKey,
                                     Map.Entry::getValue
                                 ))
    ).orElse(Collections.emptyMap());

  }

}
