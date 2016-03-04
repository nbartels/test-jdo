package org.datanucleus.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import mydomain.model.Group;
import mydomain.model.GroupRoles;
import mydomain.model.Role;

import org.datanucleus.identity.DatastoreIdImpl;
import org.datanucleus.store.rdbms.query.ForwardQueryResult;
import org.datanucleus.util.NucleusLogger;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

public class SimpleTest {

  /** Instance of the current PersistenceManagerFactory. */
  private PersistenceManagerFactory pmf;
  
  /** Holds the ID of the first group. */
  private Object group1Id;

  /** Holds the ID of the second group. */
  private Object group2Id;

  /** Holds the ID of the Role. */
  private Object roleId;
  
  @Test
  public void testOptimisticTrue() {
    this.test(true);
  }
  
  @Test
  public void testOptimisticFalse() {
    this.test(false);
  }
  
  /**
   * Configure the PersistenceManagerFactory as needed for the test.
   * @param isOptimistic
   *          value to be set for property datanucleus.Optimistic.
   */
  private void test(boolean isOptimistic) {
    Map<String, String> overrides = new HashMap<String, String>();
    overrides.put("datanucleus.Optimistic", String.valueOf(isOptimistic));
    pmf = JDOHelper.getPersistenceManagerFactory(overrides, "MyTest");
    try {
      // Insert the basic data into DB.
      this.initializeDatabase();
      
      // Execute the test
      this.execute();
    } finally {
      if (pmf != null) {
        pmf.close();
      }
    }
  }
  
  /**
   * Insert the required Objects into Database.
   */
  private void initializeDatabase() {
    PersistenceManager pm = pmf.getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      // Create Role which is assigned and deassigned later
      Role role = new Role();
      role.setName("ROLE_NAME");
      pm.makePersistent(role);

      this.roleId = pm.getObjectId(role);

      // Create first Group with a new Assignment-Container.
      GroupRoles group1Roles = new GroupRoles();
      pm.makePersistent(group1Roles);

      Group group1 = new Group();
      group1.setRoles(group1Roles);
      pm.makePersistent(group1);

      this.group1Id = pm.getObjectId(group1);

      // Create second Group with a new Assignment-Container.
      GroupRoles group2Roles = new GroupRoles();
      pm.makePersistent(group2Roles);

      Group group2 = new Group();
      group2.setRoles(group2Roles);
      pm.makePersistent(group2);

      this.group2Id = pm.getObjectId(group2);

      tx.commit();
    } catch (Throwable thr) {
      NucleusLogger.GENERAL.error(">> Exception in test", thr);
      Assume.assumeNoException(thr);
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
  }
  
  /**
   * Main Test Method.
   */
  private void execute() {

    NucleusLogger.GENERAL.info(">> test START");

    PersistenceManager pm = pmf.getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {

      // First Step: assign role to Group2.
      tx.begin();
      ((Group) pm.getObjectById(group1Id)).getRoles().assign((Role) pm.getObjectById(roleId));
      tx.commit();
      
      // Second Step: assign role to Group1 and remove assignment from Group2.
      tx.begin();
      ((Group) pm.getObjectById(group1Id)).getRoles().revoke((Role) pm.getObjectById(roleId));
      ((Group) pm.getObjectById(group2Id)).getRoles().assign((Role) pm.getObjectById(roleId));
      tx.commit();

    } catch (Throwable thr) {
      NucleusLogger.GENERAL.error(">> Exception in test", thr);
      Assert.fail("Failed test : " + thr.getMessage());
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
    
    // Now check whether Database and 2nd-Level-Cache have the same data.
    assertConsistency();

    NucleusLogger.GENERAL.info(">> test END");
  }

  private void assertConsistency() {

    DatastoreIdImpl group1IdImpl = (DatastoreIdImpl) group1Id;
    this.assertConsistencyForGroup(group1IdImpl);

    DatastoreIdImpl group2IdImpl = (DatastoreIdImpl) group2Id;
    this.assertConsistencyForGroup(group2IdImpl);

  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private void assertConsistencyForGroup(DatastoreIdImpl id) {

    PersistenceManager pm = pmf.getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      {
        // Read the current group-role-assignments from Database via SQL
        Query q = pm.newQuery("SQL",
            "SELECT grr.ROLE_ID FROM GROUP_ROLES_ROLE grr, GROUP_ g WHERE grr.GROUP_ROLES_ID = g.GROUP_ROLES_ID AND g.GROUP__ID = "
                + id.getKeyAsObject().toString());
        Object result = q.execute();
        ForwardQueryResult r = (ForwardQueryResult) result;
        List<Long> fromDB = new ArrayList<Long>();
        for (Iterator<Long> iter = r.iterator(); iter.hasNext();) {
          Long roleId = iter.next();
          fromDB.add(roleId);
        }

        // Read the current group-role-assignments via PersistenceManager using 2nd-Level-Cache etc.
        Group group = (Group) pm.getObjectById(id);
        Collection<Role> fromPM = group.getRoles().listRoles();

        // Make sure both ways return the same amount of assignments
        Assert.assertEquals("Number of Assignments are different. Database has " + fromDB.size()
            + ", while PersistenceManager has " + fromPM.size(), fromDB.size(), fromPM.size());
      }
      tx.commit();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
  }

}
