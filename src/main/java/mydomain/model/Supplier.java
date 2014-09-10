/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mydomain.model;

import java.util.HashSet;
import java.util.Set;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 *
 * @author bartels
 */
@PersistenceCapable(identityType=IdentityType.DATASTORE, detachable = "true")
public class Supplier {
    
    String name;
    
    @Persistent(mappedBy="suppliers")
    Set<Product> products;

    public String getName()
    {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void assocProduct(Product p) {
        if (null == getProducts()) {
            setProducts(new HashSet<Product>());
        }
        
        p.addSupplier(this);
        getProducts().add(p);
    }

    public Set<Product> getProducts() {
        return products;
    }

    protected void setProducts(Set<Product> products) {
        this.products = products;
    }

    public static Supplier createInstance(String name) {
        Supplier s = new Supplier();
        s.setName(name);
        return s;
    }
    
}
