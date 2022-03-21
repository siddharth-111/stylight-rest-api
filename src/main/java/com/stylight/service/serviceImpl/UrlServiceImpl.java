package com.stylight.service.serviceImpl;

import com.stylight.entity.Url;
import com.stylight.repository.UrlRepository;
import com.stylight.service.serviceInterface.UrlService;
import lombok.RequiredArgsConstructor;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.http.client.utils.URLEncodedUtils;

@RequiredArgsConstructor
@Service
public class UrlServiceImpl implements UrlService {
    private final UrlRepository urlRepository;

    Logger logger = LoggerFactory.getLogger(UrlServiceImpl.class);

    @Override
    public Url createMapping(Url url)
    {
        return urlRepository.save(url);
    }

    @Override
    public List<Url> getPrettyUrl(List<String> orderedParameters) throws Exception
    {
        List<Url> prettyUrls = null;

        prettyUrls = orderedParameters.stream().map(orderedParameter -> {

            Url mappedUrl = urlRepository.findTopByOrderedParameter(orderedParameter);

            if(mappedUrl == null)
            {
                List<NameValuePair> queryParams = getQueryParams(orderedParameter);

                mappedUrl = getMappedUrlForOrderedParameter(orderedParameter, queryParams);

                if(mappedUrl == null)
                {
                    mappedUrl = new Url(orderedParameter, orderedParameter);
                }
            }

            return mappedUrl;
        }).collect(Collectors.toList());

        return prettyUrls;
    }

    @Override
    public List<Url> getOrderedParameters(List<String> prettyUrls)
    {
        List<Url> orderedParameters = null;

        orderedParameters = prettyUrls.stream().map(prettyUrl -> {

            Url mappedUrl = urlRepository.findTopByPrettyUrl(prettyUrl);

            if(mappedUrl == null)
            {
                List<String> prettyUrlPathComponents = getPrettyUrlPathComponents(prettyUrl);

                mappedUrl = getMappedUrlForPrettyUrl(prettyUrl, prettyUrlPathComponents);

                if(mappedUrl == null)
                {
                    mappedUrl = new Url(prettyUrl, prettyUrl);
                }
            }
            return mappedUrl;
        }).collect(Collectors.toList());

        return orderedParameters;
    }

    private Url getMappedUrlForOrderedParameter(String orderedParameter, List<NameValuePair> queryParams)
    {
        Url mappedUrl = null;
        try {
            String baseUri = getBaseUri(orderedParameter);

            List<NameValuePair> residualQueryParams = new ArrayList<>();

            while(queryParams.size() != 0)
            {
                residualQueryParams.add(queryParams.remove(queryParams.size() - 1));
                String residualOrderedParameter = queryParams.size() != 0 ? baseUri + createQueryParamsString(queryParams) : baseUri;

                mappedUrl = (Url) urlRepository.findTopByOrderedParameter(residualOrderedParameter);

                if(mappedUrl != null)
                {
                    Collections.reverse(residualQueryParams);

                    mappedUrl = (Url) mappedUrl.clone();
                    mappedUrl.setPrettyUrl(mappedUrl.getPrettyUrl() + createQueryParamsString(residualQueryParams));
                    mappedUrl.setOrderedParameter(orderedParameter);

                    break;
                }
            }
        } catch (Exception e)
        {
            logger.error("Error in getting mapped url for ordered parameter, the error is :" + e.getLocalizedMessage(), e);
            e.printStackTrace();
        }

        return mappedUrl;
    }

    private Url getMappedUrlForPrettyUrl(String prettyUrl, List<String> prettyUrlPathComponents)
    {

        Url mappedUrl = null;
        try {
            List<String> residualPathComponents = new ArrayList<>();

            while(prettyUrlPathComponents.size() != 0)
            {
                residualPathComponents.add(prettyUrlPathComponents.remove(prettyUrlPathComponents.size() - 1));

                StringBuilder residualPrettyUrlBuilder = new StringBuilder();

                residualPrettyUrlBuilder.append("/");
                residualPrettyUrlBuilder.append(String.join("/", prettyUrlPathComponents));
                residualPrettyUrlBuilder.append("/");

                mappedUrl = (Url) urlRepository.findTopByPrettyUrl(residualPrettyUrlBuilder.toString());

                if(mappedUrl != null)
                {
                    mappedUrl = (Url) mappedUrl.clone();
                    Collections.reverse(residualPathComponents);
                    mappedUrl.setPrettyUrl(prettyUrl);

                    String orderedParameter = mappedUrl.getOrderedParameter();
                    List<NameValuePair> queryParams = getQueryParams(orderedParameter);

                    String baseUri = getBaseUri(orderedParameter);

                    baseUri += residualPathComponents.size() != 0 ? "/" + String.join("/", residualPathComponents) + createQueryParamsString(queryParams)
                            : createQueryParamsString(queryParams);

                    mappedUrl.setOrderedParameter(baseUri);

                    break;
                }
            }
        } catch (Exception e)
        {
            logger.error("Error in getting mapped url for pretty url, the error is :" + e.getLocalizedMessage(), e);
            e.printStackTrace();
        }

        return mappedUrl;

    }

    private List<String> getPrettyUrlPathComponents(String prettyUrl)
    {
        List<String> prettyUrlPathComponents = new LinkedList<>(Arrays.asList(prettyUrl.split("/")));
        if(prettyUrlPathComponents.size() != 0)
            prettyUrlPathComponents.remove(0);

        return prettyUrlPathComponents;
    }

    private List<NameValuePair> getQueryParams(String url)
    {
        List<NameValuePair> params = null;
        try {
            params = URLEncodedUtils.parse(new URI(url), String.valueOf(Charset.forName("UTF-8")));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return params;
    }

    private String getQueryParamsAsString(List<NameValuePair> params) throws UnsupportedEncodingException
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

    private String getBaseUri(String uri) throws URISyntaxException
    {
        String baseUri = "";
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(uri);
            uriBuilder.removeQuery();
            baseUri =  uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            throw e;
        }

        return baseUri;
    }

    private String createQueryParamsString( List<NameValuePair> queryParams) throws UnsupportedEncodingException
    {
        return "?" + getQueryParamsAsString(queryParams);
    }

}
