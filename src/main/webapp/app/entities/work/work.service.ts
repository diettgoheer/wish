import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Work } from './work.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class WorkService {

    private resourceUrl = 'api/works';
    private resourceUrlFinishWork = 'api/works/transaction';
    private resourceSearchUrl = 'api/_search/works';
    private resourceUserUrl = 'api/users';
    private works = 'works';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(work: Work): Observable<Work> {
        const copy = this.convert(work);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(work: Work): Observable<Work> {
        const copy = this.convert(work);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    finish(work: Work): Observable<Work> {
        const copy = this.convert(work);
        return this.http.post(this.resourceUrlFinishWork, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Work> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    queryByUserLogin(login: String): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUserUrl}/${login}/${this.works}`)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        /*entity.startDate = this.dateUtils
            .convertLocalDateFromServer(entity.startDate);
        entity.endDate = this.dateUtils
            .convertLocalDateFromServer(entity.endDate);*/
        entity.createdTime = this.dateUtils
            .convertDateTimeFromServer(entity.createdTime);
        entity.updatedTime = this.dateUtils
            .convertDateTimeFromServer(entity.updatedTime);
    }

    private convert(work: Work): Work {
        const copy: Work = Object.assign({}, work);
       /* copy.startDate = this.dateUtils
            .convertLocalDateToServer(work.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(work.endDate);*/

        copy.createdTime = this.dateUtils.toDate(work.createdTime);

        copy.updatedTime = this.dateUtils.toDate(work.updatedTime);
        return copy;
    }
}
