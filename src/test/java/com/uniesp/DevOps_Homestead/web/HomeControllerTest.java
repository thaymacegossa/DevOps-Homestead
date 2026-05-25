package com.uniesp.DevOps_Homestead.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.ifpb.DevOps_Homestead.domain.ListaAfazeres;
import com.ifpb.DevOps_Homestead.domain.ListaCompras;
import com.ifpb.DevOps_Homestead.service.ListaAfazeresService;
import com.ifpb.DevOps_Homestead.service.ListaComprasService;
import com.ifpb.DevOps_Homestead.web.HomeController;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @Mock
    private ListaAfazeresService listaAfazeresService;

    @Mock
    private ListaComprasService listaComprasService;

    @InjectMocks
    private HomeController controller;

    @Test
    void homeDevePopularTotaisERetornarIndex() {
        when(listaAfazeresService.listarTodos()).thenReturn(List.of(new ListaAfazeres(), new ListaAfazeres()));
        when(listaComprasService.listarTodos()).thenReturn(List.of(new ListaCompras()));
        Model model = new ExtendedModelMap();

        String view = controller.home(model);

        assertEquals("index", view);
        assertEquals(2L, model.getAttribute("totalTarefas"));
        assertEquals(1L, model.getAttribute("totalCompras"));
    }
}
