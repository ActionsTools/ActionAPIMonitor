/*
 *    Copyright 2022 Yujie Liu and other contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.jdqc.aam.service.impl;

import com.jdqc.aam.domain.api.SingleEntrypointAPIs;
import com.jdqc.aam.service.MonitoringService;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Log4j2
@Service
public class MonitoringServiceImpl implements MonitoringService {

    @Resource
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public void monitor(SingleEntrypointAPIs apIs) {
        for (var api : apIs.getSingleAPIList()){
            var restURL = apIs.getServer() + api.getPath();
            switch (api.getMethod()){
                case GET:
                    this.sendRESTGETRequest(restURL);
                    break;
            }
        }
    }

    @Override
    public void monitor(List<SingleEntrypointAPIs> apIsList) {
        apIsList.forEach(this::monitor);
    }

    public void sendRESTGETRequest(String restURL){
        this.validateRESTGETRequest(restURL, "");
    }

    public boolean validateRESTGETRequest(String restURL, String expected){
        var template = this.restTemplateBuilder.build();
        var actual = template.getForObject(restURL, String.class);
        return expected.equals(actual);
    }
}
