package org.datanucleus.test;

import static org.junit.Assert.fail;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import mydomain.model.Product;
import mydomain.model.Supplier;

import org.datanucleus.util.NucleusLogger;
import org.junit.Test;

public class SimpleTest {


  @Test
  public void simpleTest() {
    NucleusLogger.GENERAL.info(">> test START");
    PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("MyTest");
    
    PersistenceManager pm = pmf.getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      // generate two new products and a new supplier
      Object sOid = null;
      
      tx.begin();
      {
        Supplier s = new Supplier();
        s.setName("supplier");
        pm.makePersistent(s);
        sOid = pm.getObjectId(s);
        
        Product p = new Product();
        p.setName("product");
        pm.makePersistent(p);

        // add product to list more than once ...
        s.addProduct(p);
        s.addProduct(p);
      }
      tx.commit();

      // Read the supplier from db and start removing the products
      tx.begin();
      {
        Supplier supplier = (Supplier) pm.getObjectById(sOid);
        for (Product p : supplier.getProducts()) {
          supplier.getProducts().remove(p);
        }
      }
      tx.commit();
      
    } catch (Throwable thr) {
      NucleusLogger.GENERAL.error(">> Exception thrown persisting data", thr);
      fail("Failed to persist data : " + thr.getMessage());
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
    pmf.close();
    NucleusLogger.GENERAL.info(">> test END");
  }
}
