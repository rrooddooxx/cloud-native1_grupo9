import {
  BrowserCacheLocation,
  Configuration,
  InteractionType,
  IPublicClientApplication,
  LogLevel,
  PublicClientApplication,
} from '@azure/msal-browser';
import { environment } from '../../environments/environment';
import {
  MsalGuardConfiguration,
  MsalInterceptorConfiguration,
} from '@azure/msal-angular';

let msalInstance: IPublicClientApplication | null = null;

export async function MSALInstanceFactory(): Promise<IPublicClientApplication> {
  if (!msalInstance) {
    msalInstance = new PublicClientApplication(msalConfig);
    await msalInstance.initialize();
    console.log('MSAL initialized successfully');
  }
  return msalInstance;
}

export function MSALInstanceFactorySync(): IPublicClientApplication {
  if (!msalInstance) {
    msalInstance = new PublicClientApplication(msalConfig);
    // Initialize asynchronously but return immediately
    msalInstance
      .initialize()
      .then(() => {
        console.log('MSAL initialized successfully');
      })
      .catch((error) => {
        console.error('MSAL initialization failed:', error);
      });
  }
  return msalInstance;
}

export const msalConfig: Configuration = {
  auth: {
    clientId: environment.msalConfig.auth.clientId,
    redirectUri: 'http://localhost:4200/user/dashboard',
    postLogoutRedirectUri: 'http://localhost:4200',
    navigateToLoginRequestUrl: false,
  },
  cache: {
    cacheLocation: BrowserCacheLocation.LocalStorage,
    storeAuthStateInCookie: true,
    secureCookies: false,
  },
  system: {
    loggerOptions: {
      loggerCallback(logLevel: LogLevel, message: string) {
        console.log(message);
      },
      logLevel: LogLevel.Verbose,
      piiLoggingEnabled: false,
    },
    windowHashTimeout: 60000,
    iframeHashTimeout: 6000,
    loadFrameTimeout: 0,
  },
};

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
    authRequest: loginRequestConfig,
    loginFailedRoute: '/auth/login'
  };
}
