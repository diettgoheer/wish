package org.four.wish.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "project")
/*@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@EntityListeners(AuditingEntityListener.class)*/
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Size(max = 10)
    @Column(name = "simple_name", length = 10)
    private String simpleName;

    @Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    @NotNull
    @Size(max = 10)
    @Column(name = "sponsor", length = 10, nullable = false)
    private String sponsor;

    @Column(name = "science_field")
    private String scienceField;

    @Column(name = "budget")
    private Double budget;

    @Size(max = 20)
    @Column(name = "source", length = 20)
    private String source;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "status")
    private String status;

    @CreatedDate
    @Column(name = "created_time", nullable = false)
    @JsonIgnore
    private Instant createdTime = Instant.now();

    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    @JsonIgnore
    private String createdBy;

    @LastModifiedDate
    @Column(name = "updated_time")
    @JsonIgnore
    private Instant updatedTime = Instant.now();

    @LastModifiedBy
    @Column(name = "updated_by")
    @JsonIgnore
    private String updatedBy;

    @Size(max = 500000)
    @Lob
    @Column(name = "annexa")
    private byte[] annexa;

    @Column(name = "annexa_content_type")
    private String annexaContentType;

    @Size(max = 500000)
    @Lob
    @Column(name = "annexb")
    private byte[] annexb;

    @Column(name = "annexb_content_type")
    private String annexbContentType;

    @Size(max = 500000)
    @Lob
    @Column(name = "annexc")
    private byte[] annexc;

    @Column(name = "annexc_content_type")
    private String annexcContentType;

    @Size(max = 500000)
    @Lob
    @Column(name = "annexd")
    private byte[] annexd;

    @Column(name = "annexd_content_type")
    private String annexdContentType;

    @Size(max = 500000)
    @Lob
    @Column(name = "annexe")
    private byte[] annexe;

    @Column(name = "annexe_content_type")
    private String annexeContentType;

    @ManyToOne
    private Person pm;

    @ManyToOne
    private Project father;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "project_team",
               joinColumns = @JoinColumn(name="projects_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="teams_id", referencedColumnName="id"))
    private Set<Person> teams = new HashSet<>();

    @ManyToMany(mappedBy = "projects")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Work> ords = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Project name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public Project simpleName(String simpleName) {
        this.simpleName = simpleName;
        return this;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getCode() {
        return code;
    }

    public Project code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public Project description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSponsor() {
        return sponsor;
    }

    public Project sponsor(String sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getScienceField() {
        return scienceField;
    }

    public Project scienceField(String scienceField) {
        this.scienceField = scienceField;
        return this;
    }

    public void setScienceField(String scienceField) {
        this.scienceField = scienceField;
    }

    public Double getBudget() {
        return budget;
    }

    public Project budget(Double budget) {
        this.budget = budget;
        return this;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getSource() {
        return source;
    }

    public Project source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Project startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Project endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public Project type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public Project status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public Project createdTime(Instant createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Project createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public Project updatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Project updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public byte[] getAnnexa() {
        return annexa;
    }

    public Project annexa(byte[] annexa) {
        this.annexa = annexa;
        return this;
    }

    public void setAnnexa(byte[] annexa) {
        this.annexa = annexa;
    }

    public String getAnnexaContentType() {
        return annexaContentType;
    }

    public Project annexaContentType(String annexaContentType) {
        this.annexaContentType = annexaContentType;
        return this;
    }

    public void setAnnexaContentType(String annexaContentType) {
        this.annexaContentType = annexaContentType;
    }

    public byte[] getAnnexb() {
        return annexb;
    }

    public Project annexb(byte[] annexb) {
        this.annexb = annexb;
        return this;
    }

    public void setAnnexb(byte[] annexb) {
        this.annexb = annexb;
    }

    public String getAnnexbContentType() {
        return annexbContentType;
    }

    public Project annexbContentType(String annexbContentType) {
        this.annexbContentType = annexbContentType;
        return this;
    }

    public void setAnnexbContentType(String annexbContentType) {
        this.annexbContentType = annexbContentType;
    }

    public byte[] getAnnexc() {
        return annexc;
    }

    public Project annexc(byte[] annexc) {
        this.annexc = annexc;
        return this;
    }

    public void setAnnexc(byte[] annexc) {
        this.annexc = annexc;
    }

    public String getAnnexcContentType() {
        return annexcContentType;
    }

    public Project annexcContentType(String annexcContentType) {
        this.annexcContentType = annexcContentType;
        return this;
    }

    public void setAnnexcContentType(String annexcContentType) {
        this.annexcContentType = annexcContentType;
    }

    public byte[] getAnnexd() {
        return annexd;
    }

    public Project annexd(byte[] annexd) {
        this.annexd = annexd;
        return this;
    }

    public void setAnnexd(byte[] annexd) {
        this.annexd = annexd;
    }

    public String getAnnexdContentType() {
        return annexdContentType;
    }

    public Project annexdContentType(String annexdContentType) {
        this.annexdContentType = annexdContentType;
        return this;
    }

    public void setAnnexdContentType(String annexdContentType) {
        this.annexdContentType = annexdContentType;
    }

    public byte[] getAnnexe() {
        return annexe;
    }

    public Project annexe(byte[] annexe) {
        this.annexe = annexe;
        return this;
    }

    public void setAnnexe(byte[] annexe) {
        this.annexe = annexe;
    }

    public String getAnnexeContentType() {
        return annexeContentType;
    }

    public Project annexeContentType(String annexeContentType) {
        this.annexeContentType = annexeContentType;
        return this;
    }

    public void setAnnexeContentType(String annexeContentType) {
        this.annexeContentType = annexeContentType;
    }

    public Person getPm() {
        return pm;
    }

    public Project pm(Person person) {
        this.pm = person;
        return this;
    }

    public void setPm(Person person) {
        this.pm = person;
    }

    public Project getFather() {
        return father;
    }

    public Project father(Project project) {
        this.father = project;
        return this;
    }

    public void setFather(Project project) {
        this.father = project;
    }

    public Set<Person> getTeams() {
        return teams;
    }

    public Project teams(Set<Person> people) {
        this.teams = people;
        return this;
    }

    public Project addTeam(Person person) {
        this.teams.add(person);
        person.getProjects().add(this);
        return this;
    }

    public Project removeTeam(Person person) {
        this.teams.remove(person);
        person.getProjects().remove(this);
        return this;
    }

    public void setTeams(Set<Person> people) {
        this.teams = people;
    }

    public Set<Work> getOrds() {
        return ords;
    }

    public Project ords(Set<Work> works) {
        this.ords = works;
        return this;
    }

    public Project addOrd(Work work) {
        this.ords.add(work);
        work.getProjects().add(this);
        return this;
    }

    public Project removeOrd(Work work) {
        this.ords.remove(work);
        work.getProjects().remove(this);
        return this;
    }

    public void setOrds(Set<Work> works) {
        this.ords = works;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        if (project.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", simpleName='" + getSimpleName() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", sponsor='" + getSponsor() + "'" +
            ", scienceField='" + getScienceField() + "'" +
            ", budget='" + getBudget() + "'" +
            ", source='" + getSource() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", annexa='" + getAnnexa() + "'" +
            ", annexaContentType='" + annexaContentType + "'" +
            ", annexb='" + getAnnexb() + "'" +
            ", annexbContentType='" + annexbContentType + "'" +
            ", annexc='" + getAnnexc() + "'" +
            ", annexcContentType='" + annexcContentType + "'" +
            ", annexd='" + getAnnexd() + "'" +
            ", annexdContentType='" + annexdContentType + "'" +
            ", annexe='" + getAnnexe() + "'" +
            ", annexeContentType='" + annexeContentType + "'" +
            "}";
    }
}
