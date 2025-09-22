# Product Management System

A Spring Boot MVC application for managing products and categories without JPA, using raw JDBC with PostgreSQL.

## Features

- ✅ **Product Management**: Create, read, update, and delete products
- ✅ **Category Management**: Create, read, update, and delete categories  
- ✅ **Web Interface**: Bootstrap-styled responsive UI with Thymeleaf templates
- ✅ **Database**: PostgreSQL with JDBC (no JPA/Hibernate)
- ✅ **Auto Schema**: Database tables created automatically on startup

## Technologies Used

- **Backend**: Spring Boot 2.7.18, Spring MVC, Spring JDBC
- **Frontend**: Thymeleaf, Bootstrap 5.3.0
- **Database**: PostgreSQL 
- **Build Tool**: Maven
- **Java Version**: 11

## Prerequisites

Before running the application, make sure you have:

- Java 11 or higher installed
- PostgreSQL database server running
- Maven (or use the included Maven wrapper)

## Database Setup

1. **Install PostgreSQL** and start the service
2. **Create a database** named `product_db`:
   ```sql
   CREATE DATABASE product_db;
   ```
3. **Update credentials** in `src/main/resources/application.properties` if needed:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/product_db
   spring.datasource.username=postgres
   spring.datasource.password=admin
   ```

## How to Run

### Option 1: Using Maven Wrapper (Recommended)
```bash
# Make the wrapper executable (if needed)
chmod +x mvnw

# Run the application
./mvnw spring-boot:run
```

### Option 2: Using Maven (if installed globally)
```bash
mvn spring-boot:run
```

### Option 3: Using IDE
- Import the project into your IDE (IntelliJ IDEA, Eclipse, etc.)
- Run the main class: `MvcCrudProductosSinJpaApplication`

### Option 4: Build and Run JAR
```bash
# Build the project
./mvnw clean package

# Run the JAR file
java -jar target/product-management-0.0.1-SNAPSHOT.jar
```

## Accessing the Application

Once the application starts, open your browser and go to:
```
http://localhost:3000
```

## Application Structure

```
src/main/java/com/demo/productmanagement/
├── controller/          # Web controllers
│   ├── CategoryController.java
│   ├── ProductController.java
│   └── HomeController.java
├── model/              # Data models
│   ├── Category.java
│   └── Product.java
├── repository/         # Data access layer
│   ├── CategoryRepository.java
│   └── ProductRepository.java
├── service/            # Business logic
│   ├── CategoryService.java
│   ├── CategoryServiceImpl.java
│   ├── ProductService.java
│   └── ProductServiceImpl.java
└── MvcCrudProductosSinJpaApplication.java

src/main/resources/
├── templates/          # Thymeleaf templates
│   ├── fragments/
│   ├── products/
│   ├── categories/
│   └── index.html
├── static/            # CSS, JS, images
├── application.properties
└── schema.sql         # Database schema
```

## Database Schema

The application automatically creates these tables on startup:

### Products Table
```sql
CREATE TABLE productos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio NUMERIC(10, 2) NOT NULL,
    stock INTEGER DEFAULT 0
);
```

### Categories Table
```sql
CREATE TABLE categorias (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);
```

## Sample Data

The application includes sample data that gets inserted automatically:

**Categories:**
- Laptops
- Monitores  
- Accesorios
- Periféricos

**Products:**
- Laptop Dell XPS 13
- Monitor LG 27"
- Teclado Mecánico
- Mouse Inalámbrico

## Available Endpoints

- `GET /` - Home page
- `GET /products` - View all products
- `GET /products/new` - Add new product form
- `GET /products/edit/{id}` - Edit product form
- `GET /products/view/{id}` - View product details
- `GET /category` - View all categories
- `GET /category/new` - Add new category form
- `GET /category/edit/{id}` - Edit category form
- `GET /category/view/{id}` - View category details

## Configuration

Key configuration in `application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/product_db
spring.datasource.username=postgres
spring.datasource.password=admin

# Schema initialization
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql

# Development
spring.thymeleaf.cache=false
server.port=3000
```

## Troubleshooting

**Port 3000 already in use:**
- Change the port in `application.properties`: `server.port=8080`

**Database connection errors:**
- Ensure PostgreSQL is running
- Verify database name and credentials
- Check if the database `product_db` exists

**Template changes not showing:**
- Spring Boot DevTools should auto-reload templates
- If not working, restart the application
- Clear browser cache

## Development Notes

- This project intentionally avoids JPA/Hibernate to demonstrate raw JDBC usage
- Templates use Thymeleaf with Bootstrap for responsive design
- Database schema is recreated on each application restart (`spring.sql.init.mode=always`)
- DevTools enabled for hot reloading during development

## License

This is a demo project for educational purposes.
