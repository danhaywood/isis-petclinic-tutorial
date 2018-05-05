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
package domainapp.modules.impl.pets.integtests;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import org.apache.isis.applib.services.wrapper.DisabledException;

import domainapp.modules.impl.PetClinicModuleIntegTestAbstract;
import domainapp.modules.impl.pets.dom.Owner;
import domainapp.modules.impl.pets.fixture.Owner_enum;
import domainapp.modules.impl.visits.dom.Visit;
import domainapp.modules.impl.visits.dom.Visits;
import static org.assertj.core.api.Assertions.assertThat;

public class Owner_delete_IntegTest extends PetClinicModuleIntegTestAbstract {

    @Test
    public void can_delete_if_there_are_no_unpaid_visits() {

        // given
        runFixtureScript(Owner_enum.FRED_HUGHES.builder());

        Owner owner = Owner_enum.FRED_HUGHES.findUsing(serviceRegistry);
        List<Visit> any = visits.findNotPaidBy(owner);
        assertThat(any).isEmpty();

        // when
        wrap(owner).delete();

        // then
        Owner ownerAfter = Owner_enum.FRED_HUGHES.findUsing(serviceRegistry);
        assertThat(ownerAfter).isNull();
    }

    @Test
    public void cannot_delete_with_unpaid_visits() {

        // given
        runFixtureScript(Owner_enum.MARY_JONES.builder());

        Owner owner = Owner_enum.MARY_JONES.findUsing(serviceRegistry);
        List<Visit> any = visits.findNotPaidBy(owner);
        assertThat(any).isNotEmpty();

        // expect
        expectedExceptions.expect(DisabledException.class);
        expectedExceptions.expectMessage("This owner still has unpaid visit(s)");

        // when
        wrap(owner).delete();
    }

    @Inject
    Visits visits;

}