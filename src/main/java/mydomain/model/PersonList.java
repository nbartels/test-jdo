/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mydomain.model;

import java.util.HashSet;
import java.util.Set;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class PersonList {
    
    @PrimaryKey(column = "person_list_id")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    @Element(column="person_list_id")
    private Set<Person> pList;

    public PersonList() {
        setpList(new HashSet<Person>());
    }

    public Set<Person> getpList() {
        return pList;
    }

    public void setpList(Set<Person> pList) {
        this.pList = pList;
    }
}
