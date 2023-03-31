# risk_game

## Response Parameter

### Full Response

| Parameter Name | Type        | comments            |
| -------------- | ----------- | ------------------- |
| Player ID      | int         | Player's Identity   |
| Territories    | []Territory | List of Territories |
| playerName     | string      | the name of player  |

### Territory

| parameter name | Type   | comments                                                     |
| -------------- | ------ | ------------------------------------------------------------ |
| Name           | string | Name of the territory                                        |
| TerritoryId    | int    | Unique Identity of the territory                             |
| Owner          | int    | The player id which this territory belongs to                |
| UnitNum        | int    | Number of Units in this Territory                            |
| Distance       | []int  | Distance to other territories. Distance[i] indicate the distance toward territory with id i. |



## Response Sample

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

