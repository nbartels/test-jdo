package mydomain.model;

import javax.jdo.annotations.*;

@PersistenceCapable(detachable="true", identityType = IdentityType.DATASTORE)
@DatastoreIdentity(customStrategy = "max")
public class Person
{
    String name;

    public Person(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
