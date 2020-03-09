import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAbc } from 'app/shared/model/abc.model';

@Component({
    selector: 'jhi-abc-detail',
    templateUrl: './abc-detail.component.html'
})
export class AbcDetailComponent implements OnInit {
    abc: IAbc;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ abc }) => {
            this.abc = abc;
        });
    }

    previousState() {
        window.history.back();
    }
}
