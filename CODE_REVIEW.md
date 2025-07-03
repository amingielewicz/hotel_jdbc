# Code Review - Hotel JDBC Project

## Overview
This code review covers the hotel_jdbc Maven project, which appears to be a hotel management system using JDBC for MySQL database connectivity.

## Project Structure
```
src/main/java/pl/hotel/
├── Main.java
└── drivers/
    ├── DriverFactory.java
    └── ConfigLoader.java
```

## Critical Issues

### 🔴 High Priority Issues

#### 1. **Missing Configuration File**
- **File**: `ConfigLoader.java:13`
- **Issue**: The code references `config.properties` but this file doesn't exist in the project
- **Impact**: Application will fail at runtime with `RuntimeException`
- **Recommendation**: Create `src/main/resources/config.properties` with required properties

#### 2. **Hardcoded Database Credentials**
- **File**: `DriverFactory.java:10`
- **Issue**: Username "root" is hardcoded in the source code
- **Security Risk**: Credentials exposed in source code
- **Recommendation**: Move all credentials to configuration file

#### 3. **Improper Connection Management**
- **File**: `DriverFactory.java:23-32`
- **Issue**: Connection is opened and immediately closed in finally block
- **Impact**: Connection is never usable for actual database operations
- **Recommendation**: Implement proper connection lifecycle management

#### 4. **Deprecated MySQL Driver Registration**
- **File**: `DriverFactory.java:15`
- **Issue**: Manual driver registration is unnecessary since JDBC 4.0
- **Recommendation**: Remove manual `Class.forName()` call

### 🟡 Medium Priority Issues

#### 5. **Inconsistent Error Handling**
- **File**: `DriverFactory.java:35-39`
- **Issue**: Error message says "Rozłączono Bazę Danych" in catch block (misleading)
- **Recommendation**: Fix error message to indicate connection close failure

#### 6. **Missing Null Checks**
- **File**: `DriverFactory.java:35`
- **Issue**: Attempting to close connection without null check
- **Recommendation**: Add null check before closing connection

#### 7. **Static Method Design Issues**
- **File**: `ConfigLoader.java:26-34`
- **Issue**: `getIntProperty()` method is instance method but should be static
- **Recommendation**: Make method static for consistency

#### 8. **Unused Instance Variable**
- **File**: `DriverFactory.java:12`
- **Issue**: Static connection variable is set but never used effectively
- **Recommendation**: Either implement connection pooling or remove static connection

### 🟢 Low Priority Issues

#### 9. **Code Documentation**
- **Issue**: No JavaDoc comments or inline documentation
- **Recommendation**: Add proper documentation for all public methods

#### 10. **Project Documentation**
- **File**: `README.md`
- **Issue**: Minimal README with typo ("holel_jdbc" instead of "hotel_jdbc")
- **Recommendation**: Add proper project description, setup instructions, and usage examples

#### 11. **Maven Version Considerations**
- **File**: `pom.xml:19-21`
- **Issue**: Using older MySQL connector version (8.0.33)
- **Recommendation**: Consider updating to latest stable version

## Security Concerns

1. **Credential Management**: Database credentials should never be hardcoded
2. **Configuration Security**: Ensure config.properties is not committed to version control
3. **Connection Security**: Implement proper SSL/TLS configuration for database connections

## Recommended Actions

### Immediate Fixes Required:
1. Create missing `config.properties` file
2. Move hardcoded credentials to configuration
3. Fix connection management logic
4. Correct error messages

### Suggested Improvements:
1. Implement connection pooling
2. Add proper exception handling strategies
3. Create utility methods for database operations
4. Add comprehensive documentation
5. Implement logging framework instead of System.out.println

### Example Config File Structure:
```properties
# Database Configuration
url=jdbc:mysql://localhost:3306/hotel_db
username=your_username
password=your_password
driver=com.mysql.cj.jdbc.Driver
```

## Overall Assessment
**Rating: ⚠️ Needs Significant Improvement**

The project shows basic understanding of JDBC concepts but has several critical issues that prevent it from functioning correctly. The main concerns are around configuration management, connection handling, and security practices. With the recommended fixes, this could become a solid foundation for a hotel management system.

## Next Steps
1. Address all high-priority issues before deployment
2. Implement proper testing
3. Add error handling and logging
4. Consider using a database abstraction layer (like Spring JDBC or Hibernate) for more robust data access