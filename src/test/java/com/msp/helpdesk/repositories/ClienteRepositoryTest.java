package com.msp.helpdesk.repositories;

import com.msp.helpdesk.domain.Chamado;
import com.msp.helpdesk.domain.Cliente;
import com.msp.helpdesk.domain.Tecnico;
import com.msp.helpdesk.domain.dtos.ChamadoDTO;
import com.msp.helpdesk.domain.dtos.ClienteDTO;
import com.msp.helpdesk.domain.dtos.TecnicoDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class ClienteRepositoryTest {

    @Mock
    public ClienteRepository repository;

    public Cliente cliente;
    public Cliente cliente2;
    public ClienteDTO clienteDTO;
    Optional<Cliente> clienteOptional;
    @BeforeEach
    void setUp() {
        startCliente();
    }

    @Test
    @DisplayName("Test findById")
    void whenFindById_thenReturnClienteObject(){
        Cliente cliente1 = this.cliente;

        cliente1.setId(1);

        when(repository.findById(any())).thenReturn(Optional.of(cliente));

        Optional<Cliente> response = repository.findById(1);

        assertNotNull(response);
        assertEquals(1, response.get().getId());
        assertEquals("Antonio", response.get().getNome());
        assertEquals("111.661.890-74", response.get().getCpf());
        assertEquals("einstein@mail.com", response.get().getEmail());
        assertEquals("123", response.get().getSenha());
    }


    @Test
    @DisplayName("Test findAll")
    void whenFindById_thenReturnListClienteObject() {
        Cliente cliente1 = this.cliente;
        cliente1.setId(1);
        when(repository.findAll()).thenReturn(List.of(cliente));

        List<Cliente> response = repository.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(1, response.get(0).getId());
        assertEquals("Antonio", response.get(0).getNome());
        assertEquals("111.661.890-74", response.get(0).getCpf());
        assertEquals("einstein@mail.com", response.get(0).getEmail());
        assertEquals("123", response.get(0).getSenha());

    }

    @Test
    @DisplayName("Test createCliente")
    void whenSave_thenReturnClienteObject() {
        Cliente cliente1 = this.cliente;
        cliente1.setId(1);
        when(repository.save(any())).thenReturn(cliente);

        Cliente response = repository.save(cliente1);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Antonio", response.getNome());
        assertEquals("111.661.890-74", response.getCpf());
        assertEquals("einstein@mail.com", response.getEmail());
        assertEquals("123", response.getSenha());

    }

    @Test
    @DisplayName("Test updateCliente")
    void whenUpdateCliente_thenReturnClienteUpdate() {

        repository.save(this.cliente);

        Cliente newCliente = cliente;
        newCliente.setId(1);
        newCliente.setNome("Mateus");
        newCliente.setEmail("mateussilva@gmail.com");

        when(repository.save(any())).thenReturn(cliente);

        Cliente response = repository.save(newCliente);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Mateus", response.getNome());
        assertEquals("111.661.890-74", response.getCpf());
        assertEquals("mateussilva@gmail.com", response.getEmail());
        assertEquals("123", response.getSenha());

    }


    @Test
    @DisplayName("Test deleteCliente")
    void whenDeleteCliente_thenReturnSuccess() {

        when(repository.findById(anyInt())).thenReturn(clienteOptional);

        doNothing().when(repository).deleteById(anyInt());
        repository.delete(cliente);

        verify(repository, times(1)).delete(any());
    }
        void startCliente() {
        cliente = new Cliente(1, "Antonio", "111.661.890-74", "einstein@mail.com", "123");
            clienteDTO = new ClienteDTO(cliente);
            clienteOptional = Optional.of(new Cliente(1, "Antonio", "111.661.890-74", "einstein@mail.com", "123"));
    }
}