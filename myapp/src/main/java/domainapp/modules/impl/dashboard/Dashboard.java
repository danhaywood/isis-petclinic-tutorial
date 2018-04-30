package domainapp.modules.impl.dashboard;

import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.LocalDateTime;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.clock.ClockService;

import domainapp.modules.impl.pets.dom.Owner;
import domainapp.modules.impl.pets.dom.Owners;
import domainapp.modules.impl.visits.dom.Visit;
import domainapp.modules.impl.visits.dom.Visits;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        objectType = "dashboard.Dashboard"
)
public class Dashboard {

    public String title() { return getOwners().size() + " owners"; }

    @CollectionLayout(defaultView = "table")
    public List<Owner> getOwners() {
        return owners.listAll();
    }

    @CollectionLayout(defaultView = "table")
    public List<Visit> getOverdue() {
        List<Visit> notPaid = visits.findNotPaid();
        LocalDateTime thirtyDaysAgo = clockService.nowAsLocalDateTime().minusDays(30);
        return notPaid.stream()
                .filter(x -> x.getVisitAt().isBefore(thirtyDaysAgo))
                .collect(Collectors.toList());
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT, associateWith = "overdue")
    public Dashboard paid(List<Visit> visits) {
        for (Visit visit : visits) {
            if(visit.getPaidOn() == null) {
                visit.paid();
            }
        }
        return this;
    }

    @javax.inject.Inject
    Visits visits;

    @javax.inject.Inject
    ClockService clockService;

    @javax.inject.Inject
    Owners owners;
}
