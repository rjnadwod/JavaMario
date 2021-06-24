@echo off
javac Game.java View.java Controller.java Game.java Model.java Tube.java Json.java Mario.java Goomba.java Sprite.java Fireball.java
if %errorlevel% neq 0 (
	echo There was an error; exiting now.	
) else (
	echo Compiled correctly!  Running Game...
	java Game	
)