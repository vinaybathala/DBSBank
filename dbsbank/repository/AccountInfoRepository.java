package com.dbsbank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dbsbank.entity.Account;

@Repository
public interface AccountInfoRepository extends JpaRepository<Account, Long> {

	public Optional<Account> findByaccountNumber(Long fromAccountNumber);

}
