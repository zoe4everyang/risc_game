# RISC Game

**Quanzhi Fu, Yiheng Liu, Tianji Qiang, Ziyi Yang, Guowang Zeng**



## 1. Project Info

The Java RISK Game Project is a five-person team project based on Java to replicate the classic strategy game RISK. 
The project implements the core functions and game rules of the original RISK game through the Java language, while optimizing the game experience and providing a user-friendly interface. 
This project can be used as a practical case study for learning Java language programming, object-oriented programming, and game development.



## 2. Version Info

- Java11
- Gradle 7.3.3
- Springboot 2.7.10



## 3. Program Structure

The UML class diagram of our design is shown below:

[此处应有uml图]



#### A. Front-End (Client)

##### Client

Manage the initial connection with server as well as the later game process including sending request and receiving response.

（注意收到server“command错误”的响应后重新要求用户输入command的逻辑）

**Notice:**

A player loses when he no longer controls any territories.

A player who has lost may continue to watch the game if he desires, or may disconnect.

##### 	a. SimpleChecker

​	Do simple pre-check over the input command of players. 

​	(字符串格式正常即可通过check， 目的是减轻server压力)

##### 	b. ResultParser

​	Parse the json message received from the server about how the world looks like now.

##### 	c. View

​	Helps to present the current world. (text-based for Evo1)



 

#### B. Back-End (Server)

##### (1) RISC-back

Responsible for starting the server. (在这里做玩家人数的输入？)

##### (2) Server

Responsible for receiving and parsing requests from the client, and pass it to handler for further process.

##### 	a. CommandParser

​	Parse the json message received from the server about how the world looks like now.

##### (3) RequestHandler

Pass the parsed request to the controller for further process, according to the different type of the command received.

##### (4) Controller

Main body for processing the game logic! 

Generate the initial state + Update the state of each turn + Check game over

**Notice:**

At the end of each turn, one new basic unit shall appear in each territory. 

Move orders effectively occur before attack orders.

Orders may not create new units nor allow a unit to be in two places at once (attacking two territories). 

When a player has won, the server should announce this to all remaining clients, which should display this information. The game then ends. 

When a player has lost, the server should automatically consider his moves to be committed (as the empty set) at the start of each turn. 

​	**a. Player**

​	**b. Territory**

​	Each territory shall be “owned” by one player at any given time. 

​	**c. AbstractWorldFactory** (Abstract Factory)

​	 Generate the world (the initial territories and soldier distribution for each player) according to the number of players(use hardcode).

​	 The territories must form a connected graph (all territories must be reachable from any other territory). 

​	 Each territory shall be adjacent to one or more other territories. 

​	 Each player shall have the same number of initial units.

​	**d. Checker** (Chain of Responsibility)

​		i. Check if adjacency of the attack command

​		ii. Check if the move action is feasible (人够+人过得去)

​	**e. CombatResolver**

​		Responsible for the combat logic. Each territory should have an instance of this resolver. And every turn, the resolver of all territories should be 	traversed.

​	**Combat Logic:**

​	(a) Combat between one attacker and one defender is an iterative process which ends when one side runs out of units in the fight: 

​		i. The server rolls two 20-sided dice (one for the attacker, one for the defender). 

​		ii. The side with the lower roll loses 1 unit (in a tie, the defender wins). 

​	(b) If player A attacks territory X with units from multiple of her own territories, they count as a single combined force. 

​	(c) If multiple players attack the same territories, each attack is resolved sequentially, with the winner of the first attack being the defender in subsequent attacks. For example, if A,B, and C attack territory X held by player D, then B fights D first. If D wins, then C fights D. If C wins, then A fights C. The sequence in which the attacker’s actions are resolved should be randomly determined by the server. 

​	(d) If units from territory X attack territory Y, and at the same time, units from territory Y attack territory X, then they are assumed to take drastically different routes between their territories, missing each other, and ending up at their destination with no combat in the middle. For example, if all units from X attack Y, and all units from Y attack X, then (assuming no other players attack those territories) both attacks will be successful with no units lost by either side (since there will be no defenders at the start of the battle). 



## 4. HTTP Connection Info

We plan to use Restful API to build HTTP connection between client and server.

### MoveCommit

#### Request

```http
POST /Movecommit/
```

| Parameter name | type  | comments                                                   |
| -------------- | ----- | ---------------------------------------------------------- |
| Player ID      | int   | Player's Identity                                          |
| Operation      | int[] | Player's Opeation ID, 1 represent move, 2 represent attack |
| From           | int[] | src territories ids                                        |
| To             | int[] | des territories ids                                        |
| Nums           | int[] | num of units                                               |

```json
{
    "PlayerId": 1,
    "Operation" : [
        1, 
        2, 
        1
    ],
    "From" : [
        0, 
        0, 
        1, 
    ],
    "To" : [
        1, 
        2,
        3
    ],
    "Nums": [
        10,
        20,
        30
    ]
}
```



