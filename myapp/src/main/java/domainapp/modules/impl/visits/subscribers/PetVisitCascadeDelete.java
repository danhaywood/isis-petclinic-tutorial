package domainapp.modules.impl.visits.subscribers;

import java.util.Collection;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.modules.impl.pets.dom.Pet;
import domainapp.modules.impl.visits.dom.Visit;
import domainapp.modules.impl.visits.dom.Visits;

@DomainService(nature = NatureOfService.DOMAIN)
public class PetVisitCascadeDelete
        extends org.apache.isis.applib.AbstractSubscriber {

    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(Pet.RemovingEvent ev) {
        Collection<Visit> visitsForPet = visits.findByPet(ev.getSource());
        for (Visit visit : visitsForPet) {
            repositoryService.removeAndFlush(visit);
        }
    }

    @javax.inject.Inject
    Visits visits;

    @javax.inject.Inject
    RepositoryService repositoryService;
}
