import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Project } from './project.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProjectService {

    private resourceUrl = 'api/projects';
    private resourceSearchUrl = 'api/_search/projects';
    private ords = 'ords';
    private resourceUserUrl = 'api/users';
    private projects = 'projects';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(project: Project): Observable<Project> {
        const copy = this.convert(project);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(project: Project): Observable<Project> {
        const copy = this.convert(project);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Project> {
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

    queryByProject(id: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrl}/${id}/${this.ords}`)
            .map((res: Response) => this.convertResponse(res));
    }

    queryByUserLogin(login: String): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUserUrl}/${login}/${this.projects}`)
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
        entity.startDate = this.dateUtils
            .convertLocalDateFromServer(entity.startDate);
        entity.endDate = this.dateUtils
            .convertLocalDateFromServer(entity.endDate);
        entity.createdTime = this.dateUtils
            .convertDateTimeFromServer(entity.createdTime);
        entity.updatedTime = this.dateUtils
            .convertDateTimeFromServer(entity.updatedTime);
    }

    private convert(project: Project): Project {
        const copy: Project = Object.assign({}, project);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(project.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(project.endDate);

        copy.createdTime = this.dateUtils.toDate(project.createdTime);

        copy.updatedTime = this.dateUtils.toDate(project.updatedTime);
        return copy;
    }
}
