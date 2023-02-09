# A1 - Piraten Karpen

  * Author: < Mahad Ahmed >
  * Email: < ahmedmahad25@gmail.com // ahmem73@mcmaster.ca >

## Build and Execution

  * To clean your working directory:
    * `mvn clean`
  * To compile the project:
    * `mvn compile`
  * To run the project in development mode:
    * `mvn -q exec:java` (here, `-q` tells maven to be _quiet_)
  * To run the project with maven:
    * `mvn exec:java -Dexec.args="<player1strategy> <player2strategy> <numofgames> <trace?>"`
    * `example (run with no trace): mvn exec:java -Dexec.args="combo random 42"`
    * `example (run with trace): mvn exec:java -Dexec.args="random random 3 trace"`
    * Remark: **Advise not to run the trace mode with more than 5 games
  * To package the project as a turn-key artefact:
    * `mvn package`
  * To run the packaged delivery:
    * `java -jar target/piraten-karpen-jar-with-dependencies.jar <player1strategy> <player2strategy> <numofgames> <trace?>` 
    * `example (run with no trace): java -jar target/piraten-karpen-jar-with-dependencies.jar random combo 42`
    * `example (run with trace): java -jar target/piraten-karpen-jar-with-dependencies.jar random combo 5 trace`
    * Remark: **Advise not to run the trace mode with more than 5 games

  * To activate the trace mode:
    *  Enter `trace` into the third command line argument for example `.args="random combo 5 trace"`

Remark: **We are assuming here you are using a _real_ shell (e.g., anything but PowerShell on Windows)**

## Feature Backlog

 * Status: 
   * Pending (P), Started (S), Blocked (B), Done (D)
 * Definition of Done (DoD):
   * < A feature will be considered done and complete, if the feature is functioning according to the rulebook and
   business logic, tested with several inputs >

### Backlog 

| MVP? | Id  | Feature  | Status  |  Started  | Delivered |
| :-:  |:-:  |---       | :-:     | :-:       | :-:       |
| x   | F01| Roll eight dices  |  D | 01/14/23 | 01/24/23 |
| x   | F02 | end of turn with three skulls | D | 01/14/23 | 01/24/23 |
| x   | F03 | Player keeping random dice at their turn | D | 01/24/23 | 01/24/23 | 
| x   | F04 | Score points: 3-of-a-kind / *-of-a-kind | D | 01/26/23 | 01/27/23 |
| x   | F05 | Computer score by multiplying number of gold coins and diamonds by 100| D | 01/14/23 | 01/24/24 |
| x   | F06 | Turn based, with each player using same strategy (chose random dice to keep and keep re-rolling until 3 skulls appear| D  | 01/15/23 | 01/24/24 |
| x   | F07 | 42 games for simulation | D | 01/15/23 | 01/15/23 |
| x   | F08 | print percentage of wins for each player| D | 01/24/23 | 01/24/23 |
| x   | F09 | strategy "combo" player and random dice player | D | 01/25/23 | 01/26/23 |
| x   | F10 | specify which type of player is used (random or combo) | D | 01/25/23 | 01/26/23 |
| x | F11 | introduce card deck | D | 01/26/23 | 01/26/23 |
| x | F12 | implement strategy for cards | D | 01/26/23  | 01/26/23 |
| x | F13 | introduce monkeybusiness card | D | 01/28/23  | 01/28/23 |
| x | F14 | implement strategy for monkeybusiness card | D | 01/28/23  | 01/28/23 |

