package org.iftm.gerenciadorveterinarios.servicies;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.repositories.VeterinarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Testes unitários da camada VeterinarioService com Mockito.
 * Primeira parte do trabalho
 */
@ExtendWith(MockitoExtension.class)
public class VeterinarioServiceTest_BrendaLuana {

    // O repositório é isolado como Mock, aqui eu não uso nenhum banco de dados como o H2
    @Mock 
    //Anotação mock para repositorio
    private VeterinarioRepository repositorio;

    // injectMocks para o serviço
    @InjectMocks
    private VeterinarioService veterinarioService;

  
    // DESAFIO 1: Testando buscas com Listas
   
    @Test
    @DisplayName("Deve retornar lista com 2 veterinários ao buscar por 'Silva'")
    public void deveBuscarVeterinariosComParteDoNome() {
        // ARRANGE — criamos 2 veterinários em memória e treinamos o mock
        Veterinario vet1 = new Veterinario(1, "Thiago Silva", "thiago@vet.com", "Cardiologia", new BigDecimal("5000.00"));
        Veterinario vet2 = new Veterinario(2, "Kiwi Silva", "kiwi@vet.com", "Ortopedia", new BigDecimal("6000.00"));

        when(repositorio.findByNomeContains("Silva"))
                .thenReturn(Arrays.asList(vet1, vet2));
        //aqui ele me retorna o nome silva, caso tenha


        // ACT para chamar o método 
        List<Veterinario> resultado = veterinarioService.buscaVeterinariosComParteNome("Silva");

        // ASSERT aqui para o teste é necessário exatamente 2 pessoas na lista
        assertEquals(2, resultado.size(), "A lista retornada deve conter exatamente 2 veterinários");

        // VERIFY verificação se o repositório foi de fato consultado
        verify(repositorio, times(1)).findByNomeContains("Silva");
    }

   
    // DESAFIO 2: Protegendo a Exclusão (Tratamento de exceções)

    /**
     * RED: Este teste falha enquanto o VeterinarioService não tiver o método apagarPorId com a validação de existência.
     * vou rodar uma vez e subir no git, depois colocarei no service
     */
    @Test
    @DisplayName("Deve lançar exceção ao tentar apagar veterinário com ID inexistente")
    public void deveLancarExcecaoAoApagarQuandoIdNaoExistir() {
        // ARRANGE — mock vai retornar vazio para qualquer ID buscado
        Integer idInexistente = 99;
        when(repositorio.findById(idInexistente))
                .thenReturn(Optional.empty());

        // ACT + ASSERT aqui o service deve lançar RuntimeException
        assertThrows(RuntimeException.class, () -> {
            veterinarioService.apagarPorId(idInexistente);
        }, "Deve lançar RuntimeException quando o ID não existir");

        // VERIFY verifica que o delete nunca deve ser chamado quando o ID não existe
        verify(repositorio, never()).delete(any());
    }

    @Test
    @DisplayName("Deve apagar o veterinário com sucesso quando ID existir")
    public void deveApagarVeterinarioQuandoIdExistir() {
        // ARRANGE
        Veterinario vet = new Veterinario(1, "Brenda Luana", "brenda@vet.com", "Dermatologia", new BigDecimal("4500.00"));
        when(repositorio.findById(1)).thenReturn(Optional.of(vet));

        // ACT
        veterinarioService.apagarPorId(1);

        // VERIFY aqui o delete deve ter sido chamado exatamente uma vez, visto que é um
        verify(repositorio, times(1)).delete(vet);
    }
}