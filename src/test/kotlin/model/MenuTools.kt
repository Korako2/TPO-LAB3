package model

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

// page_url = https://fastpic.org/
class MenuTools(driver: WebDriver) {
    @FindBy(xpath = "(//div[@class='menuitem'])[1]")
    lateinit var mainLink: WebElement

    @FindBy(xpath = "(//div[@class='menuitem'])[2]")
    lateinit var rulesLink: WebElement

    @FindBy(xpath = "(//div[@class='menuitem'])[3]")
    lateinit var faqLink: WebElement

    @FindBy(xpath = "(//div[@class='menuitem'])[4]")
    lateinit var uploaderLink: WebElement

    @FindBy(xpath = "(//div[@class='menuitem'])[5]")
    lateinit var myLoadingLink: WebElement

    @FindBy(xpath = "(//div[@class='menuitem'])[6]")
    lateinit var donateLink: WebElement
    init {
        PageFactory.initElements(driver, this)
    }
}
