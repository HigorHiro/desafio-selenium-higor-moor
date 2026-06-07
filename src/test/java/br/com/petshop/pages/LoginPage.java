package br.com.petshop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameInput = By.id("login-username");
    private final By passwordInput = By.id("login-password");
    private final By submitButton  = By.id("login-submit");
    private final By successMessage = By.id("login-success");
    private final By errorMessage   = By.id("login-error");
    private final By globalErrorMessage = By.id("login-global-error");
    private final By loginPage = By.id("login-page");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillUsername(String username) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        input.clear();
        input.sendKeys(username);
    }

    public void fillPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickSubmit() {
        driver.findElement(submitButton).click();
    }

    public void doLogin(String username, String password) {
        fillUsername(username);
        fillPassword(password);
        clickSubmit();
    }

    public boolean isSuccessMessageVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
    }

    public boolean isErrorMessageVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
    }

    public boolean isGlobalErrorMessageVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(globalErrorMessage)).isDisplayed();
    }

    public String getErrorMessageText() {
        return driver.findElement(errorMessage).getText();
    }

    public String getGlobalErrorMessageText() {
        return driver.findElement(globalErrorMessage).getText();
    }

    public boolean isLoginPageActive() {
        return driver.findElement(loginPage).getAttribute("class").contains("active");
    }
}
