package domainapp.modules.impl.visits.subscribers;

import java.util.Collection;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import domainapp.modules.impl.pets.dom.Owner;
import domainapp.modules.impl.visits.dom.Visit;
import domainapp.modules.impl.visits.dom.Visits;

@DomainService(nature = NatureOfService.DOMAIN)
public class VetoDeleteOfOwnerWithUnpaidVisits
        extends org.apache.isis.applib.AbstractSubscriber {

    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(Owner.Delete ev) {

        switch (ev.getEventPhase()) {
        case DISABLE:
            Collection<Visit> visitsForPet = visits.findNotPaidBy(ev.getSource());
            if (!visitsForPet.isEmpty()) {
                ev.veto("This owner still has unpaid visit(s)");
            }
            break;
        }
    }

    @javax.inject.Inject
    Visits visits;
}
