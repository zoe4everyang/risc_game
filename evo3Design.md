## Evo3 Design

#### Upgrade Spy
##### Request HTTP interface
#### Move Spy
##### Request HTTP interface
#### Cloak
##### Request HTTP interface
#### Move Commit

##### Request HTTP interface

```http
POST /act/commit/
```
##### Request Format:
| Parameter name | type   | comments                                |
| -------------- | ------ | --------------------------------------- |
| username       | String | username, unique for one player         |
| roomids        | []int  | roomid available to join for the player |
```json
{
    "Username": "qf37",
    "roomId": 1,
}
```
##### Response Format:
| Parameter name | type         | comments                                                     |
| -------------- | ------------ | ------------------------------------------------------------ |
| PlayerInfo     | Player       | Information of players                                       |
| Territories    | []int        | Information of the map                                       |
| lose           | bool         | indicate if the play has lost                                |
| end            | bool         | indicate if the game was over                                |
| playerList     | []String     | name list of players in the room, with index identifying playerId |
| Visible        | \[\]\[\]bool | The visibility for each players visible[i] represent the visibility of player i on each territory |

```json
{
    "PlayerInfo" : {"PlayerId": 1,
                "Resources": {
               		"Tech_pts" : 30,
                	"Tood_pts" : 30,
                },
                "TechnologyLevel": 2
               },
 
    "Territories" : [
        {
            "Name": "A",
            "TerritoryId" : 0,
            "Owner" : 1,
            "Troop" : [
                {
                    Name : "Guowang",
                    UnitId : 0,
                    LevelName : "primary school",
  					Level : 0,
                    CombatPts: 0
                },
                {
                    name: "Yiheng",
                    UnitId : 1,
                    levelName : "PhD",
                    Level : 5
                    CombatPts: 11
                }
            ],
            "Cost" : 30,
            "Distance" : [0, 1, 2]
        }, 
        {
            "Name" : "B",
            "TerritoryId" : 1,
            "Owner" : 2,
            "Troop" : [
                {
                    Name : "Quanzhi",
                    LevelName : "master",
  					Level : 4,
                    CombatPts: 5
                }
            ],
            "Cost" : 30,
            "Distance" : [1, 0, 1]
        },
        {
            "Name" : "C",
             "TerritoryId" : 2,
            "Owner" : 0,
            "Troop" : [
                {
                    Name : "Tenki",
                    UnitId : 2,
                    LevelName : "middle school",
  					Level : 1,
                    CombatPts: 3
                },
                {
                    Name: "Zoe",
                    UnitId : 3,
                    LevelName : "professor",
                    Level : 6
                    CombatPts: 15
                }
            ],
            "Cost" : 30,
            "Distance" : [2, 1, 0]
        }
    ],
        "lose": false,
        "end": false,
        "playerList": ["Tenki", "Quanzhi", "Yiheng", "Zoe", "Guowang"],
    "Visible":[
        [true, false, true],
        [false, false true]
    ]
}

```
Territory

| Parameter name | Type   | Comments                                                     |
| -------------- | ------ | ------------------------------------------------------------ |
| Name           | string | Name of the territory                                        |
| TerritoryId    | int    | Unique Identity of the territory                             |
| Owner          | int    | The player id which this territory belongs to                |
| Troop          | []Unit | Units                                                        |
| Distance       | []int  | Distance to other territories. Distance[i] indicate the distance toward territory with id i. |
| Cost           | int    | number of food need to go through the territory              |
|                |        |                                                              |

Unit

| Parameter name | Type   | Comments            |
| -------------- | ------ | ------------------- |
| Name           | string | unit name           |
| LevelName      | int    | name of this level  |
| Level          | int    | level of unit       |
| CombatPts      | int    | bonus of this level |
| UnitId         | int    | id of unit          |

Player

| Parameter name  | Type     | Comments               |
| --------------- | -------- | ---------------------- |
| PlayerId        | int      | PlayerId               |
| Resources       | Resource | Resource the unit have |
| TechnologyLevel | int      | technology level       |

Resource

| Parameter name | Type | Comments          |
| -------------- | ---- | ----------------- |
| Tech_pts       | int  | Technology points |
| Food_pts       | int  | Food points       |
|                |      |                   |