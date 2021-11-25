# TablutAI
AI Player for the University of Bologna Competition.

#Description
White and Black players have been implemented using MinMax algorithm with AlphaBeta Pruning and the max depth has been set to 4.


## Installation on Ubuntu/Debian 

From console, run these commands to install JDK 11 and ANT:

```
sudo apt update
sudo apt install openjdk-11-jdk -y
sudo apt install ant -y
```

Now, clone the project repository:

```
git clone https://github.com/fede977/Tablut.git
```

## Run the Server

The easiest way is to utilize the ANT configuration script from console.
Go into the project folder (the folder with the `build.xml` file):
```
cd Tablut/
```

Compile the project:

```
ant clean
ant compile
```

The compiled project is in  the `build` folder.
Run the `server` with:

```
ant server
```

To start the game, run the `white` player with this command:

```
ant lastminute -Darg0=WHITE -Darg1=1 -Darg2=localhost
```
And then run the `black` player:
```
ant lastminute -Darg0=BLACK -Darg1=1 -Darg2=localhost
```
To run other classes, change the `build.xml` file and re-compile.
