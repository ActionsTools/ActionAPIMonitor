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

package com.jdqc.aam.domain.api;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdqc.aam.domain.openapi.OpenAPI;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@Setter
@Getter
public class SingleEntrypointAPIs {

    String server;

    List<SingleAPI> singleAPIList;

    public SingleEntrypointAPIs(OpenAPI openAPI){
        this.server = buildServer(openAPI);
        this.singleAPIList = buildSingleAPIList(openAPI);
    }

    private static String buildServer(OpenAPI openAPI) {
        var servers = openAPI.getServers();
        if (servers.isEmpty()){
            return "";
        }
        return servers.get(0).getUrl();
    }

    private static List<SingleAPI> buildSingleAPIList(OpenAPI openAPI){
        var paths = openAPI.getPaths();
        var singleAPIList = new ArrayList<SingleAPI>(paths.size());
        var keys = paths.keySet();
        for (var path: keys){
            var node = paths.get(path);
            var methodIterator = node.fieldNames();
            while (methodIterator.hasNext()){
                String method = methodIterator.next();
                try {
                    var api = new ObjectMapper().readValue(node.get(method).asText(), SingleAPI.class);
                    if (api == null){
                        //When no parameters and no validations
                        api = new SingleAPI();
                    }
                    api.setMethod(Objects.requireNonNull(HttpMethod.resolve(method)));
                    api.setPath(path);
                    singleAPIList.add(api);
                }catch (JacksonException e){
                    log.error("Invalid properties in API {} \"{}\"", method, path);
                }catch (NullPointerException e){
                    log.error("Invalid method name in API \"{}\"", path);
                }
            }
        }
        return singleAPIList;
    }
}
