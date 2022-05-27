package logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//Basic logging class written so I don't have to figure out how to do it properly
//I'll do this properly on the final, but for now I'll just use my existing class
//Rewritten based on comments from designEx1
public class Log {
  private BufferedWriter out;
  
  public Log() throws IOException {
	  //Starts logging to default location
      out = new BufferedWriter(new FileWriter("logs/log.txt"));
  }
  
  public Log(String location) throws IOException {
	  //Starts logging to new file of specified name
	  out = new BufferedWriter(new FileWriter("logs/"+location+".txt"));
  }
  
  public void add(String msg) throws IOException {
      msg = "[" + java.time.Clock.systemUTC().instant().toString() + "]: " + msg;
      out.write(msg);
      out.newLine();
      out.flush();
  }
  
  public void vadd(String msg) throws IOException { //Verbose add; prints to console
	  msg = "[" + java.time.Clock.systemUTC().instant().toString() + "]: " + msg;
      out.write(msg);
      out.flush();
      System.out.println(msg);
  }
}
