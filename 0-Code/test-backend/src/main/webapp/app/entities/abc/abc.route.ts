import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable } from 'rxjs';
import { Abc } from 'app/shared/model/abc.model';
import { AbcService } from './abc.service';
import { AbcComponent } from './abc.component';
import { AbcDetailComponent } from './abc-detail.component';
import { AbcUpdateComponent } from './abc-update.component';
import { AbcDeletePopupComponent } from './abc-delete-dialog.component';
import { IAbc } from 'app/shared/model/abc.model';

@Injectable({ providedIn: 'root' })
export class AbcResolve implements Resolve<IAbc> {
    constructor(private service: AbcService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).map((abc: HttpResponse<Abc>) => abc.body);
        }
        return Observable.of(new Abc());
    }
}

export const abcRoute: Routes = [
    {
        path: 'abc',
        component: AbcComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jhipsterApp.abc.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'abc/:id/view',
        component: AbcDetailComponent,
        resolve: {
            abc: AbcResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.abc.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'abc/new',
        component: AbcUpdateComponent,
        resolve: {
            abc: AbcResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.abc.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'abc/:id/edit',
        component: AbcUpdateComponent,
        resolve: {
            abc: AbcResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.abc.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const abcPopupRoute: Routes = [
    {
        path: 'abc/:id/delete',
        component: AbcDeletePopupComponent,
        resolve: {
            abc: AbcResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterApp.abc.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
