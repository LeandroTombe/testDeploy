package com.oficial.C1739.Repository;

import com.oficial.C1739.Entity.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken,Long> {
    Optional<JwtToken> findByjwt(String jwt);


}
