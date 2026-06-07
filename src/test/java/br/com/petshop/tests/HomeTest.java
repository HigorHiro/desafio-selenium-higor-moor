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

@DisplayName("Dashboard (Home)")
class HomeTest extends BaseTest {

    private HomePage homePage;

    @BeforeEach
    void loginParaAcessarHome() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin("admin", "123456");
        homePage = new HomePage(driver);
        assertTrue(homePage.isHomePageActive(), "Pré-condição: deve estar logado no dashboard");
    }

    @Test
    @DisplayName("Deve exibir a tabela de serviços com 4 serviços disponíveis")
    void deveExibirTabelaDeServicosComQuatroRegistros() {
        assertTrue(homePage.isServicesTableVisible(), "Tabela de serviços deve estar visível no dashboard");
        assertEquals(4, homePage.getServicesRowCount(), "Tabela deve exibir exatamente 4 serviços");
    }

    @Test
    @DisplayName("Deve exibir mensagem de promoção ao clicar em 'Ver promoção'")
    void deveExibirMensagemDePromocaoAoClicarNosBotao() {
        homePage.clickPromotion();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        alert.accept();

        assertTrue(alertText.contains("20% OFF"), "Alert de promoção deve informar o desconto de 20% OFF");
        assertTrue(homePage.isPromotionMessageVisible(), "Banner de promoção deve estar visível na página");
    }

    @Test
    @DisplayName("Deve realizar logout e redirecionar para a página de login")
    void deveRealizarLogoutERedirecionarParaPaginaDeLogin() {
        homePage.clickLogout();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isLoginPageActive(), "Deve ser redirecionado para login após logout");

        String userStatus = homePage.getUserStatusText();
        assertTrue(userStatus.contains("Deslogado"), "Status do usuário deve indicar 'Deslogado' após logout");
    }

    @Test
    @DisplayName("Deve navegar para a página de cadastro ao clicar em 'Cadastrar Pet'")
    void deveNavegaParaCadastroAoClicarEmCadastrarPet() {
        homePage.clickOpenRegisterPage();

        RegisterPage registerPage = new RegisterPage(driver);
        assertTrue(registerPage.isRegisterPageActive(), "Página de cadastro deve estar ativa após clique");
    }
}
