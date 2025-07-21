import {Component} from '@angular/core';
import {ShellComponent} from './core/shell/shell.component';
import {MsalBroadcastService, MsalService} from "@azure/msal-angular";
import {filter, Subject, takeUntil} from "rxjs";
import {EventMessage, EventType, InteractionStatus, AuthenticationResult, AccountInfo} from "@azure/msal-browser";
import {SessionPersistenceService} from './core/services/session-persistence.service';

@Component({
    selector: 'app-root',
    imports: [ShellComponent],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss'
})
export class AppComponent {
    title = 'ecomm-cloud';
    isIframe = false;
    loginDisplay = false;
    private readonly _destroying$ = new Subject<void>();

    constructor(
        private authService: MsalService,
        private msalBroadcastService: MsalBroadcastService,
        private sessionPersistence: SessionPersistenceService
    ) {
    }

    ngOnInit(): void {
        this.isIframe = window !== window.parent && !window.opener;
        
        // Initialize MSAL properly with delay
        setTimeout(() => {
            try {
                this.authService.handleRedirectObservable().subscribe();
                this.setLoginDisplay();
                this.authService.instance.enableAccountStorageEvents();
                
                // Restore session after MSAL is ready
                this.sessionPersistence.restoreSession().then(() => {
                    this.setLoginDisplay();
                });
            } catch (error) {
                console.warn('Error initializing MSAL, retrying...', error);
                setTimeout(() => {
                    this.authService.handleRedirectObservable().subscribe();
                    this.setLoginDisplay();
                    this.authService.instance.enableAccountStorageEvents();
                    this.sessionPersistence.restoreSession().then(() => {
                        this.setLoginDisplay();
                    });
                }, 1000);
            }
        }, 100);
        
        this.msalBroadcastService.msalSubject$
            .pipe(
                filter(
                    (msg: EventMessage) =>
                        msg.eventType === EventType.ACCOUNT_ADDED ||
                        msg.eventType === EventType.ACCOUNT_REMOVED
                )
            )
            .subscribe(() => {
                if (this.authService.instance.getAllAccounts().length === 0) {
                    window.location.pathname = '/';
                } else {
                    this.setLoginDisplay();
                }
            });

        this.msalBroadcastService.inProgress$
            .pipe(
                filter(
                    (status: InteractionStatus) => status === InteractionStatus.None
                ),
                takeUntil(this._destroying$)
            )
            .subscribe(() => {
                this.setLoginDisplay();
                this.checkAndSetActiveAccount();
            });
    }

    setLoginDisplay() {
        try {
            this.loginDisplay = this.authService.instance.getAllAccounts().length > 0;
        } catch (error) {
            // If MSAL is not initialized yet, assume not logged in
            this.loginDisplay = false;
        }
    }

    checkAndSetActiveAccount() {
        try {
            let activeAccount = this.authService.instance.getActiveAccount();

            if (
                !activeAccount &&
                this.authService.instance.getAllAccounts().length > 0
            ) {
                let accounts = this.authService.instance.getAllAccounts();
                this.authService.instance.setActiveAccount(accounts[0]);
                // Restore JWT token to localStorage if not present
                this.restoreTokenFromAccount(accounts[0]);
            } else if (activeAccount) {
                // Even if there's an active account, check if JWT token needs restoration
                const existingToken = localStorage.getItem('jwt');
                if (!existingToken) {
                    this.restoreTokenFromAccount(activeAccount);
                }
            }
        } catch (error) {
            console.warn('Error in checkAndSetActiveAccount, MSAL may not be ready:', error);
        }
    }

    private restoreTokenFromAccount(account: AccountInfo) {
        // Check if JWT token exists in localStorage
        const existingToken = localStorage.getItem('jwt');
        if (!existingToken && account) {
            // Try to get a token silently
            this.authService.acquireTokenSilent({
                scopes: ['User.Read'],
                account: account
            }).subscribe({
                next: (tokenResponse: AuthenticationResult) => {
                    localStorage.setItem('jwt', tokenResponse.idToken);
                    console.log('Token restored to localStorage after refresh');
                },
                error: (error: any) => {
                    console.log('Could not restore token silently:', error);
                }
            });
        }
    }

 
    ngOnDestroy(): void {
        this._destroying$.next(undefined);
        this._destroying$.complete();
    }

}
