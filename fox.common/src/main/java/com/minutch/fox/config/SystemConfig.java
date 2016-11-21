package com.minutch.fox.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Minutch on 16/3/20.
 */
@Data
@Service
public class SystemConfig {

    @Value("${fox.server.url}")
    private String serverUrl;
}
