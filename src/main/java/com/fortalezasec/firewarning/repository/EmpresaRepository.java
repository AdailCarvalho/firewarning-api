package com.fortalezasec.firewarning.repository;

import java.util.Optional;

import com.fortalezasec.firewarning.domain.Empresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
	Optional<Empresa> findByCnpj(String cnpj);
}
