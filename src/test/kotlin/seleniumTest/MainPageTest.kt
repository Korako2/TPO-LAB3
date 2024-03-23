package seleniumTest

import model.DownloadsPage
import model.ImagePage
import model.MainPage
import model.MenuTools
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import java.awt.image.BufferedImage
import java.io.File
import java.time.Duration
import java.util.stream.Stream
import javax.imageio.ImageIO

class MainPageTest {
    private lateinit var mainPage: MainPage
    private lateinit var menuTools: MenuTools
    private lateinit var downloadsPage: DownloadsPage
    private lateinit var imagePage: ImagePage
    private lateinit var driver : WebDriver
    private val url = "https://fastpic.org/"
    companion object {
        private val browser = arrayOf("Chrome", "Firefox")
        @JvmStatic
        fun browserProvider() : Stream<String> {
            return Stream.of(*browser)
        }
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
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
        assert(driver.findElement(By.xpath("//img[@border=0]")) != null)
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

        val img = driver.findElements(By.xpath("//img[@border=0]"))
        assert(img.size == 3)
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

        assert(driver.findElement(By.xpath("//img[@border=0]")) != null)

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
        val links = arrayOf(firstImageLink, secondImageLink, thirdImageLink)
        mainPage.linksInput.sendKeys(links.joinToString("\n"))
        mainPage.submitButton.click()

        val imageName = "https://kartinkof.club/uploads/posts/2022-04/1649633828_3-kartinkof-club-p-ugarnie-kartinki-na-avu-s-kotami-3.jpg"
        assert(mainPage.firstImageName.text == imageName)
        assert(mainPage.secondImageName.text == imageName)
        assert(mainPage.thirdImageName.text == imageName)
        assert(driver.findElement(By.xpath("//img[@border=0]")) != null)

        val img = driver.findElements(By.xpath("//img[@border=0]"))
        assert(img.size == 3)

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
        if (!file.exists()) {
            println("File not found")
            return
        }
        mainPage.fileInput.sendKeys(file.absolutePath)
        mainPage.resizeCheckbox.click()
        mainPage.resizeSelect.findElement(By.xpath("//option[text()='500 (стандарт)']")).click()
        mainPage.submitButton.click()
        assert(mainPage.firstImageName.text == "cat.jpg")
        assert(driver.findElement(By.xpath("//img[@border=0]")) != null)

        menuTools.myLoadingLink.click()
        downloadsPage.images[0].click()

        val tabs = ArrayList(driver.windowHandles)
        driver.switchTo().window(tabs[1])
        val resolution = driver.findElement(By.xpath("(//div[@class='resolution text-white-50 m-2'])[1]"))
        Thread.sleep(1000)
        val width = resolution.text.split(" ")[1].split("x")[0]
        val height = resolution.text.split(" ")[1].split("x")[1]
        assert(width.toInt() == 500 || height.toInt() == 500)
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `add image from a computer with rotate`(browser: String) {
        browserSetup(browser)
        mainPage.fromComputerLink.click()
        val image = "images/cat.jpg"
        val file = File(image)
        if (!file.exists()) {
            println("File not found")
            return
        }
        var initialWidth = 0
        var initialHeight = 0
        try {
            val img = ImageIO.read(file)
            initialWidth = img.width
            initialHeight = img.height
        } catch (e: Exception) {
            println("Error while reading file")
        }

        mainPage.fileInput.sendKeys(file.absolutePath)
        mainPage.rotateCheckbox.click()
        mainPage.rotateSelect.findElement(By.xpath("//option[text()='90° против часовой']")).click()
        mainPage.submitButton.click()
        Thread.sleep(15000)
        assert(mainPage.firstImageName.text == "cat.jpg")
        assert(driver.findElement(By.xpath("//img[@border=0]")) != null)

        menuTools.myLoadingLink.click()
        downloadsPage.images[0].click()

        val tabs = ArrayList(driver.windowHandles)
        driver.switchTo().window(tabs[1])
        Thread.sleep(2000)
        val resolution = driver.findElement(By.xpath("(//div[@class='resolution text-white-50 m-2'])[1]"))
        val width = resolution.text.split(" ")[1].split("x")[0]
        val height = resolution.text.split(" ")[1].split("x")[1]
        assert(width.toInt() == initialHeight && height.toInt() == initialWidth)
    }


    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `add image from a link with resize`(browser: String) {
        browserSetup(browser)
        mainPage.fromLinkLink.click()

        val link = "https://hkstrategies.ca/wp-content/uploads/2017/04/taming-the-troll-stay-cool-feature.jpg"
        mainPage.linksInput.sendKeys(link)

        mainPage.resizeCheckbox.click()
        mainPage.resizeSelect.findElement(By.xpath("//option[text()='500 (стандарт)']")).click()
        mainPage.submitButton.click()
        assert(mainPage.firstImageName.text == "https://hkstrategies.ca/wp-content/uploads/2017/04/taming-the-troll-stay-cool-feature.jpg")
        assert(driver.findElement(By.xpath("//img[@border=0]")) != null)

        menuTools.myLoadingLink.click()
        downloadsPage.images[0].click()

        val tabs = ArrayList(driver.windowHandles)
        driver.switchTo().window(tabs[1])

        val resolution = driver.findElement(By.xpath("(//div[@class='resolution text-white-50 m-2'])[1]"))
        Thread.sleep(1000)
        val width = resolution.text.split(" ")[1].split("x")[0]
        val height = resolution.text.split(" ")[1].split("x")[1]
        assert(width.toInt() == 500 || height.toInt() == 500)
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `add image from a link with rotate`(browser: String) {
        browserSetup(browser)
        mainPage.fromLinkLink.click()
        val link = "https://hkstrategies.ca/wp-content/uploads/2017/04/taming-the-troll-stay-cool-feature.jpg"
        mainPage.linksInput.sendKeys(link)

        mainPage.rotateCheckbox.click()
        mainPage.rotateSelect.findElement(By.xpath("//option[text()='90° против часовой']")).click()
        mainPage.submitButton.click()
        assert(mainPage.firstImageName.text == "https://hkstrategies.ca/wp-content/uploads/2017/04/taming-the-troll-stay-cool-feature.jpg")
        assert(driver.findElement(By.xpath("//img[@border=0]")) != null)

        menuTools.myLoadingLink.click()
        downloadsPage.images[0].click()

        val tabs = ArrayList(driver.windowHandles)
        driver.switchTo().window(tabs[1])

        val resolution = driver.findElement(By.xpath("(//div[@class='resolution text-white-50 m-2'])[1]"))
        Thread.sleep(1000)
        val width = resolution.text.split(" ")[1].split("x")[0]
        val height = resolution.text.split(" ")[1].split("x")[1]
        assert(width.toInt() == 1000 && height.toInt() == 2000)
    }


    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `adding ten fields should throw alert`(browser: String) {
        browserSetup(browser)
        mainPage.fromComputerLink.click()
        for (i in 1..10) {
            mainPage.addFieldLink.click()
        }
        val alert = driver.switchTo().alert()
        assert(alert.text == "Лимит на количество одновременных загрузок исчерпан")
        alert.accept()
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `help service link should be open correct page`(browser: String) {
        browserSetup(browser)
        mainPage.helpServiceLink.click()
        assert(driver.currentUrl == "https://fastpic.org/donate")
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `restrictions link should be show restriction`(browser: String) {
        browserSetup(browser)
        mainPage.restrictionsLink.click()
        assert(mainPage.restrictionsText.isDisplayed)
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `disable all effects link should disable all selects`(browser: String) {
        browserSetup(browser)

        mainPage.resizeCheckbox.click()
        mainPage.rotateCheckbox.click()
        mainPage.optimizationCheckbox.click()
        mainPage.posterCheckbox.click()
        mainPage.disableAllEffectsLink.click()

        assert(!mainPage.resizeCheckbox.isSelected)
        assert(!mainPage.rotateSelect.isSelected)
        assert(!mainPage.optimizationCheckbox.isSelected)
        assert(!mainPage.posterCheckbox.isSelected)
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `adding an image from an incorrect link`(browser: String) {
        browserSetup(browser)

        mainPage.fromLinkLink.click()
        val link = "https://hkstrategies.ca/wp-content/uploads/2017/04/taming-the-troll-stay-cool-feature"
        mainPage.linksInput.sendKeys(link)
        mainPage.submitButton.click()
        val error = driver.findElement(By.xpath("//div[contains(text(), 'В запросе')]"))
        assert(error.isDisplayed)
    }


}