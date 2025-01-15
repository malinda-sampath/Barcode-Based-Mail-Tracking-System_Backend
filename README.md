# **University of Ruhuna - Mail Tracking System**

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

## **Security Considerations**

- **Password Hashing**: All user passwords are securely hashed using industry-standard algorithms to ensure the privacy of credentials.
- **Role-Based Access Control (RBAC)**: Ensures that each user has appropriate access to system features based on their role, preventing unauthorized actions.
- **Data Encryption**: Sensitive data is transmitted securely using SSL/TLS encryption to protect it during communication.

---

## **System Architecture**

### **Technologies Used**

- **Backend**: Spring Boot (RESTful APIs for managing mail operations).
- **Database**: MySQL hosted on a DigitalOcean droplet (normalized schema for efficient storage and retrieval).
- **Frontend**: React with Tailwind CSS (dynamic UI with real-time tracking updates).
- **Version Control**: GitHub (collaborative development).
- **Project Management**: Trello (task tracking and milestones).

---

## **Scalability**

The system is designed to handle a growing volume of mail records efficiently. Key measures include:

- **Optimized Database**: The MySQL database is optimized for scaling and can be expanded to accommodate additional campuses or high mail volumes.
- **Modular Design**: The system architecture allows for future feature additions and the ability to scale for more users or branches.

---

## **Testing**

The system undergoes rigorous testing to ensure reliability:

- **Unit Testing**: JUnit and Mockito are used to test individual components of the backend.
- **Integration Testing**: Postman is used to validate API interactions between the backend and frontend.
- **User Acceptance Testing**: Ensures the system meets all user requirements and functions as expected in real-world conditions.

---

## **Future Features**

- **Real-Time Notifications**: Stakeholders could receive push notifications or SMS alerts for urgent mail updates.
- **Advanced Analytics**: Provide administrators with insights into mail processing trends, including peak mail volume periods, agent performance metrics, and branch efficiency.
- **Mobile App Integration**: A mobile app could provide real-time access to mail tracking services, allowing users to track and claim mail on the go.

---

## **Deployment and Setup Instructions**

### **Local Setup**

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/malinda-sampath/Barcode-Based-Mail-Tracking-System.git
   ```

2. **Configure Database**:

   - Set up a MySQL database and update connection properties in `application.properties`:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/mail_tracking
     spring.datasource.username=yourusername
     spring.datasource.password=yourpassword
     ```

3. **Run the Backend**:

   ```bash
   ./mvnw spring-boot:run
   ```

4. **Launch the Frontend**:

   ```bash
   npm install
   npm start
   ```

---

## **Team Members**

| Name                  | Stack/Role                       |
| --------------------- | -------------------------------- |
| G.K. Malinda Sampath  | Group Leader/FullStack Developer |
| L.A.S. Ayeshani       | FullStack Developer              |
| M.R. Hewage           | FullStack Developer              |
| A.M.T.P. Aththanayaka | FullStack Developer              |
| S.S.N. Edirisinghe    | Backend Developer                |
| K.P.G.L.R. Kossinna   | Frontend Developer               |
| D.M.G.C. Sandakelum   | Frontend Developer               |

---
