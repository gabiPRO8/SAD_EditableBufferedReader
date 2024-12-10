package SAD_SlideBar;

import java.util.*;

class Value extends Observable {
  int value, max;
  
  Value() {
    value = 0;
  }
  
  void inc() {
    setChanged();
    if (value == max)
      notifyObservers(new Console.Command(Console.Opcode.BELL)); // push style
    else {
      value++;
      notifyObservers(new Console.Command(Console.Opcode.INC));
    }
  }
  
  void dec() {
    setChanged();
    if (value == 0)
      notifyObservers(new Console.Command(Console.Opcode.BELL));
    else {
      value--;
      notifyObservers(new Console.Command(Console.Opcode.DEC));
    }
  }
  
  int get() {
  	return value;
  }
  
  void setMax(int max) {
    this.max = max;
  }
}
