package domainapp.dom.impl;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

@Mixin(method = "coll")
public class Pet_visits {

    private final Pet pet;
    public Pet_visits(Pet pet) {
        this.pet = pet;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @CollectionLayout(defaultView = "table")
    public java.util.Collection<Visit> coll() {
        return visits.findByPet(pet);
    }

    @javax.inject.Inject
    Visits visits;

}
