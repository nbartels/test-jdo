package mydomain.model;

import javax.jdo.annotations.*;
import java.util.Date;

@PersistenceCapable(detachable="true")
public class Person
{
    @PrimaryKey
    Long id;

    String name;

    Date birthDay;

    public Person(long id, String name)
    {
        this.id = id;
        this.name = name;
        this.birthDay = new Date();
    }

    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public Date getBirthDay() {
        return birthDay;
    }
}
