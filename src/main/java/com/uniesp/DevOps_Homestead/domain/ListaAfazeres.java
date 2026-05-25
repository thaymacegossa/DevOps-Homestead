package com.uniesp.DevOps_Homestead.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lista_afazeres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ListaAfazeres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "prazo_conclusao")
    private java.time.LocalDate prazoConclusao;

    @Column(name = "importancia")
    private Integer importancia;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private java.time.LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private java.time.LocalDateTime atualizadoEm;

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
        atualizadoEm = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        atualizadoEm = LocalDateTime.now();
    }
}
