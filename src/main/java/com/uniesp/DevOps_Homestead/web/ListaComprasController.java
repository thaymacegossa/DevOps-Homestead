package com.uniesp.DevOps_Homestead.web;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uniesp.DevOps_Homestead.domain.ListaCompras;
import com.uniesp.DevOps_Homestead.service.ListaComprasService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/lista-compras")
@RequiredArgsConstructor
@Slf4j
public class ListaComprasController {

    private static String REDIRECT_LISTAGEM = "redirect:/lista-compras";

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
        return HelperController.criarComRedirecionamento(
                () -> service.criar(item),
                "Item criado com sucesso!",
                "Erro ao criar item",
                REDIRECT_LISTAGEM,
                REDIRECT_LISTAGEM + "/novo",
                redirectAttributes);
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
            return REDIRECT_LISTAGEM;
        }
    }

    @PostMapping("/atualizar")
    public String atualizar(@ModelAttribute ListaCompras item,
                           RedirectAttributes redirectAttributes) {
        return HelperController.atualizarComRedirecionamento(
                () -> service.atualizar(item),
                "Item atualizado com sucesso!",
                "Erro ao atualizar item",
                REDIRECT_LISTAGEM,
                () -> REDIRECT_LISTAGEM + "/" + item.getId() + "/editar",
                redirectAttributes);
    }

    @GetMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        return HelperController.deletarComRedirecionamento(
                () -> service.deletar(id),
                "Item deletado com sucesso!",
                "Erro ao deletar item",
                REDIRECT_LISTAGEM,
                redirectAttributes);
    }

    @GetMapping("/")
    public String index() {
        return REDIRECT_LISTAGEM;
    }
    
}
