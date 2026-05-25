package com.uniesp.DevOps_Homestead.service;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uniesp.DevOps_Homestead.domain.ListaAfazeres;
import com.uniesp.DevOps_Homestead.repository.ListaAfazeresRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ListaAfazeresService {
    
    private final String ID_NAO_ENCONTRADO = "Tarefa não encontrada com ID: ";

    private final ListaAfazeresRepository repository;

    @Transactional(readOnly = true)
    public List<ListaAfazeres> listarTodos() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public ListaAfazeres buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException(ID_NAO_ENCONTRADO + id);
                });
    }

    public ListaAfazeres criar(ListaAfazeres tarefa) {
        validar(tarefa);

        tarefa.setDescricao(tarefa.getDescricao().trim());
        tarefa.setCriadoEm(LocalDateTime.now());
        tarefa.setAtualizadoEm(LocalDateTime.now());

        return repository.save(tarefa);
    }

    public ListaAfazeres atualizar(ListaAfazeres formTarefa) {
        ListaAfazeres tarefa = repository.findById(formTarefa.getId())
                .orElseThrow(() -> {
                    return new IllegalArgumentException(ID_NAO_ENCONTRADO + formTarefa.getId());
                });

        validar(formTarefa);

        tarefa.setDescricao(formTarefa.getDescricao().trim());
        tarefa.setStatus(formTarefa.getStatus());
        tarefa.setPrazoConclusao(formTarefa.getPrazoConclusao());
        tarefa.setImportancia(formTarefa.getImportancia());

        tarefa.setAtualizadoEm(LocalDateTime.now());

        return repository.save(tarefa);
    }

    public void deletar(Long id) {
        ListaAfazeres tarefa = repository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException(ID_NAO_ENCONTRADO + id);
                });

        repository.delete(tarefa);
    }

    private void validar(ListaAfazeres tarefa) {
        if (tarefa.getDescricao() == null || tarefa.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição é obrigatória");
        }

        if (tarefa.getStatus() == null) {
            throw new IllegalArgumentException("Status é obrigatório");
        }

        if (tarefa.getImportancia() == null) {
            throw new IllegalArgumentException("Importância é obrigatória");
        }
    }

}
