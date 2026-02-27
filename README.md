# Event-Ticket-Platform
A role-based Event Ticket Booking & Validation System that enables users to browse and book tickets, organizers to create and manage events, staff to validate QR tickets, and admins to oversee the platform.

This project includes both backend and frontend components, with secure authentication and real-time QR code validation.

🚀 Features
✅ User Role

Register & login

Browse available events

View event details
Book tickets

Receive QR-coded tickets

View booking history

🎤 Organizer Role

Create new events

Edit/Update own events

Manage ticket availability

View bookings for events they created
Login with staff credentials

Scan scanned tickets’ QR codes at event entrance

Validate ticket legitimacy

Mark tickets as used

Prevent reused or fraudulent access

🛡️ Admin Role

Manage all users & roles

Manage all events

View all tickets and bookingsFull platform control

🗺️ Architecture Overview

This project uses a full-stack architecture with distinct frontend and backend:

Frontend: TypeScript (React/Vue/Angular depending on your implementation)

Backend: Java Spring Boot (REST API)

Authentication: JWT (JSON Web Tokens)

Authorization: Role-based access control (User, Organizer, Staff, Admin)

Database: Relational database (e.g., MySQL/PostgreSQL)

QR Code Validation: Unique encrypted QR for each ticket, scanned and verified by staff
🧠 How QR Validation Works

A user books a ticket.

The backend generates a unique ticket ID and QR code.

The QR code encodes encrypted ticket information.

At the event entrance, a Staff user scans the QR.

The backend verifies:

The ticket is valid

The ticket belongs to that event and user

The ticket hasn’t been used before

If valid, the ticket is marked USED and entry allowed.

Duplicate or invalid scans are rejected.
🔐 Security & Roles

JWT Authentication secures all API endpoints.

Role-Based Authorization controls access per endpoint.

Sensitive data (passwords) is hashed using BCrypt.

Endpoints are protected by middleware and method-level permissions.

Assign staff to assist at events

🛂 Staff Role
