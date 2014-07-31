package mydomain.model;

import javax.jdo.annotations.*;

@PersistenceCapable(detachable="true", table = "Person")
@Discriminator(column = "jdo_class", value = "Person", strategy = DiscriminatorStrategy.VALUE_MAP)
public class Person
{
    @PrimaryKey
    Long id;

    String name;
    
    public Person(long id, String name)
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
