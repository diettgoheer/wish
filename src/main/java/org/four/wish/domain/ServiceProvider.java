package org.four.wish.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ServiceProvider.
 */
@Entity
@Table(name = "service_provider")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "serviceprovider")
public class ServiceProvider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "enterprise_created_time")
    private LocalDate enterpriseCreatedTime;

    @NotNull
    @Size(max = 200)
    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "address")
    private String address;

    @Column(name = "zip_code")
    private String zipCode;

    @NotNull
    @Column(name = "organization_code", nullable = false)
    private String organizationCode;

    @NotNull
    @Column(name = "business_license_number", nullable = false)
    private String businessLicenseNumber;

    @NotNull
    @Column(name = "legal_representative", nullable = false)
    private String legalRepresentative;

    @NotNull
    @Column(name = "general_manager", nullable = false)
    private String generalManager;

    @Column(name = "technical_manager")
    private String technicalManager;

    @Column(name = "quality_manager")
    private String qualityManager;

    @Column(name = "quality_manager_contact")
    private String qualityManagerContact;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "business_bank")
    private String businessBank;

    @Column(name = "business_city")
    private String businessCity;

    @Column(name = "business_account")
    private String businessAccount;

    @Lob
    @Column(name = "business_license")
    private byte[] businessLicense;

    @Column(name = "business_license_content_type")
    private String businessLicenseContentType;

    @Column(name = "business_scope")
    private String businessScope;

    @Column(name = "business_scope_pre_license")
    private String businessScopePreLicense;

    @Column(name = "business_years")
    private Integer businessYears;

    @Column(name = "jhi_character")
    private String character;

    @Column(name = "scale")
    private String scale;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "subsidiary")
    private Boolean subsidiary;

    @Column(name = "have_subsidiary")
    private Boolean haveSubsidiary;

    @Column(name = "quality_certificaion_system")
    private String qualityCertificaionSystem;

    @Column(name = "certification_org")
    private String certificationOrg;

    @Column(name = "certification_time")
    private LocalDate certificationTime;

    @Column(name = "employees_number")
    private Integer employeesNumber;

    @Column(name = "employees_bachelor_degree_or_above")
    private Integer employeesBachelorDegreeOrAbove;

    @Column(name = "employees_technical")
    private Integer employeesTechnical;

    @Column(name = "employees_certification")
    private String employeesCertification;

    @Column(name = "registered_capital")
    private Integer registeredCapital;

    @Column(name = "develop_team")
    private Boolean developTeam;

    @Column(name = "test_team")
    private Boolean testTeam;

    @Column(name = "complaints")
    private Boolean complaints;

    @Column(name = "training")
    private Boolean training;

    @Column(name = "training_documents")
    private Boolean trainingDocuments;

    @Column(name = "status")
    private String status;

    @Column(name = "jhi_type")
    private String type;

    @OneToMany(mappedBy = "sp")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Serv> servs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ServiceProvider name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEnterpriseCreatedTime() {
        return enterpriseCreatedTime;
    }

    public ServiceProvider enterpriseCreatedTime(LocalDate enterpriseCreatedTime) {
        this.enterpriseCreatedTime = enterpriseCreatedTime;
        return this;
    }

    public void setEnterpriseCreatedTime(LocalDate enterpriseCreatedTime) {
        this.enterpriseCreatedTime = enterpriseCreatedTime;
    }

    public String getDescription() {
        return description;
    }

    public ServiceProvider description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public ServiceProvider contactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
        return this;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public ServiceProvider contactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public ServiceProvider contactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getAddress() {
        return address;
    }

    public ServiceProvider address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public ServiceProvider zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public ServiceProvider organizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
        return this;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getBusinessLicenseNumber() {
        return businessLicenseNumber;
    }

    public ServiceProvider businessLicenseNumber(String businessLicenseNumber) {
        this.businessLicenseNumber = businessLicenseNumber;
        return this;
    }

    public void setBusinessLicenseNumber(String businessLicenseNumber) {
        this.businessLicenseNumber = businessLicenseNumber;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public ServiceProvider legalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
        return this;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public String getGeneralManager() {
        return generalManager;
    }

    public ServiceProvider generalManager(String generalManager) {
        this.generalManager = generalManager;
        return this;
    }

    public void setGeneralManager(String generalManager) {
        this.generalManager = generalManager;
    }

    public String getTechnicalManager() {
        return technicalManager;
    }

    public ServiceProvider technicalManager(String technicalManager) {
        this.technicalManager = technicalManager;
        return this;
    }

    public void setTechnicalManager(String technicalManager) {
        this.technicalManager = technicalManager;
    }

    public String getQualityManager() {
        return qualityManager;
    }

    public ServiceProvider qualityManager(String qualityManager) {
        this.qualityManager = qualityManager;
        return this;
    }

    public void setQualityManager(String qualityManager) {
        this.qualityManager = qualityManager;
    }

    public String getQualityManagerContact() {
        return qualityManagerContact;
    }

    public ServiceProvider qualityManagerContact(String qualityManagerContact) {
        this.qualityManagerContact = qualityManagerContact;
        return this;
    }

    public void setQualityManagerContact(String qualityManagerContact) {
        this.qualityManagerContact = qualityManagerContact;
    }

    public String getBusinessName() {
        return businessName;
    }

    public ServiceProvider businessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessBank() {
        return businessBank;
    }

    public ServiceProvider businessBank(String businessBank) {
        this.businessBank = businessBank;
        return this;
    }

    public void setBusinessBank(String businessBank) {
        this.businessBank = businessBank;
    }

    public String getBusinessCity() {
        return businessCity;
    }

    public ServiceProvider businessCity(String businessCity) {
        this.businessCity = businessCity;
        return this;
    }

    public void setBusinessCity(String businessCity) {
        this.businessCity = businessCity;
    }

    public String getBusinessAccount() {
        return businessAccount;
    }

    public ServiceProvider businessAccount(String businessAccount) {
        this.businessAccount = businessAccount;
        return this;
    }

    public void setBusinessAccount(String businessAccount) {
        this.businessAccount = businessAccount;
    }

    public byte[] getBusinessLicense() {
        return businessLicense;
    }

    public ServiceProvider businessLicense(byte[] businessLicense) {
        this.businessLicense = businessLicense;
        return this;
    }

    public void setBusinessLicense(byte[] businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getBusinessLicenseContentType() {
        return businessLicenseContentType;
    }

    public ServiceProvider businessLicenseContentType(String businessLicenseContentType) {
        this.businessLicenseContentType = businessLicenseContentType;
        return this;
    }

    public void setBusinessLicenseContentType(String businessLicenseContentType) {
        this.businessLicenseContentType = businessLicenseContentType;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public ServiceProvider businessScope(String businessScope) {
        this.businessScope = businessScope;
        return this;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getBusinessScopePreLicense() {
        return businessScopePreLicense;
    }

    public ServiceProvider businessScopePreLicense(String businessScopePreLicense) {
        this.businessScopePreLicense = businessScopePreLicense;
        return this;
    }

    public void setBusinessScopePreLicense(String businessScopePreLicense) {
        this.businessScopePreLicense = businessScopePreLicense;
    }

    public Integer getBusinessYears() {
        return businessYears;
    }

    public ServiceProvider businessYears(Integer businessYears) {
        this.businessYears = businessYears;
        return this;
    }

    public void setBusinessYears(Integer businessYears) {
        this.businessYears = businessYears;
    }

    public String getCharacter() {
        return character;
    }

    public ServiceProvider character(String character) {
        this.character = character;
        return this;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getScale() {
        return scale;
    }

    public ServiceProvider scale(String scale) {
        this.scale = scale;
        return this;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public ServiceProvider serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Boolean isSubsidiary() {
        return subsidiary;
    }

    public ServiceProvider subsidiary(Boolean subsidiary) {
        this.subsidiary = subsidiary;
        return this;
    }

    public void setSubsidiary(Boolean subsidiary) {
        this.subsidiary = subsidiary;
    }

    public Boolean isHaveSubsidiary() {
        return haveSubsidiary;
    }

    public ServiceProvider haveSubsidiary(Boolean haveSubsidiary) {
        this.haveSubsidiary = haveSubsidiary;
        return this;
    }

    public void setHaveSubsidiary(Boolean haveSubsidiary) {
        this.haveSubsidiary = haveSubsidiary;
    }

    public String getQualityCertificaionSystem() {
        return qualityCertificaionSystem;
    }

    public ServiceProvider qualityCertificaionSystem(String qualityCertificaionSystem) {
        this.qualityCertificaionSystem = qualityCertificaionSystem;
        return this;
    }

    public void setQualityCertificaionSystem(String qualityCertificaionSystem) {
        this.qualityCertificaionSystem = qualityCertificaionSystem;
    }

    public String getCertificationOrg() {
        return certificationOrg;
    }

    public ServiceProvider certificationOrg(String certificationOrg) {
        this.certificationOrg = certificationOrg;
        return this;
    }

    public void setCertificationOrg(String certificationOrg) {
        this.certificationOrg = certificationOrg;
    }

    public LocalDate getCertificationTime() {
        return certificationTime;
    }

    public ServiceProvider certificationTime(LocalDate certificationTime) {
        this.certificationTime = certificationTime;
        return this;
    }

    public void setCertificationTime(LocalDate certificationTime) {
        this.certificationTime = certificationTime;
    }

    public Integer getEmployeesNumber() {
        return employeesNumber;
    }

    public ServiceProvider employeesNumber(Integer employeesNumber) {
        this.employeesNumber = employeesNumber;
        return this;
    }

    public void setEmployeesNumber(Integer employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public Integer getEmployeesBachelorDegreeOrAbove() {
        return employeesBachelorDegreeOrAbove;
    }

    public ServiceProvider employeesBachelorDegreeOrAbove(Integer employeesBachelorDegreeOrAbove) {
        this.employeesBachelorDegreeOrAbove = employeesBachelorDegreeOrAbove;
        return this;
    }

    public void setEmployeesBachelorDegreeOrAbove(Integer employeesBachelorDegreeOrAbove) {
        this.employeesBachelorDegreeOrAbove = employeesBachelorDegreeOrAbove;
    }

    public Integer getEmployeesTechnical() {
        return employeesTechnical;
    }

    public ServiceProvider employeesTechnical(Integer employeesTechnical) {
        this.employeesTechnical = employeesTechnical;
        return this;
    }

    public void setEmployeesTechnical(Integer employeesTechnical) {
        this.employeesTechnical = employeesTechnical;
    }

    public String getEmployeesCertification() {
        return employeesCertification;
    }

    public ServiceProvider employeesCertification(String employeesCertification) {
        this.employeesCertification = employeesCertification;
        return this;
    }

    public void setEmployeesCertification(String employeesCertification) {
        this.employeesCertification = employeesCertification;
    }

    public Integer getRegisteredCapital() {
        return registeredCapital;
    }

    public ServiceProvider registeredCapital(Integer registeredCapital) {
        this.registeredCapital = registeredCapital;
        return this;
    }

    public void setRegisteredCapital(Integer registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public Boolean isDevelopTeam() {
        return developTeam;
    }

    public ServiceProvider developTeam(Boolean developTeam) {
        this.developTeam = developTeam;
        return this;
    }

    public void setDevelopTeam(Boolean developTeam) {
        this.developTeam = developTeam;
    }

    public Boolean isTestTeam() {
        return testTeam;
    }

    public ServiceProvider testTeam(Boolean testTeam) {
        this.testTeam = testTeam;
        return this;
    }

    public void setTestTeam(Boolean testTeam) {
        this.testTeam = testTeam;
    }

    public Boolean isComplaints() {
        return complaints;
    }

    public ServiceProvider complaints(Boolean complaints) {
        this.complaints = complaints;
        return this;
    }

    public void setComplaints(Boolean complaints) {
        this.complaints = complaints;
    }

    public Boolean isTraining() {
        return training;
    }

    public ServiceProvider training(Boolean training) {
        this.training = training;
        return this;
    }

    public void setTraining(Boolean training) {
        this.training = training;
    }

    public Boolean isTrainingDocuments() {
        return trainingDocuments;
    }

    public ServiceProvider trainingDocuments(Boolean trainingDocuments) {
        this.trainingDocuments = trainingDocuments;
        return this;
    }

    public void setTrainingDocuments(Boolean trainingDocuments) {
        this.trainingDocuments = trainingDocuments;
    }

    public String getStatus() {
        return status;
    }

    public ServiceProvider status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public ServiceProvider type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Serv> getServs() {
        return servs;
    }

    public ServiceProvider servs(Set<Serv> servs) {
        this.servs = servs;
        return this;
    }

    public ServiceProvider addServ(Serv serv) {
        this.servs.add(serv);
        serv.setSp(this);
        return this;
    }

    public ServiceProvider removeServ(Serv serv) {
        this.servs.remove(serv);
        serv.setSp(null);
        return this;
    }

    public void setServs(Set<Serv> servs) {
        this.servs = servs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceProvider serviceProvider = (ServiceProvider) o;
        if (serviceProvider.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceProvider.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceProvider{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", enterpriseCreatedTime='" + getEnterpriseCreatedTime() + "'" +
            ", description='" + getDescription() + "'" +
            ", contactPerson='" + getContactPerson() + "'" +
            ", contactNumber='" + getContactNumber() + "'" +
            ", contactEmail='" + getContactEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", organizationCode='" + getOrganizationCode() + "'" +
            ", businessLicenseNumber='" + getBusinessLicenseNumber() + "'" +
            ", legalRepresentative='" + getLegalRepresentative() + "'" +
            ", generalManager='" + getGeneralManager() + "'" +
            ", technicalManager='" + getTechnicalManager() + "'" +
            ", qualityManager='" + getQualityManager() + "'" +
            ", qualityManagerContact='" + getQualityManagerContact() + "'" +
            ", businessName='" + getBusinessName() + "'" +
            ", businessBank='" + getBusinessBank() + "'" +
            ", businessCity='" + getBusinessCity() + "'" +
            ", businessAccount='" + getBusinessAccount() + "'" +
            ", businessLicense='" + getBusinessLicense() + "'" +
            ", businessLicenseContentType='" + businessLicenseContentType + "'" +
            ", businessScope='" + getBusinessScope() + "'" +
            ", businessScopePreLicense='" + getBusinessScopePreLicense() + "'" +
            ", businessYears='" + getBusinessYears() + "'" +
            ", character='" + getCharacter() + "'" +
            ", scale='" + getScale() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", subsidiary='" + isSubsidiary() + "'" +
            ", haveSubsidiary='" + isHaveSubsidiary() + "'" +
            ", qualityCertificaionSystem='" + getQualityCertificaionSystem() + "'" +
            ", certificationOrg='" + getCertificationOrg() + "'" +
            ", certificationTime='" + getCertificationTime() + "'" +
            ", employeesNumber='" + getEmployeesNumber() + "'" +
            ", employeesBachelorDegreeOrAbove='" + getEmployeesBachelorDegreeOrAbove() + "'" +
            ", employeesTechnical='" + getEmployeesTechnical() + "'" +
            ", employeesCertification='" + getEmployeesCertification() + "'" +
            ", registeredCapital='" + getRegisteredCapital() + "'" +
            ", developTeam='" + isDevelopTeam() + "'" +
            ", testTeam='" + isTestTeam() + "'" +
            ", complaints='" + isComplaints() + "'" +
            ", training='" + isTraining() + "'" +
            ", trainingDocuments='" + isTrainingDocuments() + "'" +
            ", status='" + getStatus() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
