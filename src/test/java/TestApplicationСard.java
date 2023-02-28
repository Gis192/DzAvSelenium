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

    }


    @BeforeEach
    void setUp() {
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
    void positiveScenario() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[name ='name']")).sendKeys("Сергей Роликов");
        driver.findElement(By.cssSelector("input[name ='phone']")).sendKeys("+79991112233");
        driver.findElement(By.tagName("label")).click();
        driver.findElement(By.className("button")).click();

        String expected = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();


        Assertions.assertEquals(expected, actual);

    }

    @Test
    void invalidName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[name ='name']")).sendKeys("Sergei Rolikov");
        driver.findElement(By.cssSelector("input[name ='phone']")).sendKeys("+79991112233");
        driver.findElement(By.tagName("label")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void invaliPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[name ='name']")).sendKeys("Сергей Роликов");
        driver.findElement(By.cssSelector("input[name ='phone']")).sendKeys("+799911122");
        driver.findElement(By.tagName("label")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void notClicklabel() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("input[name ='name']")).sendKeys("Сергей Роликов");
        driver.findElement(By.cssSelector("input[name ='phone']")).sendKeys("+79991112233");
        driver.findElement(By.className("button")).click();


        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.className("checkbox__text")).getText();

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void emptyForm() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.className("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText();

        Assertions.assertEquals(expected, actual);
    }
}
