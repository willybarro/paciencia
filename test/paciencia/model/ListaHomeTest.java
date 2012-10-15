/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paciencia.model;
import helper.Helper;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import paciencia.exceptions.InvalidMoveException;
import paciencia.model.home.ListaHome;

/**
 *
 * @author willy
 */
public class ListaHomeTest {
    Baralho baralho;
    ListaHome[] listas = new ListaHome[4];
    
    public ListaHomeTest() {
    }
    
    @Before
    public void setUp() {
        this.baralho = new Baralho();
        this.baralho.embaralhar();
        
        this.listas[0] = new ListaHome("Lista 1");
        this.listas[1] = new ListaHome("Lista 2"); 
    }
    
    private void populaListas() {
        for(int i=0; i<listas.length; i++) {
            for(int j=0; j<5; j++) {
                this.listas[i].inserir(baralho.retiraCartaTopo());
            }
        }
    }
    
    @Test
    public void testRemoveCartaLista() {
    }
    
    @Test
    public void testInsereNoCartaNaListaNaoVazia() {
        assertTrue(listas[0].listaVazia());
        
        // Insere um no em uma lista que ja tenha alguma NoCarta
        NoCarta novaNoCarta = baralho.retiraCartaTopo();
        listas[0].inserir(novaNoCarta);
        
        NoCarta NoCartaInserida = listas[0].elementoFinal();
        
        assertFalse(listas[0].listaVazia());
        assertEquals(NoCartaInserida, novaNoCarta);
    }
    
    @Test
    public void testMoveNo() throws InvalidMoveException {
        listas[0] = Helper.criaLista("10co,9po,8co,7po");
        listas[1] = Helper.criaLista("11po");
        listas[2] = Helper.criaLista("10co");
                
        // Tenta mover os nós algumas vezes
        try {
            assertEquals(listas[0].toString(), Helper.criaLista("10co,9po,8co,7po").toString());
            assertEquals(listas[0].elementoInicio().toString(), "10 de COPAS");
            assertEquals(listas[0].elementoFinal().toString(), "7 de PAUS");
            assertEquals(listas[1].toString(), Helper.criaLista("11po").toString());
            assertEquals(listas[1].elementoInicio().toString(), "J de PAUS");
            assertEquals(listas[1].elementoFinal().toString(), "J de PAUS");
            this.listas[0].moverNo(1, listas[1]);
            
            assertEquals(listas[0].toString(), "");
            assertEquals(listas[0].elementoInicio(), null);
            assertEquals(listas[0].elementoFinal(), null);
            assertEquals(listas[1].toString(), Helper.criaLista("11po,10co,9po,8co,7po").toString());
            assertEquals(listas[1].elementoInicio().toString(), "J de PAUS");
            assertEquals(listas[1].elementoFinal().toString(), "7 de PAUS");
            
            this.listas[1].moverNo(3, listas[2]);
            assertEquals(listas[1].toString(), Helper.criaLista("11po,10co").toString());
            assertEquals(listas[2].toString(), Helper.criaLista("10co,9po,8co,7po").toString());
            
        } catch(InvalidMoveException e) {
            fail();
        }
        
    }
    
    @Test
    public void testMoveNoInvalidoParaListaNaoVazia() throws InvalidMoveException {
        listas[0] = Helper.criaLista("10c,2o,4c,3e,2c,1e");
        listas[1] = Helper.criaLista("3c,4c,7e,6o,5e,4c");
        listas[2] = Helper.criaLista("3c,4c,8o");
        
        // Tenta mover uma sublista inválida (sequencia incorreta) para outra lista
        try {
            listas[0].moverNo(3, listas[1]);
            fail();
        } catch(InvalidMoveException e) {
        }
        
        // Tenta mover uma sublista válida, porém, com NoCarta fechada
        try {
            listas[1].moverNo(3, listas[2]);
            fail();
        } catch(InvalidMoveException e) {
        }
        
        // Tenta mover apenas uma NoCarta para uma lista que nao tem a NoCarta da sequencia correta
        try {
            listas[1].moverNo(listas[1].contarNos(), listas[2]);
            fail();
        } catch(InvalidMoveException e) {
        }
    }
    
    @Test
    public void testMoveNoValidoParaListaNaoVazia() throws InvalidMoveException {
        listas[0] = Helper.criaLista("10c,2o,4c,3e,2c,1e"); // Sequencia invalida
        listas[1] = Helper.criaLista("3c,4c,7eo,6oo,5eo,4co"); // Sequencia valida
        listas[2] = Helper.criaLista("3c,4c,8o");
        listas[3] = Helper.criaLista("3c,4c,5eo");
        
        // Tenta mover apenas uma única NoCarta para uma lista que a suporta
        try {
            listas[1].moverNo(listas[1].contarNos(), listas[3]);
        } catch(InvalidMoveException e) {
            fail();
        }
        
        // Tenta mover uma sublista válida para uma lista que a suporta
        try {
            listas[1].moverNo(3, listas[2]);
        } catch(InvalidMoveException e) {
            fail();
        }
        
        // Verifica se a ultima NoCarta da lista foi virada
        assertTrue(listas[1].elementoFinal().isOpen());
    }
    
    @Test
    public void testMoveNoReisParaListaVazia() throws InvalidMoveException {
        listas[0] = Helper.criaLista("10c,13eo"); // Sequencia invalida
        listas[1] = Helper.criaLista("10c"); // Sequencia invalida
        listas[2] = Helper.criaLista("13eo,12co,11eo,10co");
        listas[3] = new ListaHome("Lista 1");
        
        // Move um No válido (a primeira NoCarta é REIS) para uma lista vazia
        try {
            listas[0].moverNo(2, listas[3]);
        } catch(InvalidMoveException e) {
            fail();
        }
    }
    
    @Test
    public void testMoveNoInvalidoParaListaVazia() {
        listas[0] = Helper.criaLista("10c,13e"); // Sequencia invalida
        listas[1] = Helper.criaLista("10c"); // Sequencia invalida
        listas[2] = Helper.criaLista("13eo,12co,11eo,10co");
        listas[3] = new ListaHome("Lista 2");
        
        // Move um No inválido (a primeira NoCarta não é um REIS) para a lista vazia
        try {
            listas[1].moverNo(1, listas[3]);
            fail();
        } catch(InvalidMoveException e) {
        }
        
        // Move uma sublista valida para um espaço vazio
        try {
            listas[2].moverNo(1, listas[3]);
        } catch(InvalidMoveException e) {
            fail();
        }
    }
}