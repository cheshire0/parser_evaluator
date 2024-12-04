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
- **Developer-Friendliness**: Recommending tools suitable for developers with minimal prior experience in formula parsing.

---

## **Getting Started**
To run the comparison framework and analyze the libraries:  

1. **Clone this repository**:  
   ```bash
   git clone https://github.com/your-username/parsing-math-formulae-java.git
   cd parsing-math-formulae-java
   ```

2. **Build the project using Maven**:  
   ```bash
   mvn clean install
   ```

3. **Run the framework**:  
   ```bash
   java -jar target/comparison-framework.jar
   ```

4. **View Results**:  
   The output will include performance metrics, usability assessments, and overall recommendations for each library.

---

## **Repository Structure**
```plaintext
src/
├── main/
│   ├── java/
│   │   ├── org.parser.evaluator/
│   │   │   ├── strategies/           # Library-specific parsers and evaluators
│   │   │   │   ├── EvalEx/
│   │   │   │   ├── Exp4j/
│   │   │   │   ├── Expr4j/
│   │   │   │   ├── JavaMathExpressionParser/
│   │   │   │   ├── Paralithic/
│   │   │   │   └── IParser.java      # Interface for parser strategies
│   │   │   ├── testers/              # Test framework
│   │   │   │   ├── generator/        # Expression generation logic
│   │   │   │   │   ├── ExpressionGenerator.java
│   │   │   │   │   ├── IExpressionGenerator.java
│   │   │   │   ├── DataTypeTest.java
│   │   │   │   ├── MathExpressionTest.java
│   │   │   │   ├── MemoryTest.java
│   │   │   │   ├── SpeedTest.java
│   │   │   │   ├── VariableTest.java
│   │   │   │   └── VectorTest.java
│   │   │   ├── util/                 # Utility classes
│   │   │   │   ├── Logger.java
│   │   │   │   ├── MathTestUtil.java
│   │   │   │   └── Orchestrator.java
│   │   ├── Main.java                 # Entry point for running tests
├── resources/                        # Configuration or sample data
└── test/                             # Unit test files
```

---

## **Contributing**
Contributions to improve the framework, expand comparisons, or add new libraries are welcome. Please open an issue or submit a pull request.

---

## **Contact**
For questions or feedback, feel free to reach out:  
**Lili Sebők** – [seboklili1@gmail.com](mailto:seboklili1@gmail.com)
