import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class DateServerMTP{

    static class GreetingRunnable implements Runnable {
        public GreetingRunnable(Socket soc){
            this.socket = soc;
        }
        //@Override 
        @Override
        public void run() {
    
            try(
              PrintWriter writer = new PrintWriter(this.socket.getOutputStream());
              //InputStreamReader input = new InputStreamReader(this.socket.getInputStream());
              //BufferedReader reader = new BufferedReader(input)
              )
            {
              writer.println(new java.util.Date().toString());
              writer.flush();
              socket.close();
            }
            catch (IOException ex){
              ex.printStackTrace();
            }
        }
        private Socket socket;
      }
      
    public static void main(String[] args){
        if(args.length == 0){
          System.out.println("You didn't enter anything, please enter a port number");
          return;
        }

        String port = args[0];
        int parseNumber; 
        
        try{
            parseNumber = Integer.parseInt(port);
        }catch (NumberFormatException ex){
          System.out.println(ex);
          return;
        }
        ExecutorService service = Executors.newCachedThreadPool();
        
        try{
            ServerSocket sock = new ServerSocket(parseNumber);
            /* now listen for connections */
            while (true){
                Socket client = sock.accept();
                service.submit(new GreetingRunnable(client));
                
            }
        }catch (IOException ioe){
            System.err.println(ioe);
        }
    }
}