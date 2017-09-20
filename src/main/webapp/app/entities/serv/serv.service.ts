import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Serv } from './serv.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ServService {

    private resourceUrl = 'api/servs';
    private resourceSearchUrl = 'api/_search/servs';
    private resourceUserUrl = 'api/users';
    private servs = 'servs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(serv: Serv): Observable<Serv> {
        const copy = this.convert(serv);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(serv: Serv): Observable<Serv> {
        const copy = this.convert(serv);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Serv> {
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
        return this.http.get(`${this.resourceUserUrl}/${login}/${this.servs}`)
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
        entity.createdTime = this.dateUtils
            .convertDateTimeFromServer(entity.createdTime);
        entity.updatedTime = this.dateUtils
            .convertDateTimeFromServer(entity.updatedTime);
    }

    private convert(serv: Serv): Serv {
        const copy: Serv = Object.assign({}, serv);

        copy.createdTime = this.dateUtils.toDate(serv.createdTime);

        copy.updatedTime = this.dateUtils.toDate(serv.updatedTime);
        return copy;
    }
}
