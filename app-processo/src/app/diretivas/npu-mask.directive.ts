import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appNpuMask]'
})
export class NpuMaskDirective {

  constructor(private el: ElementRef) {}

  @HostListener('input', ['$event'])
  onInput(event: InputEvent) {
    const input = event.target as HTMLInputElement;
    let value = input.value.replace(/\D/g, '');
    if (value.length > 0) {
      value = value.replace(/^(\d{7})(\d)/, '$1-$2');
      value = value.replace(/^(\d{7})-(\d{2})(\d)/, '$1-$2.$3');
      value = value.replace(/^(\d{7})-(\d{2}).(\d{4})(\d)/, '$1-$2.$3.$4');
      value = value.replace(/^(\d{7})-(\d{2}).(\d{4}).(\d{1})(\d)/, '$1-$2.$3.$4.$5');
      value = value.replace(/^(\d{7})-(\d{2}).(\d{4}).(\d{1}).(\d{2})(\d)/, '$1-$2.$3.$4.$5.$6');
    }
    input.value = value;
  }
}
