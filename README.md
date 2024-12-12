# Real-Time Event Ticketing System

A comprehensive system for managing real-time event ticketing, including a backend built with Spring Boot, a frontend powered by Angular, and a CLI developed in Java.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) (version 22.0.1)

- Node.js and Bun.js

- Maven (for backend and CLI)

- Spring Boot CLI

- Angular CLI

### Installation

Clone the repository:

```bash
git clone https://github.com/your-username/real-time-ticketing-system.git
cd real-time-ticketing-system
```

#### Backend Setup:

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

#### Frontend Setup:

```bash
cd frontend
bun install
ng serve -o
```

#### CLI Setup:

```bash
cd cli
mvn clean install
mvn exec:java -Dexec.mainClass="com.nuwanperera.cli.CliApplication"
```

## Usage

### Frontend
Run the backend and frontend using the commands mentioned in the installation section. Access the application at `http://localhost:4200`.

### CLI
Run the backend and CLI using the commands mentioned in the installation section.

