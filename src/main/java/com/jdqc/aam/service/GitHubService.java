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

import com.jdqc.aam.domain.github.GitHubIssue;

import java.util.List;

public interface GitHubService {

    /**
     * Get the open issues of a GitHub repository without labels
     *
     * @param repository    GitHub Repository
     * @return              list of issues
     */
    List<GitHubIssue> getGitHubRepoIssues(String repository);

    /**
     * Get the open issues of repository with labels
     *
     * @param repository    GitHub Repository
     * @param labels        filter the labels of the issues
     * @return              list of issues
     */
    List<GitHubIssue> getGitHubRepoIssues(String repository, String labels);

    /**
     * Get the issues of repository with state and labels
     *
     * @param repository    GitHub Repository
     * @param state         filter open or close, default open
     * @param labels        filter the labels of the issues
     * @return              list of issues
     */
    List<GitHubIssue> getGitHubRepoIssues(String repository, String state, String labels);
}
