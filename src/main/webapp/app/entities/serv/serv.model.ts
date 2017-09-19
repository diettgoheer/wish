import { BaseEntity } from './../../shared';

export class Serv implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public price?: number,
        public unit?: string,
        public description?: string,
        public terma?: string,
        public termb?: string,
        public termc?: string,
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
        public sm?: BaseEntity,
        public father?: BaseEntity,
        public sp?: BaseEntity,
        public ords?: BaseEntity[],
    ) {
    }
}
