package com.ejbcamonitor.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CaInfoList {

    private List<CaInfo> caInfos = new LinkedList<CaInfo>();

    public void add(CaInfo caInfo) {
        caInfos.add(caInfo);
    }

    public List<CaInfo> getCaInfos() {
        return Collections.unmodifiableList(caInfos);
    }

    @Override
    public String toString() {
        return "CaInfoList [caInfos=" + caInfos + "]";
    }

}
