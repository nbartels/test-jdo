package mydomain.model;

import java.util.ArrayList;
import java.util.List;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable(identityType = IdentityType.DATASTORE, detachable = "true")
public class Component {
  
  @Persistent(table = "COMPONENT_COMPONENT")
  @Join(column = "COMPONENT_ID")
  @Element(column = "ELEMENT_ID")
  @Order(column = "SEQ")
  List<Component> subComponents;

  public Component() {
      this.subComponents = new ArrayList<>();
  }
  
  public void setSubComponents(List<Component> components) {
      this.subComponents = components;
  }

    public List<Component> getSubComponents() {
        return this.subComponents;
    }

}
