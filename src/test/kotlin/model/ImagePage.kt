package model

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

// page_url = https://fastpic.org/view/123/2024/0323/_3a890e08d8cfe3e778089f328f980dc6.jpg.html
class ImagePage(driver: WebDriver) {

    @FindBy(xpath = "//div[@class='resolution text-white-50 m-2']//text()")
    lateinit var resolution: MutableList<WebElement> //todo

    init {
        PageFactory.initElements(driver, this)
    }
}