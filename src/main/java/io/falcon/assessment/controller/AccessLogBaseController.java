package io.falcon.assessment.controller;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AccessLogBaseController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Map<String, String> getInfo(){
        Map<String, String> resultMap = Maps.newHashMap();
        resultMap.put("description", "Falcon Assessment Project");
        return resultMap;
    }
}
