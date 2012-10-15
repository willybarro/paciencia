/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paciencia.ui;

import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author willy
 */
public class ListaPane extends JPanel {
    public ListaPane() {
        this.setOpaque(false);
        this.setLayout(null);
    }

    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }
}
