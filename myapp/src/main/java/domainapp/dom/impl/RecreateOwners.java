package domainapp.dom.impl;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class RecreateOwners extends FixtureScript {

    public RecreateOwners() {
        super(null, null, Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext ec) {

        isisJdoSupport.deleteAll(Owner.class);

        ec.addResult(this,
                this.owners.create("Smith", "John", null));
        ec.addResult(this,
                this.owners.create("Jones", "Mary", "+353 1 555 1234"));
        ec.addResult(this,
                this.owners.create("Hughes", "Fred", "07777 987654"));
    }

    @Inject
    Owners owners;
    @Inject IsisJdoSupport isisJdoSupport;
}
