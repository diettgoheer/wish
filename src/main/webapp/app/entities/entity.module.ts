import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { WishCircleModule } from './circle/circle.module';
import { WishPersonModule } from './person/person.module';
import { WishProjectModule } from './project/project.module';
import { WishWorkModule } from './work/work.module';
import { WishServModule } from './serv/serv.module';
import { WishServiceProviderModule } from './service-provider/service-provider.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        WishCircleModule,
        WishPersonModule,
        WishProjectModule,
        WishWorkModule,
        WishServModule,
        WishServiceProviderModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WishEntityModule {}
