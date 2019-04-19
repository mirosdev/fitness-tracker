# fitness-tracker
Angular 7 SPA + Spring Boot 2 + H2 (fake) RDB - Practice Project

# Run Requirements
- Node.js
- Angular CLI
- Maven

# Database
No database installation required
For structure and data review access http://localhost:8080/h2-console/ in your browser,
enter 'jdbc:h2:mem:testdb' in 'JDBC URL' form field, let other fields set by default and click connect to connect to H2 database review.
Everytime Spring Boot runs, the database is empty and then auto-initialized with dummy data.

# Run Commands
After required installations, locate your command line prompt into SPA directory, 
- run 'npm install' to install Angular dependencies,
- then run 'ng serve' to run Angular SPA on port localhost:4200,
- and for Spring Boot, locate into Spring Rest directory and run 'mvn spring-boot:run'

# For Application User Experience
Login with available email: test@test.com, password: 'password' or try registration with dummy example of e-mail just like 'example@example.com'

# Features
Angular Material with Flex Layout
