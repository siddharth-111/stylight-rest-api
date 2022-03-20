package com.stylight.service.serviceInterface;

import com.stylight.entity.Url;

import java.util.List;

public interface UrlService {
    List<Url> getPrettyUrl(List<String> orderedParameters) throws Exception;
    List<Url> getOrderedParameters(List<String> prettyUrls);
    Url createMapping(Url url);
}
