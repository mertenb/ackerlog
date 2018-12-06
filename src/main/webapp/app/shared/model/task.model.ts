import { Moment } from 'moment';
import { INutrient } from 'app/shared/model//nutrient.model';
import { IUser } from 'app/core/user/user.model';
import { IField } from 'app/shared/model//field.model';

export interface ITask {
    id?: number;
    action?: string;
    actionDate?: Moment;
    entryDate?: Moment;
    type?: string;
    nutrient?: INutrient;
    user?: IUser;
    field?: IField;
}

export class Task implements ITask {
    constructor(
        public id?: number,
        public action?: string,
        public actionDate?: Moment,
        public entryDate?: Moment,
        public type?: string,
        public nutrient?: INutrient,
        public user?: IUser,
        public field?: IField
    ) {}
}
