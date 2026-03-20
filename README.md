# Clinic Booking Management System

A Java EE web application for managing clinic appointments, doctor scheduling, patient records, and payments. Built as an academic project (SWP391 — FPT University, Group BL5 E1 Group 1), the system covers the full operational lifecycle of a small clinic: patient self-booking, receptionist check-in, doctor queue management, manager analytics, and online payment via VNPay.

---

## Table of Contents

- [Features by Role](#features-by-role)
- [Tech Stack](#tech-stack)
- [Requirements](#requirements)
- [Setup & Installation](#setup--installation)
- [Architecture & Coding Patterns](#architecture--coding-patterns)
- [Database Schema](#database-schema)
- [URL Routes](#url-routes)
- [Known Issues & Technical Debt](#known-issues--technical-debt)

---

## Features by Role

**Patient**
- Register with email OTP verification, or sign in with Google OAuth2
- Browse examination packages (Normal / VIP tiers per specialty)
- Filter available doctors by specialty, date, and time slot
- Book, update, and cancel appointments
- Pay online via VNPay (sandbox) during booking
- View appointment history, medical history, and invoices

**Receptionist**
- View and search the daily appointment list
- Check patients in (walk-in or pre-booked) and update appointment status
- Manage patient records

**Doctor**
- View today's patient queue with the current time slot surfaced automatically
- Access patient medical records at the point of care
- Write diagnosis, prescription, and notes per visit
- Transfer (hand off) a patient to another doctor
- Manage weekly schedule (slots per day, max appointments per slot)
- Mark specific dates as unavailable (sick leave, conference, etc.)

**Manager**
- Dashboard analytics: total patients, total revenue, appointment counts
- Charts: appointments by doctor, bookings by time slot, patient demographics
- Manage staff accounts (add / edit / deactivate)
- Manage examination packages (create, update, assign tiers)
- Manage doctor–specialty assignments
- View and adjust all time slot configurations

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17+ |
| Runtime | Jakarta EE 10 / Servlet 6.0 |
| Build | Apache Ant (NetBeans project) |
| Server | Apache Tomcat 10.x |
| Database | MySQL 8.x |
| JDBC Driver | `mysql-connector-j-9.3.0.jar` |
| Auth | Session-based + Google OAuth2 |
| Payment | VNPay (sandbox) |
| Front-end | JSP, JSTL, Bootstrap 5, ApexCharts, FullCalendar, Flatpickr, Select2, Slick |
| Email | Jakarta Mail 1.6.7 |
| JSON | Gson 2.9.0, Jackson Databind 2.18.3 |
| HTTP Client | Apache HttpClient 4.5.13 |

---

## Requirements

- **JDK 17** or later
- **Apache Tomcat 10.x** (required — the project uses `jakarta.*` namespaces from Jakarta EE 10)
- **MySQL 8.x** on `localhost:3306`
- **Apache NetBeans 19+** or any IDE that supports Ant-based Jakarta EE projects

---

## Setup & Installation

### 1. Database

Create the database and run the schema:

```sql
CREATE DATABASE swp_clinic;
USE swp_clinic;
-- then execute:
-- MySQL_Clinic_Database_LastChange_25_4.sql
```

Optionally load test data (includes Dr. House, Wilson, Cameron, Chase, Foreman and sample appointments across 2024–2025):

```
fill_mock_data_for_testing.sql
```

The seeded password hash (`827ccb0eea8a706c4c34a16891f84e7b`) is MD5 of `12345`. Log in as any seeded user with password `12345`.

### 2. Dependencies — `lib_jar/`

The `lib_jar/` directory was removed from the repository. Create it at `SWP_BL5_TEAM1/../lib_jar/` (one level above the project folder) and add the following JARs:

| JAR | Source / Maven Coordinates |
|---|---|
| `mysql-connector-j-9.3.0.jar` | `com.mysql:mysql-connector-j:9.3.0` |
| `jakarta.jakartaee-api-8.0.0.jar` | `jakarta.platform:jakarta.jakartaee-api:8.0.0` |
| `jakarta.servlet.jsp.jstl-2.0.0.jar` | `org.glassfish.web:jakarta.servlet.jsp.jstl:2.0.0` |
| `jakarta.servlet.jsp.jstl-api-2.0.0.jar` | `jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:2.0.0` |
| `jakarta.mail-1.6.7.jar` | `com.sun.mail:jakarta.mail:1.6.7` |
| `gson-2.9.0.jar` | `com.google.code.gson:gson:2.9.0` |
| `jackson-databind-2.18.3.jar` | `com.fasterxml.jackson.core:jackson-databind:2.18.3` |
| `jackson-core-2.19.0-rc2.jar` | `com.fasterxml.jackson.core:jackson-core:2.19.0-rc2` |
| `jackson-annotations-3.0-rc3.jar` | `com.fasterxml.jackson.core:jackson-annotations:3.0-rc3` |
| `google-api-client-2.7.2.jar` | `com.google.api-client:google-api-client:2.7.2` |
| `google-auth-library-oauth2-http-1.33.1.jar` | `com.google.auth:google-auth-library-oauth2-http:1.33.1` |
| `google-auth-library-credentials-1.33.1.jar` | `com.google.auth:google-auth-library-credentials:1.33.1` |
| `google-http-client-1.46.3.jar` | `com.google.http-client:google-http-client:1.46.3` |
| `google-http-client-jackson2-1.46.3.jar` | `com.google.http-client:google-http-client-jackson2:1.46.3` |
| `google-oauth-client-1.39.0.jar` | `com.google.oauth-client:google-oauth-client:1.39.0` |
| `httpclient-4.5.13.jar` | `org.apache.httpcomponents:httpclient:4.5.13` |
| `httpcore-4.4.13.jar` | `org.apache.httpcomponents:httpcore:4.4.13` |
| `commons-logging-1.2.jar` | `commons-logging:commons-logging:1.2` |
| `commons-fileupload2-core-2.0.0-M2.jar` | `org.apache.commons:commons-fileupload2-core:2.0.0-M2` |
| `poi-3.15.jar` + `poi-ooxml-3.16.jar` + `poi-ooxml-schemas-3.16.jar` | `org.apache.poi:poi:3.15` etc. |
| `xmlbeans-2.3.0.jar` + `commons-collections4-4.1.jar` | POI dependencies |
| `json-20210307.jar` | `org.json:json:20210307` |
| `activation-1.1.1.jar` + `javax.activation-1.2.0.jar` | JavaMail activation |
| `jaxb-api-2.3.1.jar` + `jaxb-runtime-2.3.1.jar` | `jakarta.xml.bind:jakarta.xml.bind-api:2.3.1` |
| `core-3.4.1.jar` + `javase-3.5.3.jar` | ZXing (QR codes): `com.google.zxing:core:3.4.1` |

### 3. Configuration

**Database** — `src/java/dal/DBContext.java`:
```java
private static final String DB_HOST     = "localhost";
private static final String DB_PORT     = "3306";
private static final String DB_NAME     = "swp_clinic";
private static final String DB_USER     = "root";
private static final String DB_PASSWORD = "";   // ← set your password
```

**Google OAuth2** — `src/java/constant/Iconstant.java`:
```java
public static final String GOOGLE_CLIENT_ID     = "YOUR_CLIENT_ID";
public static final String GOOGLE_CLIENT_SECRET = "YOUR_CLIENT_SECRET";
public static final String GOOGLE_REDIRECT_URI  = "http://localhost:8080/SWP_BL5_TEAM1/login";
```
Replace with credentials from the [Google Cloud Console](https://console.cloud.google.com/). Add `http://localhost:8080/SWP_BL5_TEAM1/login` as an authorised redirect URI.

**VNPay** — `src/java/controller/patient/Config.java`:
```java
public static String vnp_TmnCode  = "4YUP19I4";           // sandbox terminal code
public static String secretKey    = "MDUIFDCRA...";        // sandbox HMAC-SHA512 key
public static String vnp_ReturnUrl = "http://localhost:8080/SWP_BL5_TEAM1/vnpayReturn";
```
The project ships with VNPay sandbox credentials. Replace with live keys from the VNPay merchant portal for production.

**Email (OTP)** — `src/java/Service/MailService.java`:  
The entire class body is currently commented out. To enable OTP verification emails, uncomment the class and supply credentials:
```java
final String usermail = "your-email@gmail.com";
final String password = "your-16-char-app-password"; // Gmail App Password
```
The `jakarta.mail-1.6.7.jar` dependency is already declared and ready.

### 4. Running

1. Open `SWP_BL5_TEAM1` in NetBeans as an existing project.
2. In Project Properties → Run, set the server to **Apache Tomcat 10.x**.
3. Clean and Build, then Run.
4. Open `http://localhost:8080/SWP_BL5_TEAM1/`

---

## Architecture & Coding Patterns

### MVC via Plain Servlets

The project uses a classic Jakarta Servlet MVC pattern with no framework:

- **Model** — plain Java beans in `model/`, one class per database table.
- **DAO** — all SQL is in `dal/`. Every DAO extends `DBContext`, which opens a raw JDBC `Connection` in its constructor. No ORM, no prepared statement caching.
- **DTO** — `dto/` classes carry multi-table query results to JSPs without exposing raw model internals.
- **Controller** — servlets read parameters, call DAOs, set request attributes, then `forward()` to a JSP.
- **View** — JSP with JSTL + EL, plus Bootstrap 5 and JS charting libraries.

### Copy-Paste Role Guard Pattern

There is no authentication filter. Every protected servlet opens with the same three-step block:

```java
// Step 1 — is the user logged in?
User loggedInUser = (User) request.getSession().getAttribute("user");
if (loggedInUser == null) { response.sendRedirect("/SWP_BL5_TEAM1/login"); return; }

// Step 2 — are they staff at all?
StaffAccount staffAccount = staffAccountDAO.getStaffByUserId(loggedInUser.getUserId());
if (staffAccount == null) { response.sendRedirect("/SWP_BL5_TEAM1/login"); return; }

// Step 3 — do they have the right role?
if (!"Doctor".equalsIgnoreCase(staffAccount.getRole())) {
    request.setAttribute("errorMessage", "Access denied.");
    request.getRequestDispatcher("/error.jsp").forward(request, response);
    return;
}
```

This block is duplicated verbatim across every role-protected servlet. A `Filter` implementation would centralise this logic.

### `service` Parameter Dispatch

Several servlets handle multiple actions through one URL by reading a `service` request parameter:

```java
// LoginServlet at /login
switch (service) {
    case "googleLogin": handleGoogleLogin(...); break;
    case "loginUser":   handleUserLogin(...);   break;
    default:            forward to login.jsp;
}
```

`RegisterUser`, `ProfileServlet`, and a few others use the same convention, effectively making one servlet URL act as a mini-router.

### One Connection Per DAO Per Request

`DBContext` opens `DriverManager.getConnection()` in its constructor. Servlets instantiate fresh DAO objects, use them, then call `DAOUtils.disconnectAll(dao1, dao2, ...)` before the response is sent. This means one JDBC connection is opened and closed per DAO per request. No connection pool is used — under concurrent load this can exhaust MySQL's connection limit.

### Password Hashing

Passwords are stored as plain **MD5** hashes via `MD5Util.getMD5Hash()`. A salted SHA-1 class (`util/Encryption.java`) also exists but is not used for password storage in the current code. MD5 should be replaced with BCrypt or Argon2 before any real deployment.

### VNPay Payment Flow

1. Patient selects "Pay Now" → `PaymentBookingServlet.doPost()` builds a signed URL using HMAC-SHA512 (`Config.hmacSHA512`) and redirects the browser to the VNPay sandbox gateway.
2. After payment, VNPay calls back to `VnpayReturnBooking` at `/vnpayReturn`.
3. The callback re-signs the returned fields and compares the signature. On match, it parses the transaction reference (format: `paymentID_timestamp`), updates the `payment` and `appointment` tables, and renders `paymentResult.jsp`.

### Google OAuth2 Login

`GoogleLogin.java` performs the authorization code → access token exchange manually using Apache HttpClient (no Google SDK for the exchange itself). The `Iconstant.java` file stores credentials as `public static final` constants — **the client secret is committed in plaintext**. Move this to a server-side config file or environment variable before deployment.

---

## Database Schema

Database name: `swp_clinic`

```

**Appointment status lifecycle:**
```
Pending → Approved → Waiting → In progress → Waiting-Payment → Payment-Complete
                                            → Completed
        → Cancelled
        → On-hand-off → Back-from-hand-off
```

**Notable design decisions:**

- `Patient.phone` is the primary key. This allows walk-in patients (no user account) to be created with just a phone number. Registered patients link back via `patient_account_id`.
- `MedicalRecord` is associated with `patient_phone` rather than `appointment_id`. This means a doctor sees the patient's full cumulative history regardless of which appointment generated each record.
- Two invoice tables exist side-by-side: the normalised `Invoice`/`InvoiceItem` pair (flexible line items) and a denormalised `invoices` table added later with exactly three hardcoded item columns — added for a fixed PDF invoice template.
- `TimeSlot` has an `is_active` flag, but slot 5 (`Night1`, 16:00–18:00) is seeded as inactive and rarely referenced in doctor schedules.

---

## URL Routes

| URL Pattern | Servlet | Role |
|---|---|---|
| `/login` | `LoginServlet` | All |
| `/User?service=registerUser` | `RegisterUser` | All |
| `/activate` | `ActivateServlet` | All |
| `/logout` | `LogoutServlet` | All |
| `/profile` | `ProfileServlet` | All |
| `/home` | `HomePageServlet` | All |
| `/patientBookAppointment` | `PatientBookAppointmentServlet` | Patient |
| `/patientExaminationPackage` | `PatientExaminationPackageSelectServlet` | Patient |
| `/doctorBySlot` | `DoctorBySlotServlet` | Patient (AJAX) |
| `/patientAppointments` | `PatientAppointmentsListServlet` | Patient |
| `/deleteAppointment` | `DeleteAppointmentServlet` | Patient |
| `/updateAppointment` | `UpdateAppointmentServlet` | Patient |
| `/patientQueue` | `PatientQueueViewer` | Patient |
| `/paymentBooking` | `PaymentBookingServlet` | Patient |
| `/vnpayReturn` | `VnpayReturnBooking` | VNPay callback |
| `/receptionAppointments` | `ReceptionAppointmentsListServlet` | Receptionist |
| `/patientCheckin` | `PatientCheckin` | Receptionist |
| `/patientList` | `PatientListServlet` | Receptionist |
| `/appointmentManager` | `AppointmentManager` | Receptionist |
| `/queueManager` | `QueueManager` | Doctor |
| `/doctorAppointmentDetail` | `DoctorAppointmentDetail` | Doctor |
| `/doctorTimeSlotList` | `DoctorTimeSlotListServlet` | Doctor |
| `/updateDoctorTimeSlot` | `UpdateDoctorTimeSlotServlet` | Doctor |
| `/addUnavailability` | `AddUnavailabilityServlet` | Doctor |
| `/removeUnavailability` | `RemoveUnavailabilityServlet` | Doctor |
| `/medicalRecords` | `MedicalRecordsServlet` | Doctor |
| `/transferPatient` | `TransferPatient` | Doctor |
| `/managerAnalytic` | `ManagerAnalyticServlet` | Manager |
| `/managerAppointmentAnalytic` | `ManagerAppointmentAnalyticServlet` | Manager |
| `/accountManager` | `AccountManager` | Manager |
| `/staffManager` | `StaffManager` | Manager |
| `/examinationPackageManager` | `ExaminationPackageManager` | Manager |
| `/managerTimeSlotList` | `ManagerTimeSlotListServlet` | Manager |
| `/insertDoctorAssignment` | `InsertDoctorAssignmentServlet` | Manager |
| `/updateDoctorAssignment` | `UpdateDoctorAssignmentServlet` | Manager |
| `/deleteDoctorAssignment` | `DeleteDoctorAssignmentServlet` | Manager |

---

## Known Issues & Technical Debt

**Security:**
- The Google OAuth2 `GOOGLE_CLIENT_SECRET` is hardcoded as a `public static final` constant and committed to source control. Move to an environment variable or a gitignored properties file immediately.
- VNPay sandbox keys are similarly hardcoded in `Config.java`.
- Passwords are stored as plain MD5 hashes with no salt. MD5 is not a suitable password hashing algorithm. Migrate to BCrypt, Argon2, or PBKDF2 before any production use.
- There is no `Filter` for authentication or RBAC. The three-step role guard is copy-pasted into every protected servlet — a missing guard in any new servlet leaves that endpoint unprotected.

**Architecture:**
- Two parallel servlet packages: `controller` (primary, ~40 servlets registered in `web.xml`) and `controllers` (plural — 4 servlets, **not registered** in `web.xml`). The plural package appears to be an abandoned refactor attempt or a parallel development branch that was never merged. The two `LogoutServlet`/`LogoutController` classes describe the same action independently.
- Three overlapping User DAOs: `DAOUser`, `UsersDAO`, and `UserDAO`. Each has slightly different method sets for the same `User` table.
- Three duplicate component directories for JSP includes: `web/component/`, `web/components/`, and `web/cpn/` — each containing versions of `header.jsp` and `footer.jsp`. JSPs likely import from different directories depending on who wrote them, producing inconsistent UI between pages.
- No connection pooling. One JDBC connection is opened and closed per DAO per request.
- `MailService.java` is entirely commented out, so OTP-based email verification and password reset are non-functional as-is. The `jakarta.mail` dependency is present; the class just needs to be uncommented and configured.

**Database & SQL:**
- `DAOPayment.getLatestpayment_id()` uses `SELECT TOP 1` — SQL Server syntax — instead of MySQL's `LIMIT 1`. This will throw a `SQLSyntaxErrorException` at runtime.
- Two invoice tables (`Invoice`/`InvoiceItem` and `invoices`) serve the same conceptual purpose with different schemas. The `invoices` table hardcodes exactly three line-item columns, making it inflexible for any invoice that needs more or fewer items.
- `Appointment.status` ENUM was `ALTER TABLE`-d multiple times in the schema script. Old status values like `No-show` and `Waiting payment` appear in some code paths while the current ENUM uses `Pending`, `Waiting-Payment`, `Payment-Complete`, etc. Ensure all servlet and DAO code uses only the final ENUM values.
