package SAD_SlideBar;

import java.util.*;
import java.io.*;

class ConsoleBar implements Observer {
	static enum Opcode {
	  INC, DEC, BELL
	}

	static class Command {
	  Opcode op;
	  
	  Command(Opcode op) {
	    this.op = op;
	  }
	}

	int cmax;
	Value model;
	static final char BLOCK = (char) 219;
  
	ConsoleBar(Value value) {
		model = value;
		
		// get max columns
	}
	
	int getMax() {
		return cmax;
	}
	
	public void update(Observable o, Object arg) {
		Command comm = (Command) arg;
		switch (comm.op) {
			case INC:
				System.out.print(BLOCK);
				break;
			case DEC:
				// delete char to the left
				break;
			case BELL:
				System.out.print('\007');
				break;
		}
	}
}
