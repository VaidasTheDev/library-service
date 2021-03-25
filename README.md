# A book library management service
A simple service for managing books in a library. Books may be added, removed or lent out to its customers.

# Pre-requisites
- Java 11
- Maven
- Lombok (IntelliJ/Eclipse plugin for annotation processing in your local environment)

# Suggested improvements
- Security: the application does not enforce any security rules
- DB: replace H2 local database (which saves to a file) with a more matured database such as PostgreSQL (works well with JPA)
- Testing: add integration tests using Gherkin
- Validation: incoming data should have custom validation instead of 'javax';
  Although 'javax.validation' module does the job, it's difficult to apply complex validation rules

# To-do
- Unit tests
