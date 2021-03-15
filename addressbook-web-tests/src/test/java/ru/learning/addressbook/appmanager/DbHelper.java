package ru.learning.addressbook.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.learning.addressbook.model.GroupData;
import ru.learning.addressbook.model.GroupSet;
import ru.learning.addressbook.model.ContactData;
import ru.learning.addressbook.model.ContactSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DbHelper {
	
	private final SessionFactory sessionFactory;
	
	public DbHelper() {
		// A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
	}
	
	public GroupSet groupSet() {
		Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<GroupData> result = session.createQuery("from GroupData").list();
        session.getTransaction().commit();
        session.close();
		return new GroupSet(result);
	}

	public GroupData groupById(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<GroupData> result = session.createQuery(String.format("from GroupData where group_id = '%s'", id)).list();
		session.getTransaction().commit();
		session.close();
		GroupData group = result.iterator().next(); //в результате всегда будет список из одной группы, поэтому выбираем любую
		return group;
	}
	
	public ContactSet contactSet() {
		Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactData> result = session.createQuery("from ContactData where deprecated = '0000-00-00'").list();
        session.getTransaction().commit();
        session.close();
		return new ContactSet(result);
	}

	public ContactData contactById(int id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<ContactData> result = session
				.createQuery(String.format("from ContactData where deprecated = '0000-00-00' and id = '%s'", id)).list();
		session.getTransaction().commit();
		session.close();
		ContactData contact = result.iterator().next(); //в результате всегда будет список из одного контакта, поэтому выбираем любой
		return contact;
	}
}
