# University of Ruhuna - Mail Tracking System

This project implements a barcode-based mail tracking system to efficiently manage incoming mail within a university administrative unit. The system offers role-based access and functionalities tailored for **Super Admin**, **Mail Admin**, and **Branch Agents**, enhancing accountability and streamlining operations.

---

## Key Features

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
  - Allow users to activate the servide to view detailed tracking information.

---

## System Architecture

### **Technologies Used**

- **Backend**: Spring Boot (RESTful APIs for managing mail operations).
- **Database**: MySQL (normalized schema for efficient storage and retrieval).
- **Frontend**: React (dynamic UI with real-time tracking updates).
- **Barcode Generation**: ZXing library (for creating and reading barcodes).
- **Email Notifications**: Spring Boot Mail (for automated updates).
- **Cloud Deployment**: DigitalOcean (scalable testing and production environment).
- **Version Control**: GitHub (collaborative development).
- **Project Management**: Trello (task tracking and milestones).

### **Key Components**

- **REST API Endpoints**:
  - `/api/super_admin`: Manage administrators.
  - `/api/mail_admin`: CRUD operations for mail records.
  - `/api/branch_agent`: CRUD operations for mail records.
  - `/api/barcode`: Generate and validate barcodes.
  - `/api/tracking`: Fetch status for urgent mail.
- **Database Design**:

---

## Deployment and Setup Instructions

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

## Team Members

| Name                  | Stack/Role                       |
| --------------------- | -------------------------------- |
| G.K. Malinda Sampath  | Group Leader/FullStack Developer |
| L.A.S. Ayeshani       | FullStack Developer              |
| M.R. Hewage           | FullStack Developer              |
| A.M.T.P. Aththanayaka | FullStack Developer              |
| S.S.N. Edirisinghe    | Backend Developer                |
| K.P.G.L.R. Kossinna   | Frontend Developer               |
| D.M.G.C. Sandakelum   | Frontend Developer               |
