package com.uniesp.DevOps_Homestead.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.uniesp.DevOps_Homestead.service.ListaAfazeresService;
import com.uniesp.DevOps_Homestead.service.ListaComprasService;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ListaAfazeresService listaAfazeresService;
    private final ListaComprasService listaComprasService;

    @GetMapping("/")
    public String home(Model model) {
        // Busca totais para exibir nas estatísticas da homepage
        long totalTarefas = listaAfazeresService.listarTodos().size();
        long totalCompras = listaComprasService.listarTodos().size();

        model.addAttribute("totalTarefas", totalTarefas);
        model.addAttribute("totalCompras", totalCompras);

        return "index";
    }
}
