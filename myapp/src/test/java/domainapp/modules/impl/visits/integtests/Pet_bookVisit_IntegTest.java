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
import org.junit.Test;

import org.apache.isis.applib.services.wrapper.InvalidException;

import domainapp.modules.impl.PetClinicModuleIntegTestAbstract;
import domainapp.modules.impl.pets.dom.Owner;
import domainapp.modules.impl.pets.dom.Pet;
import domainapp.modules.impl.pets.fixture.Owner_enum;
import domainapp.modules.impl.visits.contributions.Pet_bookVisit;
import domainapp.modules.impl.visits.dom.Visit;
import static org.assertj.core.api.Assertions.assertThat;

public class Pet_bookVisit_IntegTest extends PetClinicModuleIntegTestAbstract {

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