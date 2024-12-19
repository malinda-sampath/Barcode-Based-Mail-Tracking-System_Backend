# Barcode-Based Mail Tracking System

This project implements a barcode-based mail tracking system designed for managing incoming mail within a university administrative unit. The system incorporates multiple user roles—**Super Admin**, **Mail Admin**, and **Branch Agents**—each with specific access levels and functionalities.

## Key Features

- **Super Admin**: 
  - Add and manage mail administrators.
  - Send points and branches to different locations.
  - Perform database backups and recovery.
  - Monitor deleted mail activities and manage the overall system.

- **Mail Admin**: 
  - Enter and update mail details.
  - Generate unique barcodes for each piece of mail.
  - Manage mail records (insert, update, delete).
  - Track the status of claimed and unclaimed mail.

- **Branch Agents**: 
  - View daily received mail.
  - Claim mail by scanning barcode IDs.

- **Urgent Mail Tracing Service**: 
  - Provides email notifications and real-time status updates on specific mail items.

## Technologies Used

- **Backend**: Spring Boot
- **Database**: MySQL
- **Frontend**: React
- **Barcode Generation**: (Optional: Add specific libraries or methods used)
- **Cloud Deployment**: DigitalOcean (for the testing purposes)
- **Version Control**: GitHub
- **Project Management**: Trello

## Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/barcode-mail-tracking.git
