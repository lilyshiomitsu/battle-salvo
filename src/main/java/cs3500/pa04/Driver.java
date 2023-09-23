package cs3500.pa04;

import com.fasterxml.jackson.core.JsonProcessingException;
import cs3500.pa04.controller.AiController;
import cs3500.pa04.controller.HumanController;
import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.model.AiPlayerDependency;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * entrypoint of the program
 */
public class Driver {
  /**
   * main method, if 0 arguments, runs pa03, if 2 arguments, runs pa04
   *
   * @param args commandline arguments
   */
  public static void main(String[] args) {

    if (args.length == 0) {
      new HumanController(new InputStreamReader(System.in)).run();
    } else {
      try {
        if (args.length == 2) {
          runClient(args[0], Integer.parseInt(args[1]));
        } else {
          System.out.println("Expected two arguments: `[host] [port]`.");
        }
      } catch (IllegalStateException e) {
        System.out.println("unable to connect to the server");
      } catch (NumberFormatException e) {
        System.out.println("second argument should be an integer. format: `[host] [port]`.");
      }
    }
  }


  /**
   * This method connects to the server at the given host and port, builds a proxy referee
   * to handle communication with the server, and sets up a client player.
   *
   * @param host the server host
   * @param port the server port
   * @throws IOException if there is a communication issue with the server
   */
  private static void runClient(String host, int port) {
    try (Socket server = new Socket(host, port);
         InputStream inputStream = server.getInputStream();
         PrintStream outputStream = new PrintStream(server.getOutputStream())) {

      ProxyController proxyController = new ProxyController(server, new AiPlayerDependency());
      proxyController.run();
    } catch (IOException e) {
      System.err.println("An error occurred while connecting to the server: " + e.getMessage());
      e.printStackTrace();
    }
  }

}
