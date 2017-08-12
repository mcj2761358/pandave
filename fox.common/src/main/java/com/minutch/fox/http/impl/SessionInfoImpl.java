package com.minutch.fox.http.impl;

import com.minutch.fox.http.SessionInfo;
import lombok.Data;

/**
 * Created by Minutch on 16/1/22.
 */
@Data
public class SessionInfoImpl implements SessionInfo {

     private Long storeId;
     private Long empId;
     private String ip;


    public void clean() {
        storeId = null;
        empId = null;
        ip = null;
    }

    public void copy(SessionInfo sessionInfo) {
        this.storeId = sessionInfo.getStoreId();
        this.empId = sessionInfo.getEmpId();
        this.ip = sessionInfo.getIp();
    }
}
