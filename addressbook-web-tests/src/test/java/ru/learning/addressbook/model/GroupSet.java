package ru.learning.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GroupSet extends ForwardingSet<GroupData> {

    private Set<GroupData> delegate;

    // конструктор из одного объекта типа GroupSet строит другой объект типа GroupSet
    public GroupSet(GroupSet groupSet) {
        this.delegate = new HashSet<GroupData>(groupSet);
    }

    public GroupSet() {
        this.delegate = new HashSet<GroupData>();
    }
	
	// конструктор по произвольной коллекции строит объект типа GroupSet 
	// строит новый HashSet (множество объектов типа GroupData) из коллекции
	public GroupSet(Collection<GroupData> groupSet) {
        this.delegate = new HashSet<GroupData>(groupSet);
    }

    @Override
    protected Set<GroupData> delegate() {
        return delegate;
    }

    public GroupSet withAdded(GroupData group) {
        GroupSet groupSet = new GroupSet(this);
        groupSet.add(group);
        return groupSet;
    }

    public GroupSet without(GroupData group) {
        GroupSet groupSet = new GroupSet(this);
        groupSet.remove(group);
        return groupSet;
    }
}
