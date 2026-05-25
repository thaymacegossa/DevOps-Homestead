package com.uniesp.DevOps_Homestead.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uniesp.DevOps_Homestead.domain.ListaAfazeres;
import com.uniesp.DevOps_Homestead.service.ListaAfazeresService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/lista-afazeres")
@RequiredArgsConstructor
@Slf4j
public class ListaAfazeresController {

    private static String REDIRECT_LISTAGEM = "redirect:/lista-afazeres";

    private final ListaAfazeresService service;

    @GetMapping
    public String listar(Model model) {
        var itens = service.listarTodos();
        model.addAttribute("itens", itens);
        model.addAttribute("totalItens", itens.size());
        return "lista-afazeres/list";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("item", new ListaAfazeres());
        model.addAttribute("isEdicao", false);
        return "lista-afazeres/form";
    }

    @PostMapping
    public String criar(@ModelAttribute ListaAfazeres item,
                       RedirectAttributes redirectAttributes) {
        return HelperController.criarComRedirecionamento(
                () -> service.criar(item),
                "Tarefa criada com sucesso!",
                "Erro ao criar tarefa",
                REDIRECT_LISTAGEM,
                REDIRECT_LISTAGEM + "/novo",
                redirectAttributes);
            
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        try {
            var item = service.buscarPorId(id);
            model.addAttribute("item", item);
            model.addAttribute("isEdicao", true);
            return "lista-afazeres/form";
        } catch (Exception e) {
            log.error("Erro ao buscar tarefa para edição", e);
            model.addAttribute("mensagem", "Tarefa não encontrada: " + e.getMessage());
            model.addAttribute("tipo", "erro");
            return REDIRECT_LISTAGEM;
        }
    }

    @PostMapping("/atualizar")
    public String atualizar(@ModelAttribute ListaAfazeres item,
                            RedirectAttributes redirectAttributes) {
        return HelperController.atualizarComRedirecionamento(
                () -> service.atualizar(item),
                "Tarefa atualizada com sucesso!",
                "Erro ao atualizar tarefa",
                REDIRECT_LISTAGEM,
                () -> REDIRECT_LISTAGEM + "/" + item.getId() + "/editar",
                redirectAttributes);
    }

    @GetMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        return HelperController.deletarComRedirecionamento(
                () -> service.deletar(id),
                "Tarefa deletada com sucesso!",
                "Erro ao deletar tarefa",
                REDIRECT_LISTAGEM,
                redirectAttributes);
    }

    @GetMapping("/")
    public String index() {
        return REDIRECT_LISTAGEM;
    }
    
}
