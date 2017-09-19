import { BaseEntity } from './../../shared';

export class ServiceProvider implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public enterpriseCreatedTime?: any,
        public description?: string,
        public contactPerson?: string,
        public contactNumber?: string,
        public contactEmail?: string,
        public address?: string,
        public zipCode?: string,
        public organizationCode?: string,
        public businessLicenseNumber?: string,
        public legalRepresentative?: string,
        public generalManager?: string,
        public technicalManager?: string,
        public qualityManager?: string,
        public qualityManagerContact?: string,
        public businessName?: string,
        public businessBank?: string,
        public businessCity?: string,
        public businessAccount?: string,
        public businessLicenseContentType?: string,
        public businessLicense?: any,
        public businessScope?: string,
        public businessScopePreLicense?: string,
        public businessYears?: number,
        public character?: string,
        public scale?: string,
        public serialNumber?: string,
        public subsidiary?: boolean,
        public haveSubsidiary?: boolean,
        public qualityCertificaionSystem?: string,
        public certificationOrg?: string,
        public certificationTime?: any,
        public employeesNumber?: number,
        public employeesBachelorDegreeOrAbove?: number,
        public employeesTechnical?: number,
        public employeesCertification?: string,
        public registeredCapital?: number,
        public developTeam?: boolean,
        public testTeam?: boolean,
        public complaints?: boolean,
        public training?: boolean,
        public trainingDocuments?: boolean,
        public status?: string,
        public type?: string,
        public servs?: BaseEntity[],
    ) {
        this.subsidiary = false;
        this.haveSubsidiary = false;
        this.developTeam = false;
        this.testTeam = false;
        this.complaints = false;
        this.training = false;
        this.trainingDocuments = false;
    }
}
