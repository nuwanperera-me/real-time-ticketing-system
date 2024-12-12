package com.nuwanperera.cli.modal;

public class Vendor {
  private int vendorId;
  private int ticketsPerRelease;
  private int releaseInterval;
  private boolean isRunning;

  public int getVendorId() {
    return vendorId;
  }

  public int getTicketsPerRelease() {
    return ticketsPerRelease;
  }

  public void setTicketsPerRelease(int ticketsPerRelease) {
    if (ticketsPerRelease < 1) {
      throw new IllegalArgumentException("Tickets per release should be greater than 0");
    }
    this.ticketsPerRelease = ticketsPerRelease;
  }

  public int getReleaseInterval() {
    return releaseInterval;
  }

  public void setReleaseInterval(int releaseInterval) {
    if (releaseInterval < 1) {
      throw new IllegalArgumentException("Release interval should be greater than 0");
    }
    this.releaseInterval = releaseInterval;
  }

  public boolean getRunningStatus() {
    return isRunning;
  }

  public void setRunningStatus(boolean isRunning) {
    this.isRunning = isRunning;
  }

  @Override
  public String toString() {
    return "Vendor{" +
        "vendorId=" + vendorId +
        ", ticketsPerRelease=" + ticketsPerRelease +
        ", releaseInterval=" + releaseInterval +
        ", isRunning=" + isRunning +
        '}';
  }
}
