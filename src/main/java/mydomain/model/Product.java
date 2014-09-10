package mydomain.model;

import java.util.HashSet;
import java.util.Set;
import javax.jdo.annotations.*;

@PersistenceCapable(identityType=IdentityType.DATASTORE, detachable = "true")
public class Product
{
    String name;
    
    @Persistent(table="PRODUCTS_SUPPLIERS")
    @Join(column="PRODUCT_ID")
    @Element(column="SUPPLIER_ID")
    Set<Supplier> suppliers;

    public String getName()
    {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Supplier> getSuppliers() {
        if (null == suppliers) {
            setSuppliers(new HashSet<Supplier>());
        }
        return suppliers;
    }

    protected void setSuppliers(Set<Supplier> suppliers) {
        this.suppliers = suppliers;
    }
    
    public void addSupplier(Supplier s) {
        if (!getSuppliers().contains(s)) {
            getSuppliers().add(s);
        }
    }
    
    public static Product createInstance(String name) {
        Product p = new Product();
        p.setSuppliers(new HashSet<Supplier>());
        return p;
    }
}
