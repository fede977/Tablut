package it.unibo.ai.didattica.competition.tablut.lastminute.client;

import com.google.gson.Gson;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.server.Server;
import it.unibo.ai.didattica.competition.tablut.util.StreamUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;

public abstract class TablutClient implements Runnable{

    private State.Turn player;
    private String name;
    private Socket playerSocket;
    private DataInputStream input;
    private DataOutputStream output;
    private Gson gson;
    private State currentState;
    private int timeout;
    private String serverIp;

    /**
     * Initialize State by creating player, socket and logs
     *
     * @param  player
     *          Player Role(Black,White)
     * @param name
     *          Player name
     * @param timeout
     *          timeout before action
     * @param ipAddress
     *           Server's IP
     * @throws java.net.UnknownHostException
     * @throws java.io.IOException
     */

    public TablutClient(String player, String name, int timeout, String ipAddress) throws UnknownHostException, IOException {

        int port = 0;
        this.serverIp = ipAddress;
        this.timeout = timeout;
        this.gson = new Gson();

        if(player.toLowerCase().equals("white")){
            this.player = State.Turn.WHITE;
            port = Server.whitePort;
        }else if(player.toLowerCase().equals("white")){
            this.player = State.Turn.BLACK;
            port = Server.blackPort;
        }else{
            throw new InvalidParameterException("Player role must be either BLACK ot WHITE");
        }
        this.playerSocket = new Socket(this.serverIp,port);
        this.output = new DataOutputStream(this.playerSocket.getOutputStream());
        this.input = new DataInputStream(this.playerSocket.getInputStream());
        this.name = name;

    }

    public State.Turn getPlayer(){
        return this.player;
    }

    public void setPlayer(State.Turn player){
        this.player=player;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setCurrentState(State currentState){
        this.currentState = currentState;
    }

    public State getCurrentState(){
        return this.currentState;
    }

    /**write action to the server**/
    public void writeAction(Action action) throws IOException, ClassNotFoundException {
        StreamUtils.writeString(this.output, this.gson.toJson(this.name));
    }

    /**
     * write the name on the server
     */
    public void writeName() throws IOException, ClassNotFoundException {
        StreamUtils.writeString(this.output, this.gson.toJson(this.name));
    }

    /**
     * read Server's state
     */
    public void readState() throws ClassNotFoundException, IOException {
        this.currentState = this.gson.fromJson(StreamUtils.readString(this.input), StateTablut.class);
    }
}
