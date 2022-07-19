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

package com.jdqc.aam.domain.openapi;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
public class OpenAPI {

    @JsonAlias("servers")
    List<Server> servers;

    @JsonAlias("openapi")
    String openAPIVersion;

    @JsonAlias("info")
    Info info;

    @JsonAlias("tags")
    List<Tag> tags;

    @JsonAlias("paths")
    Map<String, JsonNode> paths;

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Info{
        String title;
        String description;
        String version;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Tag{
        String name;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Parameter{
        String name;
        String in;
        String description;
        Boolean required;
        Schema schema;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Schema {
        String type;

        Map<String, JsonNode> properties;

        List<String> required;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Server {
        String url;
    }
}
