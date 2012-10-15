/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paciencia.model.interfaces;

import java.awt.Point;
import javax.swing.JLabel;

/**
 *
 * @author willy
 */
public abstract class IBase extends JLabel {
    protected int x = 0;
    protected int y = 0;
    
    abstract public IHome getHome();

    public void setBaseX(int x) {
        this.x = x;
    }
    
    public int getBaseX() {
        return this.x;
    }
    
    public void setBaseY(int y) {
        this.y = y;
    }
    
    public int getBaseY() {
        return this.y;
    }
    
    public abstract Point getNextCardPoint();
}