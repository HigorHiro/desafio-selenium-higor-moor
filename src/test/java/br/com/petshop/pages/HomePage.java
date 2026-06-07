package br.com.petshop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By homePage         = By.id("home-page");
    private final By loginPage        = By.id("login-page");
    private final By promotionButton  = By.id("promotion-button");
    private final By promotionMessage = By.id("promotion-message");
    private final By logoutButton     = By.id("logout-button");
    private final By openRegisterBtn  = By.id("open-register-page");
    private final By userStatus       = By.id("user-status");
    private final By servicesTable    = By.id("services-table");
    private final By navHomeButton    = By.id("nav-home");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isHomePageActive() {
        wait.until(ExpectedConditions.attributeContains(homePage, "class", "active"));
        return driver.findElement(homePage).getAttribute("class").contains("active");
    }

    public boolean isLoginPageActive() {
        return driver.findElement(loginPage).getAttribute("class").contains("active");
    }

    public void clickPromotion() {
        wait.until(ExpectedConditions.elementToBeClickable(promotionButton)).click();
    }

    public boolean isPromotionMessageVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(promotionMessage)).isDisplayed();
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }

    public void clickOpenRegisterPage() {
        wait.until(ExpectedConditions.elementToBeClickable(openRegisterBtn)).click();
    }

    public void clickNavHome() {
        driver.findElement(navHomeButton).click();
    }

    public String getUserStatusText() {
        return driver.findElement(userStatus).getText();
    }

    public boolean isServicesTableVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(servicesTable)).isDisplayed();
    }

    public int getServicesRowCount() {
        WebElement table = driver.findElement(servicesTable);
        return table.findElements(By.cssSelector("tbody tr")).size();
    }
}
