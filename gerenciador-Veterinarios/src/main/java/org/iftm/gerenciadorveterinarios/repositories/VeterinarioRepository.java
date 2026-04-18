package org.iftm.gerenciadorveterinarios.repositories;

import java.util.List;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VeterinarioRepository extends JpaRepository<Veterinario, Integer> {

   public List<Veterinario> findByNomeContains(String nome);


   //Ciclo 1 - buscando por um nome exato

    List<Veterinario> findByNome(String nome); // exato (case sensitive)
    List<Veterinario> findByNomeIgnoreCase(String nome);// case insensitive aqui pega tanto maiusculo quanto minusculo

   //Ciclo 2 - buscando por pedaço de nome usando o LIKE
   //  List<Veterinario> findByNomeContainsIgnoreCase(String nome);  //no JPA o contains já entende o LIKE, nesse caso ignora se é maíusculo ou minisculo

   // mas vou usar o like conforme o exercicio fala

   List<Veterinario> findByNomeLike(String nome);
   List<Veterinario> findByNomeLikeIgnoreCase(String nome);


     //Ciclo 3 - filtros para salário
    List<Veterinario> findBySalarioGreaterThan(Double salario);
    List<Veterinario> findBySalarioLessThan(Double salario);
    List<Veterinario> findBySalarioBetween(Double min, Double max);


}


