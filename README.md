Certainly! Here is the README in pure Markdown format.

---

# RestApiTutorial Project: Audio Transcription API Interaction with Java

## Table of Contents

- [Introduction](#introduction)
- [Dependencies](#dependencies)
- [File Structure](#file-structure)
- [Classes](#classes)
  - [Transcript.java](#transcriptjava)
  - [RestApiTutorial.java](#restapitutorialjava)
  - [HTTPRequestApiTutorial.java](#httprequestapitutorialjava)
- [How to Run](#how-to-run)
- [Features](#features)
- [Contributors](#contributors)
- [License](#license)

## Introduction

This Java project serves as a robust example of how to interact with a RESTful API to transcribe audio files. It employs key Java libraries such as Spring's `RestTemplate` and Google's `Gson` for seamless API interaction and JSON handling.

## Dependencies

- Java 1.8 or higher
- Spring Framework
- Google's Gson library

### Maven Dependencies

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-web</artifactId>
    <version>5.3.10</version>
  </dependency>
  <dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.8.8</version>
  </dependency>
</dependencies>
```

## File Structure

```plaintext
RestApiTutorial/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── tutorial/
│                   └── RestApiTutorial/
│                       ├── Confidential.java
│                       ├── HTTPRequestApiTutorial.java
│                       ├── RestApiTutorial.java
│                       └── Transcript.java
```

## Classes

### Transcript.java

This class acts as a POJO (Plain Old Java Object) that maps to a transcript. It includes fields like `audio_url`, `id`, `status`, and `text`, along with their respective getters and setters.

```java
public class Transcript {
    // Fields and Methods
}
```

### RestApiTutorial.java

This is the powerhouse class that conducts all API interactions. It sets up HTTP headers, initiates a POST request to kick-start the audio transcription process, and then continually polls the API until the transcription is either completed or faces an error.

```java
public class RestApiTutorial {
    // Methods and logic
}
```

### HTTPRequestApiTutorial.java

This class demonstrates how to make HTTP requests using Java's `HttpClient`. It showcases another approach to interacting with the transcription API and includes continuous status checking until the transcript is either completed or faces an error.

```java
public class HTTPRequestApiTutorial {
    // Methods and logic
}
```

## How to Run

1. Clone the repository to your local machine.
2. Navigate to the project directory and run `mvn clean install` to build the project.
3. Execute the `RestApiTutorial.java` class.

## Features

- Asynchronous audio transcription
- Serialization and Deserialization using Gson
- RESTful API interaction using Spring's RestTemplate

## Contributors

- Ryan Hunter

## License

MIT License
