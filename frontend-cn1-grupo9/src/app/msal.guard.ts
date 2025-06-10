import {Injectable} from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import {MsalService} from '@azure/msal-angular';

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {
    constructor(private msalService: MsalService, private router: Router) {
    }

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): boolean | UrlTree {
        const account = this.msalService.instance.getActiveAccount();
        if (account == null) {
            try {
                // Check if any accounts exist first
                const accounts = this.msalService.instance.getAllAccounts();
                if (accounts.length === 0) {
                    this.msalService.loginRedirect();
                }
            } catch (error: any) {
                // If interaction is already in progress, just return false and let it complete
                if (error.errorCode === 'interaction_in_progress') {
                    console.log('MSAL interaction already in progress');
                    return false;
                }
                // Re-throw other errors
                throw error;
            }
            return false;
        }
        return true;
    }
}