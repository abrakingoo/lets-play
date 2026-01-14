# Let's Play - RESTful CRUD API

A secure RESTful API built with Spring Boot and MongoDB for managing users and products with JWT-based authentication and role-based access control.

## Features

- ✅ RESTful CRUD operations for Users and Products
- ✅ JWT-based authentication and authorization
- ✅ Role-based access control (ADMIN and USER roles)
- ✅ BCrypt password hashing
- ✅ MongoDB injection prevention
- ✅ Comprehensive error handling
- ✅ Input validation and sanitization

## Tech Stack

- **Framework**: Spring Boot 4.0.1
- **Database**: MongoDB
- **Security**: Spring Security + JWT (jjwt 0.12.5)
- **Validation**: Jakarta Validation
- **Build Tool**: Maven
- **Java Version**: 21

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- MongoDB Atlas account or local MongoDB instance

## Setup Instructions

### 1. Clone the repository
```bash
git clone https://learn.zone01kisumu.ke/git/abrakingoo/lets-play
cd lets-play
```

### 2. Configure environment variables
Create a `.env` file in the project root:
```properties
MONGO_USERNAME=your_mongodb_username
MONGO_PASSWORD=your_mongodb_password
JWT_SECRET_STRING=your_jwt_secret_key_min_32_characters
```

### 3. Build the project
```bash
mvn clean install
```

### 4. Run the application
```bash
mvn spring-boot:run
```

The API will start on `http://localhost:8080`

## API Endpoints

### Authentication Endpoints

#### Sign Up
```http
POST /auth/signup
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
```

#### Sign In
```http
POST /auth/signin
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

#### Logout
```http
POST /auth/logout
Authorization: Bearer <token>
```

### Product Endpoints

#### Get All Products (Public)
```http
GET /products
```

#### Get Product by ID (Public)
```http
GET /products/{id}
```

#### Create Product (Authenticated)
```http
POST /products
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Product Name",
  "description": "Product Description",
  "price": 99.99
}
```

#### Update Product (Owner or Admin)
```http
PUT /products/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Updated Name",
  "description": "Updated Description",
  "price": 149.99
}
```

#### Delete Product (Owner or Admin)
```http
DELETE /products/{id}
Authorization: Bearer <token>
```

### User Endpoints (Admin Only)

#### Get All Users
```http
GET /users
Authorization: Bearer <token>
```

#### Get User by ID
```http
GET /users/{id}
Authorization: Bearer <token>
```

#### Delete User
```http
DELETE /users/{id}
Authorization: Bearer <token>
```

## Security Features

### Password Security
- Passwords are hashed using BCrypt before storage
- Minimum password length: 8 characters
- Passwords never appear in API responses

### JWT Authentication
- Token-based stateless authentication
- Tokens expire after 3 hours
- Token blacklist for logout functionality

### Input Sanitization
- All user inputs are sanitized to prevent MongoDB injection
- Special characters like `$`, `{`, `}` are removed

### Role-Based Access Control
- **USER**: Can create products and manage their own products
- **ADMIN**: Can manage all users and all products

## Error Handling

The API returns appropriate HTTP status codes:

- `200 OK` - Successful GET/DELETE requests
- `201 Created` - Successful POST/PUT requests
- `400 Bad Request` - Validation errors or invalid input
- `401 Unauthorized` - Missing or invalid authentication
- `403 Forbidden` - Insufficient permissions
- `404 Not Found` - Resource not found
- `409 Conflict` - Duplicate resource (e.g., email already exists)
- `500 Internal Server Error` - Server errors (handled globally)

### Error Response Format
```json
{
  "status": 400,
  "error": "Error message"
}
```

### Validation Error Format
```json
{
  "status": 400,
  "errors": {
    "field1": "Error message 1",
    "field2": "Error message 2"
  }
}
```

## Database Schema

### User Collection
```json
{
  "_id": "uuid",
  "name": "string",
  "email": "string",
  "password": "string (hashed)",
  "role": "USER | ADMIN"
}
```

### Product Collection
```json
{
  "_id": "uuid",
  "name": "string",
  "description": "string",
  "price": "double",
  "ownerId": "string (user id)"
}
```

## Testing

Run tests with:
```bash
mvn test
```

## Production Deployment

### HTTPS Configuration
Uncomment HTTPS settings in `application.properties` and provide SSL certificate:
```properties
server.ssl.enabled=true
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=${SSL_PASSWORD}
server.ssl.key-store-type=PKCS12
server.port=8443
```

### Environment Variables
Ensure all sensitive data is stored in environment variables, never in code.

## Project Structure
```
src/main/java/abu/lets_play/
├── Config/              # Security and validation configuration
├── Controller/          # REST controllers
├── Model/
│   ├── Entity/         # MongoDB entities
│   ├── dto/            # Data transfer objects
│   └── Enums/          # Enumerations
├── Repository/         # MongoDB repositories
├── Security/           # JWT utilities and input sanitization
└── Service/            # Business logic
```

## License

This project is for educational purposes.

## Author

Abu
