package domainapp.modules.impl.pets.fixture;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Lists;

import org.joda.time.LocalDateTime;

import org.apache.isis.applib.fixturescripts.BuilderScriptAbstract;
import org.apache.isis.applib.fixturescripts.clock.TickingClockFixture;
import org.apache.isis.applib.services.clock.ClockService;

import org.isisaddons.module.fakedata.dom.FakeDataService;

import domainapp.modules.impl.pets.dom.Owner;
import domainapp.modules.impl.pets.dom.Owners;
import domainapp.modules.impl.pets.dom.Pet;
import domainapp.modules.impl.pets.dom.PetSpecies;
import domainapp.modules.impl.visits.contributions.Pet_bookVisit;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@lombok.experimental.Accessors(chain = true)
public class OwnerBuilderScript extends BuilderScriptAbstract<Owner, OwnerBuilderScript> {

    @Getter @Setter
    private String lastName;
    @Getter @Setter
    private String firstName;
    @Getter @Setter
    private String phoneNumber;
    @Getter @Setter
    private String emailAddress;

    @Setter
    private List<PetData> petData = Lists.newArrayList();

    @Data
    static class PetData {
        private final String name;
        private final PetSpecies petSpecies;
        private final int numberOfVisits;
    }

    @Getter
    private Owner object;

    @Override
    protected void execute(final ExecutionContext ec) {

        checkParam("lastName", ec, String.class);
        checkParam("firstName", ec, String.class);

        Owner owner = wrap(owners).create(lastName, firstName, phoneNumber);
        wrap(owner).setEmailAddress(emailAddress);

        LocalDateTime now = clockService.nowAsLocalDateTime();
        try {
            for (PetData petDatum : petData) {
                Pet pet = wrap(owner).newPet(petDatum.name, petDatum.petSpecies);
                for (int i = 0; i < petDatum.numberOfVisits; i++) {
                    LocalDateTime someTimeInPast = someRandomTimeInPast();
                    String someReason = someReason();
                    setTimeTo(ec, someTimeInPast);
                    wrap(mixin(Pet_bookVisit.class, pet)).act(someTimeInPast.plusDays(3), someReason);
                }
            }
        } finally {
            setTimeTo(ec, now);
        }

        this.object = owner;
    }

    private String someReason() {
        return fakeDataService.lorem().paragraph(fakeDataService.ints().between(1, 3));
    }

    private LocalDateTime someRandomTimeInPast() {
        return clockService.now()
                .toDateTimeAtStartOfDay().minus(fakeDataService.jodaPeriods().daysBetween(5, 365))
                .plusHours(fakeDataService.ints().between(9, 17))
                .plusMinutes(5 * fakeDataService.ints().between(0, 12))
                .toLocalDateTime();
    }

    private void setTimeTo(final ExecutionContext ec, final LocalDateTime ldt) {
        ec.executeChild(this, new TickingClockFixture().setDate(ldt.toString("yyyyMMddhhmm")));
    }

    @Inject
    Owners owners;

    @Inject
    FakeDataService fakeDataService;

    @Inject
    ClockService clockService;
}
