package com.dbsbank.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbsbank.entity.FundTransfer;

@Repository
public interface FundTransferRepository extends JpaRepository<FundTransfer, Long> {

	List<FundTransfer> findByfromAccountNumber(Long accountNumber,  Pageable paging);

	

	

}
