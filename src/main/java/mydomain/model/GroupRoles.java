package mydomain.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PersistenceModifier;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

@PersistenceCapable(table="GROUP_ROLES")
@Version(strategy=VersionStrategy.VERSION_NUMBER, column="JDO_VERSION")
public class GroupRoles {

  @Persistent(persistenceModifier=PersistenceModifier.PERSISTENT, defaultFetchGroup="true", table="GROUP_ROLES_ROLE")
  @Join(column="GROUP_ROLES_ID")
  @Element(column="ROLE_ID")
  private Set<Role> roleSet;
 
  @Persistent(column="group_id")
  private Group group;

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group aGroup) {
    group = aGroup;
  }
  
  public boolean assign(Role aRole) {
    return getRoleSet().add(aRole);
  }

  public boolean revoke(Role aRole) {
    return getRoleSet().remove(aRole);
  }
  
  public Collection<Role> listRoles() {
    return Collections.unmodifiableCollection(getRoleSet());
  }

  private Set<Role> getRoleSet() {
    if (null == roleSet) {
      setRoleSet(new HashSet<Role>());
    }
    return roleSet;
  }

  private void setRoleSet(Set<Role> argSet) {
    roleSet = argSet;
  }
  
  
}
