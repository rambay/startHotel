package com.springboot.app.repository;

import org.springframework.stereotype.Repository;

import com.springboot.app.model.Rol;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long>{

}
