package com.stylight.service;


import com.stylight.entity.Url;

import com.stylight.repository.UrlRepository;
import com.stylight.service.serviceInterface.UrlService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UrlServiceTest {

    @Autowired
    UrlService urlService;

    @MockBean
    private UrlRepository urlRepository;

    private Url urlOne, urlTwo;

    @BeforeEach
    public void init() {
        urlOne = new Url();

        urlOne.setPrettyUrl("/Fashion");
        urlOne.setOrderedParameter("/products");

        urlTwo = new Url();

        urlTwo.setPrettyUrl("/Women/");
        urlTwo.setOrderedParameter("/products?gender=female");
    }

    @AfterEach
    public void teardown() {
        urlOne = null;
        urlTwo = null;
    }

    @Test
    public void shouldCreateMappedUrl() {

        when(urlRepository.save(any(Url.class))).thenReturn(urlOne);

        Url mappedUrl = urlService.createMapping(urlOne);

        assertEquals(mappedUrl.getOrderedParameter(), "/products");
        assertEquals(mappedUrl.getPrettyUrl(), "/Fashion");
    }

    @Test
    public void shouldFetchCompletelyMappedPrettyUrl() throws Exception {

        when(urlRepository.findTopByOrderedParameter("/products")).thenReturn(urlOne);
        when(urlRepository.findTopByOrderedParameter("/products?gender=female")).thenReturn(urlTwo);

        List<String> orderedParameters = Arrays.asList("/products", "/products?gender=female");

        List<Url> mappedUrlList = urlService.getPrettyUrl(orderedParameters);

        assertEquals(mappedUrlList.size(), 2);

        assertEquals(mappedUrlList.get(0).getOrderedParameter(), "/products");
        assertEquals(mappedUrlList.get(0).getPrettyUrl(), "/Fashion");
        assertEquals(mappedUrlList.get(1).getOrderedParameter(), "/products?gender=female");
        assertEquals(mappedUrlList.get(1).getPrettyUrl(), "/Women/");
    }

    @Test
    public void shouldFetchPartiallyMappedPrettyUrl() throws Exception {

        when(urlRepository.findTopByOrderedParameter("/products?tag=123")).thenReturn(null);
        when(urlRepository.findTopByOrderedParameter("/products")).thenReturn(urlOne);

        List<String> orderedParameters = Arrays.asList("/products?tag=123");

        List<Url> mappedUrlList = urlService.getPrettyUrl(orderedParameters);

        assertEquals(mappedUrlList.size(), 1);

        assertEquals(mappedUrlList.get(0).getOrderedParameter(), "/products?tag=123");
        assertEquals(mappedUrlList.get(0).getPrettyUrl(), "/Fashion?tag=123");
    }

    @Test
    public void shouldFetchCompletelyMappedOrderedParameters() throws Exception {

        when(urlRepository.findTopByPrettyUrl("/Fashion")).thenReturn(urlOne);
        when(urlRepository.findTopByPrettyUrl("/Women/")).thenReturn(urlTwo);

        List<String> prettyUrls = Arrays.asList("/Fashion", "/Women/");

        List<Url> mappedUrlList = urlService.getOrderedParameters(prettyUrls);

        assertEquals(mappedUrlList.size(), 2);

        assertEquals(mappedUrlList.get(0).getOrderedParameter(), "/products");
        assertEquals(mappedUrlList.get(0).getPrettyUrl(), "/Fashion");
        assertEquals(mappedUrlList.get(1).getOrderedParameter(), "/products?gender=female");
        assertEquals(mappedUrlList.get(1).getPrettyUrl(), "/Women/");
    }

    @Test
    public void shouldFetchPartiallyMappedOrderedParameter() throws Exception {

        when(urlRepository.findTopByPrettyUrl("/Fashion/Adidas/Women")).thenReturn(null);
        when(urlRepository.findTopByPrettyUrl("/Fashion/Adidas/")).thenReturn(urlTwo);

        List<String> prettyUrls = Arrays.asList("/Fashion/Adidas/Women");

        List<Url> mappedUrlList = urlService.getOrderedParameters(prettyUrls);

        assertEquals(mappedUrlList.size(), 1);

        assertEquals(mappedUrlList.get(0).getOrderedParameter(), "/products/Women?gender=female");
        assertEquals(mappedUrlList.get(0).getPrettyUrl(), "/Fashion/Adidas/Women");
    }
}
