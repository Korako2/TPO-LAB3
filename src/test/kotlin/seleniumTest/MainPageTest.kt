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
import org.openqa.selenium.support.FindBy
import java.io.File
import java.time.Duration
import java.util.stream.Stream

class MainPageTest {
    private lateinit var mainPage: MainPage
    private lateinit var menuTools: MenuTools
    private lateinit var downloadsPage: DownloadsPage
    private lateinit var imagePage: ImagePage
    private lateinit var driver : WebDriver
    private val url = "https://fastpic.org/"
    companion object {
        private val browser = arrayOf("Chrome")
        @JvmStatic
        fun browserProvider() : Stream<String> {
            return Stream.of(*browser)
        }
    }

//    @AfterEach
//    fun tearDown() {
//        driver.quit()
//    }

    private fun browserSetup(browser: String) {
        if (browser == "Chrome") {
            driver = ChromeDriver()
        } else if (browser == "Firefox") {
            driver = FirefoxDriver()
        }
        driver.manage().window().maximize()
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        driver.get(url)

        mainPage = MainPage(driver)
        menuTools = MenuTools(driver)
        downloadsPage = DownloadsPage(driver)
        imagePage = ImagePage(driver)
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `add image from a computer`(browser: String) {
        browserSetup(browser)
        mainPage.fromComputerLink.click()
        val image = "images/cat.jpg"
        val file = File(image)
        mainPage.fileInput.sendKeys(file.absolutePath)
        mainPage.submitButton.click()
        assert(mainPage.firstImageName.text == "cat.jpg")
        menuTools.myLoadingLink.click()
        assert(downloadsPage.images.size == 1)
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `add three images from a computer`(browser: String) {
        browserSetup(browser)
        mainPage.fromComputerLink.click()
        mainPage.addFieldLink.click()
        mainPage.addFieldLink.click()
        val images = arrayOf("images/cat.jpg", "images/cat2.jpg", "images/cat3.jpg")
        var file = File(images[0])
        mainPage.fileInput.sendKeys(file.absolutePath)
        file = File(images[1])
        mainPage.file2Input.sendKeys(file.absolutePath)
        file = File(images[2])
        mainPage.file3Input.sendKeys(file.absolutePath)
        mainPage.submitButton.click()

        assert(mainPage.firstImageName.text == "cat.jpg")
        assert(mainPage.secondImageName.text == "cat2.jpg")
        assert(mainPage.thirdImageName.text == "cat3.jpg")

        menuTools.myLoadingLink.click()
        assert(downloadsPage.images.size == 3)
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `add image from a link`(browser: String) {
        browserSetup(browser)

        mainPage.fromLinkLink.click()
        val link = "https://hkstrategies.ca/wp-content/uploads/2017/04/taming-the-troll-stay-cool-feature.jpg"
        mainPage.linksInput.sendKeys(link)
        mainPage.submitButton.click()
        assert(mainPage.firstImageName.text == "https://hkstrategies.ca/wp-content/uploads/2017/04/taming-the-troll-stay-cool-feature.jpg")

        menuTools.myLoadingLink.click()
        assert(downloadsPage.images.size == 1)
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `add three images from a link`(browser: String) {
        browserSetup(browser)

        val firstImageLink = "https://hkstrategies.ca/wp-content/uploads/2017/04/taming-the-troll-stay-cool-feature.jpg"
        val secondImageLink = "https://uprostim.com/wp-content/uploads/2021/05/image072-4.jpg"
        val thirdImageLink = "https://kartinkof.club/uploads/posts/2022-04/1649633828_3-kartinkof-club-p-ugarnie-kartinki-na-avu-s-kotami-3.jpg"
        mainPage.fromLinkLink.click()
        mainPage.addFieldLink.click()
        mainPage.addFieldLink.click()
        val links = arrayOf(firstImageLink, secondImageLink, thirdImageLink)
        mainPage.linksInput.sendKeys(links.joinToString("\n"))
        mainPage.submitButton.click()

        val imageName = "https://kartinkof.club/uploads/posts/2022-04/1649633828_3-kartinkof-club-p-ugarnie-kartinki-na-avu-s-kotami-3.jpg"
        assert(mainPage.firstImageName.text == imageName)
        assert(mainPage.secondImageName.text == imageName)
        assert(mainPage.thirdImageName.text == imageName)

        menuTools.myLoadingLink.click()
        assert(downloadsPage.images.size == 3)
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `add image from a computer with resize`(browser: String) {
        browserSetup(browser)
        mainPage.fromComputerLink.click()
        val image = "images/cat.jpg"
        val file = File(image)
        mainPage.fileInput.sendKeys(file.absolutePath)
        mainPage.resizeCheckbox.click()
        mainPage.resizeSelect.findElement(By.xpath("//option[text()='500 (стандарт)']")).click()
        mainPage.submitButton.click()
        assert(mainPage.firstImageName.text == "cat.jpg")

        menuTools.myLoadingLink.click()
        downloadsPage.images[0].click()
        println(imagePage.resolution[0].text) //todo
    }


}