# Event-Ticket-Platform
Event-Ticket-Platform is a comprehensive role-based Event Ticket Booking & Validation System that enables seamless event management and secure ticketing. It allows users to browse and book tickets, organizers to create and manage events, staff to validate tickets via QR codes, and administrators to oversee the platform.
This full-stack application combines robust backend services with an interactive frontend and is fully containerized using Docker for consistent deployment.

# 🚀 Key Features

# ✅ User :
Register, login, and manage account
Browse and search events
View detailed event information
Book tickets and receive QR-coded tickets
Access personal booking history

# 🎤 Organizer
Create, update, and manage events
Control ticket availability and allocations
Track event bookings and analytics
Assign staff to manage on-site ticket validation

# 🛂 Staff
Scan and validate tickets via QR codes at event entrances
Verify ticket authenticity and prevent duplicates
Mark tickets as USED in real time
Ensure secure and efficient event entry

# 🛡️ Admin
Manage all users, roles, and permissions
Oversee all events and bookings
Monitor platform activity and generate reports
Maintain full control over system operations

# 🗺️ Architecture Overview

This platform follows a modern full-stack architecture:
Frontend: TypeScript with React / Vue / Angular
Backend: Java Spring Boot (RESTful API)
Authentication: JWT (JSON Web Tokens)
Authorization: Role-based access control (User, Organizer, Staff, Admin)
Database: PostgreSQL
Containerization: Docker & Docker Compose for isolated, reproducible environments

# QR Code Validation
Each ticket is assigned a unique, encrypted QR code, which staff scan at the event. The backend verifies the ticket, checks usage status, and marks it USED upon validation to prevent fraud or duplicate entry.

# 🧠 Ticket Validation Workflow
User books a ticket.
Backend generates a unique ticket ID and QR code.
QR code contains encrypted ticket details.
Staff scans the QR code at the venue.

Backend verifies:
Ticket exists and matches the event and user
Ticket has not been used previously
Valid tickets are marked USED, granting entry.
Duplicate or invalid scans are rejected, ensuring secure validation.

# 🔐 Security Implementation
JWT-based stateless authentication
Role-based access control to enforce permissions
Passwords hashed using BCrypt for security
Endpoint protection via middleware and method-level authorization
Input validation and global exception handling

# 🐳 Docker Deployment
The platform is fully containerized, enabling seamless development and deployment:
Backend (Spring Boot)

Frontend (React)

PostgreSQL Database
