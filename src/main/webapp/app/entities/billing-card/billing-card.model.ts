import { BaseEntity } from './../../shared';

export class BillingCard implements BaseEntity {
    constructor(
        public id?: number,
        public user?: string,
        public ab?: number,
        public maxab?: number,
        public remark?: string,
        public name?: string,
        public description?: string,
        public createdBy?: string,
        public createdDate?: any,
        public updatedBy?: string,
        public updatedDate?: any,
        public type?: string,
        public status?: string,
    ) {
    }
}
