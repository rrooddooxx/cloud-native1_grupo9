import {ApplicationConfig, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';
import {
    MSAL_GUARD_CONFIG,
    MSAL_INSTANCE,
    MsalBroadcastService,
    MsalGuard,
    MsalInterceptor,
    MsalService
} from '@azure/msal-angular'
import {routes} from './app.routes';
import {
    HTTP_INTERCEPTORS,
    provideHttpClient,
    withFetch,
    withInterceptors,
    withInterceptorsFromDi
} from "@angular/common/http";
import {MsalGuardConfigurationFactory, MSALInstanceFactory} from './config/msal.config';
import {authInterceptorProvider} from "./interceptors/auth-interceptor.interceptor";

export const appConfig: ApplicationConfig = {
    providers: [provideZoneChangeDetection({eventCoalescing: true}),
        provideRouter(routes),
        provideHttpClient(withInterceptorsFromDi(),
            withFetch(),
            withInterceptors([authInterceptorProvider])),
        {
            provide: HTTP_INTERCEPTORS,
            useClass: MsalInterceptor,
            multi: true,
        },
        {
            provide: MSAL_INSTANCE,
            useFactory: MSALInstanceFactory,
        },
        {
            provide: MSAL_GUARD_CONFIG,
            useFactory: MsalGuardConfigurationFactory,
        },
        MsalService,
        MsalBroadcastService,
        MsalGuard
    ]
};
