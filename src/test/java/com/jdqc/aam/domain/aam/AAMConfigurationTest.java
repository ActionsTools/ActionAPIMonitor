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

package com.jdqc.aam.domain.aam;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import javax.annotation.Resource;

@SpringBootTest
class AAMConfigurationTest {

    @Resource
    private AAMConfiguration configuration;

    @Test
    void initConfiguration() {
        assertTrue(StringUtils.isNotBlank(configuration.getServer()));
        assertNotNull(configuration.getGithub());
        assertNotNull(configuration.getGithub());
        var github = configuration.getGithub();
        assertTrue(StringUtils.isNotBlank(github.getRepository()));
        assertTrue(StringUtils.isNotBlank(github.getToken()));
        assertTrue(StringUtils.isNotBlank(github.getLabel()));
    }
}
