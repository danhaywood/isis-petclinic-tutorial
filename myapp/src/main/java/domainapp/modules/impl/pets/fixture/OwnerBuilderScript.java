package domainapp.modules.impl.pets.fixture;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.BuilderScriptAbstract;

import domainapp.modules.impl.pets.dom.Owner;
import domainapp.modules.impl.pets.dom.Owners;
import domainapp.modules.impl.pets.dom.PetSpecies;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@lombok.experimental.Accessors(chain = true)
public class OwnerBuilderScript extends BuilderScriptAbstract<Owner, OwnerBuilderScript> {

    @Getter @Setter
    private String lastName;
    @Getter @Setter
    private String firstName;
    @Getter @Setter
    private String phoneNumber;
    @Getter @Setter
    private String emailAddress;

    @Setter
    private List<PetData> petData = Lists.newArrayList();

    @Data
    static class PetData {
        private final String name;
        private final PetSpecies petSpecies;
    }

    @Getter
    private Owner object;

    @Override
    protected void execute(final ExecutionContext ec) {

        checkParam("lastName", ec, String.class);
        checkParam("firstName", ec, String.class);

        Owner owner = wrap(owners).create(lastName, firstName, phoneNumber);
        wrap(owner).setEmailAddress(emailAddress);

        for (PetData petDatum : petData) {
            wrap(owner).newPet(petDatum.name, petDatum.petSpecies);
        }

        this.object = owner;
    }

    @Inject
    Owners owners;
}
