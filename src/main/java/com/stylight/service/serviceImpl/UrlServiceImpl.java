package com.stylight.service.serviceImpl;


import com.stylight.entity.Url;
import com.stylight.repository.UrlRepository;
import com.stylight.service.serviceInterface.UrlService;
import lombok.RequiredArgsConstructor;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.http.client.utils.URLEncodedUtils;

import static java.util.stream.Collectors.mapping;

@RequiredArgsConstructor
@Service
public class UrlServiceImpl implements UrlService {
    private final UrlRepository urlRepository;

    @Override
    public Url createMapping(Url url)
    {
        return urlRepository.save(url);
    }

    @Override
    public List<Url> getPrettyUrl(List<String> orderedParameters) throws Exception {
        List<Url> prettyUrls = null;

        prettyUrls = orderedParameters.stream().map(orderedParameter -> {

            Url url = urlRepository.findTopByOrderedParameter(orderedParameter);

            if(url == null)
            {

                List<NameValuePair> params = null;
                try {
                    params = URLEncodedUtils.parse(new URI(orderedParameter), String.valueOf(Charset.forName("UTF-8")));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                List<NameValuePair> outputParams = new ArrayList<>();
                String baseUri = "";
                URIBuilder uriBuilder = null;
                try {
                    uriBuilder = new URIBuilder(orderedParameter);
                    uriBuilder.removeQuery();
                    baseUri =  uriBuilder.build().toString();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                while(params.size() != 0)
                {
                    outputParams.add(params.remove(params.size() - 1));
                    String temp = "";
                    try {
                        temp = params.size() != 0 ? baseUri + "?" + getQuery(params) : baseUri;

                        url = (Url) urlRepository.findTopByOrderedParameter(temp);
                        if(url != null)
                        {
                            url = (Url) url.clone();
                            Collections.reverse(outputParams);
                            url.setPrettyUrl(url.getPrettyUrl() + "?" + getQuery(outputParams));
                            url.setOrderedParameter(orderedParameter);
                            break;
                        }

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                if(url == null)
                {
                    url = new Url(orderedParameter, orderedParameter);
                }
            }

            return url;
        }).collect(Collectors.toList());

        return prettyUrls;
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    @Override
    public List<Url> getOrderedParameters(List<String> prettyUrls) {
        return null;
    }
}
