# Chatbots - AI-Powered Developer Assistant Platform

A full-stack AI chatbot application built with Spring Boot and React, featuring multiple AI assistants for developers including a JUnit test generator (Genie) and a debugging assistant (Duck).

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Usage](#usage)
- [Contributing](#contributing)

## 🎯 Overview

This project provides AI-powered chatbots designed to assist developers with common tasks such as:
- **Genie Bot**: Automatically generates JUnit tests for Java methods
- **Duck Bot**: Debug assistant for troubleshooting code issues
- **Bot Factory**: Create custom chatbots with specific system prompts and behavior

## ✨ Features

- 🧞 **JUnit Test Generation**: Automatically generate comprehensive unit tests for Java methods
- 🦆 **Debug Assistant**: Interactive debugging help using rubber duck debugging methodology
- 🏭 **Custom Bot Creation**: Create and configure custom chatbots for specific purposes
- 💬 **Conversational Interface**: Real-time chat interface for interacting with AI assistants
- 🎨 **Modern UI**: Beautiful, responsive React interface built with Tailwind CSS and Radix UI
- 🔄 **Persistent Conversations**: Store and retrieve bot configurations using H2 database

## 🏗️ Architecture

### Backend Architecture - Clean Architecture Interpretation

The backend follows an **interpretation of Clean Architecture**, organizing code into three distinct layers with clear separation of concerns:

```
┌─────────────────────────────────────────────────────┐
│                    ADAPTER LAYER                     │
│  (Infrastructure & External Interfaces)              │
│                                                       │
│  • Controllers (REST endpoints)                      │
│  • Repository implementations                        │
│  • DTOs (Data Transfer Objects)                      │
│  • External service integrations                     │
└───────────────────┬─────────────────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────────────────┐
│                   USECASE LAYER                      │
│  (Application Business Rules)                        │
│                                                       │
│  • Service classes                                   │
│  • Business logic orchestration                      │
│  • Application-specific operations                   │
└───────────────────┬─────────────────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────────────────┐
│                   DOMAIN LAYER                       │
│  (Enterprise Business Rules & Entities)              │
│                                                       │
│  • Domain models                                     │
│  • Core business entities                            │
│  • Value objects                                     │
└─────────────────────────────────────────────────────┘
```

#### Layer Responsibilities

**1. Domain Layer** (`be.talks.chatbots.domain`)
- Contains core business entities and value objects
- Defines the fundamental business rules and logic
- Independent of external frameworks and technologies
- Examples: `DuckRequest`, `DuckResponse`, `GenieRequest`, `GenieResponse`

**2. Usecase Layer** (`be.talks.chatbots.usecase`)
- Implements application-specific business rules
- Orchestrates the flow of data between adapters and domain
- Contains service classes that execute use cases
- Examples: `PromptGeneratorService`, `FileProcessingService`

**3. Adapter Layer** (`be.talks.chatbots.adapter`)
- Handles external communication and infrastructure concerns
- Implements interfaces for databases, web APIs, and external services
- Converts external data formats to/from domain models
- Sub-layers:
  - **Controllers**: REST API endpoints (`ChatBotController`)
  - **Repositories**: Database access layer
  - **DTOs**: Data transfer objects for API communication

#### Dependency Rule

The architecture follows the **Dependency Rule**:
- Dependencies point **inward** (Adapter → Usecase → Domain)
- Domain layer has **no dependencies** on outer layers
- Usecase layer depends only on Domain
- Adapter layer can depend on both Usecase and Domain layers

This structure provides:
- ✅ **Testability**: Easy to test business logic in isolation
- ✅ **Maintainability**: Clear separation makes code easier to understand and modify
- ✅ **Flexibility**: Easy to swap out infrastructure components
- ✅ **Independence**: Business logic is independent of frameworks and external tools

### Frontend Architecture

The frontend is built with React and follows a component-based architecture:
- **Components**: Reusable UI components (using shadcn/ui)
- **Pages**: Route-specific page components
- **Layout**: Common layout components (Dashboard, Sidebar)
- **Routing**: React Router for navigation

## 🛠️ Tech Stack

### Backend
- **Framework**: Spring Boot 3.5.6
- **Java Version**: 21
- **AI Integration**: Spring AI 1.0.3 with Ollama
- **Database**: H2 (in-memory)
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven
- **Language Features**: Lombok for boilerplate reduction

### Frontend
- **Framework**: React 19.1.1
- **Language**: TypeScript
- **Build Tool**: Vite
- **Styling**: Tailwind CSS 4.1.16
- **UI Components**: Radix UI
- **Icons**: Lucide React
- **Routing**: React Router 7.9.4
- **State Management**: TanStack Query (React Query)

### AI/ML
- **LLM**: Ollama (llama3.2:latest)
- **Integration**: Spring AI framework

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

1. **Java 21** or higher
2. **Maven 3.6+**
3. **Node.js 18+** and npm
4. **Ollama** with llama3.2 model
   ```bash
   # Install Ollama
   curl -fsSL https://ollama.com/install.sh | sh
   
   # Pull the model
   ollama pull llama3.2:latest
   ```

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd chatbots
```

### 2. Start Ollama Service

Make sure Ollama is running with the required model:

```bash
ollama serve
```

In another terminal:
```bash
ollama pull llama3.2:latest
```

### 3. Start the Backend

```bash
# Build and run the Spring Boot application
./mvnw clean install
./mvnw spring-boot:run
```

The backend will start on `http://localhost:8080`

### 4. Start the Frontend

```bash
# Navigate to the UI directory
cd chatbots-ui

# Install dependencies
npm install

# Start the development server
npm run dev
```

The frontend will start on `http://localhost:5173`

### 5. Access the Application

Open your browser and navigate to `http://localhost:5173`

## 📡 API Endpoints

### Genie Bot - Test Generation

**Generate JUnit Test**
```http
POST /api/genie/test
Content-Type: application/json

{
  "code": "public int add(int a, int b) { return a + b; }"
}
```

### Duck Bot - Debugging Assistant

**Debug Code**
```http
POST /api/duck/debug
Content-Type: application/json

{
  "conversationId": "123e4567-e89b-12d3-a456-426614174000",
  "requestMessage": "I'm getting a NullPointerException in my code..."
}
```

### Bot Factory - Custom Bot Management

**Create Custom Bot**
```http
POST /api/bot-factory
Content-Type: application/json

{
  "name": "My Custom Bot",
  "systemPrompt": "You are a helpful assistant that...",
  "description": "Custom bot for specific tasks"
}
```

**Chat with Custom Bot**
```http
POST /api/chat
Content-Type: application/json

{
  "botId": "123e4567-e89b-12d3-a456-426614174000",
  "conversationId": "123e4567-e89b-12d3-a456-426614174001",
  "message": "Hello, how can you help me?"
}
```

## 📁 Project Structure

```
chatbots/
├── src/main/java/be/talks/chatbots/
│   ├── adapter/                    # Adapter Layer (Infrastructure)
│   │   ├── controller/            # REST controllers & service
│   │   │   ├── ChatBotController.java
│   │   │   ├── ChatBotService.java
│   │   │   └── dto/              # Data Transfer Objects
│   │   └── repository/           # Database repositories
│   ├── domain/                    # Domain Layer (Business Entities)
│   │   ├── DuckRequest.java
│   │   ├── DuckResponse.java
│   │   ├── GenieRequest.java
│   │   └── GenieResponse.java
│   ├── usecase/                   # Usecase Layer (Business Logic)
│   │   └── service/
│   │       ├── PromptGeneratorService.java
│   │       └── FileProcessingService.java
│   ├── config/                    # Configuration classes
│   └── utils/                     # Utility classes
├── src/main/resources/
│   ├── application.yml            # Application configuration
│   └── prompts/                   # AI prompt templates
├── chatbots-ui/                   # Frontend Application
│   ├── src/
│   │   ├── components/           # Reusable UI components
│   │   │   ├── Sidebar.tsx
│   │   │   └── ui/              # shadcn/ui components
│   │   ├── layout/              # Layout components
│   │   │   └── Dashboard.tsx
│   │   ├── pages/               # Page components
│   │   │   ├── DuckPage.tsx
│   │   │   └── GeniePage.tsx
│   │   ├── App.tsx
│   │   └── main.tsx
│   ├── package.json
│   └── vite.config.ts
└── pom.xml                        # Maven configuration
```

## ⚙️ Configuration

### Backend Configuration (`application.yml`)

```yaml
spring:
  ai:
    ollama:
      chat:
        options:
          model: llama3.2:latest
  datasource:
    url: jdbc:h2:mem:chatbotdb
    driver-class-name: org.h2.Driver
    username: sa
    password: ''
  h2:
    console:
      enabled: true
  jpa:
    hibernate:
      ddl-auto: update
```

### Environment Variables

You can customize the following:
- **Ollama Model**: Change the model in `application.yml`
- **Database**: Switch from H2 to PostgreSQL/MySQL by updating datasource configuration
- **Server Port**: Add `server.port: 8080` to `application.yml`

## 💡 Usage

### Using the Genie Bot (Test Generator)

1. Navigate to the Genie page
2. Paste your Java method in the text area
3. Click "Generate Test"
4. Copy the generated JUnit test code

### Using the Duck Bot (Debug Assistant)

1. Navigate to the Duck page
2. Describe your debugging issue in the chat
3. Interact with the bot to troubleshoot your problem
4. The bot uses conversational context to provide relevant help

### Creating Custom Bots

1. Use the Bot Factory API to create a new bot with a custom system prompt
2. The bot will be stored in the database with a unique ID
3. Use the chat endpoint to interact with your custom bot

## 🧪 Running Tests

### Backend Tests
```bash
./mvnw test
```

### Frontend Tests
```bash
cd chatbots-ui
npm run lint
```

## 🔧 Development

### Building for Production

**Backend:**
```bash
./mvnw clean package
java -jar target/chatbots-0.0.1-SNAPSHOT.jar
```

**Frontend:**
```bash
cd chatbots-ui
npm run build
npm run preview
```

## 📝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is part of a talks/demo series by BOSA SDG.

## 🤝 Support

For issues, questions, or contributions, please refer to the TODO.md file or open an issue in the repository.

---

**Built with ❤️ using Spring AI and React**

