package com.msp.helpdesk.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.msp.helpdesk.domain.Chamado;
import com.msp.helpdesk.domain.Cliente;
import com.msp.helpdesk.domain.Tecnico;
import com.msp.helpdesk.domain.dtos.ChamadoDTO;
import com.msp.helpdesk.domain.dtos.ClienteDTO;
import com.msp.helpdesk.domain.dtos.TecnicoDTO;
import com.msp.helpdesk.domain.enums.Prioridade;
import com.msp.helpdesk.domain.enums.Status;
import com.msp.helpdesk.services.ChamadoService;

@SpringBootTest
class ChamadoResourceTest {

	@InjectMocks
	private ChamadoResource controller;

	@Mock
	private ChamadoService service;

	private static final Integer ID = 1;
	private static final String TITULO = "Chamado 1";
	private static final String OBSERVACOES = "Teste chamado 1";
	private static final Status STATUS = Status.ENCERRADO;
	private static final Prioridade PRIORIDADE = Prioridade.MEDIA;

	private Cliente cliente;
	private Tecnico tecnico;
	private Chamado chamado;
	private ChamadoDTO chamadoDTO;
	private TecnicoDTO tecnicoDTO;
	private ClienteDTO clienteDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startChamado();
	}

	@Test
	@DisplayName("FindById")
	void whenFindById_thenReturnSuccess() {
		when(service.findById(anyInt())).thenReturn(chamado);
		 ResponseEntity<ChamadoDTO> response = controller.findById(ID);
		 
		 assertNotNull(response);
		 assertNotNull(response.getBody());
		 assertEquals(ResponseEntity.class, response.getClass());
		 assertEquals(ChamadoDTO.class, response.getBody().getClass());
		 assertEquals(ID, response.getBody().getId());
		 assertEquals(TITULO, response.getBody().getTitulo());
		 assertEquals(OBSERVACOES, response.getBody().getObservacoes());
		 assertEquals(2, response.getBody().getStatus());
		 assertEquals(1, response.getBody().getPrioridade());
	}

	@Test
	@DisplayName("FindAll")
	void whenFindAll_thenReturnListOfChamado() {
		
		when(service.findAll()).thenReturn(List.of(chamado));
		
		ResponseEntity<List<ChamadoDTO>> response = controller.findAll();
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ArrayList.class, response.getBody().getClass());
	    assertEquals(ChamadoDTO.class, response.getBody().get(0).getClass());
	    assertEquals(ID, response.getBody().get(0).getId());
	    assertEquals(TITULO, response.getBody().get(0).getTitulo());
		assertEquals(OBSERVACOES, response.getBody().get(0).getObservacoes());
		assertEquals(2, response.getBody().get(0).getStatus());
	    assertEquals(1, response.getBody().get(0).getPrioridade());
	}

	@Test
	void whentCreate_thenReturnCreated() {

		when(service.create(any())).thenReturn(chamado);
 
		ResponseEntity<ChamadoDTO> response = controller.create(chamadoDTO);
		
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getHeaders().get("Location"));
		assertEquals(null, response.getBody());
		
	}

	@Test
	void whenUpdate_thenReturnSuccess() {
		
		when(service.update(ID, chamadoDTO)).thenReturn(chamado);
		
		ResponseEntity<ChamadoDTO> response = controller.update(ID, chamadoDTO);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ChamadoDTO.class, response.getBody().getClass());
		assertEquals(ID, response.getBody().getId());
		assertEquals(TITULO, response.getBody().getTitulo());
		assertEquals(OBSERVACOES, response.getBody().getObservacoes());
		assertEquals(2, response.getBody().getStatus());
		assertEquals(1, response.getBody().getPrioridade());
	}

	private void startChamado() {

		tecnico = new Tecnico(ID, "Mateus", "778.899.120-13", "mateussilvapereira@gmail.com", "123");
		cliente = new Cliente(ID, "Antonio", "111.661.890-74", "einstein@mail.com", "123");

		tecnicoDTO = new TecnicoDTO(tecnico);
		clienteDTO = new ClienteDTO(cliente);
		chamado = new Chamado(ID, PRIORIDADE, STATUS, TITULO, OBSERVACOES, tecnico, cliente);
		chamadoDTO = new ChamadoDTO(chamado);

	}

}
