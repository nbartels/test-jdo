package org.datanucleus.test;

import java.util.HashMap;
import java.util.Map;
import javax.jdo.*;

import mydomain.model.*;
import org.datanucleus.store.rdbms.query.ForwardQueryResult;
import org.datanucleus.util.NucleusLogger;
import org.junit.*;
import static org.junit.Assert.*;

public class SimpleTest {

    @Test
    public void testSimpleOptimisticFalse() {
        doTest(false);
    }

    @Test
    public void testSimpleOptimisticTrue() {
        doTest(true);
    }

    public void doTest(boolean isOptimistic) {
        NucleusLogger.GENERAL.info(">> test START");

        Map<String, String> overrides = new HashMap<String, String>();
        overrides.put("datanucleus.Optimistic", String.valueOf(isOptimistic));
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(overrides, "MyTest");
        assertEquals(isOptimistic, pmf.getOptimistic());

        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            // generate new product and new supplier
            tx.begin();
            Supplier s = new Supplier();
            s.setName("s_one");
            pm.makePersistent(s);
            Product p = new Product();
            p.setName("p_one");
            pm.makePersistent(p);
            Object sOid = pm.getObjectId(s);
            Object pOid = pm.getObjectId(p);
            tx.commit();

            // add mapping between poduct and supplier in new transaction
            tx.begin();
            Product pRead = (Product) pm.getObjectById(pOid);
            Supplier sRead = (Supplier) pm.getObjectById(sOid);
            sRead.assocProduct(pRead);
            tx.commit();

            // check whether the n-m relation table contains the mapping
            tx.begin();
            Query q = pm.newQuery("SQL", "SELECT COUNT(*) FROM PRODUCTS_SUPPLIERS");
            Object result = q.execute();
            Long size = (Long) ((ForwardQueryResult) result).get(0);
            assertEquals(1L, size.longValue());
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
