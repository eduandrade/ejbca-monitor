package com.ejbcamonitor.service;

import java.util.List;

import com.ejbcamonitor.model.CaStatus;

public interface CheckCaStatusService {
    
    List<CaStatus> checkCaStatus();

}
