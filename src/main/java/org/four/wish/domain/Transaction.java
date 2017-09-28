package org.four.wish.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "from_user", nullable = false, updatable = false)
    private String fromUser;


    @Column(name = "to_user", nullable = false, updatable = false)
    private String toUser;

    @NotNull
    @Column(name = "amount", nullable = false, updatable = false)
    private Double amount;


    @Column(name = "jhi_time", nullable = false, updatable = false)
    private ZonedDateTime time = ZonedDateTime.now();

    @Size(max = 200)
    @Column(name = "remark", length = 200)
    private String remark;

    @ManyToOne
    private Work work;

    @ManyToOne
    private Person fromPerson;

    @ManyToOne
    private Person toPerson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Transaction name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFromUser() {
        return fromUser;
    }

    public Transaction fromUser(String fromUser) {
        this.fromUser = fromUser;
        return this;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public Transaction toUser(String toUser) {
        this.toUser = toUser;
        return this;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public Double getAmount() {
        return amount;
    }

    public Transaction amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Transaction time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public Transaction remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Work getWork() {
        return work;
    }

    public Transaction work(Work work) {
        this.work = work;
        return this;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public Person getFromPerson() {
        return fromPerson;
    }

    public Transaction fromPerson(Person person) {
        this.fromPerson = person;
        return this;
    }

    public void setFromPerson(Person person) {
        this.fromPerson = person;
    }

    public Person getToPerson() {
        return toPerson;
    }

    public Transaction toPerson(Person person) {
        this.toPerson = person;
        return this;
    }

    public void setToPerson(Person person) {
        this.toPerson = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction transaction = (Transaction) o;
        if (transaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fromUser='" + getFromUser() + "'" +
            ", toUser='" + getToUser() + "'" +
            ", amount='" + getAmount() + "'" +
            ", time='" + getTime() + "'" +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
