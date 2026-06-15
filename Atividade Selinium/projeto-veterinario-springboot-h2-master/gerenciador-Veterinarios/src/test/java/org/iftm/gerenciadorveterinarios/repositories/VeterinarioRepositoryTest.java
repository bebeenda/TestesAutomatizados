package org.iftm.gerenciadorveterinarios.repositories;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@TestMethodOrder(OrderAnnotation.class) // define a forma de ordenar os métodos
public class VeterinarioRepositoryTest {

    @Autowired
    private VeterinarioRepository repository;

    @Test
    @Order(2)
    public void testarBuscaVeterinarioComIdExistenteRetornaCorreto1() {
        // arrange
        int idExistente = 1;
        String nomeEsperado = "Conceição Evaristo";
        String emailEsperado = "conceicao@gmail.com";

        // act
        Veterinario veterinarioEncontrado = repository.getById(idExistente);

        // assert
        assertNotNull(veterinarioEncontrado);
        assertEquals(nomeEsperado, veterinarioEncontrado.getNome());
        assertEquals(emailEsperado, veterinarioEncontrado.getEmail());
    }

    @Test
    @Order(1)
    public void testarBuscaVeterinarioComIdExistenteRetornaCorreto2() {
        // arrange
        int idExistente = 1;
        String nomeEsperado = "Conceição Evaristo";
        String emailEsperado = "conceicao@gmail.com";

        // act
        Optional<Veterinario> veterinarioEncontrado = repository.findById(idExistente);

        // assert
        assertTrue(veterinarioEncontrado.isPresent());
        assertEquals(nomeEsperado, veterinarioEncontrado.get().getNome());
        assertEquals(emailEsperado, veterinarioEncontrado.get().getEmail());
    }

}
