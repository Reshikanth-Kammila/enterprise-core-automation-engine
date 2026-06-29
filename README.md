# 🚀 Enterprise Core Automation Engine

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://java.oracle.com)
[![Selenium](https://img.shields.io/badge/Selenium-4.21.0-green.svg)](https://www.selenium.dev/)
[![RestAssured](https://img.shields.io/badge/RestAssured-5.x-blue.svg)](https://rest-assured.io/)
[![TestNG](https://img.shields.io/badge/TestNG-7.10-yellow.svg)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9-C71A36.svg)](https://maven.apache.org/)
[![CI/CD](https://img.shields.io/badge/GitHub_Actions-Passing-brightgreen.svg)]()

A multi-layered, highly scalable test automation framework engineered for product-focused platforms. This architecture moves beyond traditional, brittle UI-only testing by validating complex systems simultaneously across the **API, UI, and Database** layers.

## 📖 Table of Contents
- [Business Value & Strategy](#-business-value--strategy)
- [Architecture & Tech Stack](#-architecture--tech-stack)
- [Project Directory Structure](#-project-directory-structure)
- [The 4-Phase Execution Model](#-the-4-phase-execution-model)
- [Local Setup & Installation](#-local-setup--installation)
- [Execution Strategies](#-execution-strategies)
- [CI/CD Cloud Integration](#-cicd-cloud-integration)
- [Execution Dashboards](#-execution-dashboards)

---

## 💡 Business Value & Strategy

Modern SaaS applications require testing that is fast, deterministic, and resilient. This framework was built from the ground up to solve the most common bottlenecks in enterprise QA:
* **Eliminating Flakiness:** By injecting test data directly via the backend API instead of relying on slow UI setup steps, execution time is slashed and false positives are dramatically reduced.
* **Deep System Confidence:** UI tests only prove the frontend works. This framework uses JDBC to query the backend database directly, proving that UI actions result in actual data persistence.
* **Zero-Touch Execution:** Integrated directly into a CI/CD pipeline, ensuring tests run autonomously on headless cloud servers the moment code is merged.

---

## 🏗️ Architecture & Tech Stack

### Core Technologies
* **Java 17:** The bedrock language, utilizing modern features like Records and switch expressions.
* **Selenium WebDriver (v4.x):** Drives the frontend execution using the updated W3C protocol.
* **RestAssured:** Handles HTTP requests for rapid data injection and state setup.
* **JDBC:** Establishes direct MySQL/PostgreSQL tunnels for raw data assertions.

### Build & Execution
* **Apache Maven:** Manages dependencies, build lifecycles, and CI execution commands.
* **TestNG:** Powers the execution engine, enabling parallel threading, assertions, and custom reporting listeners.

### Design Patterns Implemented
* **ThreadLocal Driver Management:** Ensures 100% thread safety during parallel test execution, preventing session ID collisions across concurrent browser instances.
* **Page Object Model (POM):** Abstracts web locators and UI actions away from the test scripts, ensuring high maintainability when DOM structures change.
* **Singleton Design:** Controls the initialization of the `ExtentReportManager` and `DatabaseManager` to prevent memory leaks and database connection exhaustion.
* **Network Interceptor Pattern:** Gracefully catches HTTP 401/403 gateway blockages during API testing and activates a localized mock `ResponseBuilder` fallback.

---

## 📂 Project Directory Structure

```text
enterprise-core-automation-engine/
├── .github/workflows/
│   └── pipeline.yml              # CI/CD Cloud Execution Configuration
├── src/main/java/com/engine/
│   ├── core/
│   │   ├── api/ApiClient.java    # RestAssured API wrappers & interceptors
│   │   ├── db/DatabaseManager.java # JDBC connection & query execution
│   │   ├── driver/DriverManager.java # Thread-safe Selenium initialization
│   │   └── utils/
│   │       ├── ConfigReader.java # Environment properties parser
│   │       ├── ExtentReportManager.java # HTML dashboard generator
│   │       └── TestListener.java # Automatic failure screenshot capture
│   └── pages/
│       ├── BasePage.java         # Common UI interactions & waits
│       └── LoginPage.java        # Specific POM representations
├── src/test/java/com/engine/tests/
│   ├── BaseTest.java             # Setup/Teardown execution hooks
│   ├── HybridWorkflowTest.java   # 3-Layer API -> UI -> DB test script
│   └── LoginTest.java            # Standard functional UI test
├── src/test/resources/
│   ├── config.properties         # Global environment variables
│   └── runners/testng.xml        # Execution suite & listener config
├── target/
│   └── ExtentReports/            # Auto-generated HTML execution dashboards
└── pom.xml                       # Maven dependency tree
```

⚙️ The 4-Phase Execution Model
Every comprehensive test in this framework follows a strict, layered sequence:

API Data Injection: RestAssured fires a POST request to the backend to create the required test data (e.g., a new user) in milliseconds.

Frontend Validation: Selenium navigates to the application, utilizes the injected data, and asserts that the UI behaves as expected.

Database Assertion: JDBC queries the backend database tables to guarantee the application actually saved the transaction correctly.

Reporting: The custom TestNG Listener captures the execution results. If a test fails, it takes a Base64 screenshot of the DOM and embeds it directly into the ExtentReport dashboard.

💻 Local Setup & Installation
Prerequisites
Java JDK 17: Installed and configured in system path.

Apache Maven: Installed and configured in system path (MAVEN_HOME).

Git: For repository cloning.

Installation Steps
Clone the repository:

Bash
git clone [https://github.com/Reshikanth-Kammila/enterprise-core-automation-engine.git](https://github.com/Reshikanth-Kammila/enterprise-core-automation-engine.git)
cd enterprise-core-automation-engine
Configure Environment Variables:
Update src/test/resources/config.properties to point to your desired target environment (QA, Staging, Prod):

Properties
env=QA
api.base.url=[https://reqres.in](https://reqres.in)
ui.base.url=[https://practice.expandtesting.com/](https://practice.expandtesting.com/)
# Database Credentials (Keep secure in real environments)
db.host=jdbc:mysql://localhost:3306/enterprise_db
db.username=root
db.password=securepass123
Install Dependencies:

Bash
mvn clean install -DskipTests
🚀 Execution Strategies
1. Command Line Execution (Maven)
To run the entire suite exactly as it executes in a CI/CD pipeline:

Bash
mvn clean test
2. IDE Execution (Eclipse / IntelliJ)
Navigate to src/test/resources/runners/testng.xml.

Right-click the file -> Run As -> TestNG Suite.

3. Headless Execution Setup
For running on servers without a GUI, the DriverManager is pre-configured with headless capabilities. Update the driver initialization to include:

Java
options.addArguments("--headless=new");
options.addArguments("--window-size=1920,1080");
☁️ CI/CD Cloud Integration
This framework requires zero manual intervention. It is fully integrated with GitHub Actions.

Triggering the Pipeline
Automated: The pipeline automatically spins up a virtual Ubuntu server and executes the mvn clean test suite every time code is pushed or a Pull Request is merged into the main branch.

Manual: You can manually trigger a run by navigating to the Actions tab in GitHub, selecting the workflow, and clicking Run workflow (powered by workflow_dispatch).

Cloud Artifacts
Upon pipeline completion, the server automatically compresses the target/ExtentReports folder. You can download the complete HTML dashboard directly from the GitHub Actions summary page.

📸 Execution Dashboards
(Create a docs/screenshots folder in your repository to host these images)

Interactive HTML Reporting (ExtentReports)
Automated Failure Captures
CI/CD Pipeline Tracking

Architected and engineered by Reshi — SDET & QA Automation Engineer.
