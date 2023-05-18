package com.msp.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msp.helpdesk.domain.Chamado;
import com.msp.helpdesk.domain.Cliente;
import com.msp.helpdesk.domain.Tecnico;
import com.msp.helpdesk.domain.enums.Perfil;
import com.msp.helpdesk.domain.enums.Prioridade;
import com.msp.helpdesk.domain.enums.Status;
import com.msp.helpdesk.repositories.ChamadoRepository;
import com.msp.helpdesk.repositories.ClienteRepository;
import com.msp.helpdesk.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;

	public void instanciaDB() {

		Tecnico tec1 = new Tecnico(null, "Mateus Silva", "778.899.120-13", "mateussilvapereira2018@gmail.com", "123");
		tec1.addPerfis(Perfil.ADMIN);

		Tecnico tec2 = new Tecnico(null, "Marcos Antonio", "844.058.150-54", "MarcosAntonio8@gmail.com", "1323");
		tec2.addPerfis(Perfil.CLIENTE);
		Cliente cli1 = new Cliente(null, "Linus Trovalds", "001.359.120-75", "linustrovalds2018@gmail.com", "123");

		Chamado chamado1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1,
				cli1);
		
		

		tecnicoRepository.saveAll(Arrays.asList(tec1, tec2));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(chamado1));
	}
}
