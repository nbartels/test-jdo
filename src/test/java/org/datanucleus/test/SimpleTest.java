package org.datanucleus.test;

import static org.junit.Assert.fail;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import mydomain.model.Group;
import mydomain.model.Person;
import mydomain.model.PersonImpl;

import org.datanucleus.util.NucleusLogger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SimpleTest {

  private static PersistenceManagerFactory PMF;
  
  private Object groupId;
  
  @BeforeClass
  public static void beforeClass() {
    PMF = JDOHelper.getPersistenceManagerFactory("MyTest");
  }
  
  @AfterClass
  public static void afterClass() {
    PMF.close();
  }
  
  /**
   * Create the Objects required for Test.
   */
  @Before
  public void setUp() {
    PersistenceManager pm = PMF.getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      
      Group group = new Group();
      group.setPerson(new PersonImpl("Person A"));
      pm.makePersistent(group);
      groupId = pm.getObjectId(group);
      
      tx.commit();
    } catch (Throwable thr) {
      NucleusLogger.GENERAL.error(">> Exception in test", thr);
      fail("Failed test : " + thr.getMessage());
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
  }
  
  /**
   * Call updateGroup.
   * 
   * Delete successful on Transaction commit.
   */
  @Test
  public void noGarbageCollection_commitSuccessful() {
    try {
      this.updateGroup();
    } catch (Exception e) {
      Assert.fail();
    }
  }
  
  /**
   * Do a Garbage Collection 
   * Call updateGroup.
   * 
   * Deleting fails on Transaction commit.
   */
  @Test
  public void withGarbageCollection_commitFails() {
    // Do a Garbage Collection and therefore clean the WEAK-Lvl2-Cache
    System.gc();
    this.updateGroup();
  }
  
  /**
   * Read the Group again.
   * Replace the person assigned to the group with a new one.
   * Delete the old person.
   */
  private void updateGroup() {
    NucleusLogger.GENERAL.info(">> test START");
    
    PersistenceManager pm = PMF.getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      
      Group group = (Group) pm.getObjectById(groupId);
      Person oldPerson = group.getPerson();
      Person newPerson = new PersonImpl("Person B");
      group.setPerson(newPerson);
      pm.deletePersistent(oldPerson);
      
      tx.commit();
    } catch (Throwable thr) {
      NucleusLogger.GENERAL.error(">> Exception in test", thr);
      fail("Failed test : " + thr.getMessage());
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
    
    NucleusLogger.GENERAL.info(">> test END");
  }
}
