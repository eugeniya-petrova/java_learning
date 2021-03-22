package ru.learning.mantis.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserSet extends ForwardingSet<UserData>{
	
	private Set<UserData> delegate;

    // конструктор из одного объекта типа UserSet строит другой объект типа UserSet
    public UserSet(UserSet userSet) {
        this.delegate = new HashSet<UserData>(userSet);
    }

    public UserSet() {
        this.delegate = new HashSet<UserData>();
    }
	
	// конструктор по произвольной коллекции строит объект типа UserSet 
	// строит новый HashSet (множество объектов типа UserData) из коллекции
	public UserSet(Collection<UserData> userSet) {
        this.delegate = new HashSet<UserData>(userSet);
    }

    @Override
    protected Set<UserData> delegate() {
        return delegate;
    }
}
