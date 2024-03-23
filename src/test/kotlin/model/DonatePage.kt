package model

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class DonatePage(driver: WebDriver) {
    @FindBy(xpath = "//button[@class='btn-copy-textarea']")
    lateinit var copyButtons: MutableList<WebElement>
    init {
        PageFactory.initElements(driver, this)
    }
}