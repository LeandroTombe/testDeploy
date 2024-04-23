package com.oficial.C1739.Extern.Email.Repository;

import com.oficial.C1739.Extern.Email.Entity.MailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailTokenRepository extends JpaRepository<MailToken,Long> {

    MailToken findByToken(String token);

}
