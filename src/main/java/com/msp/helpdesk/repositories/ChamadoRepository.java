package com.msp.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msp.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{

}
