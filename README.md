# **Reversi (Othello) – Java Console Application ⚫⚪**

This project is a **Java console-based implementation of Reversi (Othello)**, developed as part of an effort to **strengthen core software development fundamentals** through deliberate practice and cross-language implementation.

The project is a **Java reimplementation** of an earlier C++ Reversi simulation. Rather than focusing on adding new features, the emphasis is on **structure, clarity, correctness, and disciplined design**, while adapting an existing solution to Java’s language model and conventions.

The goal is not to present a production-ready system, but to **practice reasoning about design, state, and behaviour** in a non-trivial codebase.

---

## **Project overview**

The game models a standard Reversi board, turn-based gameplay, legal move validation, token flipping, and end-game evaluation. The core challenge of the project lies in **correctly identifying legal moves in all directions**, handling skipped turns, tracking game state, and keeping responsibilities clearly separated across classes.

The project is intentionally **console-based** to keep the focus on:

* Game logic and rule enforcement
* State management
* Clear separation of responsibilities

rather than graphical or UI concerns.

---

## **Context**

### 📘 About Reversi (Othello)

Reversi is a two-player strategy board game where:

* Players take turns placing tokens on an `n × n` board
* Opponent tokens are flipped when flanked in any valid direction
* A player must pass if no legal moves are available
* The game ends when neither player can move
* The winner is the player with the most tokens on the board

This implementation supports:

* Human vs Human, Human vs AI, and AI vs AI play
* Variable board sizes (4×4 up to 10×10)
* Console-based board rendering with colour-coded feedback
* Logged moves, flips, and final results

---

## **Original project background**

The original Reversi implementation was written in C++ as a university project.
This Java version revisits the same problem space with a stronger focus on:

* Clear responsibility boundaries
* Java-specific design considerations
* Explicit game flow and state ownership
* Readability and maintainability over cleverness

Rather than treating the Java version as a simple rewrite, it is used to **question and refine earlier design decisions**.

---

## **🧩 What this project explores**

This project is being used to practice and reinforce:

* Representing board state and turn-based logic in Java
* Implementing directional rule checks without duplication
* Managing shared state safely using object references
* Applying object-oriented principles such as encapsulation and composition
* Structuring console applications beyond a single `Main` class
* Gradually refactoring code to improve clarity and reduce coupling
* Reasoning about behaviour through iteration and debugging

The emphasis is on **understanding and discipline**, not speed or feature count.

---

## **Java-specific considerations**

Reimplementing the project in Java highlighted several important differences compared to C++:

* Object references required more deliberate thinking about shared state and copying
* Garbage collection removed manual memory concerns but increased focus on ownership and mutability
* Java’s exception model encouraged clearer handling of invalid input and illegal states
* Standard collections and enums reinforced explicit modelling of game state

Overall, the project reinforced the idea that **good design principles transfer across languages**, while implementation details require careful adaptation.

---

## **Why revisit this project**

Reworking this project in Java provides an opportunity to:

* Strengthen programming fundamentals through repetition
* Apply object-oriented principles more deliberately
* Learn how design decisions shift across languages
* Practice improving an existing codebase incrementally

 This project is part of a broader effort to **build disciplined software engineering habits**, focusing on clarity, structure, and reasoning rather than surface-level complexity.

---

## **Current state and direction**

* Fully playable console-based Reversi implementation
* Supports multiple player types and board sizes
* Focused on correctness, clarity, and structure
* Ongoing refactoring to simplify logic and improve readability
* Future work prioritises learning and code quality over new features

---

## **📂 Project structure**

```
ReversiJava/
 ├─ src/
 │   ├─ Main.java
 │   ├─ Reversi.java
 │   ├─ GameManager.java
 │   ├─ Board.java
 │   ├─ BoardRender.java
 │   ├─ BoardRenderContext.java
 │   ├─ MoveInspector.java
 │   ├─ MoveRules.java
 │   ├─ PlayerType.java
 │   ├─ HumanPlayer.java
 │   ├─ RandomAI.java
 │   ├─ SelectiveAI.java
 │   ├─ InputValidator.java
 │   ├─ GameLogger.java
 │   ├─ ResultsEvaluator.java
 │   └─ ConsoleColours.java
 └─ README.md
```

---

## **🏁 How to run**

```bash
git clone https://github.com/Mahlatse-Seloane/Reversi-Java.git
cd Reversi-Java
javac src/*.java
java src.Main
```

The game runs entirely in the console, logging moves, flips, and final results in a structured and readable format.
