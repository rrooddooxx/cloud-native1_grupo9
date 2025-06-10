import {Component, Inject} from '@angular/core';
import {ShellComponent} from './core/shell/shell.component';
import {
    MSAL_GUARD_CONFIG,
    MsalBroadcastService,
    MsalGuardConfiguration,
    MsalService
} from "@azure/msal-angular";
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
        @Inject(MSAL_GUARD_CONFIG) private msalGuardConfig: MsalGuardConfiguration,
        private authService: MsalService,
        private msalBroadcastService: MsalBroadcastService
    ) {
    }

    ngOnInit(): void {

        /**
         * Inicia el manejo de redirección de MSAL para capturar el resultado del login
         * Esto es necesario para que la aplicación pueda manejar correctamente el flujo de autenticación
         */
        this.authService.handleRedirectObservable().subscribe();
        this.isIframe = window !== window.parent && !window.opener;

        /**
         * Configura el estado de inicio de sesión al cargar la aplicación
         * Esto asegura que la UI refleje correctamente si el usuario está autenticado o no
         */
        this.setLoginDisplay();

        /**
         * Habilita los eventos de almacenamiento de cuenta para sincronizar el estado de la sesión
         * entre diferentes pestañas del navegador. Esto es útil para mantener la UI actualizada
         * sin necesidad de recargar la página.
         */
        this.authService.instance.enableAccountStorageEvents();

        /**
         * Sincroniza el estado de la sesión entre pestañas y asegura que si un usuario hace logout en otra ventana,
         * la aplicación se actualiza correctamente. Permite reaccionar a cambios de cuentas
         * y actualizar la UI sin tener que recargar la página completa
         */
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

        /**
         * Permite refrescar la UI y el estado de la cuenta solo cuando todas las interacciones han terminado,
         * evitando mostrar datos inconsistentes mientras hay logins/logouts en proceso.
         * Garantiza que la experiencia de usuario sea consistente y reactiva
         */
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

        // Verifica si ya hay una cuenta activa
        let activeAccount = this.authService.instance.getActiveAccount();

        if (
            !activeAccount &&
            this.authService.instance.getAllAccounts().length > 0
        ) {
            let accounts = this.authService.instance.getAllAccounts();
            this.authService.instance.setActiveAccount(accounts[0]);
        }
    }


}
