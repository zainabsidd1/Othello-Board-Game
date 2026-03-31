## Othello Board Game 𖣯

A console-based Othello (Reversi) engine built in Java, emphasizing encapsulation, 
separation of concerns, and single responsibility. Handles the full game lifecycle — 
move validation, disc flipping in all 8 directions, turn management, and endgame detection.

## OOP & OOD

The board state and game rules are fully contained within `OthelloBoard`, with no 
leaking logic across classes. `Player` serves as an abstract base — `PlayerHuman`, 
`PlayerGreedy`, and `PlayerRandom` each extend it without touching the core engine. 
Adding a new AI strategy requires only a new subclass. `Move` encapsulates row/column 
coordinates for clean, scalable move passing throughout the program.

## How to run
```bash
mvn compile
mvn exec:java -Dexec.mainClass="ca.utoronto.utm.assignment1.othello.OthelloControllerHumanVSGreedy"
```

Replace `OthelloControllerHumanVSGreedy` with any matchup:
`OthelloControllerHumanVSHuman`, `OthelloControllerHumanVSRandom`, 
`OthelloControllerRandomVSGreedy`, or `OthelloControllerRandomVSRandom`.
