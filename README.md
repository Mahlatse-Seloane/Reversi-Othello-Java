# Reversi (Othello) ⚫/⚪ – Java Console Application

*A Java console-based implementation of Reversi (Othello), developed as a deliberate exercise in cross-language translation and professional software 
engineering practice.*

This project is a **Java reimplementation** of my original [C++ Reversi simulation](https://github.com/Mahlatse-Seloane/Reversi-Othello). Rather than treating it as a simple rewrite, the focus was on 
**preserving and strengthening industry-standard development practices**, while adapting to Java’s language model, tooling, and conventions.

The aim was not only to make the game work, but to **understand why certain design decisions change across languages**, and how to carry professional 
habits forward rather than resetting them with each new language.

---

## 🥅 Project Goals & Motivation

The primary goal of this project was to **translate an existing C++ codebase into Java**, while:

* Retaining professional development practices already established in C++
* Actively adapting those practices to align with Java conventions
* Strengthening core programming fundamentals through repetition and refinement
* Treating Git and GitHub as part of the development process, not an afterthought

This was intentionally approached as a **learning project**, where design decisions were questioned, refactored, and improved over time, even when the 
original C++ solution already “worked”.

---

## 🎮 About Reversi (Othello)

Reversi is a two-player strategy board game where:

* Players take turns placing tokens on an `n × n` board
* Opponent tokens are flipped when flanked in any direction
* Turns are passed when no legal moves are available
* The game ends when neither player can move
* The winner is the player with the most tokens on the board

This implementation supports:

* AI vs AI, Human vs AI, and Human vs Human gameplay
* Variable board sizes (4×4 up to 10×10)
* Structured console rendering with colour-coded feedback
* Logged moves, captures, and end-game results

---

## 🧱 Technical Overview

This section provides a high-level overview of the project architecture, highlighting how responsibilities are separated across classes and how the system 
is structured to remain readable, maintainable, and extensible. The focus is on *responsibilities and boundaries*, rather than implementation details.

### Core Classes & Responsibilities

| Component                                | Responsibility                                                        |
| ---------------------------------------- | --------------------------------------------------------------------- |
| `Main`                                   | Application entry point                                               |
| `Reversi`                                | High-level game orchestration and user flow                           |
| `GameManager`                            | Controls turn flow, move execution, and game termination              |
| `Board`                                  | Owns and mutates board state                                          |
| `BoardRender`                            | Handles all console rendering                                         |
| `BoardRenderContext`                     | Encapsulates transient render state (valid moves, flips, chosen move) |
| `MoveRules`                              | Determines legal moves and captured tokens                            |
| `MoveInspector`                          | Directional board scanning utility used by multiple systems           |
| `PlayerType`                             | Base player abstraction                                               |
| `HumanPlayer`, `RandomAI`, `SelectiveAI` | Concrete player implementations                                       |
| `GameLogger`                             | Structured console output and game reporting                          |
| `InputValidator`                         | Centralised user input validation                                     |
| `ResultsEvaluator`                       | Determines game outcome and statistics                                |
| `ConsoleColours`                         | Console colour abstraction                                            |


This structure intentionally avoids “god classes” and tightly coupled logic. Each component is designed to be reasoned about, modified, or replaced with 
minimal impact on the rest of the system. Overall, the system was designed to **separate game logic, presentation, and input**, allowing each concern to 
evolve independently.

---

## 🔹 Design Principles in Practice

Rather than following patterns mechanically, this project was used to **apply and test core software engineering principles in context**.

* **Separation of concerns**
  Rendering, game rules, input handling, and orchestration are deliberately isolated. For example, the board renderer has no knowledge of game rules or 
  turn logic.

* **Single responsibility**
  Classes such as `MoveInspector` exist purely to scan the board in different directions. This avoids duplicating complex directional logic across multiple 
  systems.

* **Explicit orchestration**
  Game flow is intentionally controlled from a small number of coordinating classes (`Reversi`, `GameManager`), rather than being distributed implicitly.

* **Designing for change**
  AI strategies are polymorphic and can be extended without modifying existing game logic.

* **Defensive programming**
  Input validation and state checks are explicit, making failures visible early rather than allowing silent corruption.

These principles were not always applied perfectly on the first attempt. Refactoring was a core part of the learning process.

---

## 🔹 Java vs C++: Practical Lessons from the Translation

Translating the project from C++ to Java highlighted both **comforting similarities** and **important conceptual differences**.

### Shared Ground

* Object-oriented design concepts transferred cleanly (encapsulation, inheritance, polymorphism)
* Enum-driven state modelling remained effective
* Modular architecture and layered responsibilities translated well

This reinforced the idea that **good design is largely independent of any specific programming language**, focusing on core concepts, problem-solving, or 
universal principles rather than syntax.

### Meaningful Differences

* **Object references**
  In Java, all objects are passed by reference (technically, references by value). This required more deliberate thinking about shared state, copying data
  (e.g. board snapshots), and unintended side effects.

* **Memory management**
  Garbage collection removed manual lifetime concerns, but shifted focus towards ownership, mutability, and defensive copying.

* **Error handling**
  Java’s exception model encouraged clearer separation between normal flow and error cases, particularly for invalid input and illegal states.

* **Collections and immutability**
  Java’s immutable collections (`List.of`) forced explicit decisions about when data should be copied versus shared, reinforcing discipline around state 
  changes.

Takeaways:

- Translating the project deepened understanding of language-specific trade-offs.
- Forced deliberate thinking about object ownership, mutability, and exception safety.
- Reinforced cross-language OOP thinking.

---

## 💡 Key Learning Outcomes

This project strengthened both **technical ability** and **engineering mindset**.

* **Version control as a process**
  Commits were treated as documentation: small, purposeful, and descriptive. This reinforced thinking in terms of *changesets* rather than isolated edits.

* **Stronger fundamentals**
  Rewriting familiar logic exposed assumptions and gaps that were easy to overlook the first time. The repetition itself became a learning tool.

* **Improved design judgement**
  Knowing when to refactor, when to keep things simple, and when abstraction adds value became clearer with each iteration.

* **Language adaptability**
  The project reinforced confidence that learning a new language is less about syntax, and more about understanding constraints, idioms, and trade-offs.

The project shifted focus from “making it work” to **making it understandable, maintainable, and intentional**. The overall architecture remained familiar, 
but Java enforced a more explicit and disciplined structure, which influenced design decisions and highlighted trade-offs that were less visible in C++.

---

## ⚙️ How to Run

```bash
git clone https://github.com/Mahlatse-Seloane/Reversi-Java.git
cd Reversi-Java
javac src/*.java
java src.Main
```

The game runs in the console, allowing Human vs AI, AI vs AI, or Human vs Human. All moves, flips, and game results are logged in a structured, readable format.

---

### 📂 Project Structure

```
ReversiJava/
 ├─ src/
 │    ├─ Main.java
 │    ├─ Reversi.java
 │    ├─ GameManager.java
 │    ├─ Board.java
 │    ├─ MoveInspector.java
 │    ├─ PlayerType.java
 │    ├─ RandomAI.java
 │    ├─ SelectiveAI.java
 │    ├─ GameLogger.java
 │    ├─ InputValidator.java
 │    ├─ ConsoleColours.java
 │    ├─ BoardRenderContext.java
 │    └─ EndResults.java
 └─ README.md
```

---

### 💡 Reflection

Through this project, I applied **software engineering principles in Java** while reflecting on my C++ experience:

* Moved logic from `Main` into small, focused classes (`Reversi`, `GameManager`) to enforce single responsibility.
* Implemented **robust input validation** and structured logging.
* Designed a flexible AI framework using **polymorphism** and **modular design**.
* Learned how to **translate C++ idioms to Java**, respecting language constraints and best practices.
* Practiced **Git and GitHub workflows** in a structured, iterative project.

This project represents a deliberate move toward **professional, maintainable, and extensible code**, suitable for further AI experimentation or UI extension.

---

## 🚀 Possible Extensions

* Introduce smarter AI strategies (minimax, mobility heuristics, Monte Carlo).
* Add optional interactive or GUI mode.
* Implement automated unit tests for `MoveInspector`, AI decision-making, and input validation.
* Enhance logging with optional colored board snapshots or real-time game view.

---

### 🛠️ Technologies Used

| Language | Tools                                                  |
| -------- | ------------------------------------------------------ |
| Java 17  | JDK, Console, IntelliJ IDEA Community Edition 2025.2.4 |
| Git      | GitHub                                                 |

---

## 👤 Author

**Mahlatse Seloane**
BEngSc (Digital Arts) – University of the Witwatersrand
Game Developer / Software Developer

> A professional Java reimagining of a C++ academic project, focused on reinforcing software engineering fundamentals, language adaptability, and disciplined development practices.

---
