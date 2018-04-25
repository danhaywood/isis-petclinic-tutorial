package domainapp.modules.impl.pets.fixture;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.setup.PersonaEnumPersistAll;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import domainapp.modules.impl.pets.dom.Owner;
import domainapp.modules.impl.pets.dom.Pet;

public class RecreateOwnersAndPets extends FixtureScript {

    public RecreateOwnersAndPets() {
        super(null, null, Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext ec) {

        isisJdoSupport.deleteAll(Pet.class);
        isisJdoSupport.deleteAll(Owner.class);

        ec.executeChild(this, new PersonaEnumPersistAll<>(Owner_enum.class));
    }

    @Inject
    IsisJdoSupport isisJdoSupport;
}
