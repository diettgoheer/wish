import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Circle } from './circle.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CircleService {

    private resourceUrl = 'api/circles';
    private resourceSearchUrl = 'api/_search/circles';

    constructor(private http: Http) { }

    create(circle: Circle): Observable<Circle> {
        const copy = this.convert(circle);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(circle: Circle): Observable<Circle> {
        const copy = this.convert(circle);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Circle> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(circle: Circle): Circle {
        const copy: Circle = Object.assign({}, circle);
        return copy;
    }
}
