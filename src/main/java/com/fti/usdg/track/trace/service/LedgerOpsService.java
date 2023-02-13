/**
 * 
 */
package com.fti.usdg.track.trace.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fti.usdg.track.trace.dto.GatewayAPIDto;

/**
 * @author Anup
 *
 */
@Service
public class LedgerOpsService {

	private static final Logger logger = LoggerFactory.getLogger(LedgerOpsService.class);
	
	@Value("${fabric.user}")
	private String fabricUser;
	
	

	public String submitTransactionToLedger(@RequestBody GatewayAPIDto gatewayAPIDto) throws Exception {
		byte[] fabresponse = null;
		// Load an existing wallet holding identities used to access the network.
		Path walletDirectory = Paths.get("/data/wallet");
		Wallet wallet = Wallets.newFileSystemWallet(walletDirectory);
		logger.debug("here 1 " + gatewayAPIDto.getChannelName());
		// Path to a common connection profile describing the network.
		Path networkConfigFile = Paths.get("/data/chain-config/connection-" + gatewayAPIDto.getChannelName() + ".json");
		logger.debug("here 2");
		// Configure the gateway connection used to access the network.
		Gateway.Builder builder = Gateway.createBuilder().identity(wallet, fabricUser)
				.networkConfig(networkConfigFile);
		logger.debug("here 3");
		// Create a gateway connection
		try (Gateway gateway = builder.connect()) {
			logger.debug("here 4");
			// Obtain a smart contract deployed on the network.
			Network network = gateway.getNetwork(gatewayAPIDto.getChannelName());
			Contract contract = network.getContract("price_manager_cc");
			logger.debug("here 5");

			// Submit transactions that store state to the ledger.
			if (!gatewayAPIDto.getIsReadOps()) {
				logger.debug("here 5.5");
				fabresponse = contract.createTransaction(gatewayAPIDto.getMethodName())
						.submit(gatewayAPIDto.getParameters());
				System.out.println(new String(fabresponse, StandardCharsets.UTF_8));
			} else {
				logger.debug("here 5.6");
				fabresponse = contract.evaluateTransaction(gatewayAPIDto.getMethodName(),
						gatewayAPIDto.getParameters());
				System.out.println(new String(fabresponse, StandardCharsets.UTF_8));
			}
			logger.debug("here 6");
		}
		return new String(fabresponse, StandardCharsets.UTF_8);
	}
}
