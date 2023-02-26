import org.junit.jupiter.api.BeforeAll;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;




public class TestApplicationСard {

    private WebDriver driver;


    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
        //System.setProperty("webdriver.chrome.driver", "./driver/win/chromedriver.exe");

    }


    @BeforeEach
    void setUp() {
        //driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;

    }

    @Test
    void test(){
       driver.get("http://localhost:9999/");
       driver.findElement(By.cssSelector("input[name ='name']")).sendKeys("Сергей Роликов");
       driver.findElement(By.cssSelector("input[name ='phone']")).sendKeys("+79991112233");
       driver.findElement(By.tagName("label")).click();
       driver.findElement(By.className("button")).click();

       String expected = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
       String actual = driver.findElement(By.className("Success_successBlock__2L3Cw")). getText();


       Assertions.assertEquals(expected, actual);

    }

}
