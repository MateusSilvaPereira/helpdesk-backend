package com.msp.helpdesk;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.msp.helpdesk.domain.Chamado;
import com.msp.helpdesk.domain.Cliente;
import com.msp.helpdesk.domain.Tecnico;
import com.msp.helpdesk.domain.enums.Perfil;
import com.msp.helpdesk.domain.enums.Prioridade;
import com.msp.helpdesk.domain.enums.Status;
import com.msp.helpdesk.repositories.ChamadoRepository;
import com.msp.helpdesk.repositories.ClienteRepository;
import com.msp.helpdesk.repositories.TecnicoRepository;

@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner{

	
	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		Tecnico tec1 = new Tecnico(null, "Mateus Silva", "778.899.120-13", "mateussilvapereira2018@gmail.com", "123");
		tec1.addPerfis(Perfil.ADMIN);
	
		Cliente cli1 = new Cliente(null, "Linus Trovalds", "001.359.120-75", "linustrovalds2018@gmail.com", "123");
	
		
		Chamado chamado1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);
	
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(chamado1));
	}

}
