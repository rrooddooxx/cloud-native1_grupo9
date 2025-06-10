import {Component} from '@angular/core';
import {ShellComponent} from './core/shell/shell.component';
import {MsalService} from "@azure/msal-angular";

@Component({
    selector: 'app-root',
    imports: [ShellComponent],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss'
})
export class AppComponent {
    title = 'ecomm-cloud';

    constructor(
        private authService: MsalService,
    ) {
    }

    ngOnInit(): void {

        this.authService.initialize().subscribe(result => {
            console.log(result)

        })
    }

}
