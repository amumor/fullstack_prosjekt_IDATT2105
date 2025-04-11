# Find.no - IDATT2105 Full-Stack Spring 2025 Project

[![Java Version](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/SpringBoot-3.3.10-blue.svg)](https://spring.io/projects/spring-boot)
[![Vue](https://img.shields.io/badge/Vue-3.5.13-green.svg)](https://vuejs.org)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Find.no is a dynamic second-hand marketplace that connects buyers and sellers, facilitating seamless transactions. This project features a robust full-stack architecture where the backend is built on Java Spring Boot, secured with a comprehensive JWT-based security suite and an advanced user management system. It exposes a range of RESTful endpoints and leverages a fast in-file H2 database managed via JPA. The frontend is crafted with Vue, utilizing its reactive capabilities and efficient routing to deliver a smooth user experience. This project is the culmination of the IDATT2105 Full-Stack course at NTNU, Spring 2025.

---

## Table of Contents

- [Find.no - IDATT2105 Full-Stack Spring 2025 Project](#findno---idatt2105-full-stack-spring-2025-project)
	- [Table of Contents](#table-of-contents)
	- [Project Overview](#project-overview)
		- [Problem Statement](#problem-statement)
		- [Our Solution](#our-solution)
		- [Architecture Overview](#architecture-overview)
		- [Context and Academic Integration](#context-and-academic-integration)
	- [Tech Stack](#tech-stack)
		- [Backend](#backend)
		- [Frontend](#frontend)
		- [Continuous Integration](#continuous-integration)
	- [Prerequisites](#prerequisites)
	- [Installation / Deployment](#installation--deployment)
		- [.env variables](#env-variables)
			- [Frontend](#frontend-1)
			- [Backend](#backend-1)
		- [Clone the Repository](#clone-the-repository)
		- [Start the dockerized system](#start-the-dockerized-system)
		- [Usage](#usage)
		- [Running tests in backend](#running-tests-in-backend)
		- [Running tests in frontend](#running-tests-in-frontend)
	- [Guides](#guides)
		- [How To - Update OpenAPI services in frontend](#how-to---update-openapi-services-in-frontend)
			- [1. Generate OpenAPI functions using this command:](#1-generate-openapi-functions-using-this-command)
			- [2. Removed everything OTHER than these files and folders:](#2-removed-everything-other-than-these-files-and-folders)
			- [3. Comment out / remove user-Agent in ApiClient.js](#3-comment-out--remove-user-agent-in-apiclientjs)
			- [4. Run npm install](#4-run-npm-install)
---

## Project Overview

**Find.no** is a dynamic second-hand marketplace designed to connect buyers and sellers in a secure, intuitive online environment. The platform addresses the need for a reliable and efficient marketplace where individuals can seamlessly conduct transactions and exchange pre-owned goods.

### Problem Statement
Second-hand marketplaces can often be fragmented and lack robust security measures, leading to trust issues and inefficient transaction processes. Many existing systems do not offer streamlined user experiences or fail to integrate modern web technologies to ensure responsiveness and ease of use.

### Our Solution
Find.no leverages a modern full-stack architecture to tackle these challenges:
- **Robust Security:** The backend employs Java Spring Boot with a comprehensive security suite, including JWT-based authentication and a well-designed user system, ensuring that every transaction is conducted securely.
- **Efficient Data Management:** Using a fast in-file H2 database managed by JPA, Find.no achieves quick data retrieval and scalability, which is crucial for handling a dynamic online marketplace.
- **Seamless User Experience:** The frontend is built with Vue, harnessing its reactive reference system and efficient routing capabilities to create a smooth and responsive user interface.

### Architecture Overview
- **Docker Compose:** Find.no is a dockerized system with Volumes for persistent image storage and database storage. Backend builds automatically with its own custom build stage.
- **Backend:**  
  - **Language & Framework:** Java 21 using Spring Boot 3.3.10.  
  - **Security:** Implements JWT authentication, Spring security and a comprehensive user management system. Passwords are stored encrypted in the database.
  - **Database:** Uses a fast in-file H2 database managed via JPA to support rapid data transactions. 
  - **API:** Exposes a robust set of RESTful endpoints to handle various marketplace actions, ranging from user management to transaction handling.

- **Frontend:**  
  - **Framework:** Built with Vue ^3.5.13, which provides a reactive and modular framework for creating a responsive user interface.  
  - **Development Tool:** Utilizes Vite for a high-performance development environment, ensuring quick builds and fast module reloading.  
  - **User Experience:** Focuses on intuitive navigation and responsiveness through Vue Router and Vue’s reactive data binding.

### Context and Academic Integration
This project serves as the culminating work for the IDATT2105 Full-Stack course at NTNU in Spring 2025. It encapsulates all the core concepts learned throughout the course, demonstrating practical integration of modern backend and frontend technologies in a real-world application.

By addressing the common pitfalls of existing marketplaces and employing cutting-edge technologies, Find.no offers a secure, fast, and user-friendly platform, setting a new standard for online second-hand trading.

---

## Tech Stack

### Backend
- **Language:** Java 21
- **Framework:** Spring Boot 3.3.10
- **ORM:** Hibernate/JPA
- **Build Tool:** Maven
- **Database:** H2 in file
- **Security:** Spring Security / JWT, Password encryption

### Frontend
- **Framework:** Vue ^3.5.13
- **Build Tool & Dev Server:** Vite
- **State Management:** Pinia (with `pinia-plugin-persistedstate`)
- **Routing:** Vue Router
- **Other Libraries:** 
  - `@iconify/vue`
  - `@vue-leaflet/vue-leaflet`
  - `@vueuse/components`
  - `axios`
  - `dotenv`
  - `jwt-decode`
  - `leaflet`
  - `superagent`
  - `vue-map-ui`

---

### Continuous Integration 

We use github actions to continually test each pull-request into main, running tests in deployment. .env secrets are configured under [Settings - Action secrets and variables](https://github.com/amumor/fullstack_prosjekt_IDATT2105/settings/secrets/actions) in the repo.

---

## Prerequisites

Ensure you have the following installed on your development machine:
- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Node.js (Latest LTS)](https://nodejs.org/en/)
- Package managers: Maven for Java, and npm for Node.js
- Git

---

## Installation / Deployment

Find.no utelizes a self building docker-compose setup which requires minimal setup to deploy, as detailed below:

---

### .env variables
This project uses `.env` files to manage project secrets in both frontend and backend. Below each required variable is described:

#### Frontend
The `.env` file must be placed in `frontend/VueFrontend/` folder.

Required variables to be set in the `.env` file:

- `VITE_GOOGLE_API_KEY` - Secret API key to [Google APIs](https://console.cloud.google.com/projectselector2/apis?invt=AbudEw). To get started, create your own project and API key. For the sensors of this project, our key will be submitted in Blackboard.

#### Backend
Backends `.env` file must be placed in `backend/SpringBackend/src/main/resources`.

Required variables to be set in the `.env` file:

- `SECRET_KEY` - Unique 256-bit hexadecimal key, Used for JWT token generation
- `APP_DEFAULT_ADMIN_EMAIL` - Email address of the initial hardcoded admin user in backend. example: **admin@find.no**
- `APP_DEFAULT_ADMIN_PASSWORD` - Password of the of the initial hardcoded admin user. Requirements: Must be **at least 10 characters**, contain **an uppercase letter**, **a digit**, and **a special character**. 

---

### Clone the Repository
```bash
git clone https://github.com/amumor/fullstack_prosjekt_IDATT2105.git
````

---

### Start the dockerized system

Navigate into the project folder:

```bash
cd fullstack_prosjekt_IDATT2105
```

Start docker-compose (On non-Linux systems: remember to start your docker daemon before running):
```bash
docker-compose up --build
```

---

### Usage

- Access website: [http://localhost:5173/](http://localhost:5173/)
- Access backendAPIs: [http://localhost:8080](http://localhost:8080)
- Access API documentation: [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)
  - For JSON output: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

### Running tests in backend

Navigate to the backend project (from repo root):
```bash
cd backend/SpringBackend/
```

Run tests with maven:
```bash
mvn test
```

---

### Running tests in frontend

Navigate to the frontend project (from repo root):
```bash
cd frontend/VueFrontend/
```

Start the frontend project:
```bash
npm run dev
```

in another terminal, start the cypress interface:
```bash
npm run cypress:open
```

A browser window will open, select "E2E Testing", select Chrome, and start the tests.

---

## Guides

### How To - Update OpenAPI services in frontend

#### 1. Generate OpenAPI functions using this command:
Remember to have backend running and check that Swagger outputs a JSON description of the endpoints on this **http://localhost:8080/v3/api-docs **

```sh
npx @openapitools/openapi-generator-cli generate \
  -i http://localhost:8080/v3/api-docs \
  -g javascript \
  -o ./src/api \
  --additional-properties=usePromises=true,useES6=true
```

#### 2. Removed everything OTHER than these files and folders:
- /api (the one containing the SomeControllerApi files)
- /model (containing the DTOs)
- /test
- ApiClient.js
- index.js

and move the content of the src/ folder out into root of the generated folder, beside the generated README.md

#### 3. Comment out / remove user-Agent in ApiClient.js
As shown below:

```javascript
* The default HTTP headers to be included for all API calls.
* @type {Array.<String>}
* @default {}
*/
this.defaultHeaders = {
   // 'User-Agent': 'OpenAPI-Generator/1.0/Javascript'
};
```

#### 4. Run npm install

```sh
npm install
```
