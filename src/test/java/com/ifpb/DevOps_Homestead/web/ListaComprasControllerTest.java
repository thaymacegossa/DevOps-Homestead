package com.ifpb.DevOps_Homestead.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.ifpb.DevOps_Homestead.domain.ListaCompras;
import com.ifpb.DevOps_Homestead.service.ListaComprasService;

@ExtendWith(MockitoExtension.class)
class ListaComprasControllerTest {

    @Mock
    private ListaComprasService service;

    @InjectMocks
    private ListaComprasController controller;

    @Test
    void listarDevePopularModelERetornarView() {
        ListaCompras item = new ListaCompras();
        item.setTotal(new BigDecimal("10.00"));
        when(service.listarTodos()).thenReturn(List.of(item));
        Model model = new ExtendedModelMap();

        String view = controller.listar(model);

        assertEquals("lista-compras/list", view);
        assertEquals(1, model.getAttribute("totalItens"));
        assertEquals(new BigDecimal("10.00"), model.getAttribute("totalGeral"));
        assertTrue(model.containsAttribute("itens"));
    }

    @Test
    void novoFormDeveRetornarFormulario() {
        Model model = new ExtendedModelMap();

        String view = controller.novoForm(model);

        assertEquals("lista-compras/form", view);
        assertEquals(false, model.getAttribute("isEdicao"));
        assertTrue(model.containsAttribute("item"));
    }

    @Test
    void criarDeveRedirecionarParaListaQuandoSucesso() {
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.criar(new ListaCompras(), redirect);

        assertEquals("redirect:/lista-compras", view);
        assertEquals("sucesso", redirect.getFlashAttributes().get("tipo"));
    }

    @Test
    void criarDeveRedirecionarParaNovoQuandoErro() {
        doThrow(new IllegalArgumentException("falha")).when(service).criar(org.mockito.ArgumentMatchers.any(ListaCompras.class));
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.criar(new ListaCompras(), redirect);

        assertEquals("redirect:/lista-compras/novo", view);
        assertEquals("erro", redirect.getFlashAttributes().get("tipo"));
    }

    @Test
    void editarFormDeveRetornarFormularioQuandoSucesso() {
        when(service.buscarPorId(1L)).thenReturn(new ListaCompras());
        Model model = new ExtendedModelMap();
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.editarForm(1L, model, redirect);

        assertEquals("lista-compras/form", view);
        assertEquals(true, model.getAttribute("isEdicao"));
    }

    @Test
    void editarFormDeveRedirecionarQuandoErro() {
        when(service.buscarPorId(1L)).thenThrow(new IllegalArgumentException("nao encontrado"));
        Model model = new ExtendedModelMap();
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.editarForm(1L, model, redirect);

        assertEquals("redirect:/lista-compras", view);
        assertEquals("erro", redirect.getFlashAttributes().get("tipo"));
    }

    @Test
    void atualizarDeveRedirecionarParaListaQuandoSucesso() {
        ListaCompras item = new ListaCompras();
        item.setId(2L);
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.atualizar(item, redirect);

        assertEquals("redirect:/lista-compras", view);
        assertEquals("sucesso", redirect.getFlashAttributes().get("tipo"));
    }

    @Test
    void atualizarDeveRedirecionarParaEdicaoQuandoErro() {
        ListaCompras item = new ListaCompras();
        item.setId(2L);
        doThrow(new IllegalArgumentException("falha")).when(service).atualizar(org.mockito.ArgumentMatchers.any(ListaCompras.class));
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.atualizar(item, redirect);

        assertEquals("redirect:/lista-compras/2/editar", view);
        assertEquals("erro", redirect.getFlashAttributes().get("tipo"));
    }

    @Test
    void deletarDeveRedirecionarParaListaQuandoSucesso() {
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.deletar(3L, redirect);

        assertEquals("redirect:/lista-compras", view);
        assertEquals("sucesso", redirect.getFlashAttributes().get("tipo"));
    }

    @Test
    void deletarDeveRedirecionarParaListaQuandoErro() {
        doThrow(new IllegalArgumentException("falha")).when(service).deletar(3L);
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.deletar(3L, redirect);

        assertEquals("redirect:/lista-compras", view);
        assertEquals("erro", redirect.getFlashAttributes().get("tipo"));
    }

    @Test
    void indexDeveRedirecionarParaLista() {
        assertEquals("redirect:/lista-compras", controller.index());
    }
}
