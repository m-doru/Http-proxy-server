package pao.mdoru;

import pao.mdoru.impl.DefaultHttpProxyServer;
import pao.mdoru.impl.DefaultHttpProxyServerBootstrap;
import pao.mdoru.interfaces.HttpProxyServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by m-doru on 10.05.2016.
 */
public class Main {
    public static void main(String[] args) {
        DefaultHttpProxyServerBootstrap serverBuilder = new DefaultHttpProxyServerBootstrap();

        switch (args.length){
            case 3:
                try{
                    serverBuilder.withBacklog(Integer.parseInt(args[2]));
                }
                catch (NumberFormatException e ){
                };
            case 2:
                try{
                    serverBuilder.withPort(Integer.parseInt(args[1]));
                }
                catch (NumberFormatException e){
                };
            case 1:
                serverBuilder.withName(args[0]);
                break;
            default:
                System.out.println("Possible args: <proxy name> <port> <backlog>");
        }
        HttpProxyServer httpProxyServer;
        try {
            httpProxyServer = serverBuilder.start();
        } catch (IOException e) {
            Logger.getLogger("Main").log(Level.SEVERE, "Failed to start the http proxy server");
            return;
        }
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            try {
                String line = consoleReader.readLine();

                if(line.equals("exit")) {
                    httpProxyServer.stop();
                    System.out.println("Server is stopping...");
                    break;
                }

            } catch (IOException e) {
            }


        }
    }
}
