package domainapp.modules.impl.pets.fixture;

import org.apache.isis.applib.fixturescripts.teardown.TeardownFixtureAbstract2;

import domainapp.modules.impl.pets.dom.Owner;
import domainapp.modules.impl.pets.dom.Pet;
import domainapp.modules.impl.visits.dom.Visit;

public class DeleteAllOwnersAndPets extends TeardownFixtureAbstract2 {
    @Override
    protected void execute(final ExecutionContext ec) {
        deleteFrom(Visit.class);
        deleteFrom(Pet.class);
        deleteFrom(Owner.class);
    }
}
