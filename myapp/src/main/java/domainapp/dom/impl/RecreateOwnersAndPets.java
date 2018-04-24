package domainapp.dom.impl;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import lombok.Data;

public class RecreateOwnersAndPets extends FixtureScript {

    public RecreateOwnersAndPets() {
        super(null, null, Discoverability.DISCOVERABLE);
    }

    @Data
    static class PetData {
        private final String name;
        private final PetSpecies petSpecies;
    }

    @Override
    protected void execute(final ExecutionContext ec) {

        isisJdoSupport.deleteAll(Pet.class);
        isisJdoSupport.deleteAll(Owner.class);

        ec.addResult(this,
                createOwner("Smith", "John", null,
                        new PetData("Rover", PetSpecies.Dog))
        );
        ec.addResult(this,
                createOwner("Jones", "Mary", "+353 1 555 1234",
                        new PetData("Tiddles", PetSpecies.Cat),
                        new PetData("Harry", PetSpecies.Budgerigar)
                ));
        ec.addResult(this,
                createOwner("Hughes", "Fred", "07777 987654",
                        new PetData("Jemima", PetSpecies.Hamster)
                ));
    }

    private Owner createOwner(
            final String lastName,
            final String firstName,
            final String phoneNumber,
            final PetData... pets) {
        Owner owner = this.owners.create(lastName, firstName, phoneNumber);
        for (PetData pet : pets) {
            owner.newPet(pet.name, pet.petSpecies);
        }
        return owner;
    }

    @Inject
    Owners owners;
    @Inject
    IsisJdoSupport isisJdoSupport;
}
