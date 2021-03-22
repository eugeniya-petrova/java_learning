package ru.learning.mantis.model;

import javax.persistence.*;

@Entity //объявляет класс UserData привязанным к бд
@Table(name = "mantis_user_table")
public class UserData {

	@Id //аннотация для Hibernate, указание, что это айди
    @Column(name = "id")
	private int id = 0;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "realname")
	private String realname;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;
	
	public int getId() {
        return id;
    }

    public String getUsername() {
        return 	username;
    }
	
	public String getRealname() {
        return 	realname;
    }
	
	public String getEmail() {
        return email;
    }
	
	public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", realname='" + realname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserData userData = (UserData) o;

        if (id != userData.id) return false;
        if (username != null ? !username.equals(userData.username) : userData.username != null) return false;
        if (realname != null ? !realname.equals(userData.realname) : userData.realname != null) return false;
        return email != null ? email.equals(userData.email) : userData.email == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (realname != null ? realname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

}
