package org.datanucleus.test;

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
            // generate User object with embedded Email object and store it in the DB
            tx.begin();
            Email myEmail = new Email("tester", "example.com");
            Person myPerson = new Person(1l, "Test User", myEmail);
            pm.makePersistent(myPerson);
            tx.commit();
            
            
            // retrieve user obect from db and check the stored email
            tx.begin();
            Person myPerson2 = pm.getObjectById(Person.class, 1L);
            Assert.assertEquals(myPerson2.getName(), "Test User");
            Assert.assertEquals(myPerson2.getEmail().getHostname(), "example.com");    
            tx.commit();
            
            // search user object with given name via Query
            tx.begin();            
            Query q = pm.newQuery(Person.class);
            q.setFilter("name == theName");
            q.declareParameters("java.lang.String theName");
            List results = (List) q.execute("Test User");
            Assert.assertEquals(results.size(), 1);            
            tx.commit();
            
            // search user object with given Email via Query
            tx.begin();            
            Email testEmailObj = new Email("tester","example.com");            
            Query q2 = pm.newQuery(Person.class);
            q2.setFilter("email == theEmail");
            q.declareParameters("mydomain.model.Email theEmail");
            List results2 = (List) q2.execute(testEmailObj);
            Assert.assertEquals(results2.size(), 1);            
            tx.commit();
        }
        catch (Throwable thr)
        {
            NucleusLogger.GENERAL.error(">> Exception thrown persisting data", thr);
            thr.printStackTrace();
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
