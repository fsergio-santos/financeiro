package com.financeiro.repository;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.financeiro.model.security.ResetPasswordToken;
import com.financeiro.model.security.Usuario;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<ResetPasswordToken, Long> {
	
	public ResetPasswordToken findByToken(String token);

    public ResetPasswordToken findByUsuario(Usuario usuario);

    public Stream<ResetPasswordToken> findAllByExpiryDateLessThan(LocalDate now);

    void deleteByExpiryDateLessThan(LocalDate now);

    @Modifying
    @Query("delete from ResetPasswordToken p where p.expiryDate <= ?1")
    public void deleteAllExpiredSince(LocalDate now);

}
