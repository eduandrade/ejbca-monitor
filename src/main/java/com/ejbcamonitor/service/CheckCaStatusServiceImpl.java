package com.ejbcamonitor.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ejbcamonitor.model.CaInfo;
import com.ejbcamonitor.model.CaInfoList;
import com.ejbcamonitor.model.CaStatus;

public class CheckCaStatusServiceImpl implements CheckCaStatusService {
    
    private Logger logger = LoggerFactory.getLogger(CheckCaStatusServiceImpl.class);

    private static final int DEFAULT_THREADS = 15;

    private static final int DEFAULT_TIMEOUT = 10;

    @Autowired
    private LoadConfigurationService loadConfigurationService;

    @Autowired
    private EjbcaHealthCheckService ejbcaHealthCheckService;

    private CaInfoList caInfoList;
    
    private int timeout = DEFAULT_TIMEOUT;
    
    private int threads = DEFAULT_THREADS;
    
    private ExecutorService executorService;

    @PostConstruct
    public final void init() {
        caInfoList = loadConfigurationService.loadConfiguration();
        executorService = Executors.newFixedThreadPool(threads);
    }

    @Override
    public List<CaStatus> checkCaStatus() {
        List<CaStatus> caStatusList = new ArrayList<CaStatus>();

        Set<Future<CaStatus>> set = new HashSet<Future<CaStatus>>();
        for (CaInfo caInfo : caInfoList.getCaInfos()) {
            Future<CaStatus> future = executorService.submit(new AsyncCheckStatus(caInfo));
            set.add(future);
        }
        
        try {
            for (Future<CaStatus> future : set) {
                caStatusList.add(future.get(timeout, TimeUnit.SECONDS));
            }
        } catch (InterruptedException e) {
            logger.error("Thread interrompida!", e);
        } catch (ExecutionException e) {
            logger.error("Erro durante execucao da thread!", e);
        } catch (TimeoutException e) {
            logger.error("Timeout da thread!", e);
        }

        return caStatusList;
    }

    private class AsyncCheckStatus implements Callable<CaStatus> {

        private CaInfo caInfo;

        private AsyncCheckStatus(CaInfo caInfo) {
            this.caInfo = caInfo;
        }

        @Override
        public CaStatus call() throws Exception {
            return ejbcaHealthCheckService.checkEjbcaHealth(caInfo);
        }

    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

}
