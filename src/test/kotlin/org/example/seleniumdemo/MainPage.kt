package org.example.seleniumdemo

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

// page_url = https://fastpic.org/
class MainPage {
    @FindBy(xpath = "//*[@data-test-marker='Developer Tools']")
    lateinit var seeDeveloperToolsButton: WebElement

    @FindBy(xpath = "//*[@data-test='suggestion-action']")
    lateinit var findYourToolsButton: WebElement

    @FindBy(xpath = "//div[@data-test='main-menu-item' and @data-test-marker = 'Developer Tools']")
    lateinit var toolsMenu: WebElement

    @FindBy(xpath = "//*[@data-test='site-header-search-action']")
    lateinit var searchButton: WebElement
}
