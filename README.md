# Spring Boot API Documentation

This repository contains the documentation for a Spring Boot API that provides endpoints for managing institutions, courses, and students.

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

### Other

- **GET /v2/api-docs**: Retrieve the API documentation in JSON format.

## Getting Started

To get started with the API, follow these steps:

1. Clone this repository to your local machine.
2. Install the required dependencies using Maven.
3. Start the Spring Boot application.
4. Access the API using the provided endpoints.

## Documentation

For detailed information on each API endpoint, refer to the [API Documentation](/documentation) provided in this repository.

## Technologies Used

- Spring Boot
- Java
- Maven

## Contributors

This API was developed by [Your Name](https://github.com/yourusername).

## License

This project is licensed under the [MIT License](LICENSE).

Feel free to contribute to the project by submitting issues or pull requests.
