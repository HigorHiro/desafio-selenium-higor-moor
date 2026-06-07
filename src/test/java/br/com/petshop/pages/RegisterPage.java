package br.com.petshop.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class RegisterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By registerPage    = By.id("register-page");
    private final By petNameInput    = By.id("pet-name");
    private final By petOwnerInput   = By.id("pet-owner");
    private final By petSpeciesSelect = By.id("pet-species");
    private final By petAgeInput     = By.id("pet-age");
    private final By petNotesArea    = By.id("pet-notes");
    private final By petVaccinatedCheckbox = By.id("pet-vaccinated");
    private final By savePetButton   = By.id("save-pet-button");
    private final By clearFormButton = By.id("clear-form-button");
    private final By successMessage  = By.id("pet-success");
    private final By petsTableBody   = By.id("pets-table-body");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isRegisterPageActive() {
        wait.until(ExpectedConditions.attributeContains(registerPage, "class", "active"));
        return driver.findElement(registerPage).getAttribute("class").contains("active");
    }

    public void fillPetName(String name) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(petNameInput));
        input.clear();
        input.sendKeys(name);
    }

    public void fillPetOwner(String owner) {
        driver.findElement(petOwnerInput).sendKeys(owner);
    }

    public void selectSpecies(String species) {
        new Select(driver.findElement(petSpeciesSelect)).selectByVisibleText(species);
    }

    public void fillPetAge(String age) {
        driver.findElement(petAgeInput).sendKeys(age);
    }

    public void fillPetNotes(String notes) {
        driver.findElement(petNotesArea).sendKeys(notes);
    }

    public void checkVaccinated() {
        WebElement checkbox = driver.findElement(petVaccinatedCheckbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void clickSave() {
        driver.findElement(savePetButton).click();
    }

    public void clickClearForm() {
        driver.findElement(clearFormButton).click();
    }

    public boolean isSuccessMessageVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
    }

    public int getPetsTableRowCount() {
        WebElement tbody = wait.until(ExpectedConditions.visibilityOfElementLocated(petsTableBody));
        return tbody.findElements(By.tagName("tr")).size();
    }

    public List<WebElement> getPetsTableRows() {
        return driver.findElement(petsTableBody).findElements(By.tagName("tr"));
    }

    public String getLastPetName() {
        List<WebElement> rows = getPetsTableRows();
        if (rows.isEmpty()) return "";
        WebElement lastRow = rows.get(rows.size() - 1);
        return lastRow.findElements(By.tagName("td")).get(0).getText();
    }

    public String getLastPetVaccinationStatus() {
        List<WebElement> rows = getPetsTableRows();
        if (rows.isEmpty()) return "";
        WebElement lastRow = rows.get(rows.size() - 1);
        return lastRow.findElements(By.tagName("td")).get(4).getText();
    }

    public boolean isPetNameInputEmpty() {
        return driver.findElement(petNameInput).getAttribute("value").isEmpty();
    }
}
