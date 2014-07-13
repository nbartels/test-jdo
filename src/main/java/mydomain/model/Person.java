package mydomain.model;

import java.util.List;
import javax.jdo.annotations.*;

@PersistenceCapable(detachable="true")
public class Person
{
    @PrimaryKey
    Long id;

    String name;
    
    @Persistent
    @Extension(vendorName="DataNucleus", key="mapping-class", value="mydomain.model.mapping.EmailMapping")
    Email email;

    public Person(long id, String name, Email email)
    {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public Long getId()
    {
        return id;
    }
}
