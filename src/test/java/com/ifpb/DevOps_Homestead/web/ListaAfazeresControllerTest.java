package com.ifpb.DevOps_Homestead.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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

import com.ifpb.DevOps_Homestead.domain.ListaAfazeres;
import com.ifpb.DevOps_Homestead.service.ListaAfazeresService;

@ExtendWith(MockitoExtension.class)
class ListaAfazeresControllerTest {

    @Mock
    private ListaAfazeresService service;

    @InjectMocks
    private ListaAfazeresController controller;

    @Test
    void listarDevePopularModelERetornarView() {
        when(service.listarTodos()).thenReturn(List.of(new ListaAfazeres(), new ListaAfazeres()));
        Model model = new ExtendedModelMap();

        String view = controller.listar(model);

        assertEquals("lista-afazeres/list", view);
        assertEquals(2, model.getAttribute("totalItens"));
        assertTrue(model.containsAttribute("itens"));
    }

    @Test
    void novoFormDeveRetornarFormulario() {
        Model model = new ExtendedModelMap();

        String view = controller.novoForm(model);

        assertEquals("lista-afazeres/form", view);
        assertEquals(false, model.getAttribute("isEdicao"));
        assertTrue(model.containsAttribute("item"));
    }

    @Test
    void criarDeveRedirecionarParaListaQuandoSucesso() {
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.criar(new ListaAfazeres(), redirect);

        assertEquals("redirect:/lista-afazeres", view);
        assertEquals("sucesso", redirect.getFlashAttributes().get("tipo"));
    }

    @Test
    void criarDeveRedirecionarParaNovoQuandoErro() {
        doThrow(new IllegalArgumentException("falha")).when(service).criar(org.mockito.ArgumentMatchers.any(ListaAfazeres.class));
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.criar(new ListaAfazeres(), redirect);

        assertEquals("redirect:/lista-afazeres/novo", view);
        assertEquals("erro", redirect.getFlashAttributes().get("tipo"));
    }

    @Test
    void editarFormDeveRetornarFormularioQuandoSucesso() {
        when(service.buscarPorId(1L)).thenReturn(new ListaAfazeres());
        Model model = new ExtendedModelMap();

        String view = controller.editarForm(1L, model);

        assertEquals("lista-afazeres/form", view);
        assertEquals(true, model.getAttribute("isEdicao"));
    }

    @Test
    void editarFormDeveRedirecionarQuandoErro() {
        when(service.buscarPorId(1L)).thenThrow(new IllegalArgumentException("nao encontrado"));
        Model model = new ExtendedModelMap();

        String view = controller.editarForm(1L, model);

        assertEquals("redirect:/lista-afazeres", view);
    }

    @Test
    void atualizarDeveRedirecionarParaListaQuandoSucesso() {
        ListaAfazeres item = new ListaAfazeres();
        item.setId(2L);
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.atualizar(item, redirect);

        assertEquals("redirect:/lista-afazeres", view);
        assertEquals("sucesso", redirect.getFlashAttributes().get("tipo"));
    }

    @Test
    void atualizarDeveRedirecionarParaEdicaoQuandoErro() {
        ListaAfazeres item = new ListaAfazeres();
        item.setId(2L);
        doThrow(new IllegalArgumentException("falha")).when(service).atualizar(org.mockito.ArgumentMatchers.any(ListaAfazeres.class));
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.atualizar(item, redirect);

        assertEquals("redirect:/lista-afazeres/2/editar", view);
        assertEquals("erro", redirect.getFlashAttributes().get("tipo"));
    }

    @Test
    void deletarDeveRedirecionarParaListaQuandoSucesso() {
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.deletar(3L, redirect);

        assertEquals("redirect:/lista-afazeres", view);
        assertEquals("sucesso", redirect.getFlashAttributes().get("tipo"));
    }

    @Test
    void deletarDeveRedirecionarParaListaQuandoErro() {
        doThrow(new IllegalArgumentException("falha")).when(service).deletar(3L);
        RedirectAttributes redirect = new RedirectAttributesModelMap();

        String view = controller.deletar(3L, redirect);

        assertEquals("redirect:/lista-afazeres", view);
        assertEquals("erro", redirect.getFlashAttributes().get("tipo"));
    }

    @Test
    void indexDeveRedirecionarParaLista() {
        assertEquals("redirect:/lista-afazeres", controller.index());
    }
}
