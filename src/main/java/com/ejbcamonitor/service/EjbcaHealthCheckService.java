package com.ejbcamonitor.service;

import com.ejbcamonitor.model.CaInfo;
import com.ejbcamonitor.model.CaStatus;

public interface EjbcaHealthCheckService {
    
    CaStatus checkEjbcaHealth(CaInfo caInfo);

}
