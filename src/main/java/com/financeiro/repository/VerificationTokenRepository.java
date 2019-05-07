package com.financeiro.repository;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.financeiro.model.security.Usuario;
import com.financeiro.model.security.ValidarTokenUsuario;

@Repository
public interface VerificationTokenRepository extends JpaRepository<ValidarTokenUsuario, Long> {
	
	public ValidarTokenUsuario findByToken(String token);

    public ValidarTokenUsuario findByUsuario(Usuario usuario);

    public Stream<ValidarTokenUsuario> findAllByExpiryDateLessThan(LocalDate now);

    public void deleteByExpiryDateLessThan(LocalDate now);

    @Modifying
    @Query("delete from TokenVerification v where v.expiryDate <= ?1")
    public void deleteAllExpiredSince(LocalDate now);

}
