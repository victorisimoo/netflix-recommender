package com.netflix.app;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Class that facilitates the transformation of the parameters in java to parameters for the rest apis.
 *
 * @reference https://www.baeldung.com/java-http-request
 * @author victorisimoo
 */
public class ParameterStringBuilder {
    public static String getParamsString (Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                result.append("&");
            }
        String resultString = result.toString();
        return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
    }
}
