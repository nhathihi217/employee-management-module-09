# Employee Management - Module 9

Spring Boot REST API lab for Spring Security, role-based authorization, HTTP Basic, and JWT bearer authentication.

## Features

- User records store a unique username, BCrypt password hash, and `USER`/`ADMIN` role.
- Public registration always assigns `USER`; clients cannot grant themselves an elevated role.
- Login returns a signed JWT valid for 15 minutes by default.
- JWT and HTTP Basic authentication are both enabled; the API is stateless.
- `USER` and `ADMIN` may list employees. Only `ADMIN` may create, update, or delete employees.

## Run locally

```bash
mvn spring-boot:run
```

The app uses an in-memory H2 database. To provision an admin account, set credentials before starting it:

```bash
ADMIN_USERNAME=admin ADMIN_PASSWORD='choose-a-strong-password' mvn spring-boot:run
```

The bootstrap account is created only if both variables are supplied and the username does not already exist. Never use development defaults or commit credentials in production. Set `JWT_SECRET` to a cryptographically random secret of at least 32 bytes in every deployed environment.

## API examples

Register a regular user:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H 'Content-Type: application/json' \
  -d '{"username":"alice","password":"a-long-password"}'
```

Login to obtain a JWT:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"alice","password":"a-long-password"}'
```

Use the returned `accessToken` as `Authorization: Bearer <token>`.

- `GET /api/employees` — available to `USER` and `ADMIN`
- `POST /api/employees` — `ADMIN` only
- `PUT /api/employees/{id}` — `ADMIN` only
- `DELETE /api/employees/{id}` — `ADMIN` only

Employee request body:

```json
{"name":"Nguyen Van A","email":"a@example.com"}
```

## Notes

The included HMAC JWT secret is for local learning only. Replace it with a strong secret from a secret manager before deployment; use HTTPS in real environments. This lab's in-memory database resets when the process stops.
