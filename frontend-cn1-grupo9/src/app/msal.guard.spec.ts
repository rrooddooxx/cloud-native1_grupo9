import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { msalGuard } from './msal.guard';

describe('msalGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => msalGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
