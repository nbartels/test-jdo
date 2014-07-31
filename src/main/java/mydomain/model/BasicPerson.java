package mydomain.model;

import javax.jdo.annotations.*;

@PersistenceCapable(detachable="true")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Discriminator(column = "jdo_class", value = "BasicPerson")
public class BasicPerson
{
    @PrimaryKey
    Long id;

    String name;

    public BasicPerson(long id, String name)
    {
        this.id = id;
        this.name = name;
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
