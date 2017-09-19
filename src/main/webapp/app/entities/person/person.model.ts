import { BaseEntity } from './../../shared';

export class Person implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public email?: string,
        public telephone?: string,
        public description?: string,
        public homePage?: string,
        public picContentType?: string,
        public pic?: any,
        public saa?: string,
        public sab?: string,
        public mac?: string,
        public mad?: string,
        public lae?: string,
        public laf?: string,
        public lag?: string,
        public xlah?: string,
        public xlai?: string,
        public xlaj?: string,
        public ba?: any,
        public bb?: any,
        public bc?: any,
        public bd?: any,
        public be?: any,
        public type?: string,
        public status?: string,
        public user?: string,
        public projects?: BaseEntity[],
    ) {
    }
}
