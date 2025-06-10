import {Component, Inject, inject, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {
    MSAL_GUARD_CONFIG,
    MsalBroadcastService,
    MsalGuardConfiguration,
    MsalService
} from '@azure/msal-angular';
import {RedirectRequest} from '@azure/msal-browser';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule, ReactiveFormsModule, RouterLink, NgbModule],
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
    loginForm: FormGroup;
    isLoading = false;
    showPassword = false;
    isRedirecting = true;
    private fb = inject(FormBuilder);
    private router = inject(Router);

    constructor(
        @Inject(MSAL_GUARD_CONFIG) private msalGuardConfig: MsalGuardConfiguration,
        private authService: MsalService,
        private msalBroadcastService: MsalBroadcastService
    ) {
        this.loginForm = this.fb.group({
            email: ['demo@example.com', [Validators.required, Validators.email]],
            password: ['password123', [Validators.required, Validators.minLength(6)]],
            rememberMe: [false]
        });
    }

    get email() {
        return this.loginForm.get('email');
    }

    get password() {
        return this.loginForm.get('password');
    }

    ngOnInit(): void {
        // Only redirect if user is not already authenticated
        const account = this.authService.instance.getActiveAccount();
        if (!account) {
            try {
                if (this.msalGuardConfig.authRequest) {
                    this.authService.loginRedirect({...this.msalGuardConfig.authRequest} as RedirectRequest);
                } else {
                    this.authService.loginRedirect();
                }
            } catch (error: any) {
                if (error.errorCode === 'interaction_in_progress') {
                    console.log('MSAL interaction already in progress');
                    this.isRedirecting = false;
                } else {
                    throw error;
                }
            }
        } else {
            // User is already authenticated, redirect to dashboard
            this.router.navigate(['/user']);
        }
    }

    togglePasswordVisibility(): void {
        this.showPassword = !this.showPassword;
    }

    onSubmit(): void {
        if (this.loginForm.valid) {
            this.isLoading = true;

            // Simular login
            setTimeout(() => {
                this.isLoading = false;
                console.log('Login successful:', this.loginForm.value);
                // TODO: Implement real authentication
                this.router.navigate(['/user']);
            }, 1500);
        } else {
            this.markFormGroupTouched();
        }
    }

    loginWithDemo(): void {
        this.loginForm.patchValue({
            email: 'demo@example.com',
            password: 'password123'
        });
        this.onSubmit();
    }

    private markFormGroupTouched(): void {
        Object.keys(this.loginForm.controls).forEach(key => {
            const control = this.loginForm.get(key);
            control?.markAsTouched();
        });
    }
}