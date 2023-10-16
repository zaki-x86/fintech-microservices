package com.example.repository;

import com.example.entity.Service;
import com.example.model.dto.ServiceResponse;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {


    @Query("select new ServiceResponse(s.id,s.name,s.icon,s.rowNumber) from Service s where s.status = 'ENABLED'")
    List<ServiceResponse> findAll2(Sort sort);

    @Query("select new ServiceResponse(s.id,s.name,s.icon,s.rowNumber) from Service s where s.id = :id and s.status = 'ENABLED'")
    Optional<ServiceResponse> findById2(@Param("id") int id);
}
