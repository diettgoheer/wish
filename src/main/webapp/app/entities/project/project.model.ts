import { BaseEntity } from './../../shared';

export class Project implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public simpleName?: string,
        public code?: string,
        public description?: string,
        public sponsor?: string,
        public scienceField?: string,
        public budget?: number,
        public source?: string,
        public startDate?: any,
        public endDate?: any,
        public type?: string,
        public status?: string,
        public createdTime?: any,
        public createdBy?: string,
        public updatedTime?: any,
        public updatedBy?: string,
        public annexaContentType?: string,
        public annexa?: any,
        public annexbContentType?: string,
        public annexb?: any,
        public annexcContentType?: string,
        public annexc?: any,
        public annexdContentType?: string,
        public annexd?: any,
        public annexeContentType?: string,
        public annexe?: any,
        public pm?: BaseEntity,
        public father?: BaseEntity,
        public teams?: BaseEntity[],
        public ords?: BaseEntity[],
    ) {
    }
}
