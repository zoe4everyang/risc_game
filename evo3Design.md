## Evo3 Design

#### Upgrade Spy
##### Request HTTP interface

```http
POST /act/upspy/
```

##### Request Format

| Parameter name | type   | comments                                           |
| -------------- | ------ | -------------------------------------------------- |
| username       | String | username, unique for one player                    |
| roomids        | []int  | roomid available to join for the player            |
| TerritoryId    | int    | The territory where the unit to be upgraded locate |
| UnitLevel      | int    | The level of unit to be upgraded                   |

```json
{
    "Username": "qf37",
    "roomId": 1,
    "TerritoryId": 0,
    "UnitLevel":2 
}
```

##### Response Format 

| Parameter name | type | comments                         |
| -------------- | ---- | -------------------------------- |
| Success        | bool | Indicate if the operation succed |
| ErrorCode      | int  | Error code to classify the error |
| Information    | int  | Information related              |


```json
{
    "Success" : False,
    "ErrorCode" : 5,
    "Information" :  "Insufficient Resources",
}
```



#### Move Spy

##### Request HTTP interface

```http
POST /act/movespy/
```

##### Request Format

| Parameter name | type   | comments                                |
| -------------- | ------ | --------------------------------------- |
| username       | String | username, unique for one player         |
| roomids        | []int  | roomid available to join for the player |
| target         | int    | The target territory to move            |

```json
{
    "Username": "qf37",
    "roomId": 1,
	"target": 1,
}
```

##### 

##### Response Format 

| Parameter name | type | comments                         |
| -------------- | ---- | -------------------------------- |
| Success        | bool | Indicate if the operation succed |
| ErrorCode      | int  | Error code to classify the error |
| Information    | int  | Information related              |


```json
{
    "Success" : False,
    "ErrorCode" : 5,
    "Information" :  "Target not adjacent to current position",
}
```



#### Upgrade Cloak

##### Request HTTP interface

```http
POST /act/upcloak/
```

##### Request Format

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

##### 

##### Response Format 

| Parameter name | type | comments                         |
| -------------- | ---- | -------------------------------- |
| Success        | bool | Indicate if the operation succed |
| ErrorCode      | int  | Error code to classify the error |
| Information    | int  | Information related              |


```json
{
    "Success" : False,
    "ErrorCode" : 5,
    "Information" :  "Insufficient Resources",
}
```





#### Set Cloak
##### Request HTTP interface

```http
POST /act/setcloak/
```

##### Request Format

| Parameter name | type   | comments                                |
| -------------- | ------ | --------------------------------------- |
| username       | String | username, unique for one player         |
| roomids        | []int  | roomid available to join for the player |
| TerritoryId    | int    | The territory to be cloaked             |

```json
{
    "Username": "qf37",
    "roomId": 1,
    "TerritoryId": 0,
}
```

##### 

##### Response Format 

| Parameter name | type | comments                         |
| -------------- | ---- | -------------------------------- |
| Success        | bool | Indicate if the operation succed |
| ErrorCode      | int  | Error code to classify the error |
| Information    | int  | Information related              |


```json
{
    "Success" : False,
    "ErrorCode" : 5,
    "Information" :  "Insufficient Resources",
}
```

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
| Parameter name | type     | comments                                                     |
| -------------- | -------- | ------------------------------------------------------------ |
| PlayerInfo     | Player   | Information of players                                       |
| Territories    | []int    | Information of the map                                       |
| lose           | bool     | indicate if the play has lost                                |
| end            | bool     | indicate if the game was over                                |
| playerList     | []String | name list of players in the room, with index identifying playerId |
|                |          |                                                              |

```json
{
    "PlayerInfo" : {"PlayerId": 1,
                "Resources": {
               		"Tech_pts" : 30,
                	"Tood_pts" : 30,
                },
                "TechnologyLevel": 2,
                "Visible":[true, false, true],
                "Visited":[true, true, true],
                "SpyPos": 0,
                "HasSpy": true,
                "canCloak" : true
               },
 
    "Territories" : [
        {
            "Name": "A",
            "TerritoryId" : 0,
            "Owner" : 1,
            "Troop" : [              [
                5,
                4,
                0,
                0,
                1,
                2,
                3
            ],
            "Cost" : 30,
            "Distance" : [0, 1, 2],
            "Cloaked": 3
        }, 
        {
            "Name" : "B",
            "TerritoryId" : 1,
            "Owner" : 2,
            "Troop" :
                [
                5,
                4,
                0,
                0,
                1,
                2,
                3
            ]
           ,
            "Cost" : 30,
            "Distance" : [1, 0, 1]
        },
        {
            "Name" : "C",
             "TerritoryId" : 2,
            "Owner" : 0,
            "Troop" : [
                5,
                4,
                0,
                0,
                1,
                2,
                3
            ],
            "Cost" : 30,
            "Distance" : [2, 1, 0],
            "Cloaked": 2
        }
    ],
        "lose": false,
        "end": false,
        "playerList": ["Tenki", "Quanzhi", "Yiheng", "Zoe", "Guowang"],

}

```
Territory

| Parameter name | Type   | Comments                                                     |
| -------------- | ------ | ------------------------------------------------------------ |
| Name           | string | Name of the territory                                        |
| TerritoryId    | int    | Unique Identity of the territory                             |
| Owner          | int    | The player id which this territory belongs to                |
| Troop          | []int  | Number of units in each level, troop[i] indicate the number units with level i |
| Distance       | []int  | Distance to other territories. Distance[i] indicate the distance toward territory with id i. |
| Cost           | int    | number of food need to go through the territory              |
| Cloaked        | int    | The number of round remained hiding this territory. cloacked <= 0 indicate the territory is visible to everyone. |
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

| Parameter name  | Type     | Comments                                                     |
| --------------- | -------- | ------------------------------------------------------------ |
| PlayerId        | int      | PlayerId                                                     |
| Resources       | Resource | Resource the unit have                                       |
| TechnologyLevel | int      | technology level                                             |
| View            | []bool   | Indicate which territory is visible to the player, view[i] indicate the visibility of ith territory for this player |
| SpyPos          | int      | indicate the current spy position, -1 indicate spy haven't been upgraded |
| HasSpy          | bool     | Indicate if the spy have been upgraded                       |
| Visited         | []bool   | Indicate if the territory has been visible to current player before |

Resource

| Parameter name | Type | Comments          |
| -------------- | ---- | ----------------- |
| Tech_pts       | int  | Technology points |
| Food_pts       | int  | Food points       |
|                |      |                   |