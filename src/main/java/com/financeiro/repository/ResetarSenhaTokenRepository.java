package com.financeiro.repository;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.financeiro.model.security.ResetarSenhaToken;
import com.financeiro.model.security.Usuario;

@Repository
public interface ResetarSenhaTokenRepository extends JpaRepository<ResetarSenhaToken, Long> {
	
	public ResetarSenhaToken findByToken(String token);

    public ResetarSenhaToken findByUsuario(Usuario usuario);

    public Stream<ResetarSenhaToken> findAllByDataExpiracaoLessThan(LocalDate now);

    public void deleteByDataExpiracaoLessThan(LocalDate now);

    @Modifying
    @Query("delete from ResetarSenhaToken rst where rst.dataExpiracao <= ?1")
    public void deleteAllExpiredSince(LocalDate now);
}
