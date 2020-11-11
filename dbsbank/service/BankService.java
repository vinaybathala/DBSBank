package com.dbsbank.service;

import java.util.List;

import com.dbsbank.dto.FundTransferRequestDto;
import com.dbsbank.dto.FundTransferResponseDto;

public interface BankService {

	String fundTransfer(FundTransferRequestDto fundTransferRequestDto);

	List<FundTransferResponseDto> getTransactionStatement(Long accountNumber, Integer pageNo, Integer pageSize);

}
