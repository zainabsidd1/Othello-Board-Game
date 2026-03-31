## Othello Board Game 𖣯

A console-based Othello (Reversi) engine built in Java, emphasizing encapsulation, 
separation of concerns, and single responsibility. Handles the full game lifecycle:
move validation, disc flipping in all 8 directions, turn management, and endgame detection.

## OOP & OOD

The board state and game rules are fully contained within `OthelloBoard`, with no 
leaking logic across classes. `Player` serves as an abstract base — `PlayerHuman`, 
`PlayerGreedy`, and `PlayerRandom` each extend it without touching the core engine. 
Adding a new AI strategy requires only a new subclass. `Move` encapsulates row/column 
coordinates for clean, scalable move passing throughout the program.

OthelloBoard` manages the 8x8 board as a 2D character array, handling move validation 
and flipping in all 8 directions. `Othello` drives the game loop, alternating turns 
and automatically skipping a player if no valid moves exist. The game ends when neither 
player can move, at which point discs are counted and a winner is declared.


## How to run

Open in any Java IDE (IntelliJ, Eclipse, VS Code) and run any of the controller 
files directly: `OthelloControllerHumanVSHuman`, `OthelloControllerHumanVSGreedy`, 
`OthelloControllerHumanVSRandom`, `OthelloControllerRandomVSGreedy`, 
or `OthelloControllerRandomVSRandom`.
