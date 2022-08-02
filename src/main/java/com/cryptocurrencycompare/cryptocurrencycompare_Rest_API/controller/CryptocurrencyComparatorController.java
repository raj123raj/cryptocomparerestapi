package com.cryptocurrencycompare.cryptocurrencycompare_Rest_API.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
public class CryptocurrencyComparatorController {
    @RequestMapping("/getCryptocurrencyComparisionDetailsByName")
    public @ResponseBody
    JsonObject getCryptocurrencyComparatorDetails(String  fsym,String tsyms) throws IOException {

        JsonObject jsonObject = new JsonObject();
        jsonObject = getCryptocurrencyComparatorData(fsym,tsyms);
        String data = jsonObject.toString().replaceAll("^\"|\"$", "");
        StringTokenizer jsonTokenizer = new StringTokenizer(data,",");
        String internalData[];
        String expectedCryptocurrencyOutput = null;        
        while (jsonTokenizer.hasMoreTokens()) {  
        	expectedCryptocurrencyOutput = jsonTokenizer.nextToken();
        	internalData = StringUtils.split(expectedCryptocurrencyOutput,":");
        	System.out.println(internalData[0]+internalData[1]);
        	if (internalData[0].substring(2,internalData[0].length()-1).equalsIgnoreCase("BTC")) {
        		jsonObject.addProperty("BTC",internalData[1].substring(0,internalData[1].length()));
        		
        	}
        	if (internalData[0].substring(1,internalData[0].length()-1).equalsIgnoreCase("USD")) {
        		jsonObject.addProperty("USD",internalData[1].substring(0,internalData[1].length()));
        	}
        	if (internalData[0].substring(1,internalData[0].length()-1).equalsIgnoreCase("EUR")) {
        		jsonObject.addProperty("EUR",internalData[1].substring(0,internalData[1].length()));
        	}
        	if (internalData[0].substring(1,internalData[0].length()-1).equalsIgnoreCase("INR")) {
        		jsonObject.addProperty("INR",internalData[1].substring(0,internalData[1].length()-1));
        	}
      
        }
        return jsonObject;
    }

    private JsonObject getCryptocurrencyComparatorData(String fromCryptocurrency,String toCryptocurrency) throws IOException {
        StringBuilder responseData = new StringBuilder();
        JsonObject jsonObject = null;

        URL url = null;
        
        url = new URL("https://min-api.cryptocompare.com/data/price?fsym="+fromCryptocurrency+"&tsyms="+toCryptocurrency+",USD,EUR,INR");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {


            String line;

            while ((line = in.readLine()) != null) {
                responseData.append(line);
            }

            jsonObject = new Gson().fromJson(responseData.toString(), JsonObject.class);
            
        }
        System.out.println(jsonObject);
        return jsonObject;
    }
}