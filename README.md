# SOEN 6011: Eternity Project - Arccos Calculator

This is a medium-sized scientific software engineering project for the SOEN 6011 course at Concordia University. The project implements the transcendental function `F1: arccos(x)` from first principles.

**Version:** 1.1.1

## Description

This application is a Java Swing-based desktop calculator that computes the arccosine of a user-provided number. The calculation is performed from scratch using a Taylor series expansion for `arcsin(x)` and the identity `arccos(x) = PI/2 - arcsin(x)`.

The project follows professional software engineering practices including:
- Adherence to the Google Java Style Guide, verified with Checkstyle.
- Static code analysis for potential bugs using SonarLint.
- Semantic Versioning for releases, managed with Git tags.
- Implementation of UI Design Principles and Accessibility (Keyboard Navigation and Screen Reader support).

## How to Compile and Run

The project is self-contained and does not require any external libraries or a specific IDE to compile.

1.  **Compile the code:**
    Navigate to the `src` directory and run the Java compiler.
    ```sh
    javac ArccosCalculatorGui.java
    ```

2.  **Run the application:**
    From the `src` directory, run the compiled Java class.
    ```sh
    java ArccosCalculatorGui
    ```

## Features

-   **GUI Interface:** Simple and intuitive graphical user interface built with Java Swing.
-   **Input Validation:** Ensures that user input is a valid number within the mathematical domain of arccosine `[-1, 1]`.
-   **Accurate Calculation:** Computes `arccos(x)` from scratch.
-   **Clear Results:** Displays the result in both radians and degrees.
-   **Error Handling:** Provides helpful error messages for invalid input.
-   **Accessibility:** Supports keyboard-only navigation and provides descriptions for screen readers.
