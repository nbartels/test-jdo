package mydomain.model;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable(identityType = IdentityType.DATASTORE, detachable = "true")
public class Product {

  String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static Product createInstance(String name) {
    Product p = new Product();
    return p;
  }
}
