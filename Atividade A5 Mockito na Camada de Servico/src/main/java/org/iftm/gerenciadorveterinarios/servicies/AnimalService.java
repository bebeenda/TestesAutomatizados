package org.iftm.gerenciadorveterinarios.servicies;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.iftm.gerenciadorveterinarios.entities.Animal;
import org.iftm.gerenciadorveterinarios.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AnimalService {

    // Espécies atendidas pela clínica
    private static final List<String> ESPECIES_PERMITIDAS =
            Arrays.asList("cachorro", "gato", "coelho", "pássaro", "hamster");

    @Autowired
    private AnimalRepository repositorio;

   
    // CICLO 1: Cadastro com status padrão (se internado = true)
   
    @Transactional
    public Animal cadastrar(Animal animal) {
        // Ciclo 2: validação de espécie
        if (animal.getEspecie() == null ||
                !ESPECIES_PERMITIDAS.contains(animal.getEspecie().toLowerCase())) {
            throw new IllegalArgumentException(
                    "Espécie não atendida: " + animal.getEspecie());
        }
        // Ciclo 1: status padrão
        animal.setInternado(true);
        return repositorio.save(animal);
    }

    // CICLO 3: Ação específica — dar alta ao animal
  
    @Transactional
    public Animal darAlta(Integer id) {
        Optional<Animal> optional = repositorio.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Animal não encontrado com id: " + id);
        }
        Animal animal = optional.get();
        animal.setInternado(false);
        return repositorio.save(animal);
    }
}