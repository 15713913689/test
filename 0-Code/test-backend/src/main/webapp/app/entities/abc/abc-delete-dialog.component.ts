import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAbc } from 'app/shared/model/abc.model';
import { AbcService } from './abc.service';

@Component({
    selector: 'jhi-abc-delete-dialog',
    templateUrl: './abc-delete-dialog.component.html'
})
export class AbcDeleteDialogComponent {
    abc: IAbc;

    constructor(private abcService: AbcService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.abcService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'abcListModification',
                content: 'Deleted an abc'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-abc-delete-popup',
    template: ''
})
export class AbcDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ abc }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AbcDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.abc = abc;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
