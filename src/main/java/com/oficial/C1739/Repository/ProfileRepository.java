package com.oficial.C1739.Repository;

import com.oficial.C1739.Entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    //Usamos Optinal como un contenedor, donde este puede o no tener un valor asignado.
    //Nos brinda una manera segura de manejar los valores que podrian ser 'null'
    Optional<Profile> findById(Long idProfile);


}
