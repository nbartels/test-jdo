package mydomain.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

@PersistenceCapable(table="ROLE_")
@Version(strategy=VersionStrategy.VERSION_NUMBER, column="JDO_VERSION")
public class Role {

  @Persistent(column="ROLE_NAME")
  private String name;
  
  /**
   * Gets the name of the role.
   * @return the name of the role.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the name of the role.
   * @param aName the name of the Role.
   */
  public void setName(String aName) {
    this.name = aName;
  }
  
}
