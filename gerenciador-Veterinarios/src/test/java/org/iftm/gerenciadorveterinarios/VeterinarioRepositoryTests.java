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
}
