package com.egg.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egg.biblioteca.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,String>{
    
    @Query("SELECT u FROM Usuario u WHERE u.userName = :userName")
    public Usuario findByUserName(@Param("userName") String userName);

}
