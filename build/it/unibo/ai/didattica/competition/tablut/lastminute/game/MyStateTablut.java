package it.unibo.ai.didattica.competition.tablut.lastminute.game;

import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static it.unibo.ai.didattica.competition.tablut.domain.State.Pawn;
import static it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class MyStateTablut {

    private static final long serialVersionUID = 1L;

    public enum Area{
        NORMAL("N"), CASTLE("K"), CAMPS("C"), ESCAPES("E");
        private final String area;

        private Area(String a) {
            this.area = a;
        }

        public boolean equalsArea(String otherArea){
            return (otherArea == null) ? false : this.area.equals(otherArea);
        }

        public String toString(){
            return this.area;
        }
    }


    public static final int WIDTH = 9;
    public static final int HEIGHT = 9;
    public static final int KING_POSITION = 4;
    private Pawn board[][];
    private Area boardArea[][];
    private Turn turn;
    private int currentDepth;


    public MyStateTablut(int depth){
        this.setBoard(new Pawn[MyStateTablut.WIDTH][MyStateTablut.HEIGHT]);
        this.setBoardArea(new Area[MyStateTablut.WIDTH][MyStateTablut.WIDTH]);
        this.initBoard();
        this.setTurn(Turn.WHITE);
        this.setCurrentDepth(depth);
    }

    public MyStateTablut stateAdapter(State state){
        MyStateTablut result = new MyStateTablut(4);

        Pawn oldBoard[][] = state.getBoard();
        Pawn newBoard[][] = result.getBoard();

        for(int i = 0; i< state.getBoard().length;i++){
            for (int j = 0; j<state.getBoard().length; j++){
                newBoard[i][j] = oldBoard[i][j];
            }
        }

        result.setBoard(newBoard);
        result.setTurn(state.getTurn());
        return result;
    }

    public MyStateTablut clone(){
        MyStateTablut result = new MyStateTablut(this.getCurrentDepth());

        Pawn old[][] = this.getBoard();
        Pawn clone[][] = result.getBoard();

        for(int i =0; i<this.getBoard().length;i++){
            for (int j =0; j<this.getBoard().length;j++){
                clone[i][j] = old[i][j];
            }
        }
        result.setBoard(clone);
        result.setTurn(this.getTurn());
        result.setCurrentDepth(this.getCurrentDepth());
        return result;
    }

    @Override
    public String toString() {
        for(int i=0; i < this.getBoard().length; i++) {
            for(int j=0; j < this.getBoard().length; j++) System.out.print(this.getBoard()[i][j] + "|");
            System.out.println("");
        }
        return "";
    }

    public void initBoard() {

        // initialize pawns on board
        for (int i = 0; i < this.getBoard().length; i++) {
            for (int j = 0; j < this.getBoard().length; j++) {
                this.setPawn(i,  j, Pawn.EMPTY);
            }
        }
        this.setPawn(MyStateTablut.KING_POSITION,  MyStateTablut.KING_POSITION, Pawn.THRONE);
        this.setPawn(MyStateTablut.KING_POSITION,  MyStateTablut.KING_POSITION, Pawn.KING);

        this.setPawn(2,  4, Pawn.WHITE);
        this.setPawn(3,  4, Pawn.WHITE);
        this.setPawn(5,  4, Pawn.WHITE);
        this.setPawn(6,  4, Pawn.WHITE);
        this.setPawn(4,  2, Pawn.WHITE);
        this.setPawn(4,  3, Pawn.WHITE);
        this.setPawn(4,  5, Pawn.WHITE);
        this.setPawn(4,  6, Pawn.WHITE);


        this.setPawn(0,  3, Pawn.BLACK);
        this.setPawn(0,  4, Pawn.BLACK);
        this.setPawn(0,  5, Pawn.BLACK);
        this.setPawn(1,  4, Pawn.BLACK);
        this.setPawn(8,  3, Pawn.BLACK);
        this.setPawn(8,  4, Pawn.BLACK);
        this.setPawn(8,  5, Pawn.BLACK);
        this.setPawn(7,  4, Pawn.BLACK);
        this.setPawn(3,  0, Pawn.BLACK);
        this.setPawn(4,  0, Pawn.BLACK);
        this.setPawn(5,  0, Pawn.BLACK);
        this.setPawn(4,  1, Pawn.BLACK);
        this.setPawn(3,  8, Pawn.BLACK);
        this.setPawn(4,  8, Pawn.BLACK);
        this.setPawn(5,  8, Pawn.BLACK);
        this.setPawn(4,  7, Pawn.BLACK);

        // initialize area on board
        for (int i = 0; i < this.getBoardArea().length; i++) {
            for (int j = 0; j < this.getBoardArea().length; j++) {
                this.setArea(i, j, Area.NORMAL);
            }
        }

        this.setArea(4, 4, Area.CASTLE);

        //this.setArea(0, 0, Area.ESCAPES);
        this.setArea(0, 1, Area.ESCAPES);
        this.setArea(0, 2, Area.ESCAPES);
        this.setArea(0, 6, Area.ESCAPES);
        this.setArea(0, 7, Area.ESCAPES);
        //this.setArea(0, 8, Area.ESCAPES);
        this.setArea(1, 0, Area.ESCAPES);
        this.setArea(2, 0, Area.ESCAPES);
        this.setArea(6, 0, Area.ESCAPES);
        this.setArea(7, 0, Area.ESCAPES);
        //this.setArea(8, 0, Area.ESCAPES);
        this.setArea(8, 1, Area.ESCAPES);
        this.setArea(8, 2, Area.ESCAPES);
        this.setArea(8, 6, Area.ESCAPES);
        this.setArea(8, 7, Area.ESCAPES);
        //this.setArea(8, 8, Area.ESCAPES);
        this.setArea(1, 8, Area.ESCAPES);
        this.setArea(2, 8, Area.ESCAPES);
        this.setArea(6, 8, Area.ESCAPES);
        this.setArea(7, 8, Area.ESCAPES);

        this.setArea(0, 3, Area.CAMPS);
        this.setArea(0, 4, Area.CAMPS);
        this.setArea(0, 5, Area.CAMPS);
        this.setArea(1, 4, Area.CAMPS);
        this.setArea(8, 3, Area.CAMPS);
        this.setArea(8, 4, Area.CAMPS);
        this.setArea(8, 5, Area.CAMPS);
        this.setArea(7, 4, Area.CAMPS);
        this.setArea(3, 0, Area.CAMPS);
        this.setArea(4, 0, Area.CAMPS);
        this.setArea(5, 0, Area.CAMPS);
        this.setArea(4, 1, Area.CAMPS);
        this.setArea(3, 8, Area.CAMPS);
        this.setArea(4, 8, Area.CAMPS);
        this.setArea(5, 8, Area.CAMPS);
        this.setArea(4, 7, Area.CAMPS);
    }

    public int getCurrentDepth() {
        return this.currentDepth;
    }

    public void setCurrentDepth(int currentDepth) {
        this.currentDepth = currentDepth;
    }

    public Pawn[][] getBoard() {
        return this.board;
    }

    public void setBoard(Pawn[][] board) {
        this.board = board;
    }

    public Area[][] getBoardArea() {
        return this.boardArea;
    }

    public void setBoardArea(Area[][] boardArea) {
        this.boardArea = boardArea;
    }

    public Turn getTurn() {
        return this.turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public Pawn getPawn(int row, int column) {
        return this.board[row][column];
    }

    public void setPawn(int row, int column, Pawn pawn) {
        this.board[row][column] = pawn;
    }

    public Area getArea(int row, int column) {
        return this.boardArea[row][column];
    }

    public void setArea(int row, int column, Area area) {
        this.boardArea[row][column] = area;
    }

    public void removePawn(int row, int column) {
        this.board[row][column] = Pawn.EMPTY;
    }

    private int[] getKingPosition() {
        for (int i = 0; i < this.getBoard().length; i++) {
            for (int j = 0; j < this.getBoard().length; j++) {
                if(this.getPawn(i, j) == Pawn.KING) {
                    return new int[] {i,j};
                }
            }
        }
        return null;
    }

    public void printBoardArea() {
        for(int i=0; i < this.getBoardArea().length; i++) {
            for(int j=0; j < this.getBoardArea().length; j++) {
                System.out.print(this.getBoardArea()[i][j]+ "");
            }
            System.out.println("|");
        }
    }

    public void printBoard() {
        for(int i=0; i < this.getBoard().length; i++) {
            for(int j=0; j < this.getBoard().length; j++) {
                System.out.print(this.getBoard()[i][j]+ "|");
            }
            System.out.println("");
        }
    }

    public String getBox(int row, int col){
        String res;
        char c = (char) (col+97);
        res = c+""+(row+1);
        return res;
    }

    public void applyMove(XYOld action){
        if(this.getCurrentDepth()!=0){
            if(this.getTurn().equals(Turn.WHITE)){
                if(this.getPawn(action.getOld()[0], action.getOld()[1]).equals(Pawn.KING)){
                    this.setPawn(action.getX(),action.getY(), Pawn.KING);
                }else{
                    this.setPawn(action.getX(),action.getY(), Pawn.WHITE);
                }
                this.setPawn(action.getOld()[0], action.getOld()[1], Pawn.EMPTY);
                this.setTurn(Turn.BLACK);
            } else {
                this.setPawn(action.getX(),action.getY(), Pawn.BLACK);
                this.setPawn(action.getOld()[0], action.getOld()[1], Pawn.EMPTY);
                this.setTurn(Turn.WHITE);
            }
            this.eat(action);
        }
    }

    public int getNumberCloseToTheKingOf(Pawn color){
        int pawnsSurroundingKing = 0;
        int[] kingPosition = this.getKingPosition();

        //diagonal
        if((kingPosition[1] -1)>=0 && this.getPawn(kingPosition[0], kingPosition[1] -1).equals(color)){
            pawnsSurroundingKing ++;
        }
        if ((kingPosition[1]+1)<(MyStateTablut.KING_POSITION *2+1) && this.getPawn(kingPosition[0],kingPosition[1]+1).equals(color)){
            pawnsSurroundingKing++;
        }
        if((kingPosition[0]-1)>= 0 && this.getPawn(kingPosition[0]-1, kingPosition[1]).equals(color)){
            pawnsSurroundingKing++;
        }
        if((kingPosition[0]+1)<(MyStateTablut.KING_POSITION*2+1)&&this.getPawn(kingPosition[0]+1,kingPosition[1]).equals(color)){
            pawnsSurroundingKing++;
        }

        //angle
        if((kingPosition[0] - 1 >= 0) && ((kingPosition[1] - 1) >= 0) && this.getPawn(kingPosition[0] - 1, kingPosition[1] - 1).equals(color)) {
            pawnsSurroundingKing++;
        }
        if(((kingPosition[0] + 1) < (MyStateTablut.KING_POSITION * 2 + 1)) && ((kingPosition[1] + 1) < (MyStateTablut.KING_POSITION * 2 + 1))
                && this.getPawn(kingPosition[0] + 1, kingPosition[1] + 1).equals(color)) {
            pawnsSurroundingKing++;
        }
        if(((kingPosition[0] - 1) >= 0) && ((kingPosition[1] + 1) < (MyStateTablut.KING_POSITION * 2 + 1))
                && this.getPawn(kingPosition[0] - 1, kingPosition[1] + 1).equals(color)) {
            pawnsSurroundingKing++;
        }
        if((kingPosition[0] + 1) < (MyStateTablut.KING_POSITION  * 2 + 1) && ((kingPosition[1] - 1) >= 0)
                && this.getPawn(kingPosition[0] + 1, kingPosition[1] - 1).equals(color)) {
            pawnsSurroundingKing++;
        }

        return pawnsSurroundingKing;
    }

    public int getNumberOfCampsCloseToKing() {
        int closeCamps = 0;

        int[] kingPosition = this.getKingPosition();

        // cross
        if((kingPosition[1] - 1) >= 0 && this.getArea(kingPosition[0], kingPosition[1] - 1).equals(Area.CAMPS)) {
            closeCamps++;
        }
        if((kingPosition[1] + 1) < (MyStateTablut.KING_POSITION * 2 + 1) && this.getArea(kingPosition[0], kingPosition[1] + 1).equals(Area.CAMPS)) {
            closeCamps++;
        }
        if((kingPosition[0] - 1) >= 0 && this.getArea(kingPosition[0] - 1, kingPosition[1]).equals(Area.CAMPS)) {
            closeCamps++;
        }
        if((kingPosition[0] + 1) < (MyStateTablut.KING_POSITION * 2 + 1) && this.getArea(kingPosition[0] + 1, kingPosition[1]).equals(Area.CAMPS)) {
            closeCamps++;
        }

        // angle
        if((kingPosition[0] - 1 >= 0) && ((kingPosition[1] - 1) >= 0) && this.getArea(kingPosition[0] - 1, kingPosition[1] - 1).equals(Area.CAMPS)) {
            closeCamps++;
        }
        if(((kingPosition[0] + 1) < (MyStateTablut.KING_POSITION * 2 + 1)) && ((kingPosition[1] + 1) < (MyStateTablut.KING_POSITION * 2 + 1))
                && this.getArea(kingPosition[0] + 1, kingPosition[1] + 1).equals(Area.CAMPS)) {
            closeCamps++;
        }
        if(((kingPosition[0] - 1) >= 0) && ((kingPosition[1] + 1) < (MyStateTablut.KING_POSITION * 2 + 1))
                && this.getArea(kingPosition[0] - 1, kingPosition[1] + 1).equals(Area.CAMPS)) {
            closeCamps++;
        }
        if((kingPosition[0] + 1) < (MyStateTablut.KING_POSITION  * 2 + 1) && ((kingPosition[1] - 1) >= 0)
                && this.getArea(kingPosition[0] + 1, kingPosition[1] - 1).equals(Area.CAMPS)) {
            closeCamps++;
        }

        return closeCamps;
    }

    public int isKingInCastle(){
        int[] kingPosition = this.getKingPosition();
        return(kingPosition[0] == KING_POSITION && kingPosition[1] == KING_POSITION)?1:0;
    }

    public int isKingEscaped(){
        int[] kingPosition = this.getKingPosition();
        return(this.getArea(kingPosition[0],kingPosition[1]).equals(Area.ESCAPES))?1:0;
    }

    public double getDistanceFromClosestEscape(){
        int[] kingPosition = this.getKingPosition();
        List<int[]> escapes = new ArrayList<>();
        List<Double> min = new ArrayList<>();

        for(int i = 0; i<this.getBoard().length;i++) {
            for (int j = 0; j < this.getBoard().length; j++) {
                if (this.getArea(i, j).equals(Area.ESCAPES) && this.getPawn(i, j).equals(Pawn.EMPTY)) {
                    escapes.add(new int[]{i, j});
                }
            }
        }
        escapePos(kingPosition, escapes, min);
        return 10 - Collections.min(min);
    }

    private void escapePos(int[] kingPosition, List<int[]> escapes, List<Double> min) {
        for (int i = 0; i < escapes.size(); i++) {
            double deltaX = Math.abs(kingPosition[0] - (escapes.get(i)[0]));
            double deltaY = Math.abs(kingPosition[1] - (escapes.get(i)[1]));
            min.add(Math.sqrt(deltaX * deltaX + deltaY * deltaY));
        }
    }

    public double getDistanceFromBlacks(){
        int[] kingPosition = this.getKingPosition();
        List<int[]> blacks = new ArrayList<>();
        List<Double> min = new ArrayList<>();

        for(int i = 0; i<this.getBoard().length;i++) {
            for (int j = 0; j < this.getBoard().length; j++) {
                if (this.getPawn(i, j).equals(Pawn.BLACK)) {
                    blacks.add(new int[]{i, j});
                }
            }
        }
        escapePos(kingPosition, blacks, min);
        return min.stream().mapToDouble(Double::doubleValue).sum();
    }

    public int getNumberOf(Pawn color){
        int count = 0;
        for(int i = 0; i<this.getBoard().length;i++){
            for(int j = 0; j<this.getBoard().length;j++){
                if(this.getPawn(i,j).equals(color)){
                    count++;
                }
            }
        }
        return count;
    }

    private void eat(XYOld action){

        if(this.getTurn().equals(Turn.WHITE)){
            for (int i = 0; i < this.getBoard().length;i++){
                for(int j=0; j<this.getBoard().length;j++){
                    if(this.getPawn(i,j).equals(Pawn.WHITE)){
                        /**
                         * B
                         * W
                         * B
                         */
                        if(((i-1)>=0 && (i+1)<this.getBoard().length)&&this.getPawn(i-1,j).equals(Pawn.BLACK) && this.getPawn(i+1,j).equals(Pawn.BLACK)){
                            if((action.getX()==(i-1) && action.getY()==j)|| ((action.getX() == (i+1) && action.getY()==j))){
                                this.setPawn(i,j,Pawn.EMPTY);
                            }
                        }
                        /**
                         * BWB
                         */
                        if(((j-1)>=0 && (j+1)< this.getBoard().length) && this.getPawn(i,j-1).equals(Pawn.BLACK)&& this.getPawn(i, j+1).equals(Pawn.BLACK)){
                            if((action.getX() == (i) && action.getY() == (j - 1)) || ((action.getX() == (i) && action.getY() == (j + 1)))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }

                        //Capture with Castle
                        if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getPawn(i - 1, j).equals(Pawn.THRONE) && this.getPawn(i + 1, j).equals(Pawn.BLACK)) {
                            if((action.getX() == (i + 1) && action.getY() == (j))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }
                        if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getPawn(i + 1, j).equals(Pawn.THRONE) && this.getPawn(i - 1, j).equals(Pawn.BLACK)) {
                            if((action.getX() == (i - 1) && action.getY() == (j))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }
                        if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getPawn(i, j - 1).equals(Pawn.THRONE) && this.getPawn(i, j + 1).equals(Pawn.BLACK)) {
                            if((action.getX() == (i) && action.getY() == (j + 1))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }
                        if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getPawn(i, j + 1).equals(Pawn.THRONE) && this.getPawn(i, j - 1).equals(Pawn.BLACK)) {
                            if((action.getX() == (i) && action.getY() == (j - 1))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }

                        //Capture with Camp
                        if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getArea(i - 1, j).equals(Area.CAMPS) && this.getPawn(i + 1, j).equals(Pawn.BLACK)) {
                            if((action.getX() == (i + 1) && action.getY() == (j))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }
                        if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getArea(i + 1, j).equals(Area.CAMPS) && this.getPawn(i - 1, j).equals(Pawn.BLACK)) {
                            if((action.getX() == (i - 1) && action.getY() == (j))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }
                        if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getArea(i, j - 1).equals(Area.CAMPS) && this.getPawn(i, j + 1).equals(Pawn.BLACK)) {
                            if((action.getX() == (i) && action.getY() == (j + 1))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }
                        if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getArea(i, j + 1).equals(Area.CAMPS) && this.getPawn(i, j - 1).equals(Pawn.BLACK)) {
                            if((action.getX() == (i) && action.getY() == (j - 1))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < this.getBoard().length;i++){
                for(int j=0; j<this.getBoard().length;j++){
                    if(this.getPawn(i,j).equals(Pawn.BLACK)){
                        /**
                         * W
                         * B
                         * W
                         */
                        if(((i-1)>=0 && (i+1)<this.getBoard().length)&&this.getPawn(i-1,j).equals(Pawn.WHITE) && this.getPawn(i+1,j).equals(Pawn.WHITE)){
                            if((action.getX()==(i-1) && action.getY()==j)|| ((action.getX() == (i+1) && action.getY()==j))){
                                this.setPawn(i,j,Pawn.EMPTY);
                            }
                        }
                        /**
                         * WBW
                         */
                        if(((j-1)>=0 && (j+1)< this.getBoard().length) && this.getPawn(i,j-1).equals(Pawn.WHITE)&& this.getPawn(i, j+1).equals(Pawn.WHITE)){
                            if((action.getX() == (i) && action.getY() == (j - 1)) || ((action.getX() == (i) && action.getY() == (j + 1)))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }

                        //Capture with Castle
                        if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getPawn(i - 1, j).equals(Pawn.THRONE) && this.getPawn(i + 1, j).equals(Pawn.WHITE)) {
                            if((action.getX() == (i + 1) && action.getY() == (j))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }
                        if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getPawn(i + 1, j).equals(Pawn.THRONE) && this.getPawn(i - 1, j).equals(Pawn.WHITE)) {
                            if((action.getX() == (i - 1) && action.getY() == (j))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }
                        if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getPawn(i, j - 1).equals(Pawn.THRONE) && this.getPawn(i, j + 1).equals(Pawn.WHITE)) {
                            if((action.getX() == (i) && action.getY() == (j + 1))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }
                        if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getPawn(i, j + 1).equals(Pawn.THRONE) && this.getPawn(i, j - 1).equals(Pawn.WHITE)) {
                            if((action.getX() == (i) && action.getY() == (j - 1))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }

                        //Capture with Camp
                        if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getArea(i - 1, j).equals(Area.CAMPS) && this.getPawn(i + 1, j).equals(Pawn.WHITE)) {
                            if((action.getX() == (i + 1) && action.getY() == (j))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }
                        if(((i - 1) >= 0 && (i + 1) < this.getBoard().length) && this.getArea(i + 1, j).equals(Area.CAMPS) && this.getPawn(i - 1, j).equals(Pawn.WHITE)) {
                            if((action.getX() == (i - 1) && action.getY() == (j))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }
                        if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getArea(i, j - 1).equals(Area.CAMPS) && this.getPawn(i, j + 1).equals(Pawn.WHITE)) {
                            if((action.getX() == (i) && action.getY() == (j + 1))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }
                        if(((j - 1) >= 0 && (j + 1) < this.getBoard().length) && this.getArea(i, j + 1).equals(Area.CAMPS) && this.getPawn(i, j - 1).equals(Pawn.WHITE)) {
                            if((action.getX() == (i) && action.getY() == (j - 1))) {
                                this.setPawn(i, j, Pawn.EMPTY);
                            }
                        }
                    }
                }
            }

        }

    }

    public List<XYOld> getAllMoves(){
        if(this.getTurn().equals(Turn.WHITE)) {
            List<XYOld> whiteLegalMoves = new ArrayList<>();
            List<XYOld> whitePositions = new ArrayList<>();
            XYOld buf;
            for (int i = 0; i < this.getBoard().length; i++) {
                for (int j = 0; j < this.getBoard().length; j++) {
                    if (this.getPawn(i, j).equals(Pawn.WHITE) || this.getPawn(i, j).equals(Pawn.KING))  {
                        buf = new XYOld(i, j, new int[]{i, j}, false);
                        whitePositions.add(buf);
                    }
                }
            }

            // for each (i, j) in white position, try every possible move
            for (XYOld white : whitePositions) {
                // move each pawn vertically
                for (int j = 0; j < this.getBoard().length; j++) {
                    //UP
                    if(((white.getY() - j) >= 0) && this.getPawn(white.getX(), white.getY() - j) == Pawn.EMPTY
                            && (this.getArea(white.getX(), white.getY() - j) != Area.CAMPS)
                            && (this.getArea(white.getX(), white.getY() - j) != Area.CASTLE)) {
                        int emptyPawns = 0;
                        for (int f = white.getY() - 1; f > white.getY() - j; f--) {
                            if(this.getPawn(white.getX(), f) != Pawn.EMPTY || this.getArea(white.getX(), f) == Area.CAMPS || this.getArea(white.getX(), f) == Area.CASTLE) {
                                emptyPawns++;
                            }
                        }
                        if(emptyPawns == 0) {
                            whiteLegalMoves.add(new XYOld(white.getX(), white.getY() - j, new int[]{white.getX(), white.getY()}, false));
                        }
                    }
                    //DOWN
                    if(((white.getY() + j) < this.getBoard().length) && this.getPawn(white.getX(), white.getY() + j) == Pawn.EMPTY
                            && (this.getArea(white.getX(), white.getY() + j) != Area.CAMPS)
                            && (this.getArea(white.getX(), white.getY() + j) != Area.CASTLE)) {
                        int howManyEmptyPawns = 0;
                        for (int f = white.getY() + 1; f < white.getY() + j; f++) {
                            if(this.getPawn(white.getX(), f) != Pawn.EMPTY || this.getArea(white.getX(), f) == Area.CAMPS || this.getArea(white.getX(), f) == Area.CASTLE) {
                                howManyEmptyPawns++;
                            }
                        }
                        if(howManyEmptyPawns == 0) {
                            whiteLegalMoves.add(new XYOld(white.getX(), white.getY() + j, new int[]{white.getX(), white.getY()}, false));
                        }
                    }
                }
                // move each pawn horizontally
                for (int i = 0; i < this.getBoard().length; i++) {
                    // (x - i, y) LEFT
                    if(((white.getX() - i) >= 0) && this.getPawn(white.getX() - i, white.getY()) == Pawn.EMPTY
                            && (this.getArea(white.getX() - i, white.getY()) != Area.CAMPS)
                            && (this.getArea(white.getX() - i, white.getY()) != Area.CASTLE)) {
                        int howManyEmptyPawns = 0;
                        for (int f = white.getX() - 1; f > white.getX() - i; f--) {
                            if(this.getPawn(f, white.getY()) != Pawn.EMPTY || this.getArea(f, white.getY()) == Area.CAMPS || this.getArea(f, white.getY()) == Area.CASTLE) {
                                howManyEmptyPawns++;
                            }
                        }
                        if(howManyEmptyPawns == 0) {
                            whiteLegalMoves.add(new XYOld(white.getX() - i, white.getY(), new int[]{white.getX(), white.getY()}, false));
                        }
                    }
                    // (x + i, y) RIGHT
                    if(((white.getX() + i) < this.getBoard().length) && this.getPawn(white.getX() + i, white.getY()) == Pawn.EMPTY
                            && (this.getArea(white.getX() + i, white.getY()) != Area.CAMPS)
                            && (this.getArea(white.getX() + i, white.getY()) != Area.CASTLE)) {
                        int howManyEmptyPawns = 0;
                        for (int f = white.getX() + 1; f < white.getX() + i; f++) {
                            if(this.getPawn(f, white.getY()) != Pawn.EMPTY || this.getArea(f, white.getY()) == Area.CAMPS || this.getArea(f, white.getY()) == Area.CASTLE) {
                                howManyEmptyPawns++;
                            }
                        }
                        if(howManyEmptyPawns == 0) {
                            whiteLegalMoves.add(new XYOld(white.getX() + i, white.getY(), new int[]{white.getX(), white.getY()}, false));
                        }
                    }
                }

            }

            return whiteLegalMoves;
        } else {
            List<XYOld> blackMoves = new ArrayList<>();
            List<XYOld> blacks = new ArrayList<>();
            XYOld buf;
            for (int i = 0; i < this.getBoard().length; i++) {
                for (int j = 0; j < this.getBoard().length; j++) {
                    if (this.getPawn(i, j).equalsPawn(Pawn.BLACK.toString()))  {
                        if(this.getArea(i, j).equalsArea(Area.CAMPS.toString())) { // if a black is still in a camp, he can move into it
                            buf = new XYOld(i, j, new int[]{i, j}, false);
                            blacks.add(buf);
                        } else if (this.getArea(i, j).equalsArea(Area.CAMPS.toString()) || this.getArea(i, j).equalsArea(Area.NORMAL.toString()) || this.getArea(i, j).equalsArea(Area.ESCAPES.toString())) {
                            buf = new XYOld(i, j, new int[]{i, j}, true); // if a black is no more in a camp, he cannot enter in any camp anymore
                            blacks.add(buf);
                        }
                    }
                }
            }
            // for each black position, try every possible move
            for (XYOld blackPawn : blacks) {
                // move vertically
                for (int j = 0; j < this.getBoard().length; j++) {
                    //UP
                    if(((blackPawn.getY() - j) >= 0) && this.getPawn(blackPawn.getX(), blackPawn.getY() - j) == Pawn.EMPTY
                            && (this.getArea(blackPawn.getX(), blackPawn.getY() - j) != Area.CASTLE)
                            && (this.getArea(blackPawn.getX(), blackPawn.getY() - j) != Area.CAMPS)) {
                        int howManyEmptyPawns = 0;
                        for (int f = blackPawn.getY() - 1; f > blackPawn.getY() - j; f--) {
                            if(this.getPawn(blackPawn.getX(), f) != Pawn.EMPTY || (this.getArea(blackPawn.getX(), f) == Area.CAMPS && blackPawn.leftCamp()) || this.getArea(blackPawn.getX(), f) == Area.CASTLE) {
                                howManyEmptyPawns++;
                            }
                        }
                        if(howManyEmptyPawns == 0) {
                            // if a black is not in a camp, it cannot enter it anymore
                            if((blackPawn.leftCamp() && (this.getArea(blackPawn.getX(), blackPawn.getY() - j) != Area.CAMPS)) || (!blackPawn.leftCamp())) {
                                blackMoves.add(new XYOld(blackPawn.getX(), blackPawn.getY() - j, new int[]{blackPawn.getX(), blackPawn.getY()}, blackPawn.leftCamp()));
                            }
                        }
                    }
                    //DOWN
                    if(((blackPawn.getY() + j) < this.getBoard().length) && this.getPawn(blackPawn.getX(), blackPawn.getY() + j) == Pawn.EMPTY
                            && (this.getArea(blackPawn.getX(), blackPawn.getY() + j) != Area.CASTLE)
                            && (this.getArea(blackPawn.getX(), blackPawn.getY() + j) != Area.CAMPS)) {
                        int howManyEmptyPawns = 0;
                        for (int f = blackPawn.getY() + 1; f < blackPawn.getY() + j; f++) {
                            if(this.getPawn(blackPawn.getX(), f) != Pawn.EMPTY || (this.getArea(blackPawn.getX(), f) == Area.CAMPS && blackPawn.leftCamp()) || this.getArea(blackPawn.getX(), f) == Area.CASTLE) {
                                howManyEmptyPawns++;
                            }
                        }
                        if(howManyEmptyPawns == 0) {
                            if((blackPawn.leftCamp() && (this.getArea(blackPawn.getX(), blackPawn.getY() + j) != Area.CAMPS)) || (!blackPawn.leftCamp())) {
                                blackMoves.add(new XYOld(blackPawn.getX(), blackPawn.getY() + j, new int[]{blackPawn.getX(), blackPawn.getY()}, blackPawn.leftCamp()));
                            }
                        }
                    }
                }
                // move horizontally
                for (int i = 0; i < this.getBoard().length; i++) {
                    //LEFT
                    if(((blackPawn.getX() - i) >= 0) && this.getPawn(blackPawn.getX() - i, blackPawn.getY()) == Pawn.EMPTY
                            && (this.getArea(blackPawn.getX() - i, blackPawn.getY()) != Area.CASTLE)
                            && (this.getArea(blackPawn.getX() - i, blackPawn.getY()) != Area.CAMPS)) {
                        int howManyEmptyPawns = 0;
                        for (int f = blackPawn.getX() - 1; f > blackPawn.getX() - i; f--) {
                            if(this.getPawn(f, blackPawn.getY()) != Pawn.EMPTY || (this.getArea(f, blackPawn.getY()) == Area.CAMPS && blackPawn.leftCamp()) || this.getArea(f, blackPawn.getY()) == Area.CASTLE) {
                                howManyEmptyPawns++;
                            }
                        }
                        if(howManyEmptyPawns == 0) {
                            if((blackPawn.leftCamp() && (this.getArea(blackPawn.getX() - i, blackPawn.getY()) != Area.CAMPS)) || (!blackPawn.leftCamp())) {
                                blackMoves.add(new XYOld(blackPawn.getX() - i, blackPawn.getY(), new int[]{blackPawn.getX(), blackPawn.getY()}, blackPawn.leftCamp()));
                            }
                        }

                    }
                    //RIGHT
                    if(((blackPawn.getX() + i) < this.getBoard().length) && this.getPawn(blackPawn.getX() + i, blackPawn.getY()) == Pawn.EMPTY
                            && (this.getArea(blackPawn.getX() + i, blackPawn.getY()) != Area.CASTLE)
                            && (this.getArea(blackPawn.getX() + i, blackPawn.getY()) != Area.CAMPS)) {
                        int howManyEmptyPawns = 0;
                        for (int f = blackPawn.getX() + 1; f < blackPawn.getX() + i; f++) {
                            if(this.getPawn(f, blackPawn.getY()) != Pawn.EMPTY || (this.getArea(f, blackPawn.getY()) == Area.CAMPS && blackPawn.leftCamp()) || this.getArea(f, blackPawn.getY()) == Area.CASTLE) {
                                howManyEmptyPawns++;
                            }
                        }
                        if(howManyEmptyPawns == 0) {
                            if((blackPawn.leftCamp() && (this.getArea(blackPawn.getX() + i, blackPawn.getY()) != Area.CAMPS)) || (!blackPawn.leftCamp())) {
                                blackMoves.add(new XYOld(blackPawn.getX() + i, blackPawn.getY(), new int[]{blackPawn.getX(), blackPawn.getY()}, blackPawn.leftCamp()));
                            }
                        }
                    }
                }

            }
            return blackMoves;
        }
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false;
        MyStateTablut state = (MyStateTablut) obj;
        if(this.getBoard()==null && state.getBoard() != null) return false;
        else {
            if(state.getBoard()==null) return false;
            if(this.getBoard().length != state.getBoard().length) return false;
            for(int i = 0;i < state.getBoard().length;i++){
                for(int j = 0;j<state.getBoard().length;j++){
                    if(!this.getBoard()[i][j].equals(state.getBoard()[i][j]))return false;
                }
            }
        }
        if(this.getTurn() != state.getTurn()) return false;

        return true;
    }

    public int noMovesLeft(Pawn color){
        int moves = 0;
        for (int i = 0; i<this.getBoard().length; i++){
            for(int j=0; j<this.getBoard().length; j++){
                if(this.getPawn(i,j).equals(color)){
                    if(((i+1)<this.getBoard().length && this.getPawn(i+1,j).equals(Pawn.EMPTY))){
                        moves++;
                    }
                    if(((i - 1) >= 0) && this.getPawn(i - 1, j).equals(Pawn.EMPTY)) {
                        moves++;
                    }
                    if(((j + 1) < this.getBoard().length) && this.getPawn(i, j + 1).equals(Pawn.EMPTY)) {
                        moves++;
                    }
                    if(((j - 1) >= 0) && this.getPawn(i, j - 1).equals(Pawn.EMPTY)) {
                        moves++;
                    }
                }
            }
        }

        return moves == 0?1:0;
    }

    public int blackWon(){
        int[] kingPosition = this.getKingPosition();
        if(this.getArea(kingPosition[0], kingPosition[1]).equals(Area.ESCAPES)) {
            return 0;
        }
        // normal capture of the king
        for(int i=0; i < this.getBoard().length; i++) {
            for(int j=0; j < this.getBoard().length; j++) {
                if(this.getPawn(i, j).equals(Pawn.KING)) {
                    /*case
                     * B
                     * K
                     * B
                     */
                    if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
                            && ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
                            && this.getPawn(i - 1, j).equals(Pawn.BLACK) && this.getPawn(i + 1, j).equals(Pawn.BLACK) &&
                            (!this.getArea(i, j).equals(Area.CASTLE) && (!this.getArea(i, j - 1).equals(Area.CASTLE) &&
                                    (!this.getArea(i, j + 1).equals(Area.CASTLE))))) {
                        return 1;
                    }
                    /* case B K B */
                    if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
                            && ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
                            && this.getPawn(i, j - 1).equals(Pawn.BLACK) && this.getPawn(i, j + 1).equals(Pawn.BLACK) &&
                            (!this.getArea(i, j).equals(Area.CASTLE) && (!this.getArea(i - 1, j).equals(Area.CASTLE) &&
                                    (!this.getArea(i + 1, j).equals(Area.CASTLE))))) {
                        return 1;
                    }
                }
            }
        }

        // case where the king is in the castle and is surrounded by 4 blacks
        for(int i=0; i < this.getBoard().length; i++) {
            for(int j=0; j < this.getBoard().length; j++) {
                if(this.getPawn(i, j).equals(Pawn.KING) && this.getArea(i, j).equals(Area.CASTLE)) {
                    if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
                            && ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
                            && this.getPawn(i, j - 1).equals(Pawn.BLACK) && this.getPawn(i, j + 1).equals(Pawn.BLACK)
                            && this.getPawn(i - 1, j).equals(Pawn.BLACK) && this.getPawn(i + 1, j).equals(Pawn.BLACK)) {
                        return 1;
                    }
                }
            }
        }
        // case if the king is adjacent to the Castle, it must be surround on all the three free sides
        if(((MyStateTablut.KING_POSITION - 1) >= 0)
                && this.getPawn(MyStateTablut.KING_POSITION, MyStateTablut.KING_POSITION - 1).equals(Pawn.KING)) {
            if(((MyStateTablut.KING_POSITION + 1) < this.getBoard().length) && ((MyStateTablut.KING_POSITION - 2) >= 0)
                    && ((MyStateTablut.KING_POSITION + 1) < this.getBoard().length)
                    && (this.getPawn(MyStateTablut.KING_POSITION, MyStateTablut.KING_POSITION - 2).equals(Pawn.BLACK)
                    && this.getPawn(MyStateTablut.KING_POSITION - 1, MyStateTablut.KING_POSITION - 1).equals(Pawn.BLACK)
                    && this.getPawn(MyStateTablut.KING_POSITION + 1, MyStateTablut.KING_POSITION - 1).equals(Pawn.BLACK))
            ){
                return 1;
            }

        } else if(((MyStateTablut.KING_POSITION + 1) < this.getBoard().length)
                && this.getPawn(MyStateTablut.KING_POSITION, MyStateTablut.KING_POSITION + 1).equals(Pawn.KING)) {
            if(((MyStateTablut.KING_POSITION + 2) < this.getBoard().length)
                    && ((MyStateTablut.KING_POSITION - 1) >= 0)
                    && (this.getPawn(MyStateTablut.KING_POSITION, MyStateTablut.KING_POSITION + 2).equals(Pawn.BLACK)
                    && this.getPawn(MyStateTablut.KING_POSITION + 1, MyStateTablut.KING_POSITION + 1).equals(Pawn.BLACK)
                    && this.getPawn(MyStateTablut.KING_POSITION - 1, MyStateTablut.KING_POSITION + 1).equals(Pawn.BLACK))
            ) {
                return 1;
            }
        } else if(((MyStateTablut.KING_POSITION - 1) >= 0)
                && this.getPawn(MyStateTablut.KING_POSITION - 1, MyStateTablut.KING_POSITION).equals(Pawn.KING)) {
            if((((MyStateTablut.KING_POSITION - 2) >= 0)
                    && ((MyStateTablut.KING_POSITION + 1) < this.getBoard().length)
                    && this.getPawn(MyStateTablut.KING_POSITION - 1, MyStateTablut.KING_POSITION - 1).equals(Pawn.BLACK)
                    && this.getPawn(MyStateTablut.KING_POSITION - 2, MyStateTablut.KING_POSITION).equals(Pawn.BLACK)
                    && this.getPawn(MyStateTablut.KING_POSITION - 1, MyStateTablut.KING_POSITION + 1).equals(Pawn.BLACK))
            ) {
                return 1;
            }

        } else if(((MyStateTablut.KING_POSITION + 1) < this.getBoard().length)
                && this.getPawn(MyStateTablut.KING_POSITION + 1, MyStateTablut.KING_POSITION).equals(Pawn.KING)) {
            if((((MyStateTablut.KING_POSITION + 2) < this.getBoard().length)
                    && ((MyStateTablut.KING_POSITION - 1) >= 0)
                    && this.getPawn(MyStateTablut.KING_POSITION + 1, MyStateTablut.KING_POSITION + 1).equals(Pawn.BLACK)
                    && this.getPawn(MyStateTablut.KING_POSITION + 2, MyStateTablut.KING_POSITION).equals(Pawn.BLACK)
                    && this.getPawn(MyStateTablut.KING_POSITION + 1, MyStateTablut.KING_POSITION - 1).equals(Pawn.BLACK))
            ) {
                return 1;
            }
        }

        // case if the king is adjacent to a camp, it is sufficient to surround it with a checker on the opposite side of the camp.
        for(int i=0; i < this.getBoard().length; i++) {
            for(int j=0; j < this.getBoard().length; j++) {
                if(this.getPawn(i, j).equals(Pawn.KING)) {
                    // normal case
                    if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
                            && this.getArea(i + 1, j).equals(Area.CAMPS) && this.getPawn(i - 1, j).equals(Pawn.BLACK)) {
                        return 1;
                    } else if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
                            && this.getArea(i - 1, j).equals(Area.CAMPS) && this.getPawn(i + 1, j).equals(Pawn.BLACK)) {
                        return 1;
                    } else if(((j - 1) >= 0 && (j + 1) < this.getBoard().length)
                            && this.getArea(i, j - 1).equals(Area.CAMPS) && this.getPawn(i, j + 1).equals(Pawn.BLACK)) {
                        return 1;
                    } else if(((j - 1) >= 0 && (j + 1) < this.getBoard().length)
                            && this.getArea(i, j + 1).equals(Area.CAMPS) && this.getPawn(i, j - 1).equals(Pawn.BLACK)) {
                        return 1;
                    }

                    // angle case
                    if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
                            && ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
                            && this.getArea(i - 1, j).equals(Area.CAMPS) && this.getArea(i, j + 1).equals(Area.CAMPS)
                            && ((this.getPawn(i + 1, j).equals(Pawn.BLACK))
                            || (this.getPawn(i, j - 1).equals(Pawn.BLACK)))) {
                        return 1;
                    } else if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
                            && ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
                            && this.getArea(i - 1, j).equals(Area.CAMPS) && this.getArea(i, j - 1).equals(Area.CAMPS)
                            && ((this.getPawn(i + 1, j).equals(Pawn.BLACK))
                            || (this.getPawn(i, j + 1).equals(Pawn.BLACK)))) {
                        return 1;
                    } else if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
                            && ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
                            && this.getArea(i, j + 1).equals(Area.CAMPS) && this.getArea(i + 1, j).equals(Area.CAMPS)
                            && ((this.getPawn(i - 1, j).equals(Pawn.BLACK))
                            || (this.getPawn(i, j - 1).equals(Pawn.BLACK)))) {
                        return 1;
                    } else if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
                            && ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
                            && this.getArea(i - 1, j).equals(Area.CAMPS) && this.getArea(i, j + 1).equals(Area.CAMPS)
                            && ((this.getPawn(i, j - 1).equals(Pawn.BLACK))
                            || (this.getPawn(i + 1, j).equals(Pawn.BLACK)))) {
                        return 1;
                    } else if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
                            && ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
                            && this.getArea(i + 1, j).equals(Area.CAMPS) && this.getArea(i, j - 1).equals(Area.CAMPS)
                            && ((this.getPawn(i - 1, j).equals(Pawn.BLACK))
                            || (this.getPawn(i, j + 1).equals(Pawn.BLACK)))) {
                        return 1;
                    } else if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
                            && ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
                            && this.getArea(i + 1, j).equals(Area.CAMPS) && this.getArea(i, j + 1).equals(Area.CAMPS)
                            && ((this.getPawn(i - 1, j).equals(Pawn.BLACK))
                            || (this.getPawn(i, j - 1).equals(Pawn.BLACK)))) {
                        return 1;
                    } else if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
                            && ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
                            && this.getArea(i, j - 1).equals(Area.CAMPS) && this.getArea(i - 1, j).equals(Area.CAMPS)
                            && ((this.getPawn(i + 1, j).equals(Pawn.BLACK))
                            || (this.getPawn(i, j + 1).equals(Pawn.BLACK)))) {
                        return 1;
                    } else if(((i - 1) >= 0 && (i + 1) < this.getBoard().length)
                            && ((j - 1) >= 0 && (j + 1) < this.getBoard().length)
                            && this.getArea(i, j - 1).equals(Area.CAMPS) && this.getArea(i + 1, j).equals(Area.CAMPS)
                            && ((this.getPawn(i - 1, j).equals(Pawn.BLACK))
                            || (this.getPawn(i, j + 1).equals(Pawn.BLACK)))) {
                        return 1;
                    }
                }
            }
        }
        return 0;

    }


}
