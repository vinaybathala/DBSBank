package com.dbsbank.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dbsbank.dto.FundTransferRequestDto;
import com.dbsbank.dto.FundTransferResponseDto;
import com.dbsbank.entity.Account;
import com.dbsbank.entity.FundTransfer;
import com.dbsbank.exception.AccountNotFoundException;
import com.dbsbank.exception.InsufficientFundException;
import com.dbsbank.repository.AccountInfoRepository;
import com.dbsbank.repository.FundTransferRepository;
import com.dbsbank.service.BankService;

@Service
public class BankServiceImpl implements BankService {

	@Autowired
	FundTransferRepository fundTransferRepository;

	@Autowired
	AccountInfoRepository accountInfoRepository;

	public String fundTransfer(FundTransferRequestDto fundTransferRequestDto) {
		long fromAccount = fundTransferRequestDto.getFromAccounNumber();
		long toAccount = fundTransferRequestDto.getToAccount();
		double transferFund = fundTransferRequestDto.getAmount();
		this.debitTransferFundFromAccount(fromAccount, transferFund);
		this.creditTransferFundtoAccount(toAccount, transferFund);
		FundTransfer fundTransfer = new FundTransfer();
		fundTransfer.setFromAccountNumber(fundTransferRequestDto.getFromAccounNumber());
		fundTransfer.setToAccount(fundTransferRequestDto.getToAccount());
		fundTransfer.setAmount(fundTransferRequestDto.getAmount());
		fundTransfer.setTransfer_type("Debit");
		fundTransfer.setRemarks(fundTransferRequestDto.getRemarks());
		fundTransfer.setTransferDate("23-10-2020 13:40");
		fundTransferRepository.save(fundTransfer);

		return "Funds Transfer Successfully";

	}

	private void debitTransferFundFromAccount(Long fromAccountNumber, double transferFund) {

		Optional<Account>  optionalAccount = accountInfoRepository.findByaccountNumber(fromAccountNumber);
		
		// exception AccountNotFoundException
		if (!optionalAccount.isPresent()) {

			throw new AccountNotFoundException(" invalid account");

		}

		Account account = optionalAccount.get();
		double beforeCurrentBalance;
		double presentCurrentBalance;

		beforeCurrentBalance = account.getCurrentBalance();

		// exception InsufficientFundException
		if (transferFund > beforeCurrentBalance) {
			throw new InsufficientFundException(" fund are not available");

		}

		if (transferFund != 0) {
			presentCurrentBalance = (beforeCurrentBalance - transferFund);
			account.setCurrentBalance(presentCurrentBalance);

		}

		accountInfoRepository.save(account);

	}

	private void creditTransferFundtoAccount(Long toAccount, double transferFund) {
		Optional<Account>  creditAccount;
		creditAccount = accountInfoRepository.findByaccountNumber(toAccount);

		//exception to account 
		if (!creditAccount.isPresent()) {

			throw new AccountNotFoundException(" invalid account");

		}
		
		Account account = creditAccount.get();
		double beforeCurrentBalance;
		double presentCurrentBalance;
		beforeCurrentBalance = account.getCurrentBalance();
		if (transferFund != 0) {
			presentCurrentBalance = (beforeCurrentBalance + transferFund);
			account.setCurrentBalance(presentCurrentBalance);

		}

		accountInfoRepository.save(account);

	}

	public List<FundTransferResponseDto> getTransactionStatement(Long accountNumber, Integer pageNo, Integer pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("transferId").descending());
		List<FundTransfer> pagesResult = fundTransferRepository.findByfromAccountNumber(accountNumber, paging);
		List<FundTransferResponseDto> fundTransferResponseDtoList = new ArrayList();
		for (FundTransfer fundTransfer : pagesResult) {
			FundTransferResponseDto fundTransferResponseDto = new FundTransferResponseDto();
			BeanUtils.copyProperties(fundTransfer, fundTransferResponseDto);
			fundTransferResponseDtoList.add(fundTransferResponseDto);

		}
		return fundTransferResponseDtoList;

	}
}
