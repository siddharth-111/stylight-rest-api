package com.example.SpringBoot.service.serviceImpl;

import com.example.SpringBoot.Model.ExchangeRateResponse;
import com.example.SpringBoot.Model.ExchangeRatesAPIResponse;
import com.example.SpringBoot.Model.Trends;
import com.example.SpringBoot.dao.ExchangeRatesDAO;
import com.example.SpringBoot.repository.ExchangeRateRepository;
import com.example.SpringBoot.service.serviceInterface.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.*;

@Service
public class ExchangeRateServiceImpl implements ExchangeRatesService {

    public String ACCESS_KEY_TOKEN = "e146203ebb0b65ff5cf4d4b750c6b9f5";
    public String EXCHANGE_RATE_BASE_URL = "http://api.exchangeratesapi.io/v1/";
    public int MAX_RANGE = 5;

    private final WebClient webClient;

    @Autowired
    ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(EXCHANGE_RATE_BASE_URL).build();
    }

    @Override
    public List<ExchangeRateResponse> getExchangeRateForDate(String year, String month, String day)  throws Exception {

        StringBuffer dateString = new StringBuffer();
        dateString.append(year + "-" + month + "-" + day);

        Date enteredDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString.toString());

        List<ExchangeRatesDAO> exchangeRatesDAOS = exchangeRateRepository.findAllByDate(enteredDate);

        List<ExchangeRateResponse> exchangeRateResponseList = new ArrayList<ExchangeRateResponse>();

        for(ExchangeRatesDAO exchangeRatesDAO : exchangeRatesDAOS)
        {
            ExchangeRateResponse exchangeRatesAPIResponse = getExchangeRates(exchangeRatesDAO.getDate().toString(), exchangeRatesDAO.getBaseCurrency(), exchangeRatesDAO.getTargetCurrency());
            exchangeRateResponseList.add(exchangeRatesAPIResponse);
        }

        return exchangeRateResponseList;
    }

    @Override
    public List<ExchangeRateResponse> getExchangeRateForMonth(int year, int month)  throws Exception {

//        List<ExchangeRatesDAO> exchangeRatesDAOS = exchangeRateRepository.findAllByYearAndMonth(year, month);
        return null;
    }

    @Override
    public ExchangeRateResponse getExchangeRates(String date, String baseCurrency, String targetCurrency)  throws Exception  {

        List<ExchangeRatesAPIResponse> exchangeRatesAPIResponseList = new ArrayList<ExchangeRatesAPIResponse>();
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        LocalDate currentDate = LocalDate.parse(date);

        while(exchangeRatesAPIResponseList.size() < MAX_RANGE)
        {
            if(!isWeekend(currentDate))
            {
                ExchangeRatesAPIResponse exchangeRatesAPIResponse = getExchangeRateAPIResponse(currentDate.toString(), baseCurrency, targetCurrency);
                exchangeRatesAPIResponseList.add(exchangeRatesAPIResponse);
            }

            currentDate = currentDate.minusDays(1);
        }

        exchangeRateResponse.setAverageRates(getAverageRates(exchangeRatesAPIResponseList, baseCurrency, targetCurrency));
        exchangeRateResponse.setRates(exchangeRatesAPIResponseList.get(0).getRates());
        exchangeRateResponse.setTrends(getRateTrends(exchangeRatesAPIResponseList, baseCurrency, targetCurrency));

        exchangeRateRepository.save(new ExchangeRatesDAO(date, baseCurrency, targetCurrency));
        return exchangeRateResponse;
    }

    private ExchangeRatesAPIResponse getExchangeRateAPIResponse(String date, String baseCurrency, String targetCurrency)
    {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_key", ACCESS_KEY_TOKEN);
        queryParams.add("symbols", baseCurrency + "," + targetCurrency);

        Mono<ExchangeRatesAPIResponse> mono = this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + date)
                        .queryParams(queryParams)
                        .build())
                .retrieve()
                .bodyToMono(ExchangeRatesAPIResponse.class);

        ExchangeRatesAPIResponse exchangeRatesAPIResponse = mono.block();

        return exchangeRatesAPIResponse;
    }

    private LinkedHashMap<String, Double> getAverageRates(List<ExchangeRatesAPIResponse> exchangeRatesAPIResponseList, String baseCurrency, String targetCurrency)
    {
        List<Double> baseCurrencyRates = new ArrayList<Double>();
        List<Double> targetCurrencyRates = new ArrayList<Double>();
        for(ExchangeRatesAPIResponse exchangeRatesAPIResponse : exchangeRatesAPIResponseList)
        {
            LinkedHashMap<String, Double> currentRate = exchangeRatesAPIResponse.getRates();
            baseCurrencyRates.add((Double) currentRate.get(baseCurrency));
            targetCurrencyRates.add((Double) currentRate.get(targetCurrency));
        }

        OptionalDouble baseAverage = baseCurrencyRates
                .stream()
                .mapToDouble(a -> a)
                .average();
        double baseCurrencyAverage = baseAverage.isPresent() ? baseAverage.getAsDouble() : 0;

        OptionalDouble targetAverage = targetCurrencyRates
                .stream()
                .mapToDouble(a -> a)
                .average();
        double targetCurrencyAverage = targetAverage.isPresent() ? targetAverage.getAsDouble() : 0;


        LinkedHashMap<String, Double> averageRates = new LinkedHashMap<String, Double>();
        averageRates.put(baseCurrency, baseCurrencyAverage);
        averageRates.put(targetCurrency, targetCurrencyAverage);

        return averageRates;
    }

    private boolean isWeekend(final LocalDate ld)
    {
        DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }

    private Trends getRateTrends(List<ExchangeRatesAPIResponse> exchangeRatesAPIResponseList , String baseCurrency, String targetCurrency)
    {
        List<Double> currencyRates = new ArrayList<Double>();
        for(ExchangeRatesAPIResponse exchangeRatesAPIResponse : exchangeRatesAPIResponseList)
        {
            LinkedHashMap<String, Double> currentRate = exchangeRatesAPIResponse.getRates();
            currencyRates.add((Double) currentRate.get(baseCurrency));
        }

        boolean ascending = true, descending = true, constant = true;
        for (int i = 1; i < currencyRates.size() && (ascending || descending || constant); i++) {
            double currentRate = (Double)currencyRates.get(i);
            double previousRate = (Double)currencyRates.get(i - 1);
            ascending = ascending && (currentRate < previousRate);
            descending = descending && (currentRate > previousRate);
            constant = constant && (currentRate == previousRate);
        }

        Trends currentTrend = null;
        if(ascending)
        {
           return Trends.ASCENDING;
        }
        if(descending)
        {
            return Trends.DESCENDING;
        }
        if(constant)
        {
            return Trends.CONSTANT;
        }

        return Trends.UNDEFINED;
    }
}
