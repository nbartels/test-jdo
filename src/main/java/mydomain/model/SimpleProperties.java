/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mydomain.model;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

@PersistenceCapable(detachable = "true", table = "properties")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Discriminator(strategy = DiscriminatorStrategy.VALUE_MAP, value="SimpleProperties", column = "jdo_class")
@Version(strategy = VersionStrategy.VERSION_NUMBER, column = "jdo_version")
public class SimpleProperties {
    
    public Long getDifferent_id() {
        return different_id;
    }

    public void setDifferent_id(Long different_id) {
        this.different_id = different_id;
    }
    
    Long different_id;
    
}
