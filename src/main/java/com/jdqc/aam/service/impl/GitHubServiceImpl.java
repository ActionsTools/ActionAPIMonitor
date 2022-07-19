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

import com.jdqc.aam.domain.aam.AAMConfiguration;
import com.jdqc.aam.domain.github.GitHubAPI;
import com.jdqc.aam.domain.github.GitHubIssue;
import com.jdqc.aam.service.GitHubService;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GitHubServiceImpl implements GitHubService {

    @Resource
    private AAMConfiguration configuration;

    @Resource
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public List<GitHubIssue> getGitHubRepoIssues(String repository) {
        return this.getGitHubRepoIssues(repository, null, null);
    }

    @Override
    public List<GitHubIssue> getGitHubRepoIssues(String repository, String labels) {
        return this.getGitHubRepoIssues(repository, null, labels);
    }

    @Override
    public List<GitHubIssue> getGitHubRepoIssues(@NonNull String repository,
                                                 @Nullable String state,
                                                 @Nullable String labels) {

        RestTemplate restTemplate = this.getTemplateWithAuth();
        String url = GitHubAPI.getIssues(repository)
                + (state == null ? "?state=open" : "?state="+state)
                + (labels == null ? "" : "&labels="+labels);
        try {
            var issues = Optional.ofNullable(restTemplate.getForObject(url, GitHubIssue[].class));
            return issues.map(issue -> new ArrayList<>(List.of(issue))).orElseGet(ArrayList::new);
        }catch (RestClientException e){
            return new ArrayList<>();
        }
    }

    private RestTemplate getTemplateWithAuth(){
        var token = configuration.getGithub().getToken();
        if (StringUtils.isNotBlank(token)){
            return this.restTemplateBuilder
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "token "+ token)
                    .build();
        } else {
            return this.restTemplateBuilder
                    .build();
        }
    }
}
