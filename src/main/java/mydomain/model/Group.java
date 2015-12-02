package mydomain.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PersistenceModifier;
import javax.jdo.annotations.Persistent;

@PersistenceCapable(table = "GROUP")
public class Group {

  @Persistent(persistenceModifier = PersistenceModifier.PERSISTENT, column = "PERSON_ID")
  private Person person;

  public void setPerson(Person aPerson) {
    this.person = aPerson;
  }

  public Person getPerson() {
    return this.person;
  }

}
