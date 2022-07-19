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

package com.jdqc.aam.controller;

import com.jdqc.aam.domain.aam.AAMConfiguration;
import com.jdqc.aam.domain.api.SingleEntrypointAPIs;
import com.jdqc.aam.service.APIService;
import com.jdqc.aam.service.GitHubService;
import com.jdqc.aam.service.MonitoringService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Log4j2
@Controller
public class MonitoringController {

    @Resource
    private GitHubService githubService;

    @Resource
    private APIService apiService;

    @Resource
    private MonitoringService monitoringService;

    @Resource
    private AAMConfiguration configuration;

    public void monitorGitHubIssues(){
        var repo = configuration.getGithub().getRepository();
        var label = configuration.getGithub().getLabel();
        log.info("Using github issues to monitor");
        var issues = githubService.getGitHubRepoIssues(repo, label);
        log.info("{} issues are found", issues.size());
        var apis = apiService.getAPIsFromIssues(issues);
        for (var api : apis){
            monitoringService.monitor(new SingleEntrypointAPIs(api));
        }
    }
}
