package seleniumTest

import model.DonatePage
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.time.Duration
import java.util.stream.Stream

class DonatePageTest {
    private lateinit var driver : WebDriver
    private lateinit var donatePage: DonatePage
    private val url = "https://fastpic.org/donate"

    companion object {
        private val browser = arrayOf("Chrome")
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

        donatePage = DonatePage(driver)
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    fun `copy buttons should copy wallets`(browser: String) {
        val wallets = listOf(
            "bc1qfs3593j0vkynksh4a007nny2atz5h285ershjy",
            "0xA79b4006a28596a732d24b7F272bc5FDd478a721",
            "TMiStxK2cPJU2kbLgtjVgW1zk1xKancxqM",
            "0xA79b4006a28596a732d24b7F272bc5FDd478a721",
            "bnb122t5qd98wuy943w6r96cmlg4mmkxkmf64zv39m",
            "DM5JTSRjqfdWt4zcpfqvXeBhcuoz49vEd2",
            "LUQuZE7AS5UoKASYdF6tjTf4qHCvwT6Q2S"
        )
        browserSetup(browser)
        for (i in 0 until donatePage.copyButtons.size) {
            donatePage.copyButtons[i].click()
            val clipboard = Toolkit.getDefaultToolkit().systemClipboard
            val clipboardData = clipboard.getData(DataFlavor.stringFlavor) as String
            assert(clipboardData == wallets[i])
        }
    }
}