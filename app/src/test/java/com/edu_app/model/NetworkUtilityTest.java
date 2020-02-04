package com.edu_app.model;

import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class NetworkUtilityTest {
    private final String HOST = "http://192.168.123.22:2000";

    @Test
    public void getRequest() {
        final String expectedResponse = "{'test':'success'}";
        try {
            String response = NetworkUtility.getRequest(HOST+"/test_network_utility", "");
            assertEquals(expectedResponse, response);
        } catch (IOException e) {
            e.printStackTrace();
            fail("网络异常");
        }
    }

    @Test
    public void postRequest() {
        final String expectedResponse = "'test':'success'";
        try {
            String response = NetworkUtility.postRequest(HOST+"/test_network_utility", "", expectedResponse.getBytes());
            assertEquals(expectedResponse, response);
        } catch (IOException e) {
            e.printStackTrace();
            fail("网络异常");
        }
    }

    private class SampleClass {
        private boolean test;

        public boolean isTest() {
            return test;
        }

        public void setTest(boolean test) {
            this.test = test;
        }
    }

    @Test
    public void parseJsonWithClass() {
        final String jsonString = "[{'test':'true'}, {'test':'false'}, {'test':'true'}]";
        final boolean[] expected = {true, false, true};

        SampleClass[] result = NetworkUtility.parseJson(jsonString, SampleClass[].class);

        assertEquals(result.length, expected.length);
        for(int i=0; i < result.length; ++i){
            assertEquals(expected[i], result[i].test);
        }
    }

    @Test
    public void parseJsonWithType() {
        final String jsonString = "[{'test':'true'}, {'test':'false'}, {'test':'true'}]";
        final boolean[] expected = {true, false, true};

        ArrayList<SampleClass> result = NetworkUtility.parseJson(jsonString, new TypeToken<ArrayList<SampleClass>>(){}.getType());

        assertEquals(result.size(), expected.length);
        for(int i=0; i < result.size(); ++i){
            assertEquals(expected[i], result.get(i).test);
        }
    }
}