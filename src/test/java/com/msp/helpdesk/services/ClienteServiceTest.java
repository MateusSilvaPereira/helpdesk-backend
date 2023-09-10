package com.msp.helpdesk.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.msp.helpdesk.domain.Cliente;
import com.msp.helpdesk.domain.dtos.ClienteDTO;
import com.msp.helpdesk.repositories.ClienteRepository;
import com.msp.helpdesk.repositories.PessoaRepository;
import com.msp.helpdesk.services.exceptions.ObjectNotFoundException;

@SpringBootTest
class ClienteServiceTest {

	@InjectMocks
	private ClienteService clienteService;
	@Mock
	private ClienteRepository repository;
	@Mock
	private PessoaRepository pessoaRepository;
	
	@Mock
	private BCryptPasswordEncoder encoder;
	
	private Cliente cliente;
	private ClienteDTO clienteDTO;
	private Optional<Cliente> optionalCliente;
	
	private static final Integer ID = 1;
	private static final String NOME = "Antonio";
	private static final String CPF = "111.661.890-74";
	private static final String EMAIL ="Mateus@mail.com";
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		startCliente();
	}
	
	@Test
	@DisplayName("Test ClienteFindById")
	void whenFindById_ThenReturnClienteInstance() {
		
		when(repository.findById(anyInt())).thenReturn(optionalCliente);
		
		Cliente response = clienteService.findById(ID);
		
		assertNotNull(response);
		assertEquals(ID, response.getId());
		assertEquals(NOME, response.getNome());
		assertEquals(CPF, response.getCpf());
		assertEquals(EMAIL, response.getEmail());
	}
	
	
	@Test
	@DisplayName("Test NotFindById")
	void whenFindById_ThenReturnObjectNotFoundException() {
		
		when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não Encontrado! id: " + ID));
		   cliente.setId(ID);
		try {
			clienteService.findById(ID);
		} catch (Exception ex) {
			assertEquals(ObjectNotFoundException.class, ex.getClass());
			assertEquals(("Objeto não Encontrado! id: " + ID), ex.getMessage());
		} 
	}
	
	@Test
	@DisplayName("Test findAllClientes")
	void whenFindAll_thenReturnListOfCliente() {
		
		when(repository.findAll()).thenReturn(List.of(cliente));
		
		List<Cliente> response = clienteService.findAll();
		
		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(ID, response.get(0).getId());
		assertEquals(NOME, response.get(0).getNome());
		assertEquals(CPF, response.get(0).getCpf());
		assertEquals(EMAIL, response.get(0).getEmail());
	}
	
	@Test
	@DisplayName("test createCliente")
	void whenCreateNewCliente_thenReturnSuccess() {
		when(repository.save(any())).thenReturn(cliente);
		
		Cliente response = clienteService.create(clienteDTO);

		assertNotNull(response);  
		assertEquals(ID, response.getId());
		assertEquals(NOME, response.getNome());
		assertEquals(CPF, response.getCpf());
		assertEquals(EMAIL, response.getEmail()); 
		
	}
	
	
	@Test
	@DisplayName("Test UpdateCliente")
	void whenUpdateCliente_thenReturnSuccess() {
		
		cliente.setId(ID);
		
		when(repository.findById(anyInt())).thenReturn(Optional.of(cliente));
		
		cliente.setNome("MSP");
		cliente.setEmail("mateussilvapereira2018@gmail.com");
		
		when(repository.save(cliente)).thenReturn(cliente);
		
		Cliente response = clienteService.update(ID, clienteDTO);
		
		assertNotNull(response); 
		
		assertEquals(ID, response.getId());
		assertEquals("MSP", response.getNome());
		assertEquals(CPF, response.getCpf());
		assertEquals("mateussilvapereira2018@gmail.com", response.getEmail()); 
	}
	
	@Test
	@DisplayName("Test deleteById")
	void whenDeleteById_thenReturnSuccess() {
		when(repository.findById(anyInt())).thenReturn(optionalCliente); 
		doNothing().when(repository).deleteById(anyInt());
		clienteService.delete(ID);
		verify(repository, times(1)).deleteById(anyInt());
		
	}
	
	@Test
	@DisplayName("Test deleteByIdNotFound")
	void whenDeleteByIdNotFound_thenReturnObjectNotFound() {
		
		when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado! ID: " + ID));
		
		try {
			clienteService.delete(ID);
		} catch(Exception ex) {
			assertEquals(ObjectNotFoundException.class, ex.getClass());
			assertEquals("Objeto não encontrado! ID: " + ID, ex.getMessage());
		}
	}
	
	
	private void startCliente() {
		
		cliente = new Cliente(ID, NOME, CPF, EMAIL, "123");
		optionalCliente = Optional.of(new Cliente(ID, NOME, CPF, EMAIL, "123")); 
		clienteDTO = new ClienteDTO(cliente);
	}

}
