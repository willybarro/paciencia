/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paciencia.model.interfaces;

import paciencia.exceptions.InvalidMoveException;
import paciencia.model.NoCarta;

/**
 * Interface dos componentes "Home"
 * Todos eles devem poder receber uma carta
 * @author willy
 */
public interface IHome {
    public boolean receberNo(NoCarta carta) throws InvalidMoveException;
    public void remover(NoCarta nc);
    public IBase getBase();
    public void setBase(IBase base);
}
