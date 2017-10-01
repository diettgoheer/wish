import { Route } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MineComponent } from './mine.component';

export const mineRoute: Route = {
    path: 'mine',
    component: MineComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'global.menu.mine'
    },
    canActivate: [UserRouteAccessService]
};
