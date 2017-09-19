import { BaseEntity } from './../../shared';

export class Work implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public budget?: number,
        public totalPrice?: number,
        public startDate?: any,
        public endDate?: any,
        public type?: string,
        public status?: string,
        public createdTime?: any,
        public createdBy?: string,
        public updatedTime?: any,
        public updatedBy?: string,
        public wm?: BaseEntity,
        public ws?: BaseEntity,
        public wf?: BaseEntity,
        public projects?: BaseEntity[],
        public servs?: BaseEntity[],
    ) {
    }
}
