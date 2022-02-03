package de.sybit.spring.customer.details;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CustomerDetails {

    @Id
    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String phone;

    private Long customerId;

    public CustomerDetails() {}

    public CustomerDetails(String firstname, String lastname, String email, String phone, Long customerId) {
        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setEmail(email);
        this.setPhone(phone);
        this.setCustomerId(customerId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
