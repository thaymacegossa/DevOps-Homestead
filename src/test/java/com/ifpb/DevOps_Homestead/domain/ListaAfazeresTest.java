package com.ifpb.DevOps_Homestead.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ListaAfazeresTest {

    @Test
    void onCreateDevePreencherCamposDeAuditoria() {
        ListaAfazeres item = new ListaAfazeres();

        item.onCreate();

        assertNotNull(item.getCriadoEm());
        assertNotNull(item.getAtualizadoEm());
    }

    @Test
    void onUpdateDeveAtualizarDataDeAtualizacao() {
        ListaAfazeres item = new ListaAfazeres();

        item.onUpdate();

        assertNotNull(item.getAtualizadoEm());
    }
}
