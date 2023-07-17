# ZERAKI API

This repository contains the documentation for a Spring Boot API that provides endpoints for managing institutions, courses, and students.


## Getting Started

To get started with the API, follow these steps:

1. Clone the repository:

```shell
git clone https://github.com/your/repository.git
```

2. Navigate to the project directory:

```shell
cd project-directory
```

3. Build the project:

```shell
./mvnw clean install
```

4. Run the application:

```shell
./mvnw spring-boot:run
```

The API will be accessible at `http://localhost:8080`.


## Technologies Used

- Spring Boot
- Java
- Maven

## API Endpoints

The API exposes the following endpoints:

### Institutions

- **GET /institutions**: Retrieve a list of all institutions.
- **GET /institutions/{id}**: Retrieve an institution by ID.
- **GET /institutions/search?query={query}**: Search for institutions by query.
- **POST /institutions**: Create a new institution.
- **PUT /institutions/{id}**: Update an institution by ID.
- **DELETE /institutions/{id}**: Delete an institution by ID.
- **GET /institutions/sort?order={order}**: Sort institutions by order.

### Courses

- **GET /courses/institution/{id}**: Retrieve courses by institution ID.
- **GET /courses/search?query={query}**: Search for courses by query.
- **GET /courses/sort?order={order}**: Sort courses by order.
- **POST /courses/institution/{id}**: Create a new course in an institution.
- **DELETE /courses/{id}**: Delete a course by ID.
- **PUT /courses/{id}**: Update a course by ID.

### Students

- **POST /students/course/{id}**: Add a new student to a course.
- **PUT /students/{studentId}/course/{courseId}**: Assign a student to a different course.
- **DELETE /students/{id}**: Delete a student by ID.
- **PUT /students/{id}**: Edit a student by ID.
- **PUT /students/{studentId}/course/{courseId}**: Change a student's course.


| Category     | Endpoint                                 | Method | Description                            |
|--------------|------------------------------------------|--------|----------------------------------------|
| Institutions | /institutions                            | GET    | Get all institutions                    |
| Institutions | /institutions/{id}                       | GET    | Get institution by ID                   |
| Institutions | /institutions/search?query={query}       | GET    | Search institutions by query            |
| Institutions | /institutions                            | POST   | Create a new institution                |
| Institutions | /institutions/{id}?name={name}           | PUT    | Update institution by ID                |
| Institutions | /institutions/{id}                       | DELETE | Delete institution by ID                |
| Institutions | /institutions/sort?order={order}         | GET    | Sort institutions by order              |
| Course       | /courses/institution/{id}                | GET    | Get courses by institution ID           |
| Course       | /courses/search?query={query}            | GET    | Search/filter courses by query          |
| Course       | /courses/sort?order={order}              | GET    | Sort courses by order                   |
| Course       | /courses/institution/{id}                | POST   | Create a new course in an institution   |
| Course       | /courses/{id}                            | DELETE | Delete course by ID                     |
| Course       | /courses/{id}                            | PUT    | Update course by ID                     |
| Student      | /students/course/{id}                    | POST   | Add a new student to a course           |
| Student      | /students/{studentId}/course/{courseId}  | PUT    | Assign a student to a different course  |
| Student      | /students/{id}                           | DELETE | Delete student by ID                    |
| Student      | /students/{id}                           | PUT    | Edit student by ID                      |
| Student      | /students/{studentId}/course/{courseId}  | PUT    | Change student's course                  |
| Other        | /v2/api-docs                             | GET    | Get API documentation in JSON format    |


## Contributors

This API was developed by [Sheila Sharon](https://github.com/devsheila).

## License

This project is licensed under the [MIT License](LICENSE).

Feel free to contribute to the project by submitting issues or pull requests.
