package com.example.repository;

import com.example.entity.MerchantAttribute;
import com.example.model.dto.MerchantAttributeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantAttributeRepository extends JpaRepository<MerchantAttribute, Integer> {

    @Query("select new MerchantAttributeResponse(ma.id,ma.name) from  MerchantAttribute ma where ma.merchant.id = :merchantId")
    List<MerchantAttributeResponse> findAllByMerchant(@Param("merchantId") int merchantId);
}
