import {
    BrowserCacheLocation,
    Configuration,
    InteractionType,
    IPublicClientApplication,
    LogLevel,
    PublicClientApplication
} from '@azure/msal-browser';
import {environment} from '../../environments/environment';
import {MsalGuardConfiguration} from "@azure/msal-angular";


export function MSALInstanceFactory(): IPublicClientApplication {
    return new PublicClientApplication(msalConfig);
}

export const msalConfig: Configuration = {
    auth: {
        clientId: environment.msalConfig.auth.clientId,
        redirectUri: 'http://localhost:4200',
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

export const loginRequestConfig = {
    scopes: [],
};

export function MsalGuardConfigurationFactory(): MsalGuardConfiguration {
    return {
        interactionType: InteractionType.Redirect,
        authRequest: loginRequestConfig
    };
}