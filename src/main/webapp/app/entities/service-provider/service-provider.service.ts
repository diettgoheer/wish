import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { ServiceProvider } from './service-provider.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ServiceProviderService {

    private resourceUrl = 'api/service-providers';
    private resourceSearchUrl = 'api/_search/service-providers';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(serviceProvider: ServiceProvider): Observable<ServiceProvider> {
        const copy = this.convert(serviceProvider);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(serviceProvider: ServiceProvider): Observable<ServiceProvider> {
        const copy = this.convert(serviceProvider);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<ServiceProvider> {
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
        entity.enterpriseCreatedTime = this.dateUtils
            .convertLocalDateFromServer(entity.enterpriseCreatedTime);
        entity.certificationTime = this.dateUtils
            .convertLocalDateFromServer(entity.certificationTime);
    }

    private convert(serviceProvider: ServiceProvider): ServiceProvider {
        const copy: ServiceProvider = Object.assign({}, serviceProvider);
        copy.enterpriseCreatedTime = this.dateUtils
            .convertLocalDateToServer(serviceProvider.enterpriseCreatedTime);
        copy.certificationTime = this.dateUtils
            .convertLocalDateToServer(serviceProvider.certificationTime);
        return copy;
    }
}
