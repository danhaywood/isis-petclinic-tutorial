package domainapp.modules.impl.dashboard;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.NatureOfService;

@DomainService(nature = NatureOfService.DOMAIN)
public class HomePageProvider {
    @HomePage
    public Dashboard dashboard() {
        return new Dashboard();
    }
}
