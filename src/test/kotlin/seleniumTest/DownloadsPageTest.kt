package seleniumTest

import model.DownloadsPage
import model.ImagePage
import model.MainPage
import model.MenuTools
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import java.time.Duration
import java.util.stream.Stream

class DownloadsPageTest {
    private lateinit var downloadsPage: DownloadsPage
    private lateinit var driver : WebDriver
    private lateinit var menuTools: MenuTools
    private lateinit var mainPage: MainPage
    private val url = "https://fastpic.org"
    companion object {
        private val browser = arrayOf("Chrome", "Firefox")
        @JvmStatic
        fun browserProvider() : Stream<String> {
            return Stream.of(*browser)
        }
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

        downloadsPage = DownloadsPage(driver)
        menuTools = MenuTools(driver)
        mainPage = MainPage(driver)
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `select all link should select all images`(browser: String) {
        browserSetup(browser)
        uploadTestImages()
        menuTools.myLoadingLink.click()
        downloadsPage.selectAllLink.click()
        val selectedImages = driver.findElements(By.xpath("//input[@type='checkbox']"))
        var countSelectedImages = countSelectedImages(selectedImages)
        assert(countSelectedImages == downloadsPage.images.size)
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `deselect all link should deselect all images`(browser: String) {
        browserSetup(browser)
        uploadTestImages()
        menuTools.myLoadingLink.click()
        downloadsPage.selectAllLink.click()
        downloadsPage.deselectAllLink.click()
        val selectedImages = driver.findElements(By.xpath("//input[@type='checkbox']"))
        var countSelectedImages = countSelectedImages(selectedImages)
        assert(countSelectedImages == 0)
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `delete one image from downloads page`(browser: String) {
        browserSetup(browser)
        uploadTestImages()
        menuTools.myLoadingLink.click()
        val initialImage = downloadsPage.images.size
        downloadsPage.checkboxes[1].click()
        downloadsPage.deleteSelectedLink.click()
        assert(initialImage == downloadsPage.images.size + 1)
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `delete all images from downloads page`(browser: String) {
        browserSetup(browser)
        uploadTestImages()
        menuTools.myLoadingLink.click()
        downloadsPage.selectAllLink.click()
        downloadsPage.deleteSelectedLink.click()
        assert(downloadsPage.images.size == 0)
    }


    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `delete two images from downloads page`(browser: String) {
        browserSetup(browser)
        uploadTestImages()
        menuTools.myLoadingLink.click()
        downloadsPage.checkboxes[0].click()
        downloadsPage.checkboxes[2].click()
        downloadsPage.deleteSelectedLink.click()
        assert(downloadsPage.images.size == 1)
    }
    private fun countSelectedImages(selectedImages: MutableList<WebElement>) : Int {
        var countSelectedImages = 0
        for (checkbox in selectedImages) {
            if (checkbox.isSelected) countSelectedImages += 1
        }
        return countSelectedImages
    }

    private fun uploadTestImages() {
        val firstImageLink = "https://hkstrategies.ca/wp-content/uploads/2017/04/taming-the-troll-stay-cool-feature.jpg"
        val secondImageLink = "https://uprostim.com/wp-content/uploads/2021/05/image072-4.jpg"
        val thirdImageLink = "https://kartinkof.club/uploads/posts/2022-04/1649633828_3-kartinkof-club-p-ugarnie-kartinki-na-avu-s-kotami-3.jpg"
        mainPage.fromLinkLink.click()
        val links = arrayOf(firstImageLink, secondImageLink, thirdImageLink)
        mainPage.linksInput.sendKeys(links.joinToString("\n"))
        mainPage.submitButton.click()
    }

}