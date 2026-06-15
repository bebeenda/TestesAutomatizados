package org.iftm.selenium_webdriver;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GerenciadorVeterinarioTest{
    private WebDriver driver;

    @BeforeEach
    public void setup(){
        //configurando o Chrome para teste
        WebDriverManager.chromedriver().setup();

        //definindo que meu WebDriver é relacionado ao navegador Chrome
         driver = new ChromeDriver();
    }
  

    @Test
    public void testarTelaInicialCarregarDadosExistentenoBD(){
        //Arrange

        String urlTestada = "http://localhost:8080/home";
        String nomePrimeiraLinha = "ConceiÃ§Ã£o Evaristo";
        String tituloPagina = "Gerenciador de Veterinários";

        //Act
        driver.get(urlTestada);
        WebElement Linha1Coluna1 = driver.findElement(By.xpath("//tbody/tr[2]/td[1]"));

        String tituloObtido = driver.getTitle();

        assertTrue(driver.getCurrentUrl().contains("/home"));
        assertEquals(tituloPagina, tituloObtido);
        assertEquals(nomePrimeiraLinha, Linha1Coluna1.getText());
    }

     @Test
    public void testarPesquisarVeterinariosExistentes() {
        // Pega a URL
        String urlTestado = "http://localhost:8080/home";
        driver.get(urlTestado);

        // Encontra o botão e acessa o driver chorme e procura pelo elemento botão
        WebElement btnConsultar = driver.findElement(By.xpath("//button[contains(text(),'Consultar')]"));

        // clica nele
        btnConsultar.click();

        WebElement campoNome = driver.findElement(By.id("nome"));

        campoNome.sendKeys("Erica Queiroz Pinto");

        WebElement btnPesquisar = driver.findElement(By.cssSelector("button[type='submit']"));

        assertEquals("Erica Queiroz Pinto", campoNome.getAttribute("value"));
        btnPesquisar.click();

    }

    
    
    @Test
    public void testarCadatrarNovoVeterinario() {
        // Pega a URL
        String urlTestado = "http://localhost:8080/home";
        driver.get(urlTestado);

        // Encontra o botão e acessa o driver chorme e procura pelo elemento botão
        WebElement btnAdicionar = driver.findElement(By.xpath("//button[contains(text(),'Adicionar')]"));
        btnAdicionar.click();

        WebElement textoNome = driver.findElement(By.id("nome"));
        WebElement textoEmail = driver.findElement(By.id("inputEmail"));
        WebElement textoEspecialidade = driver.findElement(By.id("inputEspecialidade"));
        WebElement textoSalario = driver.findElement(By.id("inputSalario"));

        textoNome.sendKeys("Thiago Henrique");
        textoEmail.sendKeys("thenriquedasilva@gmail.com");
        textoEspecialidade.sendKeys("pequenos");
        textoSalario.sendKeys("8000.00");

        
        assertEquals("Thiago Henrique", textoNome.getAttribute("value"));
        assertEquals("thenriquedasilva@gmail.com", textoEmail.getAttribute("value"));
        assertEquals("pequenos", textoEspecialidade.getAttribute("value"));
        assertEquals("8000.00", textoSalario.getAttribute("value"));

        WebElement botaoCadastrar = driver.findElement(By.xpath("//button[contains(.,'Cadastrar')]"));
        botaoCadastrar.click();
        assertTrue(driver.getCurrentUrl().contains("/home"));


        WebElement ultimaLinhaNome = driver.findElement(By.xpath("//tbody/tr[last()]/td[1]"));
        assertEquals("Thiago Henrique", ultimaLinhaNome.getText());
        
    }

      
    @Test
    public void testarAlterarCadastroDeVeterinario(){
         // Pega a URL
        String urlTestado = "http://localhost:8080/home";
        driver.get(urlTestado);

        // Encontra o botão e acessa o driver chorme e procura pelo elemento botão ALTERAR
        WebElement btnAlterar = driver.findElement(By.xpath("//tbody/tr[last()]/td[5]/a[1]"));
        btnAlterar.click();

        WebElement textoNome = driver.findElement(By.id("nome"));
        WebElement textoEmail = driver.findElement(By.id("inputEmail"));
        WebElement textoEspecialidade = driver.findElement(By.id("inputEspecialidade"));
        WebElement textoSalario = driver.findElement(By.id("inputSalario"));

        textoNome.clear();
        textoEmail.clear();
        textoEspecialidade.clear();
        textoSalario.clear();

        textoNome.sendKeys("Thiago Henrique da Silva");
        textoEmail.sendKeys("thenriquedasilva@gmail.com");
        textoEspecialidade.sendKeys("pequenos");
        textoSalario.sendKeys("10000");

        WebElement btnAtualizar = driver.findElement(By.xpath("//button[normalize-space()='Atualizar']"));
        btnAtualizar.click();


        WebElement ultimoNome = driver.findElement(By.xpath("//tbody/tr[last()]/td[1]"));

        assertEquals("Thiago Henrique da Silva",ultimoNome.getText());

     }  


    @Test
    public void testarDeletarCadastroDeVeterinario(){
         // Pega a URL
        String urlTestado = "http://localhost:8080/home";
        driver.get(urlTestado);

        WebElement ultimoNome = driver.findElement(By.xpath("//tbody/tr[last()]/td[1]"));
        String nomeVeterinario = ultimoNome.getText();

        // Encontra o botão e acessa o driver chorme e procura pelo elemento botão ALTERAR
        WebElement btnDeletar = driver.findElement(By.xpath("//tbody/tr[last()]/td[5]/a[2]"));
        btnDeletar.click();
    
        assertFalse(driver.getPageSource().contains(nomeVeterinario));
    }

    @AfterEach
    public void exit(){
        driver.close();
    }



}