package com.msp.helpdesk.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.msp.helpdesk.domain.Chamado;
import com.msp.helpdesk.domain.Cliente;
import com.msp.helpdesk.domain.Tecnico;
import com.msp.helpdesk.domain.dtos.ChamadoDTO;
import com.msp.helpdesk.domain.dtos.ClienteDTO;
import com.msp.helpdesk.domain.dtos.TecnicoDTO;
import com.msp.helpdesk.domain.enums.Prioridade;
import com.msp.helpdesk.domain.enums.Status;
import com.msp.helpdesk.repositories.ChamadoRepository;
import com.msp.helpdesk.repositories.ClienteRepository;
import com.msp.helpdesk.repositories.TecnicoRepository;
import com.msp.helpdesk.services.exceptions.ObjectNotFoundException;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class ChamadoServiceTest {

	@Autowired
	private BCryptPasswordEncoder encoder;
	@Mock
	private ClienteService clienteService;
	@Mock
	private TecnicoDTO tecnicoDTO;
	@Mock
	private ClienteDTO clienteDTO;
	@Mock
	private TecnicoService tecnicoService;
	@InjectMocks
	private ChamadoService chamadoservice;
	@Mock
	private ChamadoRepository repository;
	@Mock
	private ClienteRepository clienteRepository;
	@Mock
	private TecnicoRepository tecnicoRepository;

	private static final Integer ID = 1;
	private static final String TITULO = "Chamado 1";
	private static final String OBSERVACOES = "Teste chamado 1";
	private static final Status STATUS = Status.ENCERRADO;
	private static final Prioridade PRIORIDADE = Prioridade.MEDIA;

	private Cliente cliente;
	private Tecnico tecnico;
	private Chamado chamado;
	private ChamadoDTO chamadoDTO;
	private Optional<Chamado> optionalChamado; 

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startChamado();
	}
 
	@Test
	@DisplayName("Test FindById")
	void whenFindByIdThenReturnAnChamadoIntance() {
		when(repository.findById(anyInt())).thenReturn(optionalChamado);
		
		Chamado response = chamadoservice.findById(ID);
		
		tecnicoRepository.findById(1);
		clienteRepository.findById(1);

		assertNotNull(response);
		assertEquals(Chamado.class, response.getClass());
	    assertEquals(Tecnico.class, response.getTecnico().getClass());
	    assertEquals(Cliente.class, response.getCliente().getClass());
	    assertEquals(ID, response.getId());
	    assertEquals(TITULO, response.getTitulo());
	    assertEquals(OBSERVACOES, response.getObservacoes());
	    assertEquals(PRIORIDADE, response.getPrioridade());
		assertEquals(STATUS, response.getStatus());
	    assertEquals(tecnico, response.getTecnico());
	    assertEquals(cliente, response.getCliente()); 
	}

	@Test
	@DisplayName("Test_NotFindById")
	void whenNotFindById_thenReturnObjectNotFoundException() {
		
		when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado! ID: " + ID));
		chamado.setId(ID); 
		try {
			chamadoservice.findById(ID);
		} catch(Exception ex) {
			assertEquals(ObjectNotFoundException.class, ex.getClass());
			assertEquals("Objeto não encontrado! ID: " + ID, ex.getMessage());
		}	
	} 

	@Test
	@DisplayName("test findAllChamado")
	void whenFindAllThenReturnAnListOfUChamado() {
		when(repository.findAll()).thenReturn(List.of(chamado));
		
		List<Chamado> response = chamadoservice.findAll();
		
		assertNotNull(response);
		
		assertEquals(1, response.size());
		assertEquals(ID, response.get(0).getId());
		assertEquals(TITULO, response.get(0).getTitulo());
		assertEquals(STATUS, response.get(0).getStatus());
		assertEquals(OBSERVACOES, response.get(0).getObservacoes());
		assertEquals(PRIORIDADE, response.get(0).getPrioridade());
		assertEquals(Chamado.class, response.get(0).getClass());
		assertEquals(cliente, response.get(0).getCliente());
		assertEquals(tecnico, response.get(0).getTecnico());
	}

	@Test
	@DisplayName("test createChamado")
	void wheCreateNewChamadoThenReturnSuccess() {
		
		when(repository.save(any())).thenReturn(chamado);
		Chamado response = chamadoservice.create(chamadoDTO);
		
		assertNotNull(response);
		assertEquals(Chamado.class, response.getClass());
	    assertEquals(Tecnico.class, response.getTecnico().getClass());
	    assertEquals(Cliente.class, response.getCliente().getClass());
	    assertEquals(ID, response.getId());
	    assertEquals(TITULO, response.getTitulo());
	    assertEquals(STATUS, response.getStatus());
	    assertEquals(OBSERVACOES, response.getObservacoes());
	    assertEquals(PRIORIDADE, response.getPrioridade());
	    assertEquals(tecnico, response.getTecnico());
	    assertEquals(cliente, response.getCliente());
	
	}

	@Test
	@DisplayName("test updateChamado")
	void whenUpdateChamadothenReturnSuccess() {
		chamado.setId(ID);
		when(repository.findById(anyInt())).thenReturn(Optional.of(chamado));
		
		chamado.setTitulo("Atualizado"); 
		
		when(repository.save(chamado)).thenReturn(chamado); 
		
		Chamado response = chamadoservice.update(ID,chamadoDTO);
		
		assertNotNull(response);
		assertEquals(Chamado.class, response.getClass());
	    assertEquals(Tecnico.class, response.getTecnico().getClass());
	    assertEquals(Cliente.class, response.getCliente().getClass());
	    assertEquals(ID, response.getId());
	    assertEquals("Atualizado", response.getTitulo());
	    assertEquals(STATUS, response.getStatus());
	    assertEquals(OBSERVACOES, response.getObservacoes());
	    assertEquals(PRIORIDADE, response.getPrioridade());
	    assertEquals(tecnico, response.getTecnico());
	    assertEquals(cliente, response.getCliente()); 
	
	} 

	private void startChamado() {
 
		tecnico = new Tecnico(ID, "Mateus", "778.899.120-13", "mateussilvapereira@gmail.com", encoder.encode("123"));
		cliente = new Cliente(ID, "Antonio", "111.661.890-74", "einstein@mail.com", encoder.encode("123"));

		tecnicoDTO = new TecnicoDTO(tecnico);
		clienteDTO = new ClienteDTO(cliente);
		chamado = new Chamado(ID, PRIORIDADE, STATUS, TITULO, OBSERVACOES, tecnico, cliente);
		chamadoDTO = new ChamadoDTO(chamado);
		optionalChamado = Optional.of(new Chamado(ID, PRIORIDADE, STATUS, TITULO, OBSERVACOES, tecnico, cliente));
		

	}
}