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

import com.jdqc.aam.domain.api.SingleEntrypointAPIs;

import java.util.List;

public interface MonitoringService {

    /**
     * Monitor APIs
     *
     * @param apIs  APIs
     */
    void monitor(SingleEntrypointAPIs apIs);

    /**
     * Monitor a list of APIs
     *
     * @param apIsList  a list of APIs
     */
    void monitor(List<SingleEntrypointAPIs> apIsList);
}
