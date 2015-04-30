package org.datanucleus.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.fail;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import javax.jdo.Query;

import org.datanucleus.store.rdbms.query.ForwardQueryResult;

import mydomain.model.Component;

import org.datanucleus.util.NucleusLogger;
import org.junit.Assert;
import org.junit.Test;

public class SimpleTest {

    @Test
    public void testOptimisticTrue() {
        test(true);
    }

    @Test
    public void testOptimisticFalse() {
        test(false);
    }

    private void test(boolean isOptimistic) {
        NucleusLogger.GENERAL.info(">> test START");

        Map<String, String> overrides = new HashMap<>();
        overrides.put("datanucleus.Optimistic", String.valueOf(isOptimistic));
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(overrides, "MyTest");

        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            Object parentOid = null;
            Object childOid = null;

            // Generate two new components
            tx.begin();
            {
                Component parent = new Component();
                parent.setSubComponents(new ArrayList<Component>());
                pm.makePersistent(parent);
                parentOid = pm.getObjectId(parent);

                // add component to list more than once ...
                Component child = new Component();
                pm.makePersistent(child);
                childOid = pm.getObjectId(child);
            }
            tx.commit();

            // save one of the created components as child of the other component
            tx.begin();
            {
                Component parent = (Component) pm.getObjectById(parentOid);
                Component child = (Component) pm.getObjectById(childOid);
                List<Component> children = new ArrayList<>();
                children.add(child);
                parent.setSubComponents(children);
            }
            tx.commit();

            // Make sure the assignment is written to the database
            tx.begin();
            {
                // check whether the n-m relation table contains the mapping
                Query q = pm.newQuery("SQL", "SELECT COUNT(*) FROM COMPONENT_COMPONENT");
                Object result = q.execute();
                Long size = (Long) ((ForwardQueryResult) result).get(0);
                Assert.assertEquals(1L, size.longValue());
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
