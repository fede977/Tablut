package it.unibo.ai.didattica.competition.tablut.lastminute.game;

public class Positions {
    private int x;
    private int y;
    private int[] old;
    boolean leftTheCamp;


    public Positions(int x, int y, int[] old, boolean leftTheCamp){
        this.x = x;
        this.y = y;
        this.old = old;
        this.leftTheCamp = leftTheCamp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[] getOld(){
        return old;
    }


    public boolean leftCamp() {
        return leftTheCamp;
    }

    @Override
    public boolean equals(Object o){
        if(null != o && getClass() == o.getClass()){
            Positions loc = (Positions) o;
            return x == loc.getX() && y == loc.getX();
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + this.x+","+this.y+"), ["+this.old[0]+","+this.old[1]+"]";
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + x;
        result = 43 * result + y;
        return result;
    }








}
