# Document Management REST API

A Spring Boot REST API for managing document metadata (create, read, update,
delete and search). Built as a personal project to practise Java backend
development, inspired by content management systems.

## Tech stack
- Java 17
- Spring Boot 4.1.2 (Spring Web, Spring Data JPA, Validation)
- H2 in-memory SQL database
- Maven
- JUnit 5 and Mockito

## Architecture
Client -> Controller -> Service -> Repository -> H2 database

- **Controller**: handles HTTP requests/responses and JSON conversion
- **Service**: business logic (e.g. 404 handling, update rules)
- **Repository**: database access through Spring Data JPA
- **Model**: `Document` entity and `DocumentStatus` enum
- **GlobalExceptionHandler**: converts exceptions into clean JSON errors

## Running the project
Requirements: JDK 17+

    .\mvnw.cmd spring-boot:run      (Windows)
    ./mvnw spring-boot:run          (Mac/Linux)

The API runs at http://localhost:8080

## Endpoints
| Method | URL | Description | Success |
|---|---|---|---|
| POST | /api/documents | Create a document | 201 |
| GET | /api/documents | List all documents | 200 |
| GET | /api/documents?title=text | Search by title (case-insensitive) | 200 |
| GET | /api/documents/{id} | Get one document | 200 |
| PUT | /api/documents/{id} | Update a document | 200 |
| DELETE | /api/documents/{id} | Delete a document | 204 |

Errors: 404 if the id does not exist, 400 if validation fails.

### Example request
POST /api/documents

    {
      "title": "Q3 Report",
      "fileType": "PDF",
      "description": "Quarterly report",
      "status": "DRAFT"
    }

`status` must be DRAFT, PUBLISHED or ARCHIVED.

### Example response (201 Created)

    {
      "id": 1,
      "title": "Q3 Report",
      "fileType": "PDF",
      "description": "Quarterly report",
      "status": "DRAFT",
      "createdAt": "2026-10-27T20:40:42.301229"
    }

## Testing
    .\mvnw.cmd test

- Unit tests for the service layer (Mockito)
- API tests using MockMvc (create, validation error, not found)
- Manual testing with Postman; screenshots in `docs/screenshots/`

## Limitations / future improvements
- H2 is in-memory, so data resets on restart (would use PostgreSQL)
- No authentication or authorization
- Stores metadata only, not the actual files
- Entity used directly as the request body (would use DTOs)
- No pagination