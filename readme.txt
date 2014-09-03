s3408657 Lam Thien Duy
s3372790 Nguyen Manh Hung
s3408652 Hoang Trung Thanh


In this second part of the assignment, after the discussion with you only blackboard, we had to redo the game logic, which include more than a thousand codes eg. more than 2 weeks. 


Secondly, in the first a part, the structure of client-server we used was string. This time we used object IO, which is more efficient. Therefore, we are able to produce a full game play, so that players can bet and play game as it should be. It is a much improve compare to the assignment 1, since we were only able to send cards and winner at the time.


Thirdly, we added a sqlite database to the game. This help us manage the account info as well as update the player money much better.






Problems remain:
- Have to hard code the number of player in a game every time starting new server.
- Can not register a new account
- Player with zero money still be able to join the game.
- Small blind and big blind do not increase over time. They also does not change from player to player.
- Side pot does not working properly when “all in” situations appear.
- Some cases in one pair and two pair are not recognized