# **Parsing and Evaluating Mathematical Formulae in Java**

## **Overview**
This repository contains the code and framework developed as part of a thesis project aimed at comparing open-source Java libraries for parsing and evaluating mathematical expressions. The project focuses on analyzing the capabilities, strengths, and limitations of these libraries to provide actionable recommendations for developers based on their specific use cases.

---

## **Objectives**
The primary objective of this project is **not to create a new parsing tool**, but to develop a **comparison framework** that evaluates existing libraries based on key attributes. This framework serves as a decision-making tool to help others select the most suitable library for their needs.  

The main goals of the project are:  
1. **Provide a generic overview** of parsing and evaluating mathematical expressions.  
2. **Analyze at least three existing open-source libraries** for mathematical expression parsing in Java.  
3. **Identify evaluation criteria** to compare these libraries consistently.  
4. **Develop a comparison framework** in Java to test and evaluate libraries under different scenarios.  
5. **Deliver practical recommendations** on which library to use under specific boundary conditions.

---

## **Libraries Studied**
The project evaluates at least three popular Java libraries for parsing and evaluating mathematical expressions. Examples of libraries studied include:  
- **exp4j**  
- **EvalEx**  
- **Expr4j**  
- **JavaMathExpressionParser**  
- **Paralithic**
- **Javaluator** 
---

## **Key Features of the Comparison Framework**
The framework evaluates libraries based on the following attributes:  
- **Performance**: Measures how efficiently a library parses and evaluates expressions, particularly for large and complex formulas.  
- **Usability**: Assesses the simplicity of integration and ease of use for developers.  
- **Extensibility**: Evaluates how well the library supports custom operators, functions, and additional features.  
- **Syntax Support**: Tests the library’s ability to handle various mathematical constructs, including functions, constants, and operators.  

---

## **Example Use Cases**
The framework provides recommendations tailored to specific scenarios, such as:  
- **Performance-Oriented Tasks**: Selecting the fastest library for real-time evaluations.  
- **Customization Needs**: Identifying libraries that support user-defined operators or functions.  
- **Correctness of Evaluation**: Recommending tools suitable for developers with minimal prior experience in formula parsing.

---

Here’s a revised and polished version of the **Getting Started** section:

---

## **Getting Started**

### Setting Up the Project
1. **External Libraries**:  
   This project uses three external libraries that were not imported through Maven, their `.jar` files are included in the `lib` folder.  
   - You will need to manually add these `.jar` files to your project’s classpath to run the application.  
     - In IntelliJ IDEA:  
       1. Go to **File > Project Structure > Libraries**.  
       2. Click **+** and add the `.jar` files from the `lib` folder.  
     - In Eclipse:  
       1. Right-click the project in the Package Explorer.  
       2. Navigate to **Build Path > Add External Archives**, and select the `.jar` files.

2. **Configuring the Program**:  
   The program execution is controlled from the `Main.java` file. Here, you can:
   - **Select Tests**: Comment out any unwanted test cases or parsing libraries that you do not wish to execute.
   - Example:
     ```java
     // Comment out the unwanted tests
     orchestrator.addTest(new SpeedTest());
     orchestrator.addTest(new DataTypeTest());
     ```

3. **Customizing Output**:  
   The output behavior can be configured using the `OutputHandler` class.  
   - Modify the line `OutputHandler.setConfig(true, true, true);` in `Main.java` to enable or disable specific output methods:
     - **First Argument**: Enable/disable console output.
     - **Second Argument**: Enable/disable file logging.
     - **Third Argument**: Enable/disable data reporting filtered by test.

---

## **Repository Structure**
```plaintext
src/
├── main/
│   ├── java/
│   │   └── org.parser.evaluator/
│   │       ├── strategies/                     # Library-specific parsers and evaluators
│   │       │   ├── EvalEx/
│   │       │   │   ├── EvalEx.java
│   │       │   │   ├── FactorialFunction.java
│   │       │   │   └── FactorialOperator.java
│   │       │   ├── Exp4j/
│   │       │   │   └── Exp4j.java
│   │       │   ├── Expr4j/
│   │       │   │   └── Expr4j.java
│   │       │   ├── JavaMathExpressionParser/
│   │       │   │   └── JavaMathExpressionParser.java
│   │       │   ├── Paralithic/
│   │       │   │   └── Paralithic.java
│   │       │   └── IParser.java                # Interface for parser strategies
│   │       ├── testers/                        # Testing framework
│   │       │   ├── extensibility/              # Tests for extensibility
│   │       │   │   ├── ExtensibilityTest.java
│   │       │   │   └── VariableTest.java
│   │       │   ├── generator/                  # Expression generation logic
│   │       │   │   ├── ExpressionGenerator.java
│   │       │   │   └── IExpressionGenerator.java
│   │       │   ├── mathematical/               # Tests for mathematical correctness
│   │       │   │   ├── DataTypeTest.java
│   │       │   │   └── MathExpressionTest.java
│   │       │   ├── performance/                # Performance testing
│   │       │   │   ├── MemoryTest.java
│   │       │   │   └── SpeedTest.java
│   │       │   └── Test.java                   # Base test class
│   │       ├── util/                           # Utility classes
│   │       │   ├── log/
│   │       │   │   ├── report/                 # Reporting utilities
│   │       │   │   │   ├── GeneralRecorder.java
│   │       │   │   │   ├── LogContext.java
│   │       │   │   │   ├── TableReporter.java
│   │       │   │   │   └── OutputHandler.java
│   │       │   └── Orchestrator.java           # Orchestrates test runs
│   │       └── Main.java                       # Entry point for running tests
├── resources/                                  # Configuration files or sample data
└── test/                                       # Unit tests
    └── java/
        └── ExpressionGeneratorTest.java        # Test for expression generator

```

---

## **Contributing**
Contributions to improve the framework, expand comparisons, or add new libraries are welcome. Please open an issue or submit a pull request.

---

## **Contact**
For questions or feedback, feel free to reach out:  
**Lili Sebők** – [seboklili1@gmail.com](mailto:seboklili1@gmail.com)
