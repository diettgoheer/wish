import { BaseEntity } from './../../shared';

export class Transaction implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public fromUser?: string,
        public toUser?: string,
        public amount?: number,
        public time?: any,
        public remark?: string,
        public work?: BaseEntity,
        public fromPerson?: BaseEntity,
        public toPerson?: BaseEntity,
    ) {
    }
}
