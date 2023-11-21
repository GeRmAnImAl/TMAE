# Tournament Management and Athlete Empowerment (TMAE)

## IST 440W Group 13
- [AJ Germani](https://github.com/GeRmAnImAl)
- [Eric Moore](https://github.com/Eric131323)
- [Abraham Wagner](https://github.com/cheserpoo)

## Description
Tournament Management and Athlete Empowerment (TMAE) is a Java-based web application designed for managing sports tournaments and providing athletes with a platform to track their performance, register for events, and manage their profiles.

## Installation and Setup
To set up TMAE on your local machine, follow these steps:
1. Clone the repository to your local machine.
2. Ensure you have Java and Spring Boot installed.
3. Navigate to the application.properties file.
4. Input the address of your database instance and login information where applicable. 
5. Navigate to the project directory and build the project using Maven:
   `mvn clean install`
6. Run the application:
   `java -jar target/tmae-0.0.1-SNAPSHOT.jar`

## Usage
Once the application is running, access it through your web browser at `http://localhost:8080`. You can register as a 
new athlete, log in, view/create/manage tournaments, view brackets, view and score matches, and manage your profile.

## Structure
The project is organized into several key components:
- **Model**: Defines the data structure (`Athlete`, `Tournament`, `Bracket`, `Match`).
- **Controller**: Handles HTTP requests (`DeveloperPageController`, `LandingPageController`, `LoginController`, and subdirectories for athletes and tournaments).
- **Repository**: Interfaces for database interaction (`AthleteRepository`, `TournamentRepository`, `BracketRepository`, `MatchRepository`).
- **Service**: Business logic (`AthleteService`, `TournamentService`, and implementations in `Impl` directory).
- **Security**: Configuration for authentication and authorization (`CustomAuthenticationSuccessHandler`, `SecurityConfig`, `SecurityKeyGenerator`).

## Contributing
Contributions to TMAE are welcome! Please adhere to the following guidelines:
- Fork the repository and create a new branch for your feature or fix.
- Write clear, commented code.
- Ensure your code adheres to the existing style of the project.
- Create a pull request with a detailed description of your changes.

