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

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdqc.aam.domain.openapi.OpenAPI;
import com.jdqc.aam.service.APIService;
import com.jdqc.aam.domain.github.GitHubIssue;
import com.jdqc.aam.exception.InvalidAAMConfigException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class APIServiceImpl implements APIService {

    @Override
    public List<OpenAPI> getAPIsFromIssues(List<GitHubIssue> issues) {
        var apiList = new ArrayList<OpenAPI>(issues.size());
        for (var issue : issues){
            try {
                var api = getAPIsFromIssue(issue);
                apiList.add(api);
            }catch (InvalidAAMConfigException e){
                log.error(e.getMessage());
            }
        }
        return apiList;
    }

    private static OpenAPI getAPIsFromIssue(GitHubIssue issue) {
        String body = issue.getBody();
        var bodySp = body.split("```JSON");
        if (bodySp.length != 2){
            throw new InvalidAAMConfigException("Invalid Issue template");
        }
        var apiStr = bodySp[1].replace("```","");
        try {
            return new ObjectMapper().readValue(apiStr, OpenAPI.class);
        }catch (JacksonException e){
            throw new InvalidAAMConfigException("Invalid Issue template");
        }
    }
}
