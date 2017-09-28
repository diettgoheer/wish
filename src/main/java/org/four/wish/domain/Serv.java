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
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Serv.
 */
@Entity
@Table(name = "serv")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "serv")
/*@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@EntityListeners(AuditingEntityListener.class)*/
public class Serv implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "unit")
    private String unit;

    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    @Size(max = 200)
    @Column(name = "terma", length = 200)
    private String terma;

    @Size(max = 200)
    @Column(name = "termb", length = 200)
    private String termb;

    @Size(max = 200)
    @Column(name = "termc", length = 200)
    private String termc;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "status")
    private String status;

    @CreatedDate
    @Column(name = "created_time", nullable = false, length = 50, updatable = false)
    private Instant createdTime = Instant.now();

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedDate
    @Column(name = "updated_time")
    private Instant updatedTime = Instant.now();

    @LastModifiedBy
    @Column(name = "updated_by")
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
    private Person sm;

    @ManyToOne
    private Serv father;

    @ManyToOne
    private ServiceProvider sp;

    @ManyToMany(mappedBy = "servs")
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

    public Serv name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public Serv price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public Serv unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public Serv description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTerma() {
        return terma;
    }

    public Serv terma(String terma) {
        this.terma = terma;
        return this;
    }

    public void setTerma(String terma) {
        this.terma = terma;
    }

    public String getTermb() {
        return termb;
    }

    public Serv termb(String termb) {
        this.termb = termb;
        return this;
    }

    public void setTermb(String termb) {
        this.termb = termb;
    }

    public String getTermc() {
        return termc;
    }

    public Serv termc(String termc) {
        this.termc = termc;
        return this;
    }

    public void setTermc(String termc) {
        this.termc = termc;
    }

    public String getType() {
        return type;
    }

    public Serv type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public Serv status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public Serv createdTime(Instant createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Serv createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public Serv updatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Serv updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public byte[] getAnnexa() {
        return annexa;
    }

    public Serv annexa(byte[] annexa) {
        this.annexa = annexa;
        return this;
    }

    public void setAnnexa(byte[] annexa) {
        this.annexa = annexa;
    }

    public String getAnnexaContentType() {
        return annexaContentType;
    }

    public Serv annexaContentType(String annexaContentType) {
        this.annexaContentType = annexaContentType;
        return this;
    }

    public void setAnnexaContentType(String annexaContentType) {
        this.annexaContentType = annexaContentType;
    }

    public byte[] getAnnexb() {
        return annexb;
    }

    public Serv annexb(byte[] annexb) {
        this.annexb = annexb;
        return this;
    }

    public void setAnnexb(byte[] annexb) {
        this.annexb = annexb;
    }

    public String getAnnexbContentType() {
        return annexbContentType;
    }

    public Serv annexbContentType(String annexbContentType) {
        this.annexbContentType = annexbContentType;
        return this;
    }

    public void setAnnexbContentType(String annexbContentType) {
        this.annexbContentType = annexbContentType;
    }

    public byte[] getAnnexc() {
        return annexc;
    }

    public Serv annexc(byte[] annexc) {
        this.annexc = annexc;
        return this;
    }

    public void setAnnexc(byte[] annexc) {
        this.annexc = annexc;
    }

    public String getAnnexcContentType() {
        return annexcContentType;
    }

    public Serv annexcContentType(String annexcContentType) {
        this.annexcContentType = annexcContentType;
        return this;
    }

    public void setAnnexcContentType(String annexcContentType) {
        this.annexcContentType = annexcContentType;
    }

    public byte[] getAnnexd() {
        return annexd;
    }

    public Serv annexd(byte[] annexd) {
        this.annexd = annexd;
        return this;
    }

    public void setAnnexd(byte[] annexd) {
        this.annexd = annexd;
    }

    public String getAnnexdContentType() {
        return annexdContentType;
    }

    public Serv annexdContentType(String annexdContentType) {
        this.annexdContentType = annexdContentType;
        return this;
    }

    public void setAnnexdContentType(String annexdContentType) {
        this.annexdContentType = annexdContentType;
    }

    public byte[] getAnnexe() {
        return annexe;
    }

    public Serv annexe(byte[] annexe) {
        this.annexe = annexe;
        return this;
    }

    public void setAnnexe(byte[] annexe) {
        this.annexe = annexe;
    }

    public String getAnnexeContentType() {
        return annexeContentType;
    }

    public Serv annexeContentType(String annexeContentType) {
        this.annexeContentType = annexeContentType;
        return this;
    }

    public void setAnnexeContentType(String annexeContentType) {
        this.annexeContentType = annexeContentType;
    }

    public Person getSm() {
        return sm;
    }

    public Serv sm(Person person) {
        this.sm = person;
        return this;
    }

    public void setSm(Person person) {
        this.sm = person;
    }

    public Serv getFather() {
        return father;
    }

    public Serv father(Serv serv) {
        this.father = serv;
        return this;
    }

    public void setFather(Serv serv) {
        this.father = serv;
    }

    public ServiceProvider getSp() {
        return sp;
    }

    public Serv sp(ServiceProvider serviceProvider) {
        this.sp = serviceProvider;
        return this;
    }

    public void setSp(ServiceProvider serviceProvider) {
        this.sp = serviceProvider;
    }

    public Set<Work> getOrds() {
        return ords;
    }

    public Serv ords(Set<Work> works) {
        this.ords = works;
        return this;
    }

    public Serv addOrd(Work work) {
        this.ords.add(work);
        work.getServs().add(this);
        return this;
    }

    public Serv removeOrd(Work work) {
        this.ords.remove(work);
        work.getServs().remove(this);
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
        Serv serv = (Serv) o;
        if (serv.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serv.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Serv{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            ", unit='" + getUnit() + "'" +
            ", description='" + getDescription() + "'" +
            ", terma='" + getTerma() + "'" +
            ", termb='" + getTermb() + "'" +
            ", termc='" + getTermc() + "'" +
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
