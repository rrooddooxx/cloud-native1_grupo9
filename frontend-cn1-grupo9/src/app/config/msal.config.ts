import {
    BrowserCacheLocation,
    Configuration,
    InteractionType,
    IPublicClientApplication,
    LogLevel,
    PublicClientApplication
} from '@azure/msal-browser';
import {environment} from '../../environments/environment';
import {MsalGuardConfiguration, MsalInterceptorConfiguration} from "@azure/msal-angular";


export function MSALInstanceFactory(): IPublicClientApplication {
    return new PublicClientApplication(msalConfig);
}

export const msalConfig: Configuration = {
    auth: {
        clientId: environment.msalConfig.auth.clientId,
        authority: environment.msalConfig.auth.authority,
        redirectUri: 'http://localhost:4200/user/dashboard',
    },
    cache: {
        cacheLocation: BrowserCacheLocation.LocalStorage,
    },
    system: {
        loggerOptions: {
            loggerCallback(logLevel: LogLevel, message: string) {
                console.log(message);
            },
            logLevel: LogLevel.Verbose,
            piiLoggingEnabled: false,
        },
    }
}

export function MSALInterceptorConfigFactory(): MsalInterceptorConfiguration {
    const protectedResourceMap = new Map<string, Array<string>>();
    protectedResourceMap.set(
        environment.apiConfig.uri,
        environment.apiConfig.scopes
    );

    return {
        interactionType: InteractionType.Redirect,
        protectedResourceMap,
    };
}

export const loginRequestConfig = {
    scopes: [...environment.apiConfig.scopes],
};

export function MsalGuardConfigurationFactory(): MsalGuardConfiguration {
    return {
        interactionType: InteractionType.Redirect,
        authRequest: loginRequestConfig
    };
}