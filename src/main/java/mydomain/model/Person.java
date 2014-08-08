package mydomain.model;

import java.util.List;
import javax.jdo.annotations.*;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Discriminator(value = "Person")
public class Person extends SimpleProperties {

    @Join(column = "element_id")
    @Element(column = "address_id")
    List<AddressInfo> addressInfoList;
    
    @PrimaryKey
    Long property_id;
    
    @Embedded
    State state;
    
    public Long getProperty_id() {
        return property_id;
    }

    public void setProperty_id(Long property_id) {
        this.property_id = property_id;
    }

    public Person(long id) {
        this.property_id = id;
    }

    public Long getPersonId() {
        return property_id;
    }

    public List<AddressInfo> getAddressInfoList() {
        return addressInfoList;
    }

    public void setAddressInfoList(List<AddressInfo> addressInfoList) {
        this.addressInfoList = addressInfoList;
    }
}
