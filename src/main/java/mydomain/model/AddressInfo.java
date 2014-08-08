package mydomain.model;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Discriminator(value = "AddressInfo")
public class AddressInfo extends SimpleProperties {

    String street;
    
    Long address_id;

    public AddressInfo(String street, Long address_id) {
        this.street = street;
        this.address_id = address_id;
    }


}
