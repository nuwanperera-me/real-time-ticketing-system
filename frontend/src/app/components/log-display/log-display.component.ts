import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { LogStoreService } from '../../core/store/log-store.service';
import { LogService } from '../../core/services/log.service';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-log-display',
  standalone: true,
  imports: [NgFor],
  templateUrl: './log-display.component.html',
})
export class LogDisplayComponent implements OnInit {
  @ViewChild('logContainer') logContainer!: ElementRef;
  logs: string[] = [];

  constructor(
    private logStore: LogStoreService,
    private logService: LogService
  ) {}

  ngOnInit(): void {
    this.logStore.logs$.subscribe((logs) => {
      this.logs = logs;
    });

    this.logService.fetchLogs();
    this.logService.startPolling();
  }

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  private scrollToBottom(): void {
    const container = this.logContainer.nativeElement;
    container.scrollTop = container.scrollHeight;
  }
}
