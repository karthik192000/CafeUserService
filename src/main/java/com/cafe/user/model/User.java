package com.cafe.user.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;


@Entity
@Table(name = "user_details",schema = "cafe")
public class User {


    @Id
    @SequenceGenerator(name = "user_id_sequence", sequenceName = "cafe.user_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_sequence")
    private Integer userid;

    private String email;

    private String name;

    private String userrole;

    private String passwrd;

    public User(){

    }

    public User(String email, String name, String userrole,
                String passwrd){
        this.email = email;
        this.name = name;
        this.userrole = "ROLE_" + userrole;
        this.passwrd = new BCryptPasswordEncoder().encode(passwrd);
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public String getPasswrd() {
        return passwrd;
    }

    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd;
    }
}
