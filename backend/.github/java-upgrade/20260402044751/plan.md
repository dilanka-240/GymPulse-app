# Upgrade Plan: app (20260402044751)

- **Generated**: 2026-04-02 04:52:10
- **HEAD Branch**: appmod/java-upgrade-20260328155936
- **HEAD Commit ID**: N/A

## Available Tools

**JDKs**
- JDK 21.0.9: C:\Program Files\Java\jdk-21\bin (used by Step 2 baseline)
- JDK 25.0.1: C:\Users\User\.jdks\openjdk-25.0.1\bin (used by Steps 3-4)

**Build Tools**
- Maven Wrapper: 4.0.0-rc-5 (used by all steps; verified executable on Java 25)

## Guidelines

- Upgrade Java runtime to the latest LTS version.

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

## Options

- Working branch: appmod/java-upgrade-20260402044751
- Run tests before and after the upgrade: true

## Upgrade Goals

- Upgrade Java from 21 to 25 (latest LTS)

### Technology Stack

| Technology/Dependency | Current | Min Compatible | Why Incompatible |
| --------------------- | ------- | -------------- | ---------------- |
| Java | 21 | 25 | User requested latest LTS |
| Spring Boot Parent | 3.4.3 | 3.4.3 | - |
| Maven Wrapper | 4.0.0-rc-5 | 4.0.0+ (stable) | RC wrapper is pre-release; move to stable Maven 4 for reliable Java 25 builds |
| Maven Compiler Plugin (managed by Boot parent) | managed | 3.14.0+ recommended | Must support release 25 and remain aligned with Maven 4 |
| Maven Surefire Plugin (managed by Boot parent) | managed | 3.2.5+ recommended | Ensures reliable test execution on Java 25 |
| jjwt | 0.11.5 | 0.11.5 | - |
| pdfbox | 2.0.30 | 2.0.30 | - |

### Derived Upgrades

- Upgrade `java.version` property from 21 to 25 in pom.xml
- Validate compiler and test plugin behavior under Java 25 through full compile and test runs

## Upgrade Steps

- **Step 1: Setup Environment**
  - **Rationale**: JDK 25 is already installed; this step validates the existing Maven wrapper toolchain for Java 25 execution.
  - **Changes to Make**:
    - [ ] Verify `.mvn/wrapper/maven-wrapper.properties` resolves Maven successfully
    - [ ] Validate wrapper execution with Java 25
  - **Verification**:
    - Command: `./mvnw.cmd -q -version`
    - JDK: C:\Users\User\.jdks\openjdk-25.0.1\bin
    - Expected: Maven wrapper prints version successfully

- **Step 2: Setup Baseline**
  - **Rationale**: Establish current compile/test behavior before Java version change.
  - **Changes to Make**:
    - [ ] Run baseline compilation with current project Java version configuration
    - [ ] Run baseline tests and capture pass rate
  - **Verification**:
    - Command: `./mvnw.cmd clean test-compile -q` then `./mvnw.cmd clean test -q`
    - JDK: C:\Program Files\Java\jdk-21\bin
    - Expected: Baseline compile and test results documented in progress.md

- **Step 3: Upgrade Java Runtime Configuration to 25**
  - **Rationale**: Apply the requested Java LTS runtime upgrade with minimal code impact.
  - **Changes to Make**:
    - [ ] Update `pom.xml` `java.version` from 21 to 25
    - [ ] Adjust any build configuration only if compilation errors require it
  - **Verification**:
    - Command: `./mvnw.cmd clean test-compile -q`
    - JDK: C:\Users\User\.jdks\openjdk-25.0.1\bin
    - Expected: Main and test code compile successfully on Java 25

- **Step 4: Final Validation**
  - **Rationale**: Ensure the upgrade goal is fully satisfied and all tests pass after the runtime change.
  - **Changes to Make**:
    - [ ] Confirm target versions in `pom.xml` and wrapper config
    - [ ] Run clean rebuild on Java 25
    - [ ] Fix all remaining test failures until 100% pass rate
  - **Verification**:
    - Command: `./mvnw.cmd clean test -q`
    - JDK: C:\Users\User\.jdks\openjdk-25.0.1\bin
    - Expected: Compilation success and 100% tests pass

## Key Challenges

- **Maven Wrapper RC to Stable Transition**
  - **Challenge**: Current wrapper points to Maven 4.0.0-rc-5.
  - **Strategy**: Use the verified wrapper for this upgrade and validate full compile/test behavior under Java 25.

- **Java 25 Compatibility Surface**
  - **Challenge**: Tests and plugins may expose behavior differences when moving from Java 21 to 25.
  - **Strategy**: Run baseline first, then perform iterative compile/test fixes during and after the version bump.

## Plan Review

- Plan coverage is complete for the single requested goal (Java 25).
- No unfixable technical limitation identified at planning time.
