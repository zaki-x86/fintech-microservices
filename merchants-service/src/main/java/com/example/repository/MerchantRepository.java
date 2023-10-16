package com.example.repository;

import com.example.entity.Merchant;
import com.example.model.dto.MerchantResponse;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Integer> {

    @Query("select new MerchantResponse(m.id,m.name,m.icon,m.rowNumber) from Merchant m where m.status='ENABLED' and m.service.id = :serviceId")
    List<MerchantResponse> findAllByService(@Param("serviceId") Integer serviceId, Sort sort);


    @Query("select new MerchantResponse(m.id,m.name,m.icon,m.rowNumber) from Merchant m where m.status='ENABLED' and m.id = :id ")
    Optional<MerchantResponse> findById(@Param("id") int id);
}
