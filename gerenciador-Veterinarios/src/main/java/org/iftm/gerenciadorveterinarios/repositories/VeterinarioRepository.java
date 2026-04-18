package org.iftm.gerenciadorveterinarios.repositories;

import java.util.List;

import org.iftm.gerenciadorveterinarios.entities.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VeterinarioRepository extends JpaRepository<Veterinario, Integer> {

   public List<Veterinario> findByNomeContains(String nome);


   //Ciclo 1 - buscando por um nome exato

    List<Veterinario> findByNome(String nome); // exato (case sensitive)
    List<Veterinario> findByNomeIgnoreCase(String nome);// case insensitive aqui pega tanto maiusculo quanto minusculo


}


