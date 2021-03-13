package ru.learning.addressbook.model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //объявляет класс GroupData привязанным к бд
@Table(name = "group_list")
public class GroupData {
	@Id //аннотация для Hibernate, указание, что это айди
    @Column(name = "group_id")
	private int id = 0;
	
    @Expose
	@Column(name = "group_name")
    private String name;

    @Expose
	@Column(name = "group_header")
	@Type(type = "text") //аннотация для Hibernate, т. к. поле многострочное
    private String header;
	
    @Expose
	@Column(name = "group_footer")
	@Type(type = "text") //аннотация для Hibernate, т. к. поле многострочное
    private String footer;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHeader() {
        return header;
    }

    public String getFooter() {
        return footer;
    }

    // каждый из сеттеров ниже создаёт объект типа GroupData
    public GroupData withId(int id) {
        this.id = id;
        return this;
    }

    public GroupData withName(String name) {
        this.name = name;
        return this;
    }

    public GroupData withHeader(String header) {
        this.header = header;
        return this;
    }

    public GroupData withFooter(String footer) {
        this.footer = footer;
        return this;
    }

    @Override
    public String toString() {
        return "GroupData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupData groupData = (GroupData) o;

        if (id != groupData.id) return false;
        return name != null ? name.equals(groupData.name) : groupData.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

}