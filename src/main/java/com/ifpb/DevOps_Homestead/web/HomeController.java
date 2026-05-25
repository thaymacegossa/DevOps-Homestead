package com.ifpb.DevOps_Homestead.web;

import com.ifpb.DevOps_Homestead.service.ListaAfazeresService;
import com.ifpb.DevOps_Homestead.service.ListaComprasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
