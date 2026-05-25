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

    private static final List<String> ESPECIES_PERMITIDAS =
            Arrays.asList("cachorro", "gato", "coelho", "pássaro", "hamster");

    @Autowired
    private AnimalRepository repositorio;

    public Optional<Animal> buscaAnimaisPeloId(Integer id) {
        return repositorio.findById(id);
    }

    @Transactional
    public Animal cadastrar(Animal animal) {
        if (animal.getEspecie() == null ||
                !ESPECIES_PERMITIDAS.contains(animal.getEspecie().toLowerCase())) {
            throw new IllegalArgumentException(
                    "Espécie não atendida: " + animal.getEspecie());
        }
        animal.setInternado(true);
        return repositorio.save(animal);
    }

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