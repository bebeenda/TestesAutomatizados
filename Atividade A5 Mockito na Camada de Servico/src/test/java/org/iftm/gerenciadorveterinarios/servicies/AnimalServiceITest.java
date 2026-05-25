package org.iftm.gerenciadorveterinarios.servicies;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import javax.transaction.Transactional;

import org.iftm.gerenciadorveterinarios.entities.Animal;
import org.iftm.gerenciadorveterinarios.repositories.AnimalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AnimalServiceITest {

    @Autowired
    private AnimalRepository repositorio;

    @Autowired
    private AnimalService animalService;

    // CICLO 1: Busca por ID
    @Test
    @Transactional
    public void testarBuscarAnimalPorIDExistenteRetornaAnimalCorreto() {
        int idExistente = 1;

        Optional<Animal> resposta = animalService.buscaAnimaisPeloId(idExistente);

        assertTrue(resposta.isPresent(), "Animal com ID 1 deve existir no banco");
        assertEquals("Kiwi Abacaxi", resposta.get().getNome());
    }

    // CICLO 1: Cadastro com status padrão (internado = true)
    @Test
    @Transactional
    public void deveCadastrarAnimalComInternadoTrue() {
        Animal animalEntrada = new Animal(null, "Kiwi Abacaxi", "cachorro", 3, false);

        Animal resultado = animalService.cadastrar(animalEntrada);

        assertNotNull(resultado.getId(), "ID deve ter sido gerado pelo banco");
        assertTrue(resultado.getInternado(), "Animal recém cadastrado deve estar internado");

        // Prova real no banco
        Optional<Animal> noBanco = repositorio.findById(resultado.getId());
        assertTrue(noBanco.isPresent());
        assertTrue(noBanco.get().getInternado());
    }

    // CICLO 2: Validação de espécie inválida
    @Test
    @Transactional
    public void deveLancarExcecaoParaEspecieInvalida() {
        Animal animalInvalido = new Animal(null, "Simba", "leão", 5, false);

        assertThrows(IllegalArgumentException.class, () -> {
            animalService.cadastrar(animalInvalido);
        }, "Deve lançar IllegalArgumentException para espécie não atendida");
    }

    @Test
    @Transactional
    public void deveLancarExcecaoParaEspecieNula() {
        Animal animalSemEspecie = new Animal(null, "Bolinha", null, 2, false);

        assertThrows(IllegalArgumentException.class, () -> {
            animalService.cadastrar(animalSemEspecie);
        });
    }

    // CICLO 3: Dar alta ao animal (internado = false)
    @Test
    @DisplayName("Deve dar alta ao animal setando internado = false")
    @Transactional
    public void deveDarAltaAoAnimal() {
        // Primeiro cadastra para garantir que existe no banco
        Animal animalSalvo = animalService.cadastrar(
            new Animal(null, "Jubiscleia", "gato", 2, true)
        );

        Animal resultado = animalService.darAlta(animalSalvo.getId());

        assertFalse(resultado.getInternado(), "Após dar alta, internado deve ser false");

        // Prova real no banco
        Optional<Animal> noBanco = repositorio.findById(resultado.getId());
        assertTrue(noBanco.isPresent());
        assertFalse(noBanco.get().getInternado());
    }

    @Test
    @DisplayName("Deve lançar RuntimeException ao dar alta com ID inexistente")
    public void deveLancarExcecaoAoDarAltaComIdInexistente() {
        assertThrows(RuntimeException.class, () -> {
            animalService.darAlta(99);
        });
    }
}