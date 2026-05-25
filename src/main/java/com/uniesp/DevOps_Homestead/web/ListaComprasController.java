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
            redirectAttributes.addFlashAttribute("mensagem", "Item criado com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "sucesso");

            return "redirect:/lista-compras";
        } catch (Exception e) {
            log.error("Erro ao criar item", e);
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao criar item: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
            return "redirect:/lista-compras/novo";
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
            redirectAttributes.addFlashAttribute("mensagem", "Item não encontrado");
            redirectAttributes.addFlashAttribute("tipo", "erro");
            return "redirect:/lista-compras";
        }
    }

    @PostMapping("/atualizar")
    public String atualizar(@ModelAttribute ListaCompras item,
                           RedirectAttributes redirectAttributes) {
        try {
            service.atualizar(item);
            redirectAttributes.addFlashAttribute("mensagem", "Item atualizado com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "sucesso");

            return "redirect:/lista-compras";
        } catch (Exception e) {
            log.error("Erro ao atualizar item", e);
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao atualizar item: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
            return "redirect:/lista-compras/" + item.getId() + "/editar";
        }
    }

    @GetMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.deletar(id);
            redirectAttributes.addFlashAttribute("mensagem", "Item deletado com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "sucesso");

            return "redirect:/lista-compras";
        } catch (Exception e) {
            log.error("Erro ao deletar item", e);
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao deletar item: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
            return "redirect:/lista-compras";
        }
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/lista-compras";
    }
    
}
