package paciencia.model.base;

import paciencia.model.home.ListaHome;
import paciencia.model.interfaces.IBase;
import java.awt.Point;
import paciencia.helper.ResourceHelper;
import paciencia.ui.PacienciaUI;

/**
 * Elemento base da lista (primeiro buraco da lista, normalmente terá uma carta sobre ele)
 * @author Willy G. M. Barro Raffel
 */
public class ListaBase extends IBase {
    public ListaHome home = null;
    
    public ListaBase(int x, int y, ListaHome home) {
        this.x = x;
        this.y = y;
        this.setIcon(ResourceHelper.getCarta("border_half.png"));
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
        int nextY = this.getBaseY();
        if(this.home.contarNos() > 0)
            nextY += (PacienciaUI.cardYOffset * this.home.contarNos()+1);
            
        return new Point(this.getBaseX(), nextY);
    }
    
    @Override
    public ListaHome getHome() {
        return this.home;
    }
}