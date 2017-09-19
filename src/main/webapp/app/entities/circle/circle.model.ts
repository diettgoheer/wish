import { BaseEntity } from './../../shared';

export class Circle implements BaseEntity {
    constructor(
        public id?: number,
        public userLogin?: string,
        public friendLogin?: string,
        public person?: BaseEntity,
    ) {
    }
}
