package com.cafe.user.model;


import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "customer_details",schema = "cafe")
public class CustomerDetails {

    @Id
    @SequenceGenerator(name = "customer_id_sequence", sequenceName = "cafe.customer_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_sequence")
    private  Integer customerid;

    private Integer userid;


    @Column(columnDefinition = "customer_since")
    private Date customersince;


    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getCustomersince() {
        return customersince;
    }

    public void setCustomersince(Date customersince) {
        this.customersince = customersince;
    }
}