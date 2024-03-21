package org.example.seleniumdemo

import org.junit.jupiter.api.*
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class MainPageTest {
    private val mainPage = MainPage()
    private lateinit var driver : WebDriver
    private val url = "https://fastpic.org/"

    @BeforeEach
    fun setUp() {
        driver = ChromeDriver()
        driver.manage().window().maximize()
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        driver.get(url)
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }

    @Test
    fun mainPage() {
        WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.titleIs("FastPic — Загрузить изображения"))
    }

//    @Test
//    fun search() {
//        mainPage.searchButton.click()
//
//        element("[data-test='search-input']").sendKeys("Selenium")
//        element("button[data-test='full-search-button']").click()
//
//        element("input[data-test='search-input']").shouldHave(attribute("value", "Selenium"))
//    }
//
//    @Test
//    fun toolsMenu() {
//        mainPage.toolsMenu.click()
//
//        element("div[data-test='main-submenu']").shouldBe(visible)
//    }
//
//    @Test
//    fun navigationToAllTools() {
//        mainPage.seeDeveloperToolsButton.click()
//        mainPage.findYourToolsButton.click()
//
//        element("#products-page").shouldBe(visible)
//
//        assertEquals("All Developer Tools and Products by JetBrains", Selenide.title())
//    }
}
