import { IUser } from 'app/core/user/user.model';
import { IField } from 'app/shared/model//field.model';

export interface INutrient {
    id?: number;
    name?: string;
    n?: number;
    p?: number;
    k?: number;
    note?: string;
    user?: IUser;
    field?: IField;
}

export class Nutrient implements INutrient {
    constructor(
        public id?: number,
        public name?: string,
        public n?: number,
        public p?: number,
        public k?: number,
        public note?: string,
        public user?: IUser,
        public field?: IField
    ) {}
}
