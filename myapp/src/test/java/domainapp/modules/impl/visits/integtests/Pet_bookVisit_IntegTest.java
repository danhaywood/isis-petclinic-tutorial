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
package domainapp.modules.impl.visits.integtests;

import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.wrapper.InvalidException;
import org.apache.isis.core.integtestsupport.IntegrationTestAbstract3;
import org.apache.isis.core.metamodel.deployment.DeploymentCategory;
import org.apache.isis.core.metamodel.deployment.DeploymentCategoryProvider;
import org.apache.isis.core.runtime.services.i18n.po.TranslationServicePo;

import domainapp.modules.PetClinicModule;
import domainapp.modules.impl.pets.dom.Owner;
import domainapp.modules.impl.pets.dom.Pet;
import domainapp.modules.impl.pets.fixture.DeleteAllOwnersAndPets;
import domainapp.modules.impl.pets.fixture.Owner_enum;
import domainapp.modules.impl.visits.contributions.Pet_bookVisit;
import domainapp.modules.impl.visits.dom.Visit;
import domainapp.modules.impl.visits.fixture.DeleteAllVisits;
import lombok.Getter;
import static org.assertj.core.api.Assertions.assertThat;

public class Pet_bookVisit_IntegTest extends IntegrationTestAbstract3 {

    public Pet_bookVisit_IntegTest() {
        super(new PetClinicModule()
                // disable the TranslationServicePo domain service
                .withAdditionalServices(DeploymentCategoryProviderForTesting.class)
                .withConfigurationProperty(TranslationServicePo.KEY_PO_MODE, "write")
        );
    }

    public static class DeploymentCategoryProviderForTesting implements DeploymentCategoryProvider {
        @Getter
        DeploymentCategory deploymentCategory = DeploymentCategory.PROTOTYPING;
    }

    @Before
    public void setUp() {
        runFixtureScript(
                new DeleteAllVisits(),
                new DeleteAllOwnersAndPets()
        );
    }

    @Test
    public void happy_case() {

        // given
        runFixtureScript(Owner_enum.FRED_HUGHES.builder());

        Owner owner = Owner_enum.FRED_HUGHES.findUsing(serviceRegistry);
        Pet pet = owner.getPets().first();
        Pet_bookVisit mixin = factoryService.mixin(Pet_bookVisit.class, pet);

        // when
        LocalDateTime default0Act = mixin.default0Act();
        String reason = "off her food";
        Visit visit = wrap(mixin).act(default0Act, reason);

        // then
        assertThat(visit.getPet()).isEqualTo(pet);
        assertThat(visit.getVisitAt()).isEqualTo(default0Act);
        assertThat(visit.getReason()).isEqualTo(reason);
    }

    @Test
    public void reason_is_required() {

        // given
        runFixtureScript(Owner_enum.FRED_HUGHES.builder());

        Owner owner = Owner_enum.FRED_HUGHES.findUsing(serviceRegistry);
        Pet pet = owner.getPets().first();
        Pet_bookVisit mixin = factoryService.mixin(Pet_bookVisit.class, pet);

        // expect
        expectedExceptions.expect(InvalidException.class);
        expectedExceptions.expectMessage("Mandatory");

        // when
        LocalDateTime default0Act = mixin.default0Act();
        String reason = null;
        wrap(mixin).act(default0Act, reason);
    }

}