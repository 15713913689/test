import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAbc } from 'app/shared/model/abc.model';
import { AbcService } from './abc.service';

@Component({
    selector: 'jhi-abc-update',
    templateUrl: './abc-update.component.html'
})
export class AbcUpdateComponent implements OnInit {
    private _abc: IAbc;
    isSaving: boolean;

    constructor(private abcService: AbcService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ abc }) => {
            this.abc = abc;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.abc.id !== undefined) {
            this.subscribeToSaveResponse(this.abcService.update(this.abc));
        } else {
            this.subscribeToSaveResponse(this.abcService.create(this.abc));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAbc>>) {
        result.subscribe((res: HttpResponse<IAbc>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get abc() {
        return this._abc;
    }

    set abc(abc: IAbc) {
        this._abc = abc;
    }
}
