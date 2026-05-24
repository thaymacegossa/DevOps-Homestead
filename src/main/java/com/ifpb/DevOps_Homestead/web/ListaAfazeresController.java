package com.ifpb.DevOps_Homestead.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifpb.DevOps_Homestead.domain.ListaAfazeres;
import com.ifpb.DevOps_Homestead.service.ListaAfazeresService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/lista-afazeres")
@RequiredArgsConstructor
@Slf4j
public class ListaAfazeresController {

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
        try {
            service.criar(item);
            redirectAttributes.addFlashAttribute("mensagem", "Tarefa criada com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "sucesso");

            return "redirect:/lista-afazeres";
        } catch (Exception e) {
            log.error("Erro ao criar tarefa", e);
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao criar tarefa: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
            return "redirect:/lista-afazeres/novo";
        }
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
            return "redirect:/lista-afazeres";
        }
    }

    @PostMapping("/atualizar")
    public String atualizar(@ModelAttribute ListaAfazeres item,
                            RedirectAttributes redirectAttributes) {
        try {
            service.atualizar(item);
            redirectAttributes.addFlashAttribute("mensagem", "Tarefa atualizada com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "sucesso");
            
            return "redirect:/lista-afazeres";
        } catch (Exception e) {
            log.error("Erro ao atualizar tarefa", e);
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao atualizar tarefa: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
            return "redirect:/lista-afazeres/" + item.getId() + "/editar";
        }
    }

    @GetMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.deletar(id);
            redirectAttributes.addFlashAttribute("mensagem", "Tarefa deletada com sucesso!");
            redirectAttributes.addFlashAttribute("tipo", "sucesso");
        } catch (Exception e) {
            log.error("Erro ao deletar tarefa", e);
            redirectAttributes.addFlashAttribute("mensagem", "Erro ao deletar tarefa: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "erro");
        }
        return "redirect:/lista-afazeres";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/lista-afazeres";
    }
    
}
