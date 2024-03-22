package model

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class ImagePage(driver: WebDriver) {

    @FindBy(xpath = "//div[@class='resolution text-white-50 m-2']//text()")
    lateinit var resolution: MutableList<WebElement> //todo

    init {
        PageFactory.initElements(driver, this)
    }
}