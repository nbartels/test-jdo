package mydomain.model;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

@PersistenceCapable(table="PERSON")
@Discriminator(strategy=DiscriminatorStrategy.VALUE_MAP)
@Version(strategy=VersionStrategy.VERSION_NUMBER)
public class PersonImpl implements Person {

  @Column(name = "name")
  String name;

  public PersonImpl(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

}
