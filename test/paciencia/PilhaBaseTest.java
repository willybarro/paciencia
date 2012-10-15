/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paciencia;

import paciencia.model.Naipe;
import paciencia.model.NoCarta;
import paciencia.model.home.PilhaHome;
import paciencia.model.Baralho.ECor;
import paciencia.model.Baralho.ENaipe;
import org.junit.Before;
import org.junit.Test;
import paciencia.exceptions.InvalidMoveException;
import paciencia.model.home.ListaHome;
import static org.junit.Assert.*;

/**
 *
 * @author willy
 */
public class PilhaBaseTest {
    PilhaHome p;
    Naipe[] naipes = {
        new Naipe(ENaipe.COPAS, ECor.VERMELHO),
        new Naipe(ENaipe.OURO, ECor.VERMELHO),
        new Naipe(ENaipe.ESPADAS, ECor.PRETO),
        new Naipe(ENaipe.PAUS, ECor.PRETO)
    };
    
    public PilhaBaseTest() {
    }
    
    @Before
    public void setUp() {
        p = new PilhaHome("Pilha 1");
    }
    
    @Test
    public void testInsereCartaInicialInvalida() {
        // Tenta inserir um 3 de paus logo de inicio, deve falhar
        try {
            NoCarta carta1 = new NoCarta(3, naipes[0]);
            ListaHome lista = new ListaHome("Lista 1");
            lista.receberNo(carta1);
            
            p.receberNo(carta1);
            fail();
        } catch(InvalidMoveException e) {
        }
    }
    @Test
    public void testInsereCartaInvalidaNoFim()
        throws InvalidMoveException
    {
        p.receberNo(new NoCarta(1, naipes[0]));
        
        try {
            // Tenta inserir um 3 de paus, deve falhar
            NoCarta carta1 = new NoCarta(3, naipes[0]);
            NoCarta carta2 = new NoCarta(3, naipes[0]);
            ListaHome lista = new ListaHome("Lista 1");
            lista.receberNo(carta1);
            lista.receberNo(carta2);
            
            p.receberNo(carta2);
            fail();
        } catch(InvalidMoveException e) {
        }
    }
    
    /*
    @Test
    public void testInsereConjuntoDeCartas()
        throws InvalidMoveException
    {
        boolean inseridoSucesso = false;
        for(int i=1; i<=13; i++) {
            inseridoSucesso = p.receberNo(new Carta(i, naipes[0]));
        }
        assertTrue(inseridoSucesso);
    }
    
    @Test
    public void testInsereConjuntoDeCartasMaiorQuePossivel()
        throws InvalidMoveException
    {
        boolean inseridoSucesso = false;
        for(int i=1; i<=50; i++) {
            inseridoSucesso = p.receberNo(new Carta(i, naipes[0]));
        }
        assertFalse(inseridoSucesso);
    }
    
    @Test
    public void testRemoverCarta()
        throws InvalidMoveException
    {
        for(int i=1; i<=13; i++) {
            p.receberNo(new Carta(i, naipes[0]));
        }
        assertTrue(p.pilhaCheia());
        
        p.remover();
        assertFalse(p.pilhaCheia());
    }
    
    @Test
    public void testRemoverTodasCartas()
        throws InvalidMoveException
    {
        for(int i=1; i<=13; i++) {
            p.receberNo(new Carta(i, naipes[0]));
        }
        
        for(int i=1; i<=13; i++) {
            p.remover();
        }
        assertTrue(p.pilhaVazia());
    }
    
    @Test
    public void testRemoverMaisCartasDoQuePode()
        throws InvalidMoveException
    {
        for(int i=1; i<=13; i++) {
            p.receberNo(new Carta(i, naipes[0]));
        }
        
        Carta ultimoRetornado = null;
        for(int i=1; i<=14; i++) {
            ultimoRetornado = p.remover();
        }
        assertNull(ultimoRetornado);
    }
     */
}