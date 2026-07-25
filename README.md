#  Page Pulse

Page Pulse is a Spring Boot web application that analyzes a webpage and provides useful SEO and page insights. Users simply enter a URL, and the application fetches and displays key metrics such as HTTP status, response time, page title, meta description, heading count, image analysis, internal links, and approximate word count.

##  Live Demo

https://page-pulse-production.up.railway.app

##  GitHub Repository

https://github.com/<YOUR_USERNAME>/page-pulse

---

##  Features

- Analyze any valid webpage URL
- HTTP Status Code
- Response Time
- Page Title
- Meta Description
- H1 Count
- Total Images
- Images Missing ALT Attribute
- Internal Links Count
- Approximate Word Count
- Graceful error handling for invalid or inaccessible URLs
- Responsive and modern user interface

---

##  Tech Stack

### Backend
- Java 21
- Spring Boot
- Maven
- Jsoup

### Frontend
- HTML
- CSS
- JavaScript

### Deployment
- Railway

---

##  Setup Instructions

### Clone the repository

```bash
git clone https://github.com/<YOUR_USERNAME>/page-pulse.git
```

### Navigate to project

```bash
cd page-pulse
```

### Run the application

```bash
mvn spring-boot:run
```

The application will run on

```
http://localhost:8081
```

---

##  API Contract

### Request

**POST**

```
/api/analyze
```

### Request Body

```json
{
  "url": "https://example.com"
}
```

### Response

```json
{
  "httpStatus": 200,
  "responseTime": 245,
  "title": "Example Domain",
  "description": "Example description",
  "h1Count": 1,
  "imageCount": 5,
  "missingAltCount": 2,
  "internalLinks": 8,
  "wordCount": 420
}
```

---

##  Testing

The application has been tested for:

- Successful page analysis
- Invalid URL handling
- Unreachable/blocked website handling

---

##  Design Decisions

### 1. DTO Layer

A separate DTO layer was used to separate request and response objects from the business logic, making the application cleaner and easier to maintain.

### 2. Jsoup

Jsoup was chosen because it provides a simple and reliable way to fetch webpages and extract HTML elements without requiring a browser.

### 3. Graceful Error Handling

Instead of crashing when a webpage cannot be accessed, the application returns a meaningful response so the frontend can display the error gracefully.

---

##  AI Usage

AI tools were used to brainstorm UI improvements, review the project structure, assist in debugging, and help draft documentation. All implementation, testing, debugging, integration, and final technical decisions were completed and verified by me.

---

##  Future Improvements

- Support additional SEO metrics
- Export reports as PDF
- Add caching for faster repeated analysis
- Improve accessibility and UI customization

---

##  Author

**Nitisha Jain**