package com.ifpb.DevOps_Homestead.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ifpb.DevOps_Homestead.domain.ListaAfazeres;

@Repository
public interface ListaAfazeresRepository extends JpaRepository<ListaAfazeres, Long> {
}
