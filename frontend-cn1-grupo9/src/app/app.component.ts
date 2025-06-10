import {Component} from '@angular/core';
import {ShellComponent} from './core/shell/shell.component';
import {MsalBroadcastService, MsalService} from "@azure/msal-angular";
import {filter, Subject, takeUntil} from "rxjs";
import {EventMessage, EventType, InteractionStatus} from "@azure/msal-browser";

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
        private msalBroadcastService: MsalBroadcastService
    ) {
    }

    ngOnInit(): void {

        this.authService.handleRedirectObservable().subscribe();
        this.isIframe = window !== window.parent && !window.opener;
        this.setLoginDisplay();
        this.authService.instance.enableAccountStorageEvents();
        this.msalBroadcastService.msalSubject$
            .pipe(
                filter(
                    (msg: EventMessage) =>
                        msg.eventType === EventType.ACCOUNT_ADDED ||
                        msg.eventType === EventType.ACCOUNT_REMOVED
                )
            )
            .subscribe((result: EventMessage) => {
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
        this.loginDisplay = this.authService.instance.getAllAccounts().length > 0;
    }

    checkAndSetActiveAccount() {
        let activeAccount = this.authService.instance.getActiveAccount();

        if (
            !activeAccount &&
            this.authService.instance.getAllAccounts().length > 0
        ) {
            let accounts = this.authService.instance.getAllAccounts();
            this.authService.instance.setActiveAccount(accounts[0]);
        }
    }

 
    ngOnDestroy(): void {
        this._destroying$.next(undefined);
        this._destroying$.complete();
    }

}
