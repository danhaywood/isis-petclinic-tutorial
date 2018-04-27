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

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.setup.PersonaEnumPersistAll;
import org.apache.isis.applib.services.clock.ClockService;

import domainapp.modules.impl.PetClinicModuleIntegTestAbstract;
import domainapp.modules.impl.pets.fixture.Owner_enum;
import domainapp.modules.impl.visits.dom.Visit;
import domainapp.modules.impl.visits.dom.Visits;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Visit_pay_IntegTest extends PetClinicModuleIntegTestAbstract {

    Visit visit;

    @Before
    public void setup() {
        runFixtureScript(new PersonaEnumPersistAll<>(Owner_enum.class));
        visit = visits.findNotPaid().get(0);
    }

    @Test
    public void happy_case() {

        // given
        assertThat(visit.getPaidOn()).isNull();

        // when
        wrap(visit).paid();

        // then
        assertThat(visit.getPaidOn()).isNotNull();
        assertThat(visit.getPaidOn()).isEqualTo(clockService.now());
    }

    @Inject
    Visits visits;

    @Inject
    ClockService clockService;
}