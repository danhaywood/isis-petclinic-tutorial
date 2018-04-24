package domainapp.modules.impl;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecification;
import org.apache.isis.applib.services.fixturespec.FixtureScriptsSpecificationProvider;

import domainapp.modules.impl.pets.fixture.RecreateOwnersAndPets;

@DomainService(nature = NatureOfService.DOMAIN)
public class PetClinicFixtureScriptSpecProvider implements FixtureScriptsSpecificationProvider {
    @Override
    public FixtureScriptsSpecification getSpecification() {
        return FixtureScriptsSpecification.builder(getClass())
                .withRunScriptDefault(RecreateOwnersAndPets.class)
                .build();
    }
}
