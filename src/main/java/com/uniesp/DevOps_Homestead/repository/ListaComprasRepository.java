package com.uniesp.DevOps_Homestead.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uniesp.DevOps_Homestead.domain.ListaCompras;

@Repository
public interface ListaComprasRepository extends JpaRepository<ListaCompras, Long> {
}
