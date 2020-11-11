package com.dbsbank.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dbsbank.dto.FundTransferRequestDto;
import com.dbsbank.dto.FundTransferResponseDto;
import com.dbsbank.service.BankService;

@RestController
@RequestMapping("/dbs")
public class BankController {

	@Autowired
	BankService bankService;

	@Autowired
	Environment enviroment;

	private final static Logger logger = LoggerFactory.getLogger(BankController.class);

	@PostMapping("/transfer")
	public ResponseEntity<String> fundTransfer(@RequestBody FundTransferRequestDto fundTransferRequestDto) {
		bankService.fundTransfer(fundTransferRequestDto);
		logger.info("Before calling fund Transfer ");
		return new ResponseEntity<String>("Fund Transfer Successfully", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/transactionStatement")
	public ResponseEntity<List<FundTransferResponseDto>> getTransactions(
			@RequestParam("fromaccountNumber") Long fromaccountNumber, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		List<FundTransferResponseDto> fundTransfers = bankService.getTransactionStatement(fromaccountNumber, pageNo,
				pageSize);
		logger.info("Before calling Transactions list  ");
		return new ResponseEntity<List<FundTransferResponseDto>>(fundTransfers, HttpStatus.OK);
	}

	@GetMapping("/port")
	public String getInfo() {
		String port = enviroment.getProperty("local.server.port");
		return "From Server" + port;

	}
}
