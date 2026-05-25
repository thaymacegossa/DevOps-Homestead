package com.uniesp.DevOps_Homestead.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ifpb.DevOps_Homestead.domain.ListaCompras;
import com.ifpb.DevOps_Homestead.repository.ListaComprasRepository;
import com.ifpb.DevOps_Homestead.service.ListaComprasService;

@ExtendWith(MockitoExtension.class)
class ListaComprasServiceTest {

    @Mock
    private ListaComprasRepository repository;

    @InjectMocks
    private ListaComprasService service;

    @Test
    void listarTodosDeveRetornarItens() {
        when(repository.findAll()).thenReturn(List.of(novoItem()));

        List<ListaCompras> resultado = service.listarTodos();

        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void buscarPorIdDeveRetornarQuandoExistir() {
        ListaCompras item = novoItem();
        item.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(item));

        ListaCompras resultado = service.buscarPorId(1L);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void buscarPorIdDeveLancarExcecaoQuandoNaoExistir() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.buscarPorId(2L));
    }

    @Test
    void criarDeveCalcularTotalEDataHora() {
        ListaCompras item = novoItem();
        when(repository.save(any(ListaCompras.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ListaCompras salvo = service.criar(item);

        assertEquals("Arroz", salvo.getDescricao());
        assertEquals(0, new BigDecimal("25.00").compareTo(salvo.getTotal()));
        assertNotNull(salvo.getDataHora());
        verify(repository).save(any(ListaCompras.class));
    }

    @Test
    void atualizarDeveAtualizarCamposERecalcularTotal() {
        ListaCompras existente = novoItem();
        existente.setId(3L);

        ListaCompras formulario = novoItem();
        formulario.setId(3L);
        formulario.setDescricao("  Feijao  ");
        formulario.setPrecoUnitario(new BigDecimal("8.00"));
        formulario.setQuantidade(new BigDecimal("2.00"));

        when(repository.findById(3L)).thenReturn(Optional.of(existente));
        when(repository.save(any(ListaCompras.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ListaCompras atualizado = service.atualizar(formulario);

        assertEquals("Feijao", atualizado.getDescricao());
        assertEquals(new BigDecimal("16.0000"), atualizado.getTotal());
        verify(repository).save(any(ListaCompras.class));
    }

    @Test
    void atualizarDeveLancarExcecaoQuandoNaoExistir() {
        ListaCompras formulario = novoItem();
        formulario.setId(80L);
        when(repository.findById(80L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.atualizar(formulario));
    }

    @Test
    void deletarDeveRemoverQuandoExistir() {
        when(repository.existsById(7L)).thenReturn(true);

        service.deletar(7L);

        verify(repository).deleteById(7L);
    }

    @Test
    void deletarDeveLancarExcecaoQuandoNaoExistir() {
        when(repository.existsById(7L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> service.deletar(7L));
        verify(repository, never()).deleteById(7L);
    }

    @Test
    void criarDeveLancarExcecaoQuandoPrecoInvalido() {
        ListaCompras item = novoItem();
        item.setPrecoUnitario(BigDecimal.ZERO);

        assertThrows(IllegalArgumentException.class, () -> service.criar(item));
    }

    @Test
    void criarDeveLancarExcecaoQuandoQuantidadeInvalida() {
        ListaCompras item = novoItem();
        item.setQuantidade(new BigDecimal("-1"));

        assertThrows(IllegalArgumentException.class, () -> service.criar(item));
    }

    @Test
    void criarDeveLancarExcecaoQuandoDescricaoInvalida() {
        ListaCompras item = novoItem();
        item.setDescricao("  ");

        assertThrows(IllegalArgumentException.class, () -> service.criar(item));
    }

    private ListaCompras novoItem() {
        ListaCompras item = new ListaCompras();
        item.setDescricao("  Arroz  ");
        item.setPrecoUnitario(new BigDecimal("10.00"));
        item.setQuantidade(new BigDecimal("2.50"));
        item.setTotal(BigDecimal.ZERO);
        return item;
    }
}
