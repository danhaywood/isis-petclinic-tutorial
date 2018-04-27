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

import java.math.BigDecimal;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.wrapper.DisabledException;

import org.isisaddons.module.fakedata.dom.FakeDataService;

import domainapp.modules.impl.PetClinicModuleIntegTestAbstract;
import domainapp.modules.impl.pets.dom.Owner;
import domainapp.modules.impl.pets.dom.Pet;
import domainapp.modules.impl.pets.fixture.Owner_enum;
import domainapp.modules.impl.visits.contributions.Pet_visits;
import domainapp.modules.impl.visits.dom.Visit;
import static org.assertj.core.api.Assertions.assertThat;

public class Visit_enterOutcome_IntegTest extends PetClinicModuleIntegTestAbstract {

    Visit visit;

    @Before
    public void setup() {
        // given
        Owner owner = runBuilderScript(Owner_enum.JOHN_SMITH);
        Pet pet = owner.getPets().first();
        visit = wrap(mixin(Pet_visits.class, pet)).coll().iterator().next();
    }

    @Test
    public void happy_case() {

        // when
        String diagnosis = someRandomDiagnosis();
        BigDecimal cost = someRandomCost();

        wrap(visit).enterOutcome(diagnosis, cost);

        // then
        assertThat(visit.getDiagnosis()).isEqualTo(diagnosis);
        assertThat(visit.getCost()).isEqualTo(cost);
    }

    @Test
    public void cannot_edit_outcome_directly() {

        // expecting
        expectedExceptions.expect(DisabledException.class);
        expectedExceptions.expectMessage("Use 'enter outcome' action");

        // when
        String diagnosis = someRandomDiagnosis();
        wrap(visit).setDiagnosis(diagnosis);
    }

    @Test
    public void cannot_edit_cost_directly() {

        // expecting
        expectedExceptions.expect(DisabledException.class);
        expectedExceptions.expectMessage("Use 'enter outcome' action");

        // when
        BigDecimal cost = someRandomCost();

        wrap(visit).setCost(cost);
    }

    private BigDecimal someRandomCost() {
        return new BigDecimal(20.00 + fakeDataService.doubles().upTo(30.00d));
    }

    private String someRandomDiagnosis() {
        return fakeDataService.lorem().paragraph(3);
    }

    @Inject
    FakeDataService fakeDataService;
}