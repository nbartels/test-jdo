package mydomain.model;

import javax.jdo.annotations.*;

@PersistenceCapable(detachable = "true")
public class Person {
    @PrimaryKey
    Long id;

    String name;

    byte[] someByteData;

    public Person(long id, String name) {
        this.id = id;
        this.name = name;
        this.someByteData = name.getBytes();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getSomeByteData() {
        return someByteData;
    }
}
