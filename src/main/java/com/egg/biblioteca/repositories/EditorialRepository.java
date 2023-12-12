package com.egg.biblioteca.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egg.biblioteca.entities.Editorial;

@Repository
public interface EditorialRepository extends JpaRepository<Editorial, String>{
    
    @Query("SELECT e FROM Editorial e WHERE e.nombre = :nombre")
    public Editorial buscarEditorial(@Param("nombre") String nombre);

}
