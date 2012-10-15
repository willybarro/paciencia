package paciencia.model.base;

import paciencia.model.interfaces.IBase;
import java.awt.Point;
import paciencia.helper.ResourceHelper;
import paciencia.model.home.PilhaHome;

/**
 * Elemento base da pilha.
 * @author Willy G. M. Barro Raffel
 */
final public class PilhaBase extends IBase {
    protected PilhaHome home = null;
    
    public PilhaBase(int x, int y, PilhaHome home) {
        this.x = x;
        this.y = y;
        this.setIcon(ResourceHelper.getCarta("border_full.png"));
        this.setBounds(0, 0, 70, 100);
        this.setLocation(this.x, this.y);
        this.home = home;
    }
    
    /**
     * Retorna a próxima posição disponível para uma carta (em x,y)
     * @return Point
     */
    @Override
    public Point getNextCardPoint() {
        return new Point(this.getX(), this.getY());
    }
    
    @Override
    public PilhaHome getHome() {
        return this.home;
    }
}