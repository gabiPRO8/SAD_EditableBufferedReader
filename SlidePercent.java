package SAD_SlideBar;

import java.io.*;
import static java.lang.System.in;

public class SlidePercent {

  private static void setRaw() {
    //Passar de mode Cooked a mode Raw
    //String amb la seqüència necessaria per canviar de mode Cooked a mode Raw al terminal
    String [] modeRaw = {"/bin/sh", "-c", "stty raw </dev/tty"};
    try {
      //getRuntime().exec() serveix per poder executar la linea de comandes
      //waitFor() espera hasta que el subproceso termine
      Runtime.getRuntime().exec(modeRaw).waitFor();  
    }catch (Exception e) {
      //Comuniquem l'error
      System.out.println("Error");
    } 
  }
  
  private static void unsetRaw() {
    //Passar de mode Raw a mode Cooked
    //String amb la seqüència necessaria per canviar de mode Raw a mode Cooked al terminal
    String[] modeCooked = {"/bin/sh", "-c", "stty cooked <dev/tty"};
    try{
      //getRuntime().exec() serveix per poder executar la linea de comandes
      //waitFor() espera hasta que el subproceso termine
      Runtime.getRuntime().exec(modeCooked).waitFor();
    } catch (IOException | InterruptedException e){
      //Comuniquem l'error
      System.out.println("Error");
    }
  }
  
  static final int RIGHT = 0, LEFT = 1;
  
  public static int readArrow() throws IOException {
    int ch;
    
    do {
      // read arrow key
      ch = in.read();
      ...
    } while (ch != '\r');
    return ch;
  }
  
  public static void main(String[] args) throws IOException {
    int arrow;
    ConsolePercent con = null;
    Value value = null;
    
    try {
      setRaw();
      value = new Value();
      con = new ConsolePercent(value);
      ...
      value.addObserver(con);
      
      while ((arrow = readArrow()) != '\r')
        if (arrow == RIGHT)
          value.inc();
        else
          value.dec();
    } finally {
      unsetRaw();
      // clean-up console
    }
  }
}

