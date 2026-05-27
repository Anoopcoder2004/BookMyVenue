import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VenuecardComponent } from './venuecard.component';

describe('VenuecardComponent', () => {
  let component: VenuecardComponent;
  let fixture: ComponentFixture<VenuecardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VenuecardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VenuecardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
