package domainapp.modules.impl.visits.contributions;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;

import domainapp.modules.impl.pets.dom.Pet;
import domainapp.modules.impl.visits.contributions.Pet_bookVisit;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Pet_bookVisit_Test {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

    @Mock
    ClockService mockClockService;

    @Test
    public void default0Act() {

        // given
        Pet pet = new Pet(null, null, null);
        Pet_bookVisit mixin = new Pet_bookVisit(pet);
        mixin.clockService = mockClockService;

        // expecting
        context.checking(new Expectations() {{
            allowing(mockClockService).now();
            // 3-Mar-2018, 14:10
            will(returnValue(new LocalDate(2018,3,3)));
        }});

        // when
        LocalDateTime actual = mixin.default0Act();

        // then
        assertThat(actual).isEqualTo(new LocalDateTime(2018,3,4,9,0));
    }
}