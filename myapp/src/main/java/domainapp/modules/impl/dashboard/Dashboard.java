package domainapp.modules.impl.dashboard;

import java.util.List;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;

import domainapp.modules.impl.pets.dom.Owner;
import domainapp.modules.impl.pets.dom.Owners;

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

    @javax.inject.Inject
    Owners owners;
}
