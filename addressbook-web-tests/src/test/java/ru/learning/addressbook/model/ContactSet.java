package ru.learning.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.HashSet;
import java.util.Set;

public class ContactSet extends ForwardingSet<ContactData> {

    private Set<ContactData> delegate;

    public ContactSet(ContactSet contactSet) {
        this.delegate = new HashSet<ContactData>(contactSet);
    }

    public ContactSet() {
        this.delegate = new HashSet<ContactData>();
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
