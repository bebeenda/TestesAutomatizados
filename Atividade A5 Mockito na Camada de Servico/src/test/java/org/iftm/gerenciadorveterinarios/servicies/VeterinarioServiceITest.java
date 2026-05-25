package org.iftm.gerenciadorveterinarios.servicies;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import javax.transaction.Transactional;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.repositories.VeterinarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VeterinarioServiceITest {

    @Autowired
    private VeterinarioService service;

    @Autowired
    private VeterinarioRepository repository;

    @Test
    @Transactional
    public void testarBuscarVeterinarioPorIDExistenteRetornaVeterinarioComTruncado() {
        int tamanhoEsperadoNome = 10;
        String nomeEsperado = "Erica Quei";
        int idExistente = 2;

        Optional<Veterinario> resposta = service.buscaVeterinariosPeloId(idExistente);
        Veterinario veterinarioRetornado = resposta.get();

        assertTrue(resposta.isPresent());
        assertEquals(tamanhoEsperadoNome, veterinarioRetornado.getNome().length());
        assertEquals(nomeEsperado, veterinarioRetornado.getNome());
    }

    @Test
    @Transactional
    void deveSalvarVeterinarioNoBancoDeDados() {
        Veterinario novoVet = new Veterinario(null, "Dra. Marcia", "marcia@gmail.com", "Grandes Animais", BigDecimal.valueOf(5500.0));

        Veterinario salvo = service.salvar(novoVet);

        assertNotNull(salvo.getId(), "O banco H2 deveria ter gerado um ID automático!");
        assertEquals("Dra. Marcia", salvo.getNome());

        Optional<Veterinario> vetNoBanco = repository.findById(salvo.getId());
        assertTrue(vetNoBanco.isPresent());
        assertEquals("marcia@gmail.com", vetNoBanco.get().getEmail());
    }

    @Test
    @Transactional
    public void testarApagarRealmenteApagaRegistro() {
        Integer idExistente = 2;
        String nomeExistente = "Erica Queiroz Pinto";
        Veterinario veterinarioExcluido = new Veterinario(idExistente, nomeExistente, "", "", null);

        assertDoesNotThrow(() -> {
            service.apagar(veterinarioExcluido);
        });
    }
}