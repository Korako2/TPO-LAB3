package model

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

// page_url = https://fastpic.org/my.php
class DownloadsPage(driver: WebDriver) {

    @FindBy(xpath = "//a[@class='jslink'][text()='[ выбрать всё ]']")
    lateinit var selectAllLink: WebElement

    @FindBy(xpath = "//a[@class='jslink'][text()='[ снять выбор ]']")
    lateinit var deselectAllLink: WebElement

    @FindBy(xpath = "//a[@class='jslink'][text()='[ удалить выбранное ]']")
    lateinit var deleteSelectedLink: WebElement

    @FindBy(xpath = "//div[@class='thumb']")
    lateinit var images: MutableList<WebElement>

    @FindBy(xpath = "//input[@type='checkbox']")
    lateinit var checkboxes: MutableList<WebElement>

    init {
        PageFactory.initElements(driver, this)
    }
}