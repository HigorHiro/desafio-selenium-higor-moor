package br.com.petshop.tests;

import br.com.petshop.base.BaseTest;
import br.com.petshop.pages.HomePage;
import br.com.petshop.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Login")
class LoginTest extends BaseTest {

    @Test
    @DisplayName("Deve realizar login com credenciais válidas e redirecionar para o Dashboard")
    void deveRealizarLoginComCredenciaisValidasERedirecionarParaDashboard() {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        loginPage.doLogin("admin", "123456");

        assertTrue(loginPage.isSuccessMessageVisible(), "Mensagem de sucesso deve ser exibida após login válido");
        assertTrue(homePage.isHomePageActive(), "Dashboard deve estar ativo após login com sucesso");
        assertTrue(homePage.getUserStatusText().contains("Admin logado"), "Status do usuário deve indicar 'Admin logado'");
    }

    @Test
    @DisplayName("Deve exibir mensagem de erro ao tentar login com senha incorreta")
    void deveExibirErroAoTentarLoginComSenhaIncorreta() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.doLogin("admin", "senha_errada");

        assertTrue(loginPage.isErrorMessageVisible(), "Mensagem de erro deve ser exibida para senha incorreta");
        assertTrue(loginPage.getErrorMessageText().contains("inválidos"), "Mensagem deve informar que as credenciais são inválidas");
        assertTrue(loginPage.isLoginPageActive(), "Usuário deve permanecer na página de login após falha");
    }

    @Test
    @DisplayName("Deve exibir mensagem de erro ao tentar login com usuário incorreto")
    void deveExibirErroAoTentarLoginComUsuarioIncorreto() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.doLogin("usuario_invalido", "123456");

        assertTrue(loginPage.isErrorMessageVisible(), "Mensagem de erro deve ser exibida para usuário inválido");
        assertTrue(loginPage.isLoginPageActive(), "Usuário deve permanecer na página de login após falha");
    }

    @Test
    @DisplayName("Deve exibir aviso e redirecionar para login ao tentar acessar página protegida sem autenticação")
    void deveRedirecionarParaLoginAoAcessarPaginaProtegidaSemAutenticacao() {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        homePage.clickNavHome();

        assertTrue(loginPage.isLoginPageActive(), "Deve ser redirecionado para a página de login");
        assertTrue(loginPage.isGlobalErrorMessageVisible(), "Deve exibir aviso de autenticação necessária");
        assertTrue(loginPage.getGlobalErrorMessageText().contains("logado"), "Mensagem deve instruir o usuário a fazer login");
    }
}
