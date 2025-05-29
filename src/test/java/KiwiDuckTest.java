import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class KiwiDuckTest extends ConfigTest {

    @BeforeMethod
    public void initConfig() {
        ConfigTest.initializeProfileDirectory();
        ChromeOptions options = ConfigTest.createChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testKiwiDuckSite_Successfully() {

        driver.get("https://kiwiduck.github.io");

        //переходим к странице Button
        driver.findElement(By.linkText("Selenium practice (elements)")).click();
        driver.findElement(By.xpath("//a[@href='button']")).click();

        //На странице “Button” нажать на кнопку “CLICK ME!”
        driver.findElement(By.xpath("//button[text()='Click me!']")).click();

        //проверить, что появились текст «Excellent!» и кнопка “CLICK ME TOO!”
        Assert.assertTrue(driver.findElements(By.xpath("//label[text()='Excellent!']")).size() > 0,
                "текст 'Excellent!' отсутствует");
        Assert.assertTrue(driver.findElements(By.xpath("//input[@value='Click me too!']")).size() > 0,
                "кнопка 'Click me too!' отсутствует");

        //Нажать на кнопку “CLICK ME TOO!”
        driver.findElement(By.xpath("//input[@value='Click me too!']")).click();

        //Проверить, что появилась ссылка с текстом “Great! Return to menu”
        Assert.assertTrue(driver.findElements(By.xpath("//a[text()='Great! Return to menu']")).size() > 0,
                "ссылка 'Great! Return to menu' отсутствует");

        //и нажать на неё
        driver.findElement(By.xpath("//a[text()='Great! Return to menu']")).click();

        //переходим к странице Button
        driver.findElement(By.linkText("Selenium practice (elements)")).click();
        driver.findElement(By.xpath("//a[@href='checkboxes']")).click();

        //На странице “Checkboxes” выбрать один или несколько из представленных чек-боксов
        List<WebElement> webElementList = driver.findElements(By.xpath("//input[@type='checkbox' and (@id != 'two')]"));
        webElementList.forEach(WebElement::click);

        //и нажать на кнопку “GET RESULTS” под ними
        driver.findElement(By.xpath("//button[@id='go' and text()='Get Results']")).click();

        //Проверить, что появился текст, соответствующий атрибуту value из выделенных чек-боксов
        String resultExpected = driver.findElement(By.xpath("//label[@id='result']")).getText();
        StringBuilder resultFact = new StringBuilder();
        for (WebElement we : webElementList) {
            resultFact.append(we.getDomAttribute("value")).append(" ");
        }
        Assert.assertEquals(resultExpected.trim(), resultFact.toString().trim(),
                "строка с результатами не соответствует значениям атрибутов из выделенных чекбоксов");

        //На той же странице выбрать любую радио кнопку
        driver.findElement(By.xpath("//input[@type='radio' and @value='two']")).click();

        //и нажать кнопку “GET RESULTS”, находящуюся под ними
        driver.findElement(By.xpath("//button[@id='radio_go' and text()='Get Results']")).click();

        //Проверить, что появился текст, соответствующий значению атрибута value, выделенной кнопки
        Assert.assertEquals(driver.findElement(By.xpath("//input[@type='radio' and @value='two']")).getDomAttribute("value"),
                driver.findElement(By.xpath("//label[@id='radio_result']")).getText());

        //Проверить, что появилась ссылка с текстом “Great! Return to menu”
        Assert.assertTrue(driver.findElements(By.xpath("//a[text()='Great! Return to menu']")).size() > 0,
                "ссылка с текстом 'Great! Return to menu' не появилась");

        //и нажать на неё
        driver.findElement(By.xpath("//a[text()='Great! Return to menu']")).click();
    }
}
