package com.ejbcamonitor.controller;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ejbcamonitor.model.CaStatus;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml", "/applicationContext-test.xml"})
public class HomeControllerTest {
    
    @Autowired
    private HomeController homeController;

    @Test
    public void testCheck() {
        
        //TODO add your URL to test
        List<CaStatus> caStatusList = homeController.checkCaStatus(null);
        System.out.println(caStatusList);
        Assert.assertEquals(31, caStatusList.size());
    }
    
}
