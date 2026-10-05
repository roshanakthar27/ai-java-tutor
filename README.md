# AI Java Tutor

An AI-powered Java learning platform that combines a **Java code compiler**, **code execution**, and **AI-powered code explanations** in a simple web interface.

## Features

- Java code editor
- Compile and execute Java programs
- AI-powered Java code explanations
- Code and line-level explanations
- Compilation and runtime error handling
- REST API backend

## Tech Stack

- **Java 17**
- **Spring Boot**
- **Maven**
- **HTML / CSS / JavaScript**
- **Google Gemini API**
- **Judge0 API**

## Project Structure

```text
ai-java-tutor/
│
├── .gitignore
├── .vscode/
│
└── backend/
    ├── Dockerfile
    ├── pom.xml
    │
    └── src/
        ├── main/
        │   ├── java/
        │   │   └── com/example/aijavatutor/
        │   │       ├── AiJavaTutorApplication.java
        │   │       │
        │   │       ├── config/
        │   │       ├── controller/
        │   │       ├── exception/
        │   │       ├── model/
        │   │       └── service/
        │   │
        │   └── resources/
        │       └── application.properties
        │
        └── test/
```

## How It Works

```text
Java Code
    ↓
Frontend
    ↓
Spring Boot Backend
    ↓
 ┌──────────────┐
 │              │
 ▼              ▼
Judge0        Gemini
 │              │
 ▼              ▼
Execution     Explanation
Result
 │              │
 └──────┬───────┘
        ↓
    Frontend
```

## Running the Project

### Requirements

- Java 17+
- Maven
- Git

### Clone

```bash
git clone https://github.com/roshanakthar27/ai-java-tutor.git
```

### Run with Maven

```bash
cd ai-java-tutor/backend
mvn spring-boot:run
```

On Windows, you can also use:

```powershell
.\mvnw.cmd spring-boot:run
```
```bash
docker build -t ai-java-tutor .
```

Run it according to the port and configuration in the project.

## Main Components

### Compiler

Handles Java code compilation and execution through the Judge0 service.

### AI Explanation

Uses Gemini to analyze Java code and provide explanations.

### REST API

Spring Boot provides the backend APIs used by the frontend.

### Frontend

A web-based interface where users can write Java code, execute it, and view explanations.

## Future Improvements

- Code execution history
- User authentication
- Multiple programming language support
- Better debugging assistance
- AI code suggestions
- Test-case generation
- Learning progress tracking
