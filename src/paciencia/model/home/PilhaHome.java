/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paciencia.model.home;

import paciencia.model.interfaces.IBase;
import paciencia.model.interfaces.IHome;
import paciencia.model.base.PilhaCarta;
import paciencia.exceptions.InvalidMoveException;
import paciencia.model.NoCarta;

/**
 *
 * @author willy
 */
public class PilhaHome extends PilhaCarta implements IHome {
    public int x = 0;
    public int y = 0;
    private IBase base = null;
    
    public PilhaHome(String nome) {
        super(nome);
        this.cartas = new NoCarta[13];
    }
    
    @Override
    public void setBase(IBase base) {
        this.base = base;
    }
    
    @Override
    public IBase getBase() {
        return this.base;
    }
    
    /**
     * Insere uma carta no topo da pilha de cartas.
     * Só podemos inserir a carta se ela for um A (se a pilha for vazia)
     * ou se ela for a proxima carta da sequencia.
     * 
     * @param carta
     * @return 
     */
    @Override
    public boolean receberNo(NoCarta carta) 
        throws InvalidMoveException
    {
        NoCarta cartaTopo = this.elementoTopo();
        if((this.pilhaVazia() && carta.getNumero() == 1) ||
            (
                !this.pilhaVazia() &&
                cartaTopo.getNumero() == carta.getNumero() - 1 &&
                cartaTopo.getNaipe() == carta.getNaipe()
            )
        ) {
            IHome homeFrom = (IHome) carta.getHome();
            homeFrom.remover(carta);
            carta.setHome(this);
            return this.empilhar(carta);
        }
        
        // Movimento inválido
        throw new InvalidMoveException(InvalidMoveException.PILHA_NO_SEQUENCIA_INVALIDA);
    }
    
    /**
     * Remove uma carta do topo do baralho (se disponível)
     * 
     * @return Retorna a ultima carta do baralho
     */
    @Override
    public void remover(NoCarta nc) {
        this.desempilhar();
    }
    
    
}
