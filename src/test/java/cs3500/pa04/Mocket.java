package cs3500.pa04;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.List;

/**
 * A mock implementation of the Socket class for testing purposes.
 */
public class Mocket extends Socket {
  private final InputStream testInputs;
  private final ByteArrayOutputStream testLog;

  /**
   * Constructs a new Mocket instance.
   *
   * @param testLog The stream to store the received messages from the client.
   * @param toSend The list of messages to be sent from the server to the client.
   */
  public Mocket(ByteArrayOutputStream testLog, List<String> toSend) {
    this.testLog = testLog;

    // Set up the list of inputs as separate messages of JSON for the ProxyDealer to handle
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    for (String message : toSend) {
      printWriter.println(message);
    }
    this.testInputs = new ByteArrayInputStream(stringWriter.toString().getBytes());
  }

  /**
   * Returns the input stream for reading data from the mock socket.
   *
   * @return The input stream containing the test inputs.
   */
  @Override
  public InputStream getInputStream() {
    return this.testInputs;
  }

  /**
   * Returns the output stream for writing data to the mock socket.
   *
   * @return The output stream representing the test log.
   */
  @Override
  public OutputStream getOutputStream() {
    return this.testLog;
  }
}
