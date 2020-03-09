import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAbc } from 'app/shared/model/abc.model';

type EntityResponseType = HttpResponse<IAbc>;
type EntityArrayResponseType = HttpResponse<IAbc[]>;

@Injectable({ providedIn: 'root' })
export class AbcService {
    private resourceUrl = SERVER_API_URL + 'api/abcs';

    constructor(private http: HttpClient) {}

    create(abc: IAbc): Observable<EntityResponseType> {
        return this.http.post<IAbc>(this.resourceUrl, abc, { observe: 'response' });
    }

    update(abc: IAbc): Observable<EntityResponseType> {
        return this.http.put<IAbc>(this.resourceUrl, abc, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAbc>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAbc[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
