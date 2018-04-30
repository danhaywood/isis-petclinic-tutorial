package domainapp.modules.impl.dashboard;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.registry.ServiceRegistry2;

@DomainService(nature = NatureOfService.DOMAIN)
public class HomePageProvider {
    @HomePage
    public Dashboard dashboard() {
        return serviceRegistry2.injectServicesInto(new Dashboard());
    }
    @Inject
    ServiceRegistry2 serviceRegistry2;
}
