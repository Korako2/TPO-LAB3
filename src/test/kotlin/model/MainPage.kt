package model

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

// page_url = https://fastpic.org/
class MainPage(driver: WebDriver) {
    @FindBy(xpath = "//a[text()='[ добавить поле ]'][@class='jslink']")
    lateinit var addFieldLink: WebElement

    @FindBy(xpath = "//a[text()='[ с компьютера ]'][@class='jslink']")
    lateinit var fromComputerLink: WebElement

    @FindBy(xpath = "//a[text()='[ по ссылке ]'][@class='jslink']")
    lateinit var fromLinkLink: WebElement

    @FindBy(xpath = "//a[@class='jslink']//b[text()='[ посмотреть ограничения ]']")
    lateinit var restrictionsLink: WebElement

    @FindBy(xpath = "//a[text() = '[ отключить все эффекты ]'][@class='jslink']")
    lateinit var disableAllEffectsLink: WebElement

    @FindBy(xpath = "//input[@class='upfile']")
    lateinit var fileInput: WebElement

    @FindBy(xpath = "//input[@name='file2']")
    lateinit var file2Input: WebElement

    @FindBy(xpath = "//input[@name='file3']")
    lateinit var file3Input: WebElement

    @FindBy(xpath = "//input[@name='check_orig_resize']")
    lateinit var resizeCheckbox: WebElement

    @FindBy(xpath = "//select[@class='lmar_28']")
    lateinit var resizeSelect: WebElement

    @FindBy(xpath = "//input[@name='check_orig_rotate']")
    lateinit var rotateCheckbox: WebElement

    @FindBy(xpath = "//select[@name='orig_rotate']")
    lateinit var rotateSelect: WebElement

    @FindBy(xpath = "//input[@name='submit']")
    lateinit var submitButton: WebElement

    @FindBy(xpath = "(//h3)[2]")
    lateinit var firstImageName: WebElement

    @FindBy(xpath = "(//h3)[3]")
    lateinit var secondImageName: WebElement

    @FindBy(xpath = "(//h3)[4]")
    lateinit var thirdImageName: WebElement

    @FindBy(xpath = "//textarea[@name='files']")
    lateinit var linksInput: WebElement

    init {
        PageFactory.initElements(driver, this)
    }
}