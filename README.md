# Battle Salvo Program 

Developed a battle salvo program that can support human vs. AI or AI vs. AI through proxy server connection and sockets in Java with IntelliJ 
Structured program using MVC principles and implemented features using JSONs to ensure AI vs. AI battle functionality.

## How the Battle Salvo game works:

<img width="926" alt="Screenshot 2023-09-23 at 21 02 59" src="https://github.com/lilyshiomitsu/battle-salvo/assets/125512346/4d0229ea-db10-4d87-b46f-2648d573c5a9">

- Supports boards of varying sizes from 6x6 up to 15x15
- Fleet number does not exceed the number of the smaller dimension. Program handles invalid inputs gracefully.
- Opponent's board and your board with your ship locations are shown.

<img width="391" alt="Screenshot 2023-09-23 at 21 08 31" src="https://github.com/lilyshiomitsu/battle-salvo/assets/125512346/ff77f5f2-98d0-44db-9282-65184e8b0dc4">

- In battle salvo, the number of ships you have left determines the number of shots you have
- Once all shots are inputted, whether your shots were a hit or miss is determined by 'X' for hit, or 'M' for missed
- The opponent's (AI's) shots are visible as well, with the same symbols

<img width="303" alt="Screenshot 2023-09-23 at 21 32 02" src="https://github.com/lilyshiomitsu/battle-salvo/assets/125512346/4a4467ae-eea1-497f-8a83-c6b0b623f06d">

- As the game progresses, the number of shots that both you and your opponent have decreases as the number of sunk ships increases

<img width="224" alt="Screenshot 2023-09-23 at 21 34 19" src="https://github.com/lilyshiomitsu/battle-salvo/assets/125512346/caac8e3e-ec52-4d90-a491-6781eb04e76e">

- You win if you sink your opponent's ships before they do
- You lose if your opponent sinks your ships before you sink theirs
- You draw if you both sink your last ships on the same turn
  
