import { IUser } from 'app/core/user/user.model';

export interface IField {
    id?: number;
    name?: string;
    user?: IUser;
}

export class Field implements IField {
    constructor(public id?: number, public name?: string, public user?: IUser) {}
}
