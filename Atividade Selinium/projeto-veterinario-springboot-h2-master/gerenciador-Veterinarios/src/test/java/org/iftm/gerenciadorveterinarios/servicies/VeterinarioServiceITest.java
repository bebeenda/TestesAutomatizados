package org.iftm.gerenciadorveterinarios.servicies;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.repositories.VeterinarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VeterinarioServiceITest {

    //fazer injeção de dependencia da classe original
    @Autowired
    private VeterinarioService service;

    /*Validar se a busca por veterinario retorna o veterinário correto e o nome com no máximo 10 caracteres. */
    @Test
    public void testarBuscarVeterinarioPorIDExistenteRetornaVeterinarioComTruncado(){
        //arrange
        int tamanhoEsperadoNome = 10;        
        String nomeEsperado = "Erica Quei";
        int idExistente = 2;

        //act
        Optional<Veterinario> resposta = service.buscaVeterinariosPeloId(idExistente);   
        Veterinario veterinarioRetornado = resposta.get();

        //assert
        assertTrue(resposta.isPresent());
        assertEquals(tamanhoEsperadoNome, veterinarioRetornado.getNome().length());
        assertEquals(nomeEsperado, veterinarioRetornado.getNome());
    }

    @Test
    public void testarApagarRealmenteApagaRegistro(){
        //Arrange
        Integer idExistente = 2;
        String nomeExistente = "Erica Queiroz Pinto";
        Veterinario veterinarioExcluido = new Veterinario(idExistente, nomeExistente, "", "", null);

        //act and assert
        assertDoesNotThrow(
            ()->{
        service.apagar(veterinarioExcluido);
        }
        );
    }


}
