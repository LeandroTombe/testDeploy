package com.oficial.C1739.Repository;

import com.oficial.C1739.Entity.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {
}