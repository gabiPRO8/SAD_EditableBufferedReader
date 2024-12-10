package SAD_SlideBar;

import java.util.*;
import java.io.*;

class ConsolePercent implements Observer {
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
  
	ConsolePercent(Value value) {
		model = value;
		
		// get max columns
		...
		// write 0%
		...
	}
	
	int getMax() {
		return cmax;
	}
	
	public void update(Observable o, Object arg) {
		restore();
		Command comm = (Command) arg;
		switch (comm.op) {
			case INC:
			case DEC:
				// write Value%
				break;
			case BELL:
				System.out.print('\007');
				break;
		}
	}
}
