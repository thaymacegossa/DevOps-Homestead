package com.uniesp.DevOps_Homestead.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uniesp.DevOps_Homestead.domain.ListaCompras;
import com.uniesp.DevOps_Homestead.service.ListaComprasService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/lista-compras")
@RequiredArgsConstructor
@Slf4j
public class ListaComprasController {

    private final String FLASH_MESSAGE = "mensagem";
    private final String FLASH_TYPE = "tipo";
    private final String TIPO_SUCESSO = "sucesso";
    private final String TIPO_ERRO = "erro";
    private final String REDIRECT_LISTAGEM = "redirect:/lista-compras";

    private final ListaComprasService service;

    @GetMapping
    public String listar(Model model) {
        List<ListaCompras> itens = service.listarTodos();
        BigDecimal totalGeral = itens.stream()
                .map(ListaCompras::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("itens", itens);
        model.addAttribute("totalGeral", totalGeral);
        model.addAttribute("totalItens", itens.size());
        return "lista-compras/list";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("item", new ListaCompras());
        model.addAttribute("isEdicao", false);
        return "lista-compras/form";
    }

    @PostMapping
    public String criar(@ModelAttribute ListaCompras item,
                       RedirectAttributes redirectAttributes) {
        try {
            service.criar(item);
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Item criado com sucesso!");
            redirectAttributes.addFlashAttribute(FLASH_TYPE, TIPO_SUCESSO);

            return REDIRECT_LISTAGEM;
        } catch (Exception e) {
            log.error("Erro ao criar item", e);
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Erro ao criar item: " + e.getMessage());
            redirectAttributes.addFlashAttribute(FLASH_TYPE, TIPO_ERRO);
            return REDIRECT_LISTAGEM + "/novo";
        }
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            ListaCompras item = service.buscarPorId(id);
            model.addAttribute("item", item);
            model.addAttribute("isEdicao", true);

            return "lista-compras/form";
        } catch (Exception e) {
            log.error("Erro ao acessar formulário de edição", e);
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Item não encontrado");
            redirectAttributes.addFlashAttribute(FLASH_TYPE, TIPO_ERRO);
            return REDIRECT_LISTAGEM;
        }
    }

    @PostMapping("/atualizar")
    public String atualizar(@ModelAttribute ListaCompras item,
                           RedirectAttributes redirectAttributes) {
        try {
            service.atualizar(item);
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Item atualizado com sucesso!");
            redirectAttributes.addFlashAttribute(FLASH_TYPE, TIPO_SUCESSO);

            return REDIRECT_LISTAGEM;
        } catch (Exception e) {
            log.error("Erro ao atualizar item", e);
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Erro ao atualizar item: " + e.getMessage());
            redirectAttributes.addFlashAttribute(FLASH_TYPE, TIPO_ERRO);
            return REDIRECT_LISTAGEM + "/" + item.getId() + "/editar";
        }
    }

    @GetMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.deletar(id);
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Item deletado com sucesso!");
            redirectAttributes.addFlashAttribute(FLASH_TYPE, TIPO_SUCESSO);

            return REDIRECT_LISTAGEM;
        } catch (Exception e) {
            log.error("Erro ao deletar item", e);
            redirectAttributes.addFlashAttribute(FLASH_MESSAGE, "Erro ao deletar item: " + e.getMessage());
            redirectAttributes.addFlashAttribute(FLASH_TYPE, TIPO_ERRO);
            return REDIRECT_LISTAGEM;
        }
    }

    @GetMapping("/")
    public String index() {
        return REDIRECT_LISTAGEM;
    }
    
}
