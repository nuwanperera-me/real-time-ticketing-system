# API Documentation

- Base URL: `http://localhost:8080/api`

## Configuration

### GET `/config`  

Fetches the current configuration.

### Response:

- Status: 200 OK

- Body: Configuration object (returns the current configuration settings).


### PATCH `/api/config`

Updates the configuration based on the provided request body.

**Request Body:**


```json
{
  "status": boolean,              // Running status (true/false)
  "total_tickets": integer,       // Total tickets available
  "ticket_release_rate": integer, // Rate at which tickets are released
  "customer_retrieval_rate": integer, // Rate at which customers retrieve tickets
  "max_tickets_capacity": integer // Max capacity of tickets
}
```

**Response:**

- Status: 200 OK

- Body: Updated configuration object.

- Error Response:

  - Invalid values (e.g., non-integer values) for any parameter.

## Customer

### GET `/api/customers`

Fetches a list of all customers.

**Response:**

- Status: 200 OK

- Body: List of customer objects.

### POST /api/customers

Adds a new customer.

**Request Body:**


```json
{
  "retrieval_interval": integer   // The retrieval interval for the customer (in minutes)
}
```

**Response:**

- Status: 201 Created

- Body: Newly created customer object.

- Error Response:

  - Missing or invalid retrieval_interval.

### GET `/api/customers/{customerId}`

Fetches a specific customer by ID.

**Path Parameter:**

- `customerId`: Customer ID.

**Response:**

- Status: 200 OK

- Body: Customer object.

- Error Response:

  - 404 Not Found if customer with the given ID does not exist.


### PATCH `/api/customers/{customerId}`

Updates the running status of a specific customer.

**Path Parameter:**

- `customerId`: Customer ID.

**Request Body:**

```json
{
  "status": boolean // Updated running status (true/false)
}
```

**Response:**

- Status: 200 OK

- Body: Updated customer object.

- Error Response:

  - Invalid status or missing status.

  - 404 Not Found if customer with the given ID does not exist.

### DELETE `/api/customers/{customerId}`

Deletes a specific customer by ID.

**Path Parameter:**

- `customerId:` Customer ID.

**Response:**

- Status: 200 OK

- Body: Deleted customer object.

- Error Response:

  - 404 Not Found if customer with the given ID does not exist.

## Vendor

### GET `/api/vendors`

Fetches a list of all vendors.

**Response:**

- Status: 200 OK

- Body: List of vendor objects.

### POST `/api/vendors`

Adds a new vendor.

Request Body:

```json
{
  "release_interval": integer,      // Interval for releasing tickets
  "tickets_per_release": integer   // Number of tickets per release
}
```

**Response:**

- Status: 201 Created

- Body: Newly created vendor object.

- Error Response:

  - Missing or invalid release_interval or tickets_per_release.

### GET `/api/vendors/{vendorId}`

Fetches a specific vendor by ID.

**Path Parameter:**

- `vendorId`: Vendor ID.

**Response:**

- Status: 200 OK

- Body: Vendor object.

- Error Response:

  - 404 Not Found if vendor with the given ID does not exist.

### PATCH `/api/vendors/{vendorId}`

Updates the running status of a specific vendor.

**Path Parameter:**

- `vendorId`: Vendor ID.

**Request Body:**

```json
{
  "status": boolean // Updated running status (true/false)
}
```

**Response:**

- Status: 200 OK

- Body: Updated vendor object.

- Error Response:

  - Invalid status or missing status.

  - 404 Not Found if vendor with the given ID does not exist.

## DELETE `/api/vendors/{vendorId}`

Deletes a specific vendor by ID.

**Path Parameter:**

- `vendorId`: Vendor ID.

**Response:**

- Status: 200 OK

- Body: Deleted vendor object.

- Error Response:

  - 404 Not Found if vendor with the given ID does not exist.


## Logs

### GET `/api/logs`

Fetches a list of log entries.

**Response:**

- Status: 200 OK

- Body: List of log strings.

## Ticket

### GET `/api/tickets`

Fetches a list of all tickets.

**Response:**

- Status: 200 OK

- Body: List of ticket objects.
