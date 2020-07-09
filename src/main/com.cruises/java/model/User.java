package model;


import org.hibernate.annotations.NaturalId;


import javax.persistence.*;


@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue
    Long id;

    @Column(name="name")
    String name;

    @NaturalId
    @Column(name="email", unique = true)
    String email;

    @Column(name="password")
    String password;

    @Column(name="role")
    String role;

    public User() {
    }

    public User(Long id, String name, String email, String password, String role ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;

    }

    public User(String name, String email, String password, String role ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;

    }

    public User(Long id, String email, String password, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
