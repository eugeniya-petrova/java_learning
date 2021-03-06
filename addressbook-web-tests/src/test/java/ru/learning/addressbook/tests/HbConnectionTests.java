package ru.learning.addressbook.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.learning.addressbook.model.GroupData;
import ru.learning.addressbook.model.ContactData;

import java.util.List;

public class HbConnectionTests {

    private SessionFactory sessionFactory;

    @BeforeTest
    protected void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }


    @Test
    public void testHbConnectionC() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ContactData where deprecated = '0000-00-00' and id = '59'").list();
        session.getTransaction().commit();
        session.close();
		
		for (ContactData contact : (List<ContactData>) result) {
            System.out.println(contact);
			System.out.println(contact.getGroupSet());
        }

    }
	
	@Test
    public void testHbConnectionG() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from GroupData where group_id = '54'").list();
        session.getTransaction().commit();
        session.close();
		
		for (GroupData group : (List<GroupData>) result) {
            System.out.println(group);
			System.out.println(group.getContactSet());
        }

    }
}
