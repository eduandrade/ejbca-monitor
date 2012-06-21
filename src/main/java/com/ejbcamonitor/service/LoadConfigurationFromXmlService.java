package com.ejbcamonitor.service;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.Unmarshaller;

import com.ejbcamonitor.model.CaInfoList;

public class LoadConfigurationFromXmlService implements LoadConfigurationService {
    
    private Logger logger = LoggerFactory.getLogger(LoadConfigurationFromXmlService.class);
    
    private String xmlConfigurationPath;
    
    private Unmarshaller unmarshaller;
    
    private CaInfoList caInfoList;

    @Override
    public CaInfoList loadConfiguration() {
        
        if (caInfoList != null) {
            return caInfoList;
        }
        
        try {
            InputStream is = null;
            try {
                is = this.getClass().getResourceAsStream(this.xmlConfigurationPath);
                caInfoList = (CaInfoList) this.unmarshaller.unmarshal(new StreamSource(is));
                
                logger.info("Configuracoes carregadas! [{}]", caInfoList);
                return caInfoList;
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar o XML de configuracoes", e);
        }
        
        return new CaInfoList();
    }

    public void setXmlConfigurationPath(String xmlConfigurationPath) {
        this.xmlConfigurationPath = xmlConfigurationPath;
    }


    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

}
