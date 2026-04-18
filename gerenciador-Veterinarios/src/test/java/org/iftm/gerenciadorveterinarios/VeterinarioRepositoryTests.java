package org.iftm.gerenciadorveterinarios;
import java.util.List;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.iftm.gerenciadorveterinarios.repositories.VeterinarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class VeterinarioRepositoryTests {

    @Autowired //essa anotação já entrega instanciado o repositorio, isso devido ao JPA do spring boot
    private VeterinarioRepository repository;
    


    //Ciclo 1

    //Teste 1 RED: busca "pedro" (minúsculo), banco tem "PEDRO, o resultado deve ser 0 veterinários

    @Test
    void testFindByNome_NaoEncontraMaiusculo(){
        //Arrange
        String nome = "pedro";

        //Act
        List<Veterinario> resultado = repository.findByNome(nome);

        //Assert
        assertEquals(0, resultado.size());

    }

    //Teste 2 RED: busca "PEDRO" igual do banco então deve retornar 1

    @Test
    void testFindByNome_EncontraMaiusculo(){
        //Arrange 
        String nome = "PEDRO";

        //Act
        List<Veterinario> resultado = repository.findByNome(nome);

        //Assert
        assertEquals(1, resultado.size());
        assertEquals("PEDRO", resultado.get(0).getNome()); //eu faço o get nome para pegar apenas o nome na minha lista de veterinarios tendo em vista que tenho outras informações.
    }

    //Teste 3 RED: busca "brenda" (não existe no banco) então retorna 0

    @Test
    void testFindByNome_NomeInexistente(){
        //Arrange
        String nome = "brenda";

        //Act
        List<Veterinario> resultado = repository.findByNome(nome);

        //Assert
        assertEquals(0, resultado.size());
    }

    //Teste 4 RED: busca "fernanda" no banco contém "FERNANDA" então retorna 1

    @Test
    void testFindBynomeIgnoreCase(){
        // Arrange
        String nome = "fernanda";
        // Act
        List<Veterinario> resultado = repository.findByNomeIgnoreCase(nome);
        // Assert
        assertEquals(1, resultado.size());
        assertEquals("FERNANDA", resultado.get(0).getNome());
    }


    //Ciclo 2

    //teste 1 - buscando por %an% e vai encontrar Fernanda e Mariana

    @Test
    void testFindByNomeLike_MaisdeUmNome(){
        //Arrange
        String silaba = "%an%";

        //Act
        List<Veterinario> resultado = repository.findByNomeLikeIgnoreCase(silaba);

        //Assert
        assertEquals(2, resultado.size());
    }

    //teste 2 - busca "%Lucas" mas não existe no banco então retorna 0

    @Test
    void testFindByNomeLike_NomeInexistente(){
        //Arrange
        String silaba ="%Lucas%";

        //Act
        List<Veterinario> resultado = repository.findByNomeLikeIgnoreCase(silaba);

        //Assert
        assertEquals(0, resultado.size());
    }

    //teste 3 - busca por "%" retorna todos os registros
    @Test
    void testFindByNomeLike_RetornaTodos(){
        //Arrange
        String silaba = "%";

        //Act
        List<Veterinario> resultado = repository.findByNomeLikeIgnoreCase(silaba);

        //Assert
        assertEquals(5, resultado.size());
    }
}