#### Response

#### Full Response

| Parameter Name | Type        | comments            |
| -------------- | ----------- | ------------------- |
| Player ID      | int         | Player's Identity   |
| Territories    | []Territory | List of Territories |
| playerName     | string      | the name of player  |

#### Territory

| parameter name | Type   | comments                                                     |
| -------------- | ------ | ------------------------------------------------------------ |
| Name           | string | Name of the territory                                        |
| TerritoryId    | int    | Unique Identity of the territory                             |
| Owner          | int    | The player id which this territory belongs to                |
| UnitNum        | int    | Number of Units in this Territory                            |
| Distance       | []int  | Distance to other territories. Distance[i] indicate the distance toward territory with id i. |

#### Response Sample

```json
{
    "playerId": 1,
    "playerName": "Tenki",
    "Territories" : [
        {
            "Name": "A",
            "TerritoryId" : 0,
            "Owner" : 1,
            "UnitNum" : 10086,
            "Distance" : [0, 1, 2],
        }, 
        {
           	"Name" : "B",
            "TerritoryId" : 1,
            "Owner" : 2,
            "UnitNum" : 40086,
            "Distance" : [1, 0, 1],
        },
        {
            "Name" : "C",
             "TerritoryId" : 2,
            "Owner" : 1,
            "UnitNum" : 20086,
            "Distance" : [2, 1, 0]
        }
    ],
}
```

### Start Game

```http
POST /start/
```

#### Response

#### Full Response

| Parameter Name | Type        | comments                             |
| -------------- | ----------- | ------------------------------------ |
| Player ID      | int         | Player's Identity                    |
| Territories    | []Territory | List of Territories                  |
| playerName     | string      | the name of player                   |
| UnitAvailable  | int         | Number of units the player can place |

#### Territory

| parameter name | Type   | comments                                                     |
| -------------- | ------ | ------------------------------------------------------------ |
| Name           | string | Name of the territory                                        |
| TerritoryId    | int    | Unique Identity of the territory                             |
| Owner          | int    | The player id which this territory belongs to                |
| UnitNum        | int    | Number of Units in this Territory (all zero for this response) |
| Distance       | []int  | Distance to other territories. Distance[i] indicate the distance toward territory with id i. |

#### Response Sample

```json
{
    "playerId": 1,
    "playerName": "Tenki",
    "Territories" : [
        {
            "Name": "A",
            "TerritoryId" : 0,
            "Owner" : 1,
            "UnitNum" : 0,
            "Distance" : [0, 1, 2],
        }, 
        {
           	"Name" : "B",
            "TerritoryId" : 1,
            "Owner" : 2,
            "UnitNum" : 0,
            "Distance" : [1, 0, 1],
        },
        {
            "Name" : "C",
             "TerritoryId" : 2,
            "Owner" : 0,
            "UnitNum" : 20086,
            "Distance" : [2, 1, 0]
        }
    ],
    "UnitAvailable": 50
}
```

### Place Unit

```http
POST /place/
```

#### Request

| parameter name | type  | comments                                                     |
| -------------- | ----- | ------------------------------------------------------------ |
| PlayerId       | int   | Player's identity                                            |
| Placement      | int[] | Placement[i] denotes the number of units the plaer deployed on territory with id i. The territory not belongs to the player will be set to -1. |

```json
{
    "PlayerId": 0,
    "Placement": [
        -1, 
        20,
        30,
        50,
        -1,
        -1
    ]
    
}
```

#### Response

#### Full Response

| Parameter Name | Type        | comments            |
| -------------- | ----------- | ------------------- |
| Player ID      | int         | Player's Identity   |
| Territories    | []Territory | List of Territories |
| playerName     | string      | the name of player  |

#### Territory

| parameter name | Type   | comments                                                     |
| -------------- | ------ | ------------------------------------------------------------ |
| Name           | string | Name of the territory                                        |
| TerritoryId    | int    | Unique Identity of the territory                             |
| Owner          | int    | The player id which this territory belongs to                |
| UnitNum        | int    | Number of Units in this Territory                            |
| Distance       | []int  | Distance to other territories. Distance[i] indicate the distance toward territory with id i. |

#### Response Sample

```json
{
    "playerId": 1,
    "playerName": "Tenki",
    "Territories" : [
        {
            "Name": "A",
            "TerritoryId" : 0,
            "Owner" : 1,
            "UnitNum" : 10086,
            "Distance" : [0, 1, 2],
        }, 
        {
           	"Name" : "B",
            "TerritoryId" : 1,
            "Owner" : 2,
            "UnitNum" : 40086,
            "Distance" : [1, 0, 1],
        },
        {
            "Name" : "C",
             "TerritoryId" : 2,
            "Owner" : 1,
            "UnitNum" : 20086,
            "Distance" : [2, 1, 0]
        }
    ],
}
```
