package exeptions;

public class BrowserNotSupportedExeption extends RuntimeException {
  public BrowserNotSupportedExeption(String browser) {
    super("Browser not supported " + browser);
  }
}
