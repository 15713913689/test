export interface IAbc {
    id?: number;
    aaa?: string;
    bbb?: string;
}

export class Abc implements IAbc {
    constructor(public id?: number, public aaa?: string, public bbb?: string) {}
}
