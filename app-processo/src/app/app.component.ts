import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { LoadingModalComponent  } from './components/loading-modal/loading-modal.component';
import { LoadingService } from './servicies/loading.service';
import { Subscription } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
@Component({
  selector: 'app-root',
  imports: [RouterOutlet, LoginComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit, OnDestroy {
  usarLogout!: boolean;
  private subscription!: Subscription;
  constructor(
    private modalService: NgbModal,
    private router: Router,
    private loadingServiceService: LoadingService
  ) {}

  ngOnInit() {
    const vm = this;
    vm.router.events.subscribe((event: any) => {
      vm.usarLogout = (vm.router.url !== '/loguin'); 
    });
    this.subscription = this.loadingServiceService.loading$.subscribe(loading => {
      if (loading) {
        this.modalService.open(LoadingModalComponent, { backdrop: 'static', keyboard: false });
      } else {
        this.modalService.dismissAll();
      }
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

}