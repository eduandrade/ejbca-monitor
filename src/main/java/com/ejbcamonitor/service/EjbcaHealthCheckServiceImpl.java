package com.ejbcamonitor.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ejbcamonitor.model.CaInfo;
import com.ejbcamonitor.model.CaStatus;

public class EjbcaHealthCheckServiceImpl implements EjbcaHealthCheckService {
    
    private static final String OK_RESPONSE = "ALLOK";
    
    private Logger logger = LoggerFactory.getLogger(EjbcaHealthCheckServiceImpl.class);

    @Override
    public CaStatus checkEjbcaHealth(CaInfo caInfo) {
        CaStatus caStatus = new CaStatus();
        caStatus.setName(caInfo.getName());
        
        try {
            String responseBody = connectToHealthCheck(caInfo.getName(), caInfo.getHealthCheckUrl());
            if (OK_RESPONSE.equals(responseBody)) {
                caStatus.setAllOk(true);
                caStatus.setMessage(responseBody);
            }
        } catch (Exception e) {
            logger.warn("Nao foi possivel verificar o status da AC " + caInfo.getName(), e);
            caStatus.setAllOk(false);
            caStatus.setMessage(e.getMessage());
        }
        
        return caStatus;
    }
    
    private String connectToHealthCheck(String caName, String ejbcaUrl) throws ClientProtocolException, IOException {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 1000);
        HttpClient httpclient = new DefaultHttpClient(httpParams);
        try {
            HttpGet httpget = new HttpGet(ejbcaUrl);

            logger.info("executing request {}", httpget.getURI());

            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpget, responseHandler);
            logger.info("caName = [{}] / responseBody = [{}]", caName, responseBody);
            return responseBody;
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        
    }

}
