package domainapp.modules.impl.visits.dom;

import java.time.LocalDateTime;

import org.datanucleus.query.typesafe.TypesafeQuery;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import domainapp.modules.impl.pets.dom.Owner;
import domainapp.modules.impl.pets.dom.Pet;

@DomainService(nature = NatureOfService.DOMAIN)
public class Visits {

    @Programmatic
    public java.util.List<Visit> findByPet(Pet pet) {
        TypesafeQuery<Visit> q = isisJdoSupport.newTypesafeQuery(Visit.class);
        final domainapp.modules.impl.visits.dom.QVisit cand = domainapp.modules.impl.visits.dom.QVisit.candidate();
        q = q.filter(
                cand.pet.eq((q.parameter("pet", Pet.class))
            )
        );
        return q.setParameter("pet", pet)
                .executeList();
    }

    @Programmatic
    public java.util.List<Visit> findNotPaid() {
        TypesafeQuery<Visit> q = isisJdoSupport.newTypesafeQuery(Visit.class);
        final domainapp.modules.impl.visits.dom.QVisit cand = domainapp.modules.impl.visits.dom.QVisit.candidate();
        q = q.filter(
                cand.paidOn.eq(q.parameter("paidOn", LocalDateTime.class)
            )
        );
        return q.setParameter("paidOn", null)
                .executeList();
    }

    @Programmatic
    public java.util.List<Visit> findNotPaidBy(Owner owner) {
        TypesafeQuery<Visit> q = isisJdoSupport.newTypesafeQuery(Visit.class);
        final domainapp.modules.impl.visits.dom.QVisit cand = domainapp.modules.impl.visits.dom.QVisit.candidate();
        q = q.filter(
                cand.paidOn.eq(q.parameter("paidOn", LocalDateTime.class)
            ).and(
                    cand.pet.owner.eq(q.parameter("owner", Owner.class))
                )
        );
        return q.setParameter("paidOn", null)
                .setParameter("owner", owner)
                .executeList();
    }

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
