import {ApplicationConfig, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';
import {
    MSAL_GUARD_CONFIG,
    MSAL_INSTANCE,
    MSAL_INTERCEPTOR_CONFIG,
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
    withInterceptorsFromDi
} from "@angular/common/http";
import {MsalGuardConfigurationFactory, MSALInstanceFactorySync, MSALInterceptorConfigFactory} from './config/msal.config';
import {AuthInterceptor} from "./interceptors/auth-interceptor.interceptor";

export const appConfig: ApplicationConfig = {
    providers: [provideZoneChangeDetection({eventCoalescing: true}),
        provideRouter(routes),
        provideHttpClient(
            withInterceptorsFromDi(),
            withFetch()),
        {
            provide: HTTP_INTERCEPTORS,
            useClass: MsalInterceptor,
            multi: true,
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
        },
        {
            provide: MSAL_INSTANCE,
            useFactory: MSALInstanceFactorySync,
        },
        {
            provide: MSAL_GUARD_CONFIG,
            useFactory: MsalGuardConfigurationFactory,
        },
        {
            provide: MSAL_INTERCEPTOR_CONFIG,
            useFactory: MSALInterceptorConfigFactory,
        },
        MsalService,
        MsalBroadcastService,
        MsalGuard
    ]
};
