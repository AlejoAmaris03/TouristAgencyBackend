# 🌍 Tourist Agency Backend API (Spring Boot)

This is the backend API for a **Tourist Agency Management System** built with **Spring Boot**. The system allows for the management of tourism services and packages, enabling users (customers and employees) to browse, purchase, and manage offerings from a travel agency.

> 💡 You can find the frontend here: [Tourist Agency Frontend (Angular)](https://github.com/AlejoAmaris03/TouristAgencyFrontend)

---

## 📌 Features

- 👥 User management:
  - Roles: **Admin**, **Customer** and **Employee**
  - Secure registration and login (JWT-based)
- 🏞️ Create and manage tourism services (e.g. hotel, transport, excursions)
- 🧳 Create packages that combine multiple services
- 🛒 Customers can purchase services and packages
- 📦 View purchase history by user
- 📄 RESTful API architecture

---

## ⚙️ Tech Stack

- **Java 23+**
- **Spring Boot 3**
  - Spring Web
  - Spring Security (JWT)
  - Spring Data JPA
- **PostgreSQL**
- **Lombok**
- **Maven**

---

## 📁 Project Structure
![image](https://github.com/user-attachments/assets/7537d85b-1241-4603-90a8-4700cced9816)

### Installation

1. **Clone the repo**
   ```bash
   git clone https://github.com/AlejoAmaris03/TouristAgencyBackend.git
   cd TouristAgencyBackend-main

2. **Configure DB in application.properties**
   ```bash
    spring.datasource.username=your_user
    spring.datasource.password=your_password
    jwt.key=your_generated_key
  To generate a secure JWT key, open a terminal (CMD o PowerShell) and run:
  ```bash
    openssl rand -base64 32
  ```
  Copy the generated key and replace *your_generated_key* with it.

4. Run the project

5. The app should be running at: http://localhost:8080

### Example Endpoints
- 🟢 **GET** /touristServices – List all products
- 🟡 **POST** /auth/register – Register new user

### Access with Default Admin Credentials
  > 📝 You can log in with the following default administrator account, which is automatically created when the application starts.

👤 Username: admin  
🔑 Password: admin
 
- This account has full access to manage users, services, packages and sales.
