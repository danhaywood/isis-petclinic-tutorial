package domainapp.modules.impl.pets.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.setup.PersonaEnumPersistAll;

public class RecreateOwnersAndPets extends FixtureScript {

    public RecreateOwnersAndPets() {
        super(null, null, Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext ec) {
        ec.executeChild(this, new DeleteAllOwnersAndPets());
        ec.executeChild(this, new PersonaEnumPersistAll<>(Owner_enum.class));
    }

}
