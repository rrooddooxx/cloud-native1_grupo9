import {Component, Inject, inject, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Router, RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {
    MSAL_GUARD_CONFIG,
    MsalBroadcastService,
    MsalGuardConfiguration,
    MsalService
} from "@azure/msal-angular";
import {Subject} from "rxjs";
import {AuthenticationResult, PopupRequest} from "@azure/msal-browser";

@Component({
    selector: 'app-shell',
    standalone: true,
    imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, NgbModule],
    templateUrl: './shell.component.html',
    styleUrls: ['./shell.component.scss']
})
export class ShellComponent implements OnInit {
    isMenuOpen = false;
    // Mock user data - later replace with AuthService
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

    }

    toggleMenu() {
        this.isMenuOpen = !this.isMenuOpen;
    }

    closeMenu() {
        this.isMenuOpen = false;
    }

    logout() {
        // TODO: Implement logout with AuthService
        this.isAuthenticated = false;
        this.router.navigate(['/home']);
        this.closeMenu();
    }

    login() {
        if (this.msalGuardConfig.authRequest) {
            this.authService
                .loginPopup({...this.msalGuardConfig.authRequest} as PopupRequest)
                .subscribe((response: AuthenticationResult) => {
                    this.authService.instance.setActiveAccount(response.account);

                    // Obtener y guardar el token de acceso
                    this.authService.acquireTokenSilent({scopes: ['User.Read']}).subscribe({
                        next: (tokenResponse) => {
                            localStorage.setItem('jwt', tokenResponse.idToken); // Guarda el token en el localStorage
                            console.log('ID token guardado en localStorage:', tokenResponse.idToken);
                        },
                        error: (error) => {
                            console.error('Error obteniendo el token de acceso:', error);
                        },
                    });
                });
        } else {
            this.authService
                .loginPopup()
                .subscribe((response: AuthenticationResult) => {
                    this.authService.instance.setActiveAccount(response.account);

                    // Obtener y guardar el token de acceso
                    this.authService.acquireTokenSilent({scopes: ['User.Read']}).subscribe({
                        next: (tokenResponse) => {
                            localStorage.setItem('jwt', tokenResponse.accessToken);
                            console.log('ID token guardado en localStorage:', tokenResponse.accessToken);
                        },
                        error: (error) => {
                            console.error('Error obteniendo el token de acceso:', error);
                        },
                    });
                });
        }
    }
}