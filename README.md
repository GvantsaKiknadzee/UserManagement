# User Management System

## Description
This project entails a User Management System crafted with Spring Boot, offering APIs for CRUD operations on user data.

## Getting Started
### 1. Configuration of .env File
The initial step is to configure the `.env` file. An example file named [`.env.example`](.env.example) is provided with the following content:
```python
# USERNAME AND PASSWORD FOR POSTGRES
ROOT_USERNAME=EXAMPLE_USERNAME
ROOT_PASSWORD=EXAMPLE_PASSWORD

# POSTGRES CONFIGURATION
POSTGRES_USER=${ROOT_USERNAME}
POSTGRES_PASSWORD=${ROOT_PASSWORD}

# JAVA SPRING CONFIGURATION
SPRING_DATASOURCE_USERNAME=${ROOT_USERNAME}
SPRING_DATASOURCE_PASSWORD=${ROOT_PASSWORD}

# JAVA SPRING SECURITY USERNAME AND PASSWORD
MANAGER_USERNAME=EXAMPLE_USERNAME
MANAGER_PASSWORD=EXAMPLE_PASSWORD
```
Fill in the following fields:
- `ROOT_USERNAME`
- `ROOT_PASSWORD`
- `MANAGER_USERNAME`
- `MANAGER_PASSWORD`

`ROOT_USERNAME` and `ROOT_PASSWORD` are for configuring the `Postgres` Database and will be utilized in Docker Compose and Java Spring Boot. `MANAGER_USERNAME` and `MANAGER_PASSWORD` are credentials for the manager user role in Spring Boot Security, allowing access to endpoints and Swagger documentation.

### 2. Building the Application
After configuring the `.env` file, run the application. Navigate to the directory containing the [docker-compose.yml](docker-compose.yml) file and execute either of the following commands:
```bash
docker compose up
```
or
```bash
docker-compose up
```
`docker compose` is recommended over `docker-compose` as the latter is deprecated.

### 3. Utilizing the User Management System
Once the application is built and running, access the project's documentation via the link: `http://localhost:8082/swagger-ui/index.html`. Use the `MANAGEMENT_USERNAME` and `MANAGEMENT_PASSWORD` configured in the `.env` file to log in and access the documentation and endpoints.

Explore the available features and endpoints in the subsequent sections:

### 4. Features
- Creation of a new user
- Retrieval of all users
- Retrieval of a user by ID
- Retrieval of users by name or email
- Update of user information
- Deletion of a user by ID

### 5. Endpoints
**NOTE: All endpoints require the 'MANAGER' role.**
- **Create a User**
  - **Endpoint:** `POST /users`
  - **Description:** Creates a new user.

- **Retrieve All Users**
  - **Endpoint:** `GET /users`
  - **Description:** Retrieves all users.

- **Retrieve a User by ID**
  - **Endpoint:** `GET /users/{id}`
  - **Description:** Retrieves a user by their ID.

- **Retrieve Users by Name**
  - **Endpoint:** `GET /users/name?name={name}`
  - **Description:** Retrieves users by their name.

- **Retrieve User by Email**
  - **Endpoint:** `GET /users/email?email={email}`
  - **Description:** Retrieves a user by their email.

- **Delete a User**
  - **Endpoint:** `DELETE /users/{id}`
  - **Description:** Deletes a user by their ID.

- **Update a User**
  - **Endpoint:** `PUT /users/{id}`
  - **Description:** Updates user information by their ID.

## Technologies Used
- Java
- Spring Boot
- Spring Security
- Swagger (OpenAPI)
- Lombok
- Javax-Validations
- Model Mapper