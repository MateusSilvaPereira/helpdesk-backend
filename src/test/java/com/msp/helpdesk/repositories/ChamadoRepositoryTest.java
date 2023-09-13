package com.msp.helpdesk.repositories;

import com.msp.helpdesk.domain.Chamado;
import com.msp.helpdesk.domain.Cliente;
import com.msp.helpdesk.domain.Tecnico;
import com.msp.helpdesk.domain.dtos.ChamadoDTO;
import com.msp.helpdesk.domain.dtos.ClienteDTO;
import com.msp.helpdesk.domain.dtos.TecnicoDTO;
import com.msp.helpdesk.domain.enums.Prioridade;
import com.msp.helpdesk.domain.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


@DataJpaTest
class ChamadoRepositoryTest {

	public static String TITULO = "Chamado 1";
	public static String OBSERVACOES = "Teste chamado 1";
	public static Status STATUS = Status.ENCERRADO;
	public static Prioridade PRIORIDADE = Prioridade.MEDIA;

	public Cliente cliente;
	public Tecnico tecnico;
	public Chamado chamado;
	public ChamadoDTO chamadoDTO;
	public TecnicoDTO tecnicoDTO;
	public ClienteDTO clienteDTO;

	@Mock
	public ChamadoRepository repository;

	@Mock
	public ClienteRepository clienteRepository;

	@Mock
	public TecnicoRepository tecnicoRepository;

	@BeforeEach
	void setUp() {
		startChamado();
	}

	@Test
	@DisplayName("Test findById")
	void whenFindById_thenReturnChamadoObject() {

		Chamado chamado = this.chamado;
		chamado.setId(1);
		when(repository.findById(1)).thenReturn(Optional.of(chamado));

		Optional<Chamado> response = repository.findById(1);

		assertTrue(response.isPresent());
		assertNotNull(response);
		assertEquals(1, response.get().getId());
		assertEquals(PRIORIDADE.getCodigo(), response.get().getPrioridade().getCodigo());
		assertEquals(STATUS.getCodigo(), response.get().getStatus().getCodigo());
		assertEquals(TITULO, response.get().getTitulo());
		assertEquals(OBSERVACOES, response.get().getObservacoes());
		assertEquals(chamado, response.get());
		assertEquals(tecnico, response.get().getTecnico());
		assertEquals(cliente, response.get().getCliente());
	}

	@Test
	@DisplayName("Test findAll")
	void whenFindAll_thenReturnListOfChamado() {
		Chamado chamado = this.chamado;

		chamado.setId(1);

		when(repository.findAll()).thenReturn(List.of(chamado));

		List<Chamado> response = repository.findAll();

		assertNotNull(response);
		assertEquals(1, response.get(0).getId());
		assertEquals(PRIORIDADE.getCodigo(), response.get(0).getPrioridade().getCodigo());
		assertEquals(STATUS.getCodigo(), response.get(0).getStatus().getCodigo());
		assertEquals(TITULO, response.get(0).getTitulo());
		assertEquals(OBSERVACOES, response.get(0).getObservacoes());
		assertEquals(chamado, response.get(0));
		assertEquals(tecnico, response.get(0).getTecnico());
		assertEquals(cliente, response.get(0).getCliente());
	}

	@Test
	@DisplayName("Test saveChamado")
	void whenSave_thenRetunChamadoCreated() {

		Chamado chamado = this.chamado;

		when(repository.save(any())).thenReturn(chamado);

		Chamado response = repository.save(chamado);

		assertNotNull(response);
		assertEquals(1, response.getId());
		assertEquals(PRIORIDADE.getCodigo(), response.getPrioridade().getCodigo());
		assertEquals(STATUS.getCodigo(), response.getStatus().getCodigo());
		assertEquals(TITULO, response.getTitulo());
		assertEquals(OBSERVACOES, response.getObservacoes());
		assertEquals(chamado, response);
		assertEquals(tecnico, response.getTecnico());
		assertEquals(cliente, response.getCliente());
		verify(repository, times(1)).save(chamado);
	}
	@Test
	@DisplayName("Test updateChamado")
	void whenUpdate_thenRetunChamadoSuccess() {

		repository.save(this.chamado);

		Chamado newChamado = this.chamado;
		 chamado.setId(1);
		 newChamado.setStatus(Status.ABERTO);
		 newChamado.setPrioridade(Prioridade.MEDIA);

		when(repository.save(any())).thenReturn(chamado);

		Chamado response = repository.save(newChamado);

		assertNotNull(response);
		assertEquals(1, response.getId());
		assertEquals(1, response.getPrioridade().getCodigo());
		assertEquals(0, response.getStatus().getCodigo());
		assertEquals(TITULO, response.getTitulo());
		assertEquals(OBSERVACOES, response.getObservacoes());
		assertEquals(chamado, response);
		assertEquals(tecnico, response.getTecnico());
		assertEquals(cliente, response.getCliente());
}

	private void startChamado() {

		tecnico = new Tecnico(1, "Mateus", "778.899.120-13", "mateussilvapereira@gmail.com", "123");
		cliente = new Cliente(1, "Antonio", "111.661.890-74", "einstein@mail.com", "123");

		tecnicoDTO = new TecnicoDTO(tecnico);
		clienteDTO = new ClienteDTO(cliente);
		chamado = new Chamado(1, PRIORIDADE, STATUS, TITULO, OBSERVACOES, tecnico, cliente);
		chamadoDTO = new ChamadoDTO(chamado);

	}

}
