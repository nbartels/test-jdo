package mydomain.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

@PersistenceCapable(table="GROUP_")
@Version(strategy=VersionStrategy.VERSION_NUMBER, column="JDO_VERSION")
public class Group {

  @Persistent(column="GROUP_ROLES_ID")
  private GroupRoles roles;
  
  public void setRoles(GroupRoles roles) {
    this.roles = roles;
    if (roles != null) {
      roles.setGroup(this);
    }
  }
  
  public GroupRoles getRoles() {
    return this.roles;
  }
  
}
