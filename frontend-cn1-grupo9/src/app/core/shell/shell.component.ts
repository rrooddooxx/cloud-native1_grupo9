import {Component, inject, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Router, RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {MsalService} from "@azure/msal-angular";
import {Subject} from "rxjs";

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
        private authService: MsalService,
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
        this.authService.loginRedirect();
    }
}