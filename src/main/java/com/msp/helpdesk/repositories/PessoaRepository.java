package com.msp.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msp.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

}
