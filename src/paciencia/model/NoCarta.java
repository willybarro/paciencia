package paciencia.model;

import paciencia.model.interfaces.IHome;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import paciencia.helper.ResourceHelper;

/**
 *
 * @author Willy G. M. Barro Raffel
 */
final public class NoCarta extends JLabel {
    private int numero;
    private Naipe naipe;
    
    private NoCarta prox = null;
    private boolean aberta = false;
    private boolean draggable = false;
    
    private ImageIcon openedIcon;
    
    /**
     * Instancia da classe pai (home), pode ser uma ListaHome ua PilhaHome ou Baralho
     */
    private IHome home = null;
    
    public NoCarta(int numero, Naipe naipe) {
        this.numero = numero;
        this.naipe = naipe;
        this.setBounds(0, 0, 70, 100);
        this.openedIcon = ResourceHelper.getCarta(this.getNumRep().toLowerCase() + "-" + this.getNaipeRep() + ".png");
        this.setOpen(false);
    }
    
    public int getCountProx() {
        int totalProx = 0;
        NoCarta aux = this;
        while(aux.getProx() != null) {
            totalProx++;
            aux = aux.getProx();
        }
        
        return totalProx;
    }
    
    public NoCarta getProx() {
        return this.prox;
    }
    
    public void setProx(NoCarta prox) {
        this.prox = prox;
    }
    
    public boolean isOpen() {
        return this.aberta;
    }
    
    public boolean isDraggable() {
        return this.draggable;
    }
    
    public void setOpen(boolean open) {
        this.setOpen(open, true);
    }
    
    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }
    
    public void setOpen(boolean open, boolean draggable) {
        if(open) {
            this.setIcon(this.openedIcon);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            this.setIcon(Baralho.cartaFechada);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        
        this.aberta = open;
        this.setDraggable(draggable);
    }
    
    public int getNumero() {
        return this.numero;
    }
    
    /**
     * Recupera a representação do número da carta, por ex:
     * Ao invés de 13, 12, 11, 1 será retornado K, Q, J, A
     * @return String
     */
    public String getNumRep() {
        String ret;
        switch(this.numero) {
            case 1:
                ret = "A";
                break;
            case 11:
                ret = "J";
                break;
            case 12:
                ret = "Q";
                break;
            case 13:
                ret = "K";
                break;
            default: 
                ret = String.valueOf(this.numero);
                break;
        }
        
        return ret;
    }
    
    public String getNaipeRep() {
        String ret;
        switch(this.naipe.getNaipe()) {
            case COPAS:
                ret = "c";
                break;
            case ESPADAS:
                ret = "e";
                break;
            case OURO:
                ret = "o";
                break;
            case PAUS:
            default: 
                ret = "p";
                break;
        }
        
        return ret;
    }

    public Naipe getNaipe() {
        return this.naipe;
    }
    
    /**
     * Mostra uma representação do objeto da carta no padrão: "A de COPAS"
     * @return String
     */
    @Override
    public String toString() {
        return this.getNumRep() + " de " + this.getNaipe().getNaipe().name();
    }
    
    /**
     * Seta a home da carta (e também de todos as cartas filhas).
     * Pode ser uma ListaHome, PilhaHome ou Baralho
     * @param home 
     */
    public void setHome(IHome home) {
        this.home = home;
        
        if(this.getProx() != null)
            this.getProx().setHome(home);
    }
    
    public IHome getHome() {
        return this.home;
    }
}