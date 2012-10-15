/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paciencia.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;

/**
 *
 * @author willy
 */
public class MonteMouseListener extends JFrame implements MouseListener, MouseMotionListener {
    private PacienciaUI paciencia;
    public MonteMouseListener(PacienciaUI p) {
        this.paciencia = p;
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {
        this.paciencia.abre3Cartas();
    }

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
