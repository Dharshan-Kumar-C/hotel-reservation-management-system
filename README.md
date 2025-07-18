# Hotel Management System (Spring Boot)

A minimal backend for hotel operations using Spring Boot.

## Setup

1. **Clone the repository:**
   ```bash
   git clone <your-repo-url>
   cd hotel
   ```

2. **Configure the database:**
   - Edit `src/main/resources/application.properties` as needed for your DB setup.

3. **Build and run:**
   ```bash
   ./mvnw spring-boot:run
   ```
   Or, to build a JAR:
   ```bash
   ./mvnw clean package
   java -jar target/hotel-0.0.1-SNAPSHOT.jar
   ```

## Main API Endpoints

- `/api/auth/*` – Authentication (login, signup)
- `/api/guests/*` – Guest management
- `/api/rooms/*` – Room management
- `/api/reservations/*` – Reservation management
- `/api/payments/*` – Payment management

---

For more details, see the Swagger UI at `/swagger-ui/` when the app is running. 
