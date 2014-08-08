package org.datanucleus.test;

import java.util.ArrayList;
import java.util.List;
import javax.jdo.*;

import mydomain.model.*;
import org.datanucleus.util.NucleusLogger;
import org.junit.*;
import static org.junit.Assert.*;

public class SimpleTest
{
    @Test
    public void testSimple()
    {
        NucleusLogger.GENERAL.info(">> test START");
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("MyTest");

        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try
        {
            tx.begin();
            AddressInfo info1 = new AddressInfo("str1", 1L);
            pm.makePersistent(info1);
            AddressInfo info2 = new AddressInfo("str2", 2L);
            pm.makePersistent(info2);
            List<AddressInfo> adrList = new ArrayList<AddressInfo>();
            adrList.add(info1);
            adrList.add(info2);
            Person p = new Person(1);
            p.setAddressInfoList(adrList);
            pm.makePersistent(p);
            tx.commit();
        }
        catch (Throwable thr)
        {
            NucleusLogger.GENERAL.error(">> Exception thrown persisting data", thr);
            fail("Failed to persist data : " + thr.getMessage());
        }
        finally 
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }

        pmf.close();
        NucleusLogger.GENERAL.info(">> test END");
    }
}
