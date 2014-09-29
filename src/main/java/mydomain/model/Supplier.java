/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose
 * Tools | Templates and open the template in the editor.
 */
package mydomain.model;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * 
 * @author bartels
 */
@PersistenceCapable(identityType = IdentityType.DATASTORE, detachable = "true")
public class Supplier {

  String name;

  @Persistent(table = "PRODUCTS_SUPPLIERS")
  @Join(column = "PRODUCT_ID")
  @Element(column = "SUPPLIER_ID")
  @Order(column = "SEQ")
  List<Product> products;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addProduct(Product p) {
    if (null == getProducts()) {
      setProducts(new ArrayList<Product>());
    }
    getProducts().add(p);
  }

  public List<Product> getProducts() {
    return products;
  }

  protected void setProducts(List<Product> products) {
    this.products = products;
  }

}
