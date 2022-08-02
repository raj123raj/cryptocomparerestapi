package com.cryptocurrencycompare.cryptocurrencycompare_Rest_API.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CryptocurrencyComparatorControllerTest {
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CryptocurrencyComparatorController()).build();
    }


    @Test
    public void testCryptocurrencyComparisionDetails() throws Exception {

        
        //expected
        String expectedData = null;
        StringBuilder responseData = new StringBuilder();
        JsonObject expectedJsonObject = null;
        String expectedBTC = null,expectedUSD = null,expectedEUR = null,expectedINR = null;
        URL url = new URL("https://min-api.cryptocompare.com/data/price?fsym=LTC&tsyms=BTC,USD,EUR,INR");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {

            String line;

            while ((line = in.readLine()) != null) {
                responseData.append(line);
            }

            expectedJsonObject = new Gson().fromJson(responseData.toString(), JsonObject.class);
            //expectedData = expectedJsonObject.get("data").toString();
            expectedData = expectedJsonObject.toString().replaceAll("^\"|\"$", "");
            StringTokenizer jsonTokenizer = new StringTokenizer(expectedData,",");
            String internalData[];
            String expectedCryptocurrencyOutput = null;
            
            while (jsonTokenizer.hasMoreTokens()) {  
            	expectedCryptocurrencyOutput = jsonTokenizer.nextToken();
            	internalData = StringUtils.split(expectedCryptocurrencyOutput,":");
            	System.out.println(internalData[0]+internalData[1]);
            	if (internalData[0].substring(2,internalData[0].length()-1).equalsIgnoreCase("BTC")) {
            		expectedBTC = internalData[1].substring(0,internalData[1].length());
            		
            	}
            	if (internalData[0].substring(1,internalData[0].length()-1).equalsIgnoreCase("USD")) {
            		expectedUSD = internalData[1].substring(0,internalData[1].length());
            	}
            	if (internalData[0].substring(1,internalData[0].length()-1).equalsIgnoreCase("EUR")) {
            		expectedEUR = internalData[1].substring(0,internalData[1].length());
            	}
            	if (internalData[0].substring(1,internalData[0].length()-1).equalsIgnoreCase("INR")) {
            		expectedINR = internalData[1].substring(0,internalData[1].length()-1);
            	}
            }
            
            System.out.println(expectedBTC + " " + expectedUSD + " " + expectedEUR + " " + expectedINR);
        }

        //actual
        MvcResult result = mockMvc.perform(get("/getCryptocurrencyComparisionDetailsByName?fsym=LTC&tsyms=BTC"))
                .andReturn();
        String recievedResponse = result.getResponse().getContentAsString();
        JsonObject actualJsonObject = new Gson().fromJson(recievedResponse, JsonObject.class);
        String actualBTC = actualJsonObject.get("BTC").toString();
        actualBTC = actualBTC.replaceAll("^\"|\"$", "");
        String actualUSD = actualJsonObject.get("USD").toString();
        actualUSD = actualUSD.replaceAll("^\"|\"$", "");
        String actualEUR = actualJsonObject.get("EUR").toString();
        actualEUR = actualEUR.replaceAll("^\"|\"$", "");
        String actualINR = actualJsonObject.get("INR").toString();
        actualINR = actualINR.replaceAll("^\"|\"$", "");
        String internalData[];
        internalData = StringUtils.split(expectedBTC,".");
        System.out.println(internalData[0]+internalData[1]);
        assertEquals(expectedBTC, actualBTC);
        assertEquals(expectedUSD, actualUSD);
        assertEquals(expectedEUR, actualEUR);
        assertEquals(expectedINR, actualINR);
    }

}
