## Controller

### Public Method

#### Controller (constructor)

Create a game with given number of  players

```java
Controller(int numPlayers)
```

#### initGame

Initialize the game. Set the num of units  to each territory.  unitPlacement[i] will denotes the number of units will be placed to territory with index i. 

```java
public void initGame(List<int> unitPlacement)
```

#### getWorld

get the state of current world game. Return a list of territory represent current state of the world.

```java
public List<Territory> getWrold()
```


#### step

Step the game for one turn given a set of operations. Step will use the latter two private method resolveAttack and resolveMove to update the game state (see below). It will automatically execute the operations, and update the unit number and check for wins. Step return true if the game is over after the step. Return false otherwise.  

```java
public bool step(Iterable<int> attackPlayer, Iterable<int> attackFrom, Iterable<int> attackTo, Iterable<int> attackNums, Iterable<int> movePlayer, Iterable<int> moveFrom, Iterable<int> moveTo, Iterable<int> moveNums)
```

**resolveAttack**

Takes four iterables player, from, to, nums, in which (player[i], from[i], to[i], nums[i]) forms a attack operation. The function has no return value. Invalid operation will not be executed. The order of execution will not be guaranteed. Attack from and end at same territory will be merged and executed as a single attack.

Example: 

player = [0, 1, 0]

from = [0,  1, 0]

to = [1, 0, 1]

nums = [20, 10, 30]

ResolveAttack(player, from, to, nums) received three attack operations.

1. Player 0 attack territory 1 using 20 units from territory 0
2. Player 1 attack territory 0 using 10 units from territory 1
3. Player 0 attack territory 1 using 30 units from territory  0

As operation 1 and 3 are from and end at same territories, the operation executed is actually,

1. Player 1 attack territory 0 using 10 units from territory 1
2. Player 0 attack territory 1 using 50 units from territory 0

Or

1. Player 0 attack territory 1 using 50 units from territory 0
2. Player 1 attack territory 0 using 10 units from territory 1

Note that the order of execution are not guaranteed. 

```java
private void resolveAttack(Iterable<int> player, Iterable<int> from, Iterable<int> to, Iterable<int> nums);

```
**resolveMove**

Similar to ResolveAttack. Invalid operation will not be executed. Operation with same from and to will be merged. Order of execution is not guaranteed. 

```java
private void resolveMove(Iterable<int> player, Iterable<int> from, Iterable<int> to, Iterable<int> nums);
```

#### checkEnd

return true is the game is already end. Return false otherwise. 

```java
public bool checkEnd()
```

#### getLosers

Return the list of player id who is defeated. 

```java
public List<int> getLosers()
```

#### getWinner

Return the winner's id if the game is over. Return -1 otherwise

```java
public int getWinner()
```



