package com.ifpb.DevOps_Homestead.repository;

import com.ifpb.DevOps_Homestead.domain.ListaCompras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaComprasRepository extends JpaRepository<ListaCompras, Long> {
}
