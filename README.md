# **University of Ruhuna - Mail Tracking System(BackEnd)**

## **Executive Summary**

The University of Ruhuna's Mail Tracking System is designed to streamline the management of incoming mail within university administrative units. By utilizing a barcode-based tracking mechanism, the system enhances mail processing efficiency, accountability, and real-time tracking for stakeholders. The system is tailored for role-based access, enabling Super Admins, Mail Admins, and Branch Agents to seamlessly collaborate while ensuring comprehensive oversight and security. Additionally, the system provides an urgent mail tracing service, delivering timely updates to faculty and students.

---

## **Key Features**

### 1. **Super Admin**

- **User Management**: Add, update, or remove mail administrators and branches.
- **System Oversight**:
  - Monitor mail activities, including claimed/unclaimed and deleted records.
  - View audit logs for better traceability.
- **Data Management**:
  - Perform regular database backups and initiate recovery when needed.
- **Performance Metrics**:
  - Access statistics such as mail volume per branch, processing times, and agent performance.

### 2. **Mail Admin**

- **Mail Management**:
  - Record incoming mail details and assign to branches.
  - Generate unique barcodes for each mail item using a barcode generation library (e.g., ZXing).
  - Manage mail lifecycle (insert, update, delete records).
- **Tracking**:
  - Monitor claimed/unclaimed mail and resend notifications for pending items.
- **Operational Dashboard**:
  - View branch-wise distribution, mail history, and service delays.

### 3. **Branch Agents**

- **Daily Operations**:
  - View daily mail list categorized by urgency.
  - Claim mail by scanning or entering the corresponding barcode ID.
- **Acknowledgment**:
  - Confirm mail receipt to update the central system.

### 4. **Urgent Mail Tracing Service**

- **Real-Time Updates**:
  - Provide stakeholders (faculty/students) with email notifications and live status updates for urgent mail.
- **Tracking Portal**:
  - Allow users to activate the mail tracking service to view detailed tracking information.

---

## **User Roles and Permissions**

The system implements role-based access control (RBAC) to ensure that users can only access the features necessary for their responsibilities:

- **Super Admin**: Full system control, including user management, oversight, and data management.
- **Mail Admin**: Responsible for managing mail records, generating barcodes, and overseeing operational workflows.
- **Branch Agents**: Access to view and claim mail, as well as confirm receipt.

---

## **Backend System Architecture**

### **Technologies Used**

- **Backend Framework**: Spring Boot (RESTful APIs for managing mail operations).
- **Database**: MySQL hosted on a DigitalOcean droplet (normalized schema for efficient storage and retrieval).
- **Authentication**: Spring Security (JWT-based authentication and authorization).
- **Barcode Generation**: ZXing library to generate unique barcodes for incoming mail items.
- **Version Control**: GitHub (collaborative development).

### **Architecture Highlights**

1. **Modular Design**: The backend adheres to a layered architecture with distinct service, repository, and controller layers.
2. **RESTful APIs**:
   - Endpoints designed for seamless integration with the React frontend.
   - API documentation generated using Swagger/OpenAPI.
3. **Database Design**:
   - Relational schema with tables for users, mail records, roles, branches, and audit logs.
   - Optimized indexing for high-performance queries.
4. **Logging and Monitoring**:
   - Integrated SLF4J and Logback for application logging.
   - Custom audit logging to track critical system operations.
5. **Asynchronous Operations**:
   - Email notifications sent asynchronously to avoid blocking workflows.
6. **Data Validation**:
   - Backend validations using Hibernate Validator to ensure data integrity.

---

## **Security Considerations**

- **Password Hashing**: All user passwords are securely hashed using BCrypt to ensure the privacy of credentials.
- **JWT Authentication**: Implements token-based authentication for secure access to APIs.
- **Role-Based Access Control (RBAC)**: Ensures that each user has appropriate access to system features based on their role, preventing unauthorized actions.
- **Data Encryption**: Sensitive data is transmitted securely using SSL/TLS encryption to protect it during communication.
- **Regular Audits**: Logs critical operations and monitors access patterns to detect potential security breaches.

---

## **Scalability**

The backend system is designed to handle a growing volume of mail records efficiently. Key measures include:

- **Optimized Database**: The MySQL database is normalized and indexed for scalable performance, with future provisions for distributed storage if needed.
- **Asynchronous Processing**: Resource-intensive tasks like email notifications are processed asynchronously to improve throughput.
- **API Gateway**: The system can integrate with an API gateway for future expansion, enabling load balancing and enhanced API security.

---

## **Testing**

- **Unit Testing**: JUnit and Mockito are used to test individual backend components and ensure robustness.
- **Integration Testing**: Postman is used to validate RESTful API interactions.
- **Performance Testing**: Conducted using JMeter to evaluate the system’s performance under load.
- **Security Testing**: Penetration tests performed to identify and address vulnerabilities.

---

## **Future Enhancements**

- **Advanced Analytics**:
  - Enhance dashboards so that super admins can view metrics like average mail processing time, branch performance, and user activity.
- **Real-Time Notifications**:
  - Implement push notifications and SMS alerts for urgent mail updates.
- **Mobile App Integration**:
  - Develop a mobile-friendly version of the mail tracking system for better accessibility.
---

## **Deployment Instructions**

### **Backend Setup**

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/malinda-sampath/Barcode-Based-Mail-Tracking-System.git
   ```

2. **Configure Application Properties**:

   Update the `application.properties` file with your database credentials:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/mail_tracking
   spring.datasource.username=yourusername
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. **Run the Backend**:

   Navigate to the project directory and execute:

   ```bash
   ./mvnw spring-boot:run
   ```

4. **Database Setup**:

   - Create a MySQL database named `mail_tracking`.
   - The application will automatically initialize the schema on startup.

5. **Verify API Endpoints**:

   - Use Postman or a similar tool to test the backend APIs.

---

## **Team Members**

| Name                  | Role                             |
| --------------------- | -------------------------------- |
| G.K. Malinda Sampath  | FullStack Developer/Team Lead    |
| L.A.S. Ayeshani       | FullStack Developer              |
| M.R. Hewage           | FullStack Developer              |
| A.M.T.P. Aththanayaka | FullStack Developer              |
| S.S.N. Edirisinghe    | Backend Developer                |
| K.P.G.L.R. Kossinna   | Frontend Developer               |
| D.M.G.C. Sandakelum   | Frontend Developer               | 

---

