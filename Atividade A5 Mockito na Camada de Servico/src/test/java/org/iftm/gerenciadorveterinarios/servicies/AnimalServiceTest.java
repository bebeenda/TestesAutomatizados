package org.iftm.gerenciadorveterinarios.servicies;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Animal;
import org.iftm.gerenciadorveterinarios.repositories.AnimalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


//Testes unitários da camada AnimalService com Mockito — Parte 2.


@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    private AnimalRepository repositorio;

    @InjectMocks
    private AnimalService animalService;

   
    // CICLO 1: Cadastro com status padrão (internado = true)

    @Test
    @DisplayName("Deve cadastrar animal com internado = true independente do valor enviado")
    public void deveCadastrarAnimalComInternadoTrue() {
        // RED: animal entra com internado = false
        Animal animalEntrada = new Animal(null, "Kiwi Abacaxi", "cachorro", 3, false);

        // mock retorna o animal já com internado = true (simulando o save)
        Animal animalSalvo = new Animal(1, "Kiwi abacaxi", "cachorro", 3, true);
        when(repositorio.save(any(Animal.class))).thenReturn(animalSalvo);

        // ACT
        Animal resultado = animalService.cadastrar(animalEntrada);

        // ASSERT — status deve ser true
        assertTrue(resultado.getInternado(),
                "Animal recém cadastrado deve estar internado");

        // VERIFY — repositório deve ter salvo
        verify(repositorio, times(1)).save(any(Animal.class));
    }

   
    // CICLO 2: Validação caso o tipo de espécie não seja atendida

    @Test
    @DisplayName("Deve lançar IllegalArgumentException para espécie não atendida")
    public void deveLancarExcecaoParaEspecieInvalida() {
        // RED: espécie inválida
        Animal animalInvalido = new Animal(null, "Simba", "leão", 5, false);

        // ACT + ASSERT
        assertThrows(IllegalArgumentException.class, () -> {
            animalService.cadastrar(animalInvalido);
        }, "Deve lançar IllegalArgumentException para espécie não atendida");

        // VERIFY — save nunca deve ser chamado com dado inválido
        verify(repositorio, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException para espécie nula")
    public void deveLancarExcecaoParaEspecieNula() {
        Animal animalSemEspecie = new Animal(null, "Bolinha", null, 2, false);

        assertThrows(IllegalArgumentException.class, () -> {
            animalService.cadastrar(animalSemEspecie);
        });

        verify(repositorio, never()).save(any());
    }

    // CICLO 3: Dar alta ao animal (internado = false)

    @Test
    @DisplayName("Deve dar alta ao animal setando internado = false")
    public void deveDarAltaAoAnimal() {
        // ARRANGE — mock retorna animal internado
        Animal animalInternado = new Animal(1, "Jubiscleia", "gato", 2, true);
        when(repositorio.findById(1)).thenReturn(Optional.of(animalInternado));

        Animal animalComAlta = new Animal(1, "Jubiscleia", "gato", 2, false);
        when(repositorio.save(any(Animal.class))).thenReturn(animalComAlta);

        // ACT
        Animal resultado = animalService.darAlta(1);

        // ASSERT
        assertFalse(resultado.getInternado(),
                "Após dar alta, internado deve ser false");

        // VERIFY — save foi chamado
        verify(repositorio, times(1)).save(any(Animal.class));
    }

    @Test
    @DisplayName("Deve lançar RuntimeException ao dar alta com ID inexistente")
    public void deveLancarExcecaoAoDarAltaComIdInexistente() {
        when(repositorio.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            animalService.darAlta(99);
        });

        verify(repositorio, never()).save(any());
    }
}