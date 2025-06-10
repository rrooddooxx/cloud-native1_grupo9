import {Component, Inject, inject, OnInit, OnDestroy} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Router, RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {
    MSAL_GUARD_CONFIG,
    MsalBroadcastService,
    MsalGuardConfiguration,
    MsalService
} from "@azure/msal-angular";
import {Subject, takeUntil} from "rxjs";
import {AuthenticationResult, PopupRequest, EventType} from "@azure/msal-browser";

@Component({
    selector: 'app-shell',
    standalone: true,
    imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, NgbModule],
    templateUrl: './shell.component.html',
    styleUrls: ['./shell.component.scss']
})
export class ShellComponent implements OnInit, OnDestroy {
    isMenuOpen = false;
    isAuthenticated = false;
    tokenExpiration: string = '';
    currentUser = {
        name: 'Usuario Demo',
        avatar: 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=40&h=40&fit=crop&crop=face'
    };
    private readonly _destroying$ = new Subject<void>();
    private router = inject(Router);

    constructor(
        @Inject(MSAL_GUARD_CONFIG) private msalGuardConfig: MsalGuardConfiguration,
        private authService: MsalService,
        private msalBroadcastService: MsalBroadcastService
    ) {
    }

    ngOnInit(): void {
        // Set initial authentication state
        this.updateAuthenticationState();
        
        // Subscribe to MSAL events to update authentication state
        this.msalBroadcastService.msalSubject$
            .pipe(takeUntil(this._destroying$))
            .subscribe((result) => {
                if (result.eventType === EventType.LOGIN_SUCCESS || 
                    result.eventType === EventType.LOGOUT_SUCCESS ||
                    result.eventType === EventType.ACQUIRE_TOKEN_SUCCESS) {
                    this.updateAuthenticationState();
                }
            });
    }

    ngOnDestroy(): void {
        this._destroying$.next();
        this._destroying$.complete();
    }

    private updateAuthenticationState(): void {
        this.isAuthenticated = Boolean(this.authService.instance.getActiveAccount() !== null);
    }


    toggleMenu() {
        this.isMenuOpen = !this.isMenuOpen;
    }

    closeMenu() {
        this.isMenuOpen = false;
    }

    /*   logout() {
           // TODO: Implement logout with AuthService
           this.isAuthenticated = false;
           this.router.navigate(['/home']);
           this.closeMenu();
       }*/


    login() {
        if (this.msalGuardConfig.authRequest) {
            this.authService
                .loginPopup({...this.msalGuardConfig.authRequest} as PopupRequest)
                .subscribe((response: AuthenticationResult) => {
                    this.authService.instance.setActiveAccount(response.account);
                    this.authService.acquireTokenSilent({scopes: ['User.Read']}).subscribe({
                        next: (tokenResponse) => {
                            localStorage.setItem('jwt', tokenResponse.idToken);
                            console.log('ID token guardado en localStorage:', tokenResponse.idToken);
                            this.updateAuthenticationState();
                        },
                        error: (error) => {
                            console.error('Error obteniendo el token de acceso:', error);
                            this.updateAuthenticationState();
                        },
                    });
                });
        } else {
            this.authService
                .loginPopup()
                .subscribe((response: AuthenticationResult) => {
                    this.authService.instance.setActiveAccount(response.account);
                    this.authService.acquireTokenSilent({scopes: ['User.Read']}).subscribe({
                        next: (tokenResponse) => {
                            localStorage.setItem('jwt', tokenResponse.accessToken);
                            console.log('ID token guardado en localStorage:', tokenResponse.accessToken);
                            this.updateAuthenticationState();
                        },
                        error: (error) => {
                            console.error('Error obteniendo el token de acceso:', error);
                            this.updateAuthenticationState();
                        },
                    });
                });
        }
    }

    logout(popup?: boolean) {
        if (popup) {
            this.authService.logoutPopup({
                mainWindowRedirectUri: '/',
            });
        } else {
            this.authService.logoutRedirect();
        }
        this.updateAuthenticationState();
    }
}