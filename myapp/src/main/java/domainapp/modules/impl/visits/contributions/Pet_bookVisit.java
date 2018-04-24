package domainapp.modules.impl.visits.contributions;

import org.joda.time.LocalDateTime;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.impl.pets.dom.Pet;
import domainapp.modules.impl.visits.dom.Visit;

@Mixin(method = "act")
public class Pet_bookVisit {

    private final Pet pet;
    public Pet_bookVisit(Pet pet) {
        this.pet = pet;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT, associateWith = "visits")
    @ActionLayout(named = "Book")
    public Visit act(
            final LocalDateTime at,
            @Parameter(maxLength = 4000)
            @ParameterLayout(multiLine = 5)
            final String reason) {
        return repositoryService.persist(new Visit(this.pet, at, reason));
    }

    public LocalDateTime default0Act() {
        return clockService.now()
                .plusDays(1)
                .toDateTimeAtStartOfDay()
                .toLocalDateTime()
                .plusHours(9);
    }

    public String validate0Act(final LocalDateTime proposed) {
        return proposed.isBefore(clockService.nowAsLocalDateTime())
                ? "Cannot enter date in the past"
                : null;
    }

    @javax.jdo.annotations.NotPersistent
    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.jdo.annotations.NotPersistent
    @javax.inject.Inject
    ClockService clockService;

}
