/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testreadline;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Console implements PropertyChangeListener {
    public void updateView(Line line) {
        System.out.print("\r" + line.getText()); // Redibujar la línea
        System.out.flush();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("text".equals(evt.getPropertyName())) {
            Line line = (Line) evt.getNewValue();
            updateView(line);
        }
    }
}
