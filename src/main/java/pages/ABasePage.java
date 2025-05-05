package pages;

import annatations.Path;
import annatations.UrlTemplate;
import com.google.inject.name.Named;
import common.ACommon;
import components.ShowMoreButton;
import components.popups.BannerPopup;
import components.popups.Popup;
import jakarta.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedArrayType;


public abstract class ABasePage extends ACommon {

  private String baseUrl;
  public By body = By.tagName("body");

  protected Popup popupButton;

  protected BannerPopup bannerPopup;

  private <T extends Annotation> Annotation getAnnotationInstance(Class<T> annatation, boolean isException) {
    Class clazz = getClass();

    if (clazz.isAnnotationPresent(annatation)) {
      return clazz.getDeclaredAnnotation(annatation);
    }
    if(isException)
      throw new RuntimeException("annotation is absent for " + clazz.getCanonicalName());

    return null;
  }

@Inject
  public ABasePage(WebDriver driver, String baseUrl) {
    super(driver);
    this.baseUrl = baseUrl;
  }
  private String getPath() {

    return ((Path) getAnnotationInstance(Path.class,true)).value();
  }

  private String getPathTemplate() {
    UrlTemplate urlTemplate = (UrlTemplate) getAnnotationInstance(UrlTemplate.class,false);
    if(urlTemplate != null){
      return urlTemplate.value();
    }
    return "";
  }

  public void open(){
    driver.get(baseUrl + getPath());
    waiters.waitElementShouldBePresent(body);

    popupButton = new Popup(driver);
    bannerPopup = new BannerPopup(driver);

    popupButton.clickOnButton();
    bannerPopup.clickOnButton();
  }

  //todo передавать данные из теста по шаблону, выберет имя и дальше распаситю Убрать имена, у нас нет их
//  public void open(String nameTemplate,String... data){
//    String pathTemplate = getPathTemplate();
//    if (pathTemplate.isEmpty()){
//      throw new RuntimeException("pathTemplate is empty");
//    }
//
//    for(int i = 0; i < data.length; i++){
//      pathTemplate = pathTemplate.replace("$" +(i+1),data[i]);
//    }
//
//    driver.get(baseUrl + pathTemplate);
//  }
}
