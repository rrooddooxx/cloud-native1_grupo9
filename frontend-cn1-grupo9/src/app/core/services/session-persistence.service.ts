import { Injectable } from '@angular/core';
import { MsalService } from '@azure/msal-angular';
import { AccountInfo, AuthenticationResult } from '@azure/msal-browser';

@Injectable({
  providedIn: 'root'
})
export class SessionPersistenceService {

  constructor(private msalService: MsalService) {}

  /**
   * Check and restore session on app initialization
   */
  restoreSession(): Promise<boolean> {
    return new Promise((resolve) => {
      // Add a small delay to ensure MSAL is fully initialized
      setTimeout(() => {
        try {
          const accounts = this.msalService.instance.getAllAccounts();
          
          if (accounts.length > 0) {
            // Set active account if not already set
            if (!this.msalService.instance.getActiveAccount()) {
              this.msalService.instance.setActiveAccount(accounts[0]);
            }
            
            // Restore JWT token if missing
            this.restoreJwtToken(accounts[0]).then(() => resolve(true));
          } else {
            resolve(false);
          }
        } catch (error) {
          console.warn('Error restoring session, will retry:', error);
          // If still not initialized, wait a bit more and try again
          setTimeout(() => {
            try {
              const accounts = this.msalService.instance.getAllAccounts();
              if (accounts.length > 0) {
                if (!this.msalService.instance.getActiveAccount()) {
                  this.msalService.instance.setActiveAccount(accounts[0]);
                }
                this.restoreJwtToken(accounts[0]).then(() => resolve(true));
              } else {
                resolve(false);
              }
            } catch (retryError) {
              console.error('Failed to restore session after retry:', retryError);
              resolve(false);
            }
          }, 1000);
        }
      }, 500);
    });
  }

  /**
   * Restore JWT token from MSAL account
   */
  private restoreJwtToken(account: AccountInfo): Promise<void> {
    return new Promise((resolve) => {
      const existingToken = localStorage.getItem('jwt');
      
      if (!existingToken) {
        this.msalService.acquireTokenSilent({
          scopes: ['User.Read'],
          account: account
        }).subscribe({
          next: (tokenResponse) => {
            if (tokenResponse?.idToken) {
              localStorage.setItem('jwt', tokenResponse.idToken);
              console.log('JWT token restored successfully');
            }
            resolve();
          },
          error: (error) => {
            console.warn('Could not restore JWT token silently:', error);
            // Token might be expired, clear localStorage
            this.clearSession();
            resolve();
          }
        });
      } else {
        resolve();
      }
    });
  }

  /**
   * Clear session data
   */
  clearSession(): void {
    localStorage.removeItem('jwt');
  }

  /**
   * Check if user is authenticated
   */
  isAuthenticated(): boolean {
    try {
      const account = this.msalService.instance.getActiveAccount();
      const jwtToken = localStorage.getItem('jwt');
      return !!(account && jwtToken);
    } catch (error) {
      // If MSAL is not initialized yet, check localStorage for existing token
      const jwtToken = localStorage.getItem('jwt');
      return !!jwtToken;
    }
  }
}