package org.four.wish.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Work.
 */
@Entity
@Table(name = "work")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "work")
public class Work implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Size(max = 70)
    @Column(name = "description", length = 70)
    private String description;

    @Column(name = "budget")
    private Double budget;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "start_date", updatable = false)
    private LocalDate startDate = LocalDate.now();

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "status")
    private String status = "待处理";

    @Column(name = "created_time", updatable = false)
    private Instant createdTime = Instant.now();

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_time")
    private Instant updatedTime = Instant.now();

    @CreatedDate
    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToOne
    private Person wm;

    @ManyToOne
    private Person ws;

    @ManyToOne
    private Person wf;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "work_project",
               joinColumns = @JoinColumn(name="works_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="projects_id", referencedColumnName="id"))
    private Set<Project> projects = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "work_serv",
               joinColumns = @JoinColumn(name="works_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="servs_id", referencedColumnName="id"))
    private Set<Serv> servs = new HashSet<>();

    @ManyToOne
    private Serv buyServ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Work name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Work description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getBudget() {
        return budget;
    }

    public Work budget(Double budget) {
        this.budget = budget;
        return this;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Work totalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Work startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Work endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public Work type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public Work status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public Work createdTime(Instant createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Work createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public Work updatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Work updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Person getWm() {
        return wm;
    }

    public Work wm(Person person) {
        this.wm = person;
        return this;
    }

    public void setWm(Person person) {
        this.wm = person;
    }

    public Person getWs() {
        return ws;
    }

    public Work ws(Person person) {
        this.ws = person;
        return this;
    }

    public void setWs(Person person) {
        this.ws = person;
    }

    public Person getWf() {
        return wf;
    }

    public Work wf(Person person) {
        this.wf = person;
        return this;
    }

    public void setWf(Person person) {
        this.wf = person;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Work projects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    public Work addProject(Project project) {
        this.projects.add(project);
        project.getOrds().add(this);
        return this;
    }

    public Work removeProject(Project project) {
        this.projects.remove(project);
        project.getOrds().remove(this);
        return this;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Serv> getServs() {
        return servs;
    }

    public Work servs(Set<Serv> servs) {
        this.servs = servs;
        return this;
    }

    public Work addServ(Serv serv) {
        this.servs.add(serv);
        serv.getOrds().add(this);
        return this;
    }

    public Work removeServ(Serv serv) {
        this.servs.remove(serv);
        serv.getOrds().remove(this);
        return this;
    }

    public void setServs(Set<Serv> servs) {
        this.servs = servs;
    }

    public Serv getBuyServ() {
        return buyServ;
    }

    public Work buyServ(Serv serv) {
        this.buyServ = serv;
        return this;
    }

    public void setBuyServ(Serv serv) {
        this.buyServ = serv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Work work = (Work) o;
        if (work.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), work.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Work{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", budget='" + getBudget() + "'" +
            ", totalPrice='" + getTotalPrice() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
