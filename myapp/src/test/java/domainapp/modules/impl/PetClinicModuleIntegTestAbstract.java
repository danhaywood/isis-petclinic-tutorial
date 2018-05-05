/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.modules.impl;

import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;
import org.apache.isis.core.metamodel.deployment.DeploymentCategory;
import org.apache.isis.core.metamodel.deployment.DeploymentCategoryProvider;
import org.apache.isis.core.runtime.services.i18n.po.TranslationServicePo;

import domainapp.modules.PetClinicModule;
import lombok.Getter;

public abstract class PetClinicModuleIntegTestAbstract extends IntegrationTestAbstract3 {

    public PetClinicModuleIntegTestAbstract() {
        super(new PetClinicModule()
                // disable the TranslationServicePo domain service
                .withAdditionalServices(DeploymentCategoryProviderForTesting.class)
                .withConfigurationProperty("isis.services.eventbus.implementation","axon")
                .withConfigurationProperty(TranslationServicePo.KEY_PO_MODE, "write")
        );
    }

    public static class DeploymentCategoryProviderForTesting implements DeploymentCategoryProvider {
        @Getter
        DeploymentCategory deploymentCategory = DeploymentCategory.PROTOTYPING;
    }


}