package org.iftm.gerenciadorveterinarios.servicies;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.transaction.Transactional;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.repositories.VeterinarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Transactional
public class VeterinarioServiceTest {

    // cria a simulação/mock de todas as classes que serão injetadas na classe a ser testada
    @Mock
    private VeterinarioRepository repositorio;

    //classe a ser testada que receberá todas as injeções de classes mock
    @InjectMocks
    private VeterinarioService service;

    /*Validar se a busca por veterinario retorna o veterinário correto e o nome com no máximo 10 caracteres. */
    @Test
    public void testarBuscarVeterinarioPorIDExistenteRetornaVeterinarioComTruncado(){
        //arrange
        String nomeExistente = "Erica Queiroz Pinto";
        int tamanhoEsperadoNome = 10;        
        String nomeEsperado = "Erica Quei";
        Veterinario veterinarioEsperado = new Veterinario(null, nomeExistente, "", "", BigDecimal.valueOf(0));
        int idExistente = 2;

        //configurar o mock - criando o cenário de teste
        when(repositorio.findById(Mockito.anyInt())).thenReturn(Optional.of(veterinarioEsperado));

        //act
        Optional<Veterinario> resposta = service.buscaVeterinariosPeloId(idExistente);   
        Veterinario veterinarioRetornado = resposta.get();

        //assert
        assertTrue(resposta.isPresent());
        assertEquals(tamanhoEsperadoNome, veterinarioRetornado.getNome().length());
        assertEquals(nomeEsperado, veterinarioRetornado.getNome());

        verify(repositorio).findById(Mockito.anyInt());
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

        verify(repositorio).delete(veterinarioExcluido);

    }


}
