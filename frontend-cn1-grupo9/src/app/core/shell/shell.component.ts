import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, RouterLinkActive, Router } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, NgbModule],
  templateUrl: './shell.component.html',
  styleUrls: ['./shell.component.scss']
})
export class ShellComponent {
  private router = inject(Router);
  
  isMenuOpen = false;
  
  // Mock user data - later replace with AuthService
  isAuthenticated = false;
  currentUser = {
    name: 'Usuario Demo',
    avatar: 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=40&h=40&fit=crop&crop=face'
  };

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
    // TODO: Implement login navigation
    this.router.navigate(['/auth/login']);
    this.closeMenu();
  }
}