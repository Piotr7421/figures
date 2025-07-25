# Figures - Spring Boot REST API

## Project Description

The **Figures** project is a Spring Boot REST API application for managing geometric figures. The application enables creating different types of figures and searching them with advanced filtering criteria and pagination.

## Core Project Assumptions

### 1. Single endpoint for creating geometric figures
- **POST** `/api/v1/figures` - creating new geometric figures

### 2. Single endpoint for retrieving/searching figures
- **GET** `/api/v1/figures` - retrieving figures with pagination and advanced search using Specification

### 3. Open/Closed Principle (OCP)
**Mandatory requirement**: Adding a new figure **MUST NOT** require changing/modifying **ANY OF THE EXISTING** classes.

## Supported Geometric Figures

- **Square** - parameter: `side`
- **Rectangle** - parameters: `length`, `width`
- **Circle** - parameter: `radius`
- Easy addition of new figure types without modifying existing code

## Adding New Figures - Three-Class Approach

Adding a new figure to the application **does not require modifying any existing classes**, including search criteria. The architecture follows the Open/Closed Principle perfectly.

### Example: Adding Isosceles Triangle

To add a new figure (e.g., Isosceles Triangle), you only need to create **three new classes**:

#### 1. Model Class extending Figure

Create a model class that extends the base `Figure` class:

- Class: `IsoscelesTriangle extends Figure`
- Add specific parameters: `base`, `height`
- Include validation annotations
- Use JPA annotations for persistence

#### 2. Creation Strategy Class extending AbstractFigureCreationStrategy

Create a strategy class for figure creation:

- Class: `IsoscelesTriangleCreationStrategy extends AbstractFigureCreationStrategy`
- Implement figure creation logic from command parameters
- Handle parameter validation and conversion
- Register as Spring component with figure type name

#### 3. Mapper Class extending FigureMapper

Create a mapper class for DTO conversion:

- Class: `IsoscelesTriangleMapper extends FigureMapper`
- Implement mapping between entity and DTO
- Handle specific figure properties
- Register as Spring component

### No Modifications Required

After adding these three classes:
- **Controllers** - no changes needed
- **Services** - no changes needed
- **Repositories** - no changes needed
- **Exception handlers** - no changes needed
- **Search specifications** - automatically work with new figure
- **Validation framework** - automatically validates new figure
- **API endpoints** - immediately support new figure type

The new figure is automatically:
- Available for creation via POST endpoint
- Searchable via GET endpoint with all operators
- Validated according to defined constraints
- Mapped to appropriate DTOs
- Handled by exception framework

## API Usage Examples

### Creating Figures

```
POST /api/v1/figures
Content-Type: application/json

{
    "type": "Circle",
    "params": {
        "radius": 5.0
    }
}
```
```
POST /api/v1/figures
Content-Type: application/json

{
    "type": "Rectangle",
    "params": {
      "length" : 23,
      "width" : 4
    }
}
```
```
POST /api/v1/figures
Content-Type: application/json

{
    "type": "Square",
    "params": {
        "side": 12.7
    }
}
```

### Searching Figures

```
GET /api/v1/figures

GET /api/v1/figures?search=type:*:Circle&page=0&size=10&sort=id

GET /api/v1/figures?search=type:=:Circle,radius:>:10&page=0&size=10&sort=id

GET /api/v1/figures?search=type:=:Rectangle,length:>:10,width:<:4&page=0&size=10&sort=id
```
## Applied Java and Spring Elements

### Architecture and Design Patterns

- **Strategy Pattern** - `FigureCreation` for different figure types
- **Factory Pattern** - automatic strategy injection through Spring
- **Repository Pattern** - `FigureRepository` with JPA
- **DTO Pattern** - separation of domain model from API
- **Specification Pattern** - advanced searching
- **Template Method Pattern** - abstract base classes for extensibility

### Spring Technologies

- **Spring Boot** - application framework
- **Spring Web** - REST API
- **Spring Data JPA** - data access
- **Spring Validation** - input data validation
- **Spring AOP** - aspects (if used)
- **Spring Context** - dependency injection and component scanning

### Advanced Java Features

- **Reflection** - dynamic processing of figure parameters
- **Generics** - type-safe collections and repositories
- **Lambda Expressions** - stream processing
- **Optional** - safe null value handling
- **Records** - immutable data classes (Java 14+)
- **Annotations** - declarative programming model

### Validation and Error Handling

- **Bean Validation** (`@Valid`, `@NotNull`, `@NotBlank`, `@Positive`)
- **Custom Validators** - domain-specific validation
- **GlobalExceptionHandler** - centralized exception handling
- **Custom Exceptions**:
    - `IncorrectParamsException`
    - `IncorrectParamsSizeException`
    - `IncorrectSearchKeyException`
    - `IncorrectSearchOperationException`
    - `NoMapperFoundException`

### Error Response Structure
```
{
    "timestamp": "2025-07-25 19:39:47",
    "message": "Validation error",
    "violations": [
        {
            "field": "type",
            "message": "FIGURE_TYPE_NOT_SUPPORTED"
        },
        {
            "field": "type",
            "message": "PATTERN_MISMATCH_\\p{Lu}\\p{Ll}{1,19}"
        }
    ]
}
```

```
{
    "timestamp": "2025-07-25 19:45:36",
    "message": "INCORRECT_SEARCH_KEY : typd "
}
```
## Project Structure
```
src/main/java/com/example/figures/
├── controller/
│   └── FigureController.java
├── service/
│   ├── FigureService.java
│   └── FigureMapperService.java
├── repository/
│   └── FigureRepository.java
├── model/
│   ├── Figure.java
│   ├── Square.java
│   ├── Rectangle.java
│   ├── Circle.java
│   ├── dto/
│   │   └── FigureDto.java
│   └── command/
│       └── CreateFigureCommand.java
├── strategy/
│   ├── FigureCreation.java
│   ├── SquareCreation.java
│   ├── RectangleCreation.java
│   └── CircleCreation.java
├── mapper/
│   ├── FigureMapper.java
│   ├── SquareMapper.java
│   ├── RectangleMapper.java
│   └── CircleMapper.java
├── specification/
│   └── SpecificationCreation.java
├── validation/
│   └── CommandParamsValidation.java
├── config/
│   └── FigureFields.java
└── exception/
├── handler/
│   └── GlobalExceptionHandler.java
├── model/
│   ├── ErrorResponse.java
│   └── ValidationError.java
└── [Custom Exception Classes]
```


## Testing

### Unit Tests
- Business logic testing for services
- Figure creation strategy testing
- Validator testing
- Mapper testing
- Specification testing

### Integration Tests
- REST API endpoint testing
- Database integration testing
- Controller-level validation testing
- Exception handling testing
- End-to-end workflow testing


## Key Architecture Benefits

1. **Extensibility** - easy addition of new figure types without code modifications
2. **Maintainability** - clean and modular code structure
3. **Testability** - high testability through dependency injection
4. **Robustness** - advanced error handling and validation
5. **Performance** - efficient searching with Specification and pagination
6. **SOLID Principles** - especially Open/Closed Principle implementation
7. **Scalability** - architecture supports growing number of figure types

## System Requirements

- Java 17+
- Spring Boot 3.x
- Maven/Gradle
- Database (H2/PostgreSQL/MySQL)

## Running the Application

Clone the repository
git clone [repository-url]

Navigate to project directory
cd figures

Run the application
./mvnw spring-boot:run

Application available at
http://localhost:8080/api/v1/figures

## Key Implementation Highlights

### Reflection Usage
- Dynamic parameter processing for different figure types
- Runtime type discovery for validation and mapping
- Flexible search criteria generation

### Advanced Validation
- Multi-level validation (Bean Validation + custom validators)
- Context-aware error messages
- Comprehensive validation error reporting

### Specification Pattern Implementation
- Dynamic query building based on search criteria
- Type-safe query construction
- Flexible operator support (=, >, <, *, etc.)

### Strategy Pattern with Spring Integration
- Automatic strategy discovery and registration
- Type-based strategy selection
- Seamless integration with Spring's dependency injection

This architecture demonstrates how proper application of SOLID principles, especially the Open/Closed Principle, enables building highly extensible and maintainable systems where new functionality can be added without modifying existing code.