package com.ifpb.DevOps_Homestead.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ifpb.DevOps_Homestead.domain.ListaAfazeres;
import com.ifpb.DevOps_Homestead.repository.ListaAfazeresRepository;

@ExtendWith(MockitoExtension.class)
class ListaAfazeresServiceTest {

    @Mock
    private ListaAfazeresRepository repository;

    @InjectMocks
    private ListaAfazeresService service;

    @Test
    void listarTodosDeveRetornarLista() {
        ListaAfazeres tarefa = novaTarefa();
        when(repository.findAll()).thenReturn(List.of(tarefa));

        List<ListaAfazeres> resultado = service.listarTodos();

        assertEquals(1, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorIdDeveRetornarQuandoExistir() {
        ListaAfazeres tarefa = novaTarefa();
        tarefa.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(tarefa));

        ListaAfazeres resultado = service.buscarPorId(1L);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void buscarPorIdDeveLancarExcecaoQuandoNaoExistir() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.buscarPorId(99L));
    }

    @Test
    void criarDeveValidarNormalizarESalvar() {
        ListaAfazeres tarefa = novaTarefa();
        when(repository.save(any(ListaAfazeres.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ListaAfazeres salvo = service.criar(tarefa);

        assertEquals("Estudar", salvo.getDescricao());
        assertNotNull(salvo.getCriadoEm());
        assertNotNull(salvo.getAtualizadoEm());
        verify(repository).save(any(ListaAfazeres.class));
    }

    @Test
    void atualizarDeveAtualizarCamposESalvar() {
        ListaAfazeres existente = novaTarefa();
        existente.setId(2L);

        ListaAfazeres formulario = novaTarefa();
        formulario.setId(2L);
        formulario.setDescricao("  Estudar Java  ");
        formulario.setStatus("CONCLUIDA");
        formulario.setImportancia(5);
        formulario.setPrazoConclusao(LocalDate.now().plusDays(5));

        when(repository.findById(2L)).thenReturn(Optional.of(existente));
        when(repository.save(any(ListaAfazeres.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ListaAfazeres atualizado = service.atualizar(formulario);

        assertEquals("Estudar Java", atualizado.getDescricao());
        assertEquals("CONCLUIDA", atualizado.getStatus());
        assertEquals(5, atualizado.getImportancia());
        assertEquals(formulario.getPrazoConclusao(), atualizado.getPrazoConclusao());
        assertNotNull(atualizado.getAtualizadoEm());
    }

    @Test
    void atualizarDeveLancarExcecaoQuandoNaoExistir() {
        ListaAfazeres formulario = novaTarefa();
        formulario.setId(100L);
        when(repository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(formulario));
    }

    @Test
    void deletarDeveRemoverQuandoExistir() {
        ListaAfazeres tarefa = novaTarefa();
        tarefa.setId(3L);
        when(repository.findById(3L)).thenReturn(Optional.of(tarefa));

        service.deletar(3L);

        verify(repository).delete(tarefa);
    }

    @Test
    void deletarDeveLancarExcecaoQuandoNaoExistir() {
        when(repository.findById(50L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.deletar(50L));
    }

    @Test
    void criarDeveLancarExcecaoQuandoDescricaoForInvalida() {
        ListaAfazeres tarefa = novaTarefa();
        tarefa.setDescricao("   ");

        assertThrows(IllegalArgumentException.class, () -> service.criar(tarefa));
    }

    @Test
    void criarDeveLancarExcecaoQuandoStatusForNulo() {
        ListaAfazeres tarefa = novaTarefa();
        tarefa.setStatus(null);

        assertThrows(IllegalArgumentException.class, () -> service.criar(tarefa));
    }

    @Test
    void criarDeveLancarExcecaoQuandoImportanciaForNula() {
        ListaAfazeres tarefa = novaTarefa();
        tarefa.setImportancia(null);

        assertThrows(IllegalArgumentException.class, () -> service.criar(tarefa));
    }

    private ListaAfazeres novaTarefa() {
        ListaAfazeres tarefa = new ListaAfazeres();
        tarefa.setDescricao("  Estudar  ");
        tarefa.setStatus("PENDENTE");
        tarefa.setImportancia(3);
        tarefa.setPrazoConclusao(LocalDate.now().plusDays(1));
        return tarefa;
    }
}
