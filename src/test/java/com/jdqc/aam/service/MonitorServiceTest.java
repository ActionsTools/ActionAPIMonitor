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

package com.jdqc.aam.service;

import com.jdqc.aam.domain.aam.AAMConfiguration;
import com.jdqc.aam.domain.api.SingleEntrypointAPIs;
import com.jdqc.aam.domain.openapi.OpenAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("localtest")
@SpringBootTest
class MonitorServiceTest {

    @Resource
    private AAMConfiguration configuration;

    @Resource
    private MonitoringService monitoringService;

    @Resource
    private GitHubService githubService;

    @Resource
    private APIService apiService;

    private OpenAPI openAPI;

    @BeforeEach
    void init(){
        var issues = githubService.getGitHubRepoIssues(configuration.getGithub().getRepository());
        assertTrue(issues.size() > 0);
        var gitHubIssues = issues.stream().filter(iss -> iss.getNumber() == 4).collect(Collectors.toList());
        assertEquals(1, gitHubIssues.size());
        var apIsList = apiService.getAPIsFromIssues(gitHubIssues);
        assertEquals(1, apIsList.size());
        openAPI = apIsList.get(0);
    }

    @Test
    void monitorGitHubIssues(){
        monitoringService.monitor(new SingleEntrypointAPIs(openAPI));
        assertTrue(true);
    }
}
