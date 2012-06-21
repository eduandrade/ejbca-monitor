package com.ejbcamonitor.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ejbcamonitor.model.CaStatus;
import com.ejbcamonitor.service.CheckCaStatusService;

@Controller
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private CheckCaStatusService checkCaStatusService;
    
    @RequestMapping(value = "/")
    public String home() {
        logger.info("HomeController: Passing through...");
        return "home";
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<CaStatus> checkCaStatus(Model model) {
        List<CaStatus> caStatusList = checkCaStatusService.checkCaStatus();
        Collections.sort(caStatusList);
        return caStatusList;
    }

}
