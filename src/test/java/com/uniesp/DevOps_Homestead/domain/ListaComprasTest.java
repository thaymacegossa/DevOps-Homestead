package com.uniesp.DevOps_Homestead.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.uniesp.DevOps_Homestead.domain.ListaCompras;

class ListaComprasTest {

    @Test
    void onCreateDevePreencherCamposDeAuditoriaEDataHoraQuandoNulo() {
        ListaCompras item = new ListaCompras();

        item.onCreate();

        assertNotNull(item.getCriadoEm());
        assertNotNull(item.getAtualizadoEm());
        assertNotNull(item.getDataHora());
    }

    @Test
    void onCreateDeveManterDataHoraQuandoJaInformado() {
        ListaCompras item = new ListaCompras();
        LocalDateTime dataHora = LocalDateTime.of(2025, 1, 1, 10, 30);
        item.setDataHora(dataHora);

        item.onCreate();

        assertEquals(dataHora, item.getDataHora());
    }

    @Test
    void onUpdateDeveAtualizarDataDeAtualizacao() {
        ListaCompras item = new ListaCompras();

        item.onUpdate();

        assertNotNull(item.getAtualizadoEm());
    }
}
