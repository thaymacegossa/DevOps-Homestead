package com.ifpb.DevOps_Homestead.service;

import com.ifpb.DevOps_Homestead.domain.ListaCompras;
import com.ifpb.DevOps_Homestead.repository.ListaComprasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ListaComprasService {

    private final ListaComprasRepository repository;

    @Transactional(readOnly = true)
    public List<ListaCompras> listarTodos() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public ListaCompras buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    return new IllegalArgumentException("Item não encontrado com ID: " + id);
                });
    }

    public ListaCompras criar(ListaCompras item) {
        validar(item);

        item.setDescricao(item.getDescricao().trim());
        item.setTotal(item.getQuantidade().multiply(item.getPrecoUnitario()));
        item.setDataHora(LocalDateTime.now());

        return repository.save(item);
    }

    public ListaCompras atualizar(ListaCompras formItem) {
        ListaCompras item = repository.findById(formItem.getId())
                .orElseThrow(() -> {
                    return new IllegalArgumentException("Item não encontrado com ID: " + formItem.getId());
                });

        validar(formItem);

        item.setDescricao(formItem.getDescricao().trim());
        item.setPrecoUnitario(formItem.getPrecoUnitario());
        item.setQuantidade(formItem.getQuantidade());

        item.setTotal(formItem.getQuantidade().multiply(formItem.getPrecoUnitario()));

        return repository.save(item);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Item não encontrado com ID: " + id);
        }

        repository.deleteById(id);
    }

    private void validar(ListaCompras item) {
        if (item.getDescricao() == null || item.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição é obrigatória");
        }
        if (item.getPrecoUnitario() == null || item.getPrecoUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço unitário deve ser maior que zero");
        }
        if (item.getQuantidade() == null || item.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
    }
}
