package seleniumTest

import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.util.stream.Stream
import model.MenuTools

class MenuToolsTest {
    private lateinit var menuTools: MenuTools
    private lateinit var driver : WebDriver
    private val url = "https://fastpic.org/"
    companion object {
        private val browser = arrayOf("Chrome")
        @JvmStatic
        fun browserProvider() : Stream<String> {
            return Stream.of(*browser)
        }
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun mainPage(browser: String) {
        browserSetup(browser)
        WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.titleIs("FastPic — Загрузить изображения"))
    }

    private fun browserSetup(browser: String) {
        if (browser == "Chrome") {
            driver = ChromeDriver()
        } else if (browser == "Firefox") {
            driver = FirefoxDriver()
        }
        driver.manage().window().maximize()
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        driver.get(url)

        menuTools = MenuTools(driver)
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `mainLink should open correct page`(browser: String) {
        browserSetup(browser)
        WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.titleIs("FastPic — Загрузить изображения"))
        menuTools.mainLink.click()
        WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.urlToBe("https://fastpic.org/"))
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `ruleLink should open correct page`(browser: String) {
        browserSetup(browser)
        WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.titleIs("FastPic — Загрузить изображения"))
        menuTools.rulesLink.click()
        WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.urlToBe("https://fastpic.org/rules"))
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `faqLink should open correct page`(browser: String) {
        browserSetup(browser)
        WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.titleIs("FastPic — Загрузить изображения"))
        menuTools.faqLink.click()
        WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.urlToBe("https://fastpic.org/about"))
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `uploaderLink should open correct page`(browser: String) {
        browserSetup(browser)
        WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.titleIs("FastPic — Загрузить изображения"))
        menuTools.uploaderLink.click()
        WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.urlToBe("https://fastpic.org/fpuploader"))
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `myLoadingLink should open correct page`(browser: String) {
        browserSetup(browser)
        WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.titleIs("FastPic — Загрузить изображения"))
        menuTools.myLoadingLink.click()
        WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.urlToBe("https://fastpic.org/my.php"))
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `donateLink should open correct page`(browser: String) {
        browserSetup(browser)
        WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.titleIs("FastPic — Загрузить изображения"))
        menuTools.donateLink.click()
        WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.urlToBe("https://fastpic.org/donate"))
    }

}
