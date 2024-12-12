package com.nuwanperera.cli;

import java.util.Map;

import com.nuwanperera.cli.modal.Configuration;
import com.nuwanperera.cli.modal.Customer;
import com.nuwanperera.cli.modal.Ticket;
import com.nuwanperera.cli.modal.Vendor;
import com.nuwanperera.cli.util.HttpHandler;
import com.nuwanperera.cli.util.IOHandler;

public class CliApplication {
    static IOHandler ioHandler = IOHandler.getInstance();
    static HttpHandler httpHandler;

    public static void main(String[] args) {
        httpHandler = new HttpHandler("http://localhost:8080/api");

        System.out.println("Real-time Event Ticketing System");

        int option = -1;
        do {
            option = askOption();
            switch (option) {
                case 1:
                    getConfiguration();
                    break;
                case 2:
                    updateConfiguration();
                    break;
                case 3:
                    changeSystemRunningStatus();
                    break;
                case 4:
                    addVendor();
                    break;
                case 5:
                    addCustomer();
                    break;
                case 6:
                    viewTickets();
                    break;
                case 7:
                    viewVendors();
                    break;
                case 8:
                    viewCustomers();
                    break;
                case 0:
                    break;

                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (option != 0);
        System.out.println("Thank you for using the system.");
        ioHandler.close();
    }

    public static int askOption() {
        String menu = """

                **************************************************
                *                  Options Menu                  *
                **************************************************
                    1) View configuration
                    2) Update configuration
                    3) Chnage system running status
                    4) Add vendor
                    5) Add customer
                    6) View tickets
                    7) View vendors
                    8) View customers

                    0) Quit
                **************************************************""";

        System.out.println(menu);
        int choice = -1;
        do {
            System.out.println();
            choice = ioHandler.askUserInt("Please select an option : ");
            if (choice < 0 || choice > 8) {
                System.out.println("Please enter a valid option.");
                choice = -1;
            }
        } while (choice == -1);
        return choice;
    }

    public static void updateConfiguration() {
        Configuration config;
        getConfiguration();
        System.out.printf("%nEnter new values for the configuration.%n");
        int ticketReleaseRate = ioHandler.askUserInt("Enter new ticket release rate in seconds: ", 1);
        int customerRetrievalRate = ioHandler.askUserInt("Enter new customer retrieval rate in seconds: ", 1);
        int maxTicketsCapacity = ioHandler.askUserInt("Enter new max tickets capacity: ", 1);
        int totalTickets = ioHandler.askUserInt("Enter new total tickets: ", 1);

        try {
            config = httpHandler.get("/config", Configuration.class);
            config.setTicketReleaseRate(ticketReleaseRate);
            config.setCustomerRetrievalRate(customerRetrievalRate);
            config.setMaxTicketsCapacity(maxTicketsCapacity);
            config.setTotalTickets(totalTickets);
            config = httpHandler.patch("/config", Map.of(
                    "ticket_release_rate", config.getTicketReleaseRate(),
                    "customer_retrieval_rate", config.getCustomerRetrievalRate(),
                    "max_tickets_capacity", config.getMaxTicketsCapacity(),
                    "total_tickets", config.getTotalTickets()), Configuration.class);
            System.out.printf("Configuration updated successfully.%n");
        } catch (Exception e) {
            System.out.printf("Oops! Something went wrong. Please try again later.%n%n");
            return;
        }
        System.out.printf("%nNew configurations:%n");
        getConfiguration();
    }

    public static void getConfiguration() {
        Configuration config;
        try {
            config = httpHandler.get("/config", Configuration.class);
        } catch (Exception e) {
            System.out.printf("Oops! Something went wrong. Please try again later.%n%n");
            return;
        }
        System.out.printf("%nSystem configuration:%n%n");
        System.out.printf("Ticket release rate: %d seconds%n", config.getTicketReleaseRate());
        System.out.printf("Customer retrieval rate: %d seconds%n", config.getCustomerRetrievalRate());
        System.out.printf("Max tickets capacity: %d%n", config.getMaxTicketsCapacity());
        System.out.printf("Number of tickets per release: %d%n", config.getTotalTickets());
    }

    public static void addCustomer() {
        int retrievalInterval = ioHandler.askUserInt("Enter Customer retrieval Interval: ", 1);
        Customer customer;
        try {
            customer = httpHandler.post("/customers", Map.of("retrieval_interval", retrievalInterval), Customer.class);

            System.out
                    .printf("Customer added successfully with retrieval interval of %d minutes.%n",
                            customer.getRetrievalInterval());
        } catch (Exception e) {
            System.out.println("Oops! Something went wrong. Please try again later.");
        }
    }

    public static void addVendor() {
        int ticketsPerRelease = ioHandler.askUserInt("Enter number of tickets per release: ", 1);
        int releaseInterval = ioHandler.askUserInt("Enter release interval in minutes: ", 1);

        Vendor vendor;
        try {
            vendor = httpHandler.post("/vendors", Map.of("tickets_per_release", ticketsPerRelease, "release_interval",
                    releaseInterval), Vendor.class);

            System.out.printf(
                    "%nVendor %d added successfully with %d tickets per release and release interval of %d minutes.%n%n",
                    vendor.getVendorId(),
                    vendor.getTicketsPerRelease(),
                    vendor.getReleaseInterval());
        } catch (Exception e) {
            System.out.printf("Oops! Something went wrong. Please try again later.%n%n");
            return;
        }
    }

    public static void changeSystemRunningStatus() {
        Configuration config;
        try {
            config = httpHandler.get("/config", Configuration.class);
        } catch (Exception e) {
            System.out.printf("Oops! Something went wrong. Please try again later.%n%n");
            return;
        }
        System.out.printf("Current system running status is %s.%n", config.getRunningStatus());
        if (config.getRunningStatus()) {
            config.setRunningStatus(!ioHandler.askUserBoolean("Do you want to stop the system? (y/n) "));
        } else {
            config.setRunningStatus(ioHandler.askUserBoolean("Do you want to start the system? (y/n) "));
        }
        try {
            config = httpHandler.patch("/config", Map.of("status", config.getRunningStatus()), Configuration.class);
        } catch (Exception e) {
            System.out.printf("Oops! Something went wrong. Please try again later.%n%n");
            return;
        }
        if (config.getRunningStatus()) {
            System.out.printf("System is now running.%n");
        } else {
            System.out.printf("System is now stopped.%n");
        }
    }

    public static void viewTickets() {
        Ticket[] tickets;
        try {
            tickets = httpHandler.get("/tickets", Ticket[].class);
        } catch (Exception e) {
            System.out.printf("Oops! Something went wrong. Please try again later.%n%n");
            return;
        }
        if (tickets.length == 0) {
            System.out.printf("No tickets found.%n");
            return;
        }

        System.out.printf("%nList of tickets:%n%n");
        for (Ticket ticket : tickets) {
            System.out.printf("Ticket ID: %d, Vendor ID: %d%n",
                    ticket.getTicketId(),
                    ticket.getVendorId());
        }
    }

    public static void viewCustomers() {
        Customer[] customers;
        try {
            customers = httpHandler.get("/customers", Customer[].class);
        } catch (Exception e) {
            System.out.printf("Oops! Something went wrong. Please try again later.%n%n");
            return;
        }
        if (customers.length == 0) {
            System.out.printf("No customers found.%n");
            return;
        }

        System.out.printf("%nList of customers:%n%n");
        for (Customer customer : customers) {
            System.out.printf("Customer ID: %d, Retrieval Interval: %d seconds, Running status: %s%n",
                    customer.getCustomerId(),
                    customer.getRetrievalInterval(),
                    customer.getRunningStatus());
        }
    }

    public static void viewVendors() {
        Vendor[] vendors;
        try {
            vendors = httpHandler.get("/vendors", Vendor[].class);
        } catch (Exception e) {
            System.out.printf("Oops! Something went wrong. Please try again later.%n%n");
            return;
        }

        if (vendors.length == 0) {
            System.out.printf("No vendors found.%n");
            return;
        }

        System.out.printf("%nList of vendors:%n%n");
        for (Vendor vendor : vendors) {
            System.out.printf(
                    "Vendor ID: %d, Tickets per release: %d, Release interval: %d seconds, Running status: %s%n",
                    vendor.getVendorId(),
                    vendor.getTicketsPerRelease(),
                    vendor.getReleaseInterval(),
                    vendor.getRunningStatus());
        }
    }

}