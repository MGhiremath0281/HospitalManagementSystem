#  Hospital Management System (HMS)

A full-featured **Hospital Management System** built using **Spring Boot**, designed to simplify hospital operations such as doctor management, patient registration, appointment scheduling, and more.
This project demonstrates secure, scalable, and maintainable backend design using **Spring Security**, **JPA**, and **RESTful APIs**.

---

##  Features

**Doctor Management** – Add, update, and view doctor profiles and specializations.
**Patient Management** – Register and manage patient details securely.
**Appointments** – Schedule, view, and cancel appointments.
**User Authentication & Role-Based Access Control** using Spring Security.
**Medical Records** – Store and retrieve patient medical histories.
**Admin Dashboard** – Manage doctors, patients, and appointments efficiently.
**REST APIs** – Clean and modular APIs for all core operations.

---

## Tech Stack

| Layer               | Technology                               |
| ------------------- | ---------------------------------------- |
| **Backend**         | Spring Boot (Java)                       |
| **Security**        | Spring Security, JWT                     |
| **Database**        |  PostgreSQL (via Spring Data JPA) |
| **Validation**      | Jakarta Validation (Hibernate Validator) |
| **Build Tool**      | Maven                                    |
| **Testing**         | JUnit, Mockito                           |
| **Version Control** | Git & GitHub                             |

---

##  Setup & Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/MGhiremath0281/HospitalManagementSystem.git
   cd HospitalManagementSystem
   ```

2. **Configure the Database**

   * Update your `src/main/resources/application.properties`:

     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
     spring.datasource.username=root
     spring.datasource.password=yourpassword
     spring.jpa.hibernate.ddl-auto=update
     ```

3. **Build and Run**

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access the Application**

   * API Base URL: `http://localhost:8080/api`
   * Swagger (if enabled): `http://localhost:8080/swagger-ui.html`

---

##  Roles & Access

| Role        | Access                                             |
| ----------- | -------------------------------------------------- |
| **Admin**   | Full access to doctors, patients, and appointments |
| **Doctor**  | Manage appointments and patient records            |
| **Patient** | Book and view appointments                         |

---

## API Endpoints (Example)

| Endpoint             | Method | Description                    |
| -------------------- | ------ | ------------------------------ |
| `/api/auth/register` | POST   | Register a new user            |
| `/api/auth/login`    | POST   | Authenticate and get JWT token |
| `/api/doctors`       | GET    | List all doctors               |
| `/api/patients`      | GET    | List all patients              |
| `/api/appointments`  | POST   | Create a new appointment       |

---

##  Future Enhancements

*  Pharmacy & Inventory Management
*  Billing System Integration
*  Frontend (React or Angular)
*  Cloud Deployment (AWS / Render / Railway)

---
try to be CI/CD
## Contributing

Contributions are welcome!
To contribute:-

1. Fork this repo
2. Create a new branch (`feature/your-feature-name`)
3. Commit your changes
4. Push to your fork and open a Pull Request

---

##  Author

**Muktananda Hiremath**
 [MGhiremath0281@gmail.com](mailto:MGhiremath0281@gmail.com)
 [GitHub Profile](https://github.com/MGhiremath0281)


