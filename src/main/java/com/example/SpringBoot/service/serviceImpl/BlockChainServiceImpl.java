package com.example.SpringBoot.service.serviceImpl;

import com.example.SpringBoot.Model.BlockChainResponse;
import com.example.SpringBoot.Model.ExchangeRatesAPIResponse;
import com.example.SpringBoot.service.serviceInterface.BlockChainService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BlockChainServiceImpl implements BlockChainService {

    private final WebClient webClient;
    public String BLOCK_CHAIN_BASE_URL = "https://blockchain.info/";

    public BlockChainServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BLOCK_CHAIN_BASE_URL).build();
    }

    public List<BlockChainResponse> getBitcoinBlocks(String date) throws Exception
    {
        Date enteredDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        long milliseconds = enteredDate.getTime();

        return getExchangeRateAPIResponse("/blocks/" + milliseconds);

    }

    private List<BlockChainResponse> getExchangeRateAPIResponse(String urlcomponent)
    {
        Mono<List<BlockChainResponse>> mono = this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlcomponent)
                        .queryParam("format", "json")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<BlockChainResponse>>() {});

        List<BlockChainResponse> blockChainResponses = mono.block();

        return blockChainResponses;
    }

}
