/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paciencia.model;

import paciencia.model.Baralho.ECor;
import paciencia.model.Baralho.ENaipe;

/**
 *
 * @author willy
 */
public class Naipe {
    private ENaipe naipe;
    private ECor cor;
    
    public Naipe(ENaipe naipe, ECor cor) {
        this.naipe = naipe;
        this.cor = cor;
    }
    
    public ENaipe getNaipe() {
        return this.naipe;
    }
    
    public ECor getCor() {
        return this.cor;
    }
}
