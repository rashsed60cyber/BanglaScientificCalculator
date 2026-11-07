# বাংলা Scientific Calculator (Java Servlet)

A Bengali-language scientific calculator built with Java Servlet, Jakarta Servlet API, Maven, HTML, CSS and JavaScript. All expression evaluation happens on the server using a small recursive-descent Java parser; it does **not** use JavaScript `eval`, a database, or external calculation services.

## Requirements
- JDK 17 or later (JDK 26 is fine)
- Apache Maven 3.9+
- Apache Tomcat 11.x
- IntelliJ IDEA (Community or Ultimate)

## Import into IntelliJ IDEA
1. Extract the ZIP file.
2. Open IntelliJ IDEA → **File → Open** → select the extracted `BanglaScientificCalculator` folder.
3. Choose **Open as Project** and allow Maven to import dependencies.
4. Set Project SDK to JDK 26: **File → Project Structure → Project → SDK**.
5. In the Maven tool window, run `clean` then `package`.

The project compiles to Java 17 bytecode for broad compatibility while remaining runnable on JDK 26.

## Build the WAR
From the project root:

```bash
mvn clean package
```

Expected output:

```text
target/BanglaScientificCalculator.war
```

## Run on Tomcat 11
1. Download and extract Apache Tomcat 11.
2. Copy `target/BanglaScientificCalculator.war` into Tomcat's `webapps/` folder.
3. Start Tomcat:
   - macOS/Linux: `bin/catalina.sh run`
   - Windows: `bin\catalina.bat run`
4. Open:

```text
http://localhost:8080/BanglaScientificCalculator/
```

## Supported operations
- `+`, `-`, `*`, `/`, `%`, `^`
- Parentheses: `(2+3)*4`
- Trigonometry: `sin(30)`, `cos(60)`, `tan(45)`
- Inverse trigonometry: `asin(0.5)`, `acos(0.5)`, `atan(1)`
- `sqrt(25)`, `log(100)`, `ln(2)`, `abs(-5)`, `exp(1)`
- Factorial: `5!`
- Constants: `pi`, `e`
- `DEG` and `RAD` angle modes

## Important notes
- Trigonometric functions use degrees by default. Switch to RAD for radians.
- Factorial accepts integer values from 0 through 170.
- The calculator intentionally supports explicit function parentheses, e.g. `sin(30)`.
- No MySQL configuration is needed.
- If Tomcat reports a servlet namespace/class error, confirm that you are running Tomcat 11 and not an older `javax.servlet`-based Tomcat version.

## Project structure

```text
BanglaScientificCalculator/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/com/mashrafi/calculator/
        │   ├── CalculatorServlet.java
        │   └── ExpressionParser.java
        └── webapp/
            ├── index.jsp
            ├── css/style.css
            └── js/app.js
```
