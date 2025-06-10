import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeComponent } from './home.component';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should calculate discounted price correctly', () => {
    const product = {
      price: 100,
      promotion: { discount: 20, endsAt: new Date('2024-12-31') }
    };
    expect(component.getDiscountedPrice(product)).toBe(80);
  });

  it('should return original price when no promotion', () => {
    const product = { price: 100 };
    expect(component.getDiscountedPrice(product)).toBe(100);
  });
});