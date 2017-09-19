package org.four.wish.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Pattern(regexp = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$")
    @Column(name = "email", nullable = false)
    private String email;

    @Pattern(regexp = "^0?(1)[0-9]{10}$")
    @Column(name = "telephone")
    private String telephone;

    @Size(max = 70)
    @Column(name = "description", length = 70)
    private String description;

    @Size(min = 3, max = 20)
    @Column(name = "home_page", length = 20)
    private String homePage;

    @Size(max = 500000)
    @Lob
    @Column(name = "pic")
    private byte[] pic;

    @Column(name = "pic_content_type")
    private String picContentType;

    @Size(max = 70)
    @Column(name = "saa", length = 70)
    private String saa;

    @Size(max = 70)
    @Column(name = "sab", length = 70)
    private String sab;

    @Size(max = 200)
    @Column(name = "mac", length = 200)
    private String mac;

    @Size(max = 200)
    @Column(name = "mad", length = 200)
    private String mad;

    @Size(max = 500)
    @Column(name = "lae", length = 500)
    private String lae;

    @Size(max = 500)
    @Column(name = "laf", length = 500)
    private String laf;

    @Size(max = 500)
    @Column(name = "lag", length = 500)
    private String lag;

    @Size(max = 1000)
    @Column(name = "xlah", length = 1000)
    private String xlah;

    @Size(max = 1000)
    @Column(name = "xlai", length = 1000)
    private String xlai;

    @Size(max = 1000)
    @Column(name = "xlaj", length = 1000)
    private String xlaj;

    @Column(name = "ba")
    private ZonedDateTime ba;

    @Column(name = "bb")
    private ZonedDateTime bb;

    @Column(name = "bc")
    private ZonedDateTime bc;

    @Column(name = "bd")
    private ZonedDateTime bd;

    @Column(name = "be")
    private ZonedDateTime be;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "status")
    private String status;

    @CreatedBy
    @Column(name = "jhi_user")
    private String user;

    @ManyToMany(mappedBy = "teams")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Project> projects = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Person email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public Person telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDescription() {
        return description;
    }

    public Person description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHomePage() {
        return homePage;
    }

    public Person homePage(String homePage) {
        this.homePage = homePage;
        return this;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public byte[] getPic() {
        return pic;
    }

    public Person pic(byte[] pic) {
        this.pic = pic;
        return this;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public String getPicContentType() {
        return picContentType;
    }

    public Person picContentType(String picContentType) {
        this.picContentType = picContentType;
        return this;
    }

    public void setPicContentType(String picContentType) {
        this.picContentType = picContentType;
    }

    public String getSaa() {
        return saa;
    }

    public Person saa(String saa) {
        this.saa = saa;
        return this;
    }

    public void setSaa(String saa) {
        this.saa = saa;
    }

    public String getSab() {
        return sab;
    }

    public Person sab(String sab) {
        this.sab = sab;
        return this;
    }

    public void setSab(String sab) {
        this.sab = sab;
    }

    public String getMac() {
        return mac;
    }

    public Person mac(String mac) {
        this.mac = mac;
        return this;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMad() {
        return mad;
    }

    public Person mad(String mad) {
        this.mad = mad;
        return this;
    }

    public void setMad(String mad) {
        this.mad = mad;
    }

    public String getLae() {
        return lae;
    }

    public Person lae(String lae) {
        this.lae = lae;
        return this;
    }

    public void setLae(String lae) {
        this.lae = lae;
    }

    public String getLaf() {
        return laf;
    }

    public Person laf(String laf) {
        this.laf = laf;
        return this;
    }

    public void setLaf(String laf) {
        this.laf = laf;
    }

    public String getLag() {
        return lag;
    }

    public Person lag(String lag) {
        this.lag = lag;
        return this;
    }

    public void setLag(String lag) {
        this.lag = lag;
    }

    public String getXlah() {
        return xlah;
    }

    public Person xlah(String xlah) {
        this.xlah = xlah;
        return this;
    }

    public void setXlah(String xlah) {
        this.xlah = xlah;
    }

    public String getXlai() {
        return xlai;
    }

    public Person xlai(String xlai) {
        this.xlai = xlai;
        return this;
    }

    public void setXlai(String xlai) {
        this.xlai = xlai;
    }

    public String getXlaj() {
        return xlaj;
    }

    public Person xlaj(String xlaj) {
        this.xlaj = xlaj;
        return this;
    }

    public void setXlaj(String xlaj) {
        this.xlaj = xlaj;
    }

    public ZonedDateTime getBa() {
        return ba;
    }

    public Person ba(ZonedDateTime ba) {
        this.ba = ba;
        return this;
    }

    public void setBa(ZonedDateTime ba) {
        this.ba = ba;
    }

    public ZonedDateTime getBb() {
        return bb;
    }

    public Person bb(ZonedDateTime bb) {
        this.bb = bb;
        return this;
    }

    public void setBb(ZonedDateTime bb) {
        this.bb = bb;
    }

    public ZonedDateTime getBc() {
        return bc;
    }

    public Person bc(ZonedDateTime bc) {
        this.bc = bc;
        return this;
    }

    public void setBc(ZonedDateTime bc) {
        this.bc = bc;
    }

    public ZonedDateTime getBd() {
        return bd;
    }

    public Person bd(ZonedDateTime bd) {
        this.bd = bd;
        return this;
    }

    public void setBd(ZonedDateTime bd) {
        this.bd = bd;
    }

    public ZonedDateTime getBe() {
        return be;
    }

    public Person be(ZonedDateTime be) {
        this.be = be;
        return this;
    }

    public void setBe(ZonedDateTime be) {
        this.be = be;
    }

    public String getType() {
        return type;
    }

    public Person type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public Person status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public Person user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Person projects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    public Person addProject(Project project) {
        this.projects.add(project);
        project.getTeams().add(this);
        return this;
    }

    public Person removeProject(Project project) {
        this.projects.remove(project);
        project.getTeams().remove(this);
        return this;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        if (person.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", description='" + getDescription() + "'" +
            ", homePage='" + getHomePage() + "'" +
            ", pic='" + getPic() + "'" +
            ", picContentType='" + picContentType + "'" +
            ", saa='" + getSaa() + "'" +
            ", sab='" + getSab() + "'" +
            ", mac='" + getMac() + "'" +
            ", mad='" + getMad() + "'" +
            ", lae='" + getLae() + "'" +
            ", laf='" + getLaf() + "'" +
            ", lag='" + getLag() + "'" +
            ", xlah='" + getXlah() + "'" +
            ", xlai='" + getXlai() + "'" +
            ", xlaj='" + getXlaj() + "'" +
            ", ba='" + getBa() + "'" +
            ", bb='" + getBb() + "'" +
            ", bc='" + getBc() + "'" +
            ", bd='" + getBd() + "'" +
            ", be='" + getBe() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }
}
