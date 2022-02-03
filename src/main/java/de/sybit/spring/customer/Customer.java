package de.sybit.spring.customer;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private int accessLevel;

    private Timestamp joinedAt;

    private Long customerDetailsId;

    public Customer() {}

    public Customer(int accessLevel, Timestamp joinedAt, Long customerDetailsId) {
        this.setAccessLevel(accessLevel);
        this.setJoinedAt(joinedAt);
        this.setCustomerDetailsId(customerDetailsId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Timestamp getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Timestamp joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Long getCustomerDetailsId() {
        return customerDetailsId;
    }

    public void setCustomerDetailsId(Long customerDetailsId) {
        this.customerDetailsId = customerDetailsId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", accessLevel=" + accessLevel +
                ", joinedAt=" + joinedAt +
                ", customerDetailsId=" + customerDetailsId +
                '}';
    }
}
