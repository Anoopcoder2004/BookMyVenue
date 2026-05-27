import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VenueApprovalComponent } from './venue-approval.component';

describe('VenueApprovalComponent', () => {
  let component: VenueApprovalComponent;
  let fixture: ComponentFixture<VenueApprovalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VenueApprovalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VenueApprovalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
