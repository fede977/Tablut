import java.io.*;
import java.lang.ProcessBuilder;

public class lastminute{

    public static void main(String[] args) {

        
        if (args.length < 2) {
            System.err.println("Usage: java lastminute pawn_color host");
            System.exit(1);
        } 
    
try {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ant lastminute -Dcolor=");
        stringBuilder.append(args[0]);
        stringBuilder.append(" -Dtime=1 -Dserver=");
        stringBuilder.append(args[1]);
        
        String arguments = stringBuilder.toString();

        ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("sh", "-c", arguments);
        Process tablut_game = processBuilder.start(); 
		System.out.print("The game is aboiut to start...");

    } catch (Exception e) { 
        e.printStackTrace();

}  


    }
}