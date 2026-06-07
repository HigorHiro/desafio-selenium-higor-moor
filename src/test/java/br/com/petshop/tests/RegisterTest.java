package br.com.petshop.tests;

import br.com.petshop.base.BaseTest;
import br.com.petshop.pages.HomePage;
import br.com.petshop.pages.LoginPage;
import br.com.petshop.pages.RegisterPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cadastro de Pets")
class RegisterTest extends BaseTest {

    private RegisterPage registerPage;
    private static final int INITIAL_ROW_COUNT = 3;

    @BeforeEach
    void loginENavegaParaCadastro() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin("admin", "123456");

        HomePage homePage = new HomePage(driver);
        homePage.isHomePageActive();
        homePage.clickOpenRegisterPage();

        registerPage = new RegisterPage(driver);
        assertTrue(registerPage.isRegisterPageActive(), "Pré-condição: deve estar na página de cadastro");
    }

    @Test
    @DisplayName("Deve cadastrar pet com todos os campos preenchidos e exibir na tabela")
    void deveCadastrarPetComTodosOsCamposEExibirNaTabela() {
        int rowsBefore = registerPage.getPetsTableRowCount();

        registerPage.fillPetName("Bolt");
        registerPage.fillPetOwner("Maria Silva");
        registerPage.selectSpecies("Cachorro 🐕");
        registerPage.fillPetAge("3 anos");
        registerPage.fillPetNotes("Sem alergias conhecidas");
        registerPage.checkVaccinated();
        registerPage.clickSave();

        assertTrue(registerPage.isSuccessMessageVisible(), "Mensagem de sucesso deve ser exibida após salvar pet");
        assertEquals(rowsBefore + 1, registerPage.getPetsTableRowCount(), "Tabela deve ter uma linha a mais após cadastro");
        assertEquals("Bolt", registerPage.getLastPetName(), "Nome do pet cadastrado deve aparecer na última linha da tabela");
        assertEquals("Sim", registerPage.getLastPetVaccinationStatus(), "Status de vacinação deve ser 'Sim' quando checkbox marcado");
    }

    @Test
    @DisplayName("Deve cadastrar pet sem vacinação e registrar como 'Não' na tabela")
    void deveCadastrarPetSemVacinacaoERegistrarComoNao() {
        registerPage.fillPetName("Fifi");
        registerPage.fillPetOwner("João Souza");
        registerPage.selectSpecies("Gato 🐈");
        registerPage.clickSave();

        assertTrue(registerPage.isSuccessMessageVisible(), "Mensagem de sucesso deve ser exibida");
        assertEquals("Não", registerPage.getLastPetVaccinationStatus(), "Status de vacinação deve ser 'Não' quando checkbox desmarcado");
    }

    @Test
    @DisplayName("Deve exibir alerta de validação ao tentar salvar pet sem campos obrigatórios")
    void deveExibirAlertaAoTentarSalvarPetSemCamposObrigatorios() {
        int rowsBefore = registerPage.getPetsTableRowCount();

        registerPage.clickSave();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        alert.accept();

        assertTrue(alertText.contains("obrigatórios"), "Alert deve informar que campos obrigatórios estão faltando");
        assertEquals(rowsBefore, registerPage.getPetsTableRowCount(), "Nenhum registro deve ser adicionado após falha de validação");
    }

    @Test
    @DisplayName("Deve limpar o formulário ao clicar no botão 'Limpar campos'")
    void deveLimparFormularioAoClicarEmLimparCampos() {
        registerPage.fillPetName("Thor");
        registerPage.fillPetOwner("Pedro Lima");
        registerPage.selectSpecies("Ave 🦜");
        registerPage.fillPetAge("2 anos");

        registerPage.clickClearForm();

        assertTrue(registerPage.isPetNameInputEmpty(), "Campo nome do pet deve estar vazio após limpar formulário");
    }
}
