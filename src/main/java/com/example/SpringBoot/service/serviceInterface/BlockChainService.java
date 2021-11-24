package com.example.SpringBoot.service.serviceInterface;

import com.example.SpringBoot.Model.BlockChainResponse;

import java.util.List;

public interface BlockChainService {
    List<BlockChainResponse> getBitcoinBlocks(String date) throws Exception;
}
