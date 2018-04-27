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
package domainapp.modules.impl.visits.dom;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.ComparisonChain;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.clock.ClockService;

import domainapp.modules.impl.pets.dom.Pet;
import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "visits" )
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column ="version")
@javax.jdo.annotations.Unique(name="Visit_visitAt_pet_UNQ", members = {"visitAt","pet"})
@javax.jdo.annotations.Index(name="Visit_pet_visitAt_IDX", members = {"pet","visitAt"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
public class Visit implements Comparable<Visit> {

    public Visit(final Pet pet, final LocalDateTime visitAt, final String reason) {
        this.pet = pet;
        this.visitAt = visitAt;
        this.reason = reason;
    }

    public String title() {
        return String.format(
                "%s: %s (%s)",
                getVisitAt().toString("yyyy-MM-dd hh:mm"),
                getPet().getOwner().getName(),
                getPet().getName());
    }

    @javax.jdo.annotations.Column(allowsNull = "false", name = "petId")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private Pet pet;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private LocalDateTime visitAt;

    @javax.jdo.annotations.Column(allowsNull = "false", length = 4000)
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(multiLine = 5)
    @Getter @Setter
    private String reason;

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public Visit enterOutcome(
            @Parameter(maxLength = 4000)
            @ParameterLayout(multiLine = 5)
            final String diagnosis,
            final BigDecimal cost) {
        this.diagnosis = diagnosis;
        this.cost = cost;
        return this;
    }

    @javax.jdo.annotations.Column(allowsNull = "true", length = 4000)
    @Property(editing = Editing.DISABLED, editingDisabledReason = "Use 'enter outcome' action")
    @PropertyLayout(multiLine = 5)
    @Getter @Setter
    private String diagnosis;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 6, scale = 2)
    @Property(editing = Editing.DISABLED, editingDisabledReason = "Use 'enter outcome' action")
    @Getter @Setter
    private BigDecimal cost;

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public Visit paid() {
        paidOn = clockService.now();
        return this;
    }

    public String disablePaid() {
        return getPaidOn() != null ? "Already paid": null;
    }

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Property(editing = Editing.DISABLED, editingDisabledReason = "Use 'paid on' action")
    @Getter @Setter
    private LocalDate paidOn;

    @Override
    public String toString() {
        return getVisitAt().toString("yyyy-MM-dd hh:mm");
    }

    @Override
    public int compareTo(final Visit other) {
        return ComparisonChain.start()
                .compare(this.getVisitAt(), other.getVisitAt())
                .compare(this.getPet(), other.getPet())
                .result();
    }

    @Inject
    ClockService clockService;

}
