import { BaseEntity } from './../../shared';
import {Person} from '../person/person.model';
import {Serv} from '../serv/serv.model';

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
        public wm?: Person,
        public ws?: Person,
        public wf?: Person,
        public projects?: BaseEntity[],
        public servs?: BaseEntity[],
        public buyServ?: Serv
    ) {
    }
}
