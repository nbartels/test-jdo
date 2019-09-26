package org.datanucleus.test;

import java.util.*;
import org.junit.*;
import javax.jdo.*;

import static org.junit.Assert.*;
import mydomain.model.*;
import org.datanucleus.util.NucleusLogger;

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
            // we create a "Test" person first and commit it
            Person testPerson = new Person(1, "Tester");
            pm.makePersistent(testPerson);

            // we save  the object id here to use it later on
            Object personOid = pm.getObjectId(testPerson);
            tx.commit();
            tx.begin();

            // Now we fetch the object again
            Person testPerson2 = pm.getObjectById(Person.class, personOid);

            // check the class of the Birthday, it should be a java.util.Date
            Assert.assertEquals(Date.class, testPerson2.getBirthDay().getClass());
            tx.commit();
        }
        catch (Throwable thr)
        {
            NucleusLogger.GENERAL.error(">> Exception in test", thr);
            fail("Failed test : " + thr.getMessage());
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
