package ru.learning.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ContactSet extends ForwardingSet<ContactData> {

    private Set<ContactData> delegate;

    // конструктор из одного объекта типа ContactSet строит другой объект типа ContactSet
    public ContactSet(ContactSet contactSet) {
        this.delegate = new HashSet<ContactData>(contactSet);
    }

    public ContactSet() {
        this.delegate = new HashSet<ContactData>();
    }
	
	// конструктор по произвольной коллекции строит объект типа ContactSet 
	// строит новый HashSet (множество объектов типа ContactData) из коллекции
	public ContactSet(Collection<ContactData> contactSet) {
        this.delegate = new HashSet<ContactData>(contactSet);
    }

    @Override
    protected Set<ContactData> delegate() {
        return delegate;
    }

    public ContactSet withAdded(ContactData contact) {
        ContactSet contactSet = new ContactSet(this);
        contactSet.add(contact);
        return contactSet;
    }

    public ContactSet without(ContactData contact) {
        ContactSet contactSet = new ContactSet(this);
        contactSet.remove(contact);
        return contactSet;
    }
}
