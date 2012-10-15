/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import paciencia.model.Baralho;
import paciencia.model.Naipe;
import paciencia.model.home.ListaHome;
import paciencia.model.NoCarta;

/**
 *
 * @author willy
 */
public class Helper {
    public static int numLista = 1;
    
    /**
     * Helper, cria lista de NoCartas a partir de string.
     * Informe as NoCartas no formato de string separado por virgula: [numero][naipe], [numero][naipe]
     * Ex:
     *  "4C, 1E, 11P" - Cria uma lista de nós com: 4 de Copas, A de Espadas, J de Paus
     *  "5C, 3Eo, 1P" - Cria uma lista de nós com: 5 de Copas, 3 de Espadas ABERTO, A de Paus
     * 
     * @param NoCartas NoCartas em string no formato:, como: ""
     */
    public static ListaHome criaLista(String stcs) {
        String[] NoCartas = stcs.split(",");
        
        ArrayList<NoCarta> noAbrir = new ArrayList<NoCarta>();
        ListaHome lista = new ListaHome("Lista " + Helper.numLista++);
        for(String NoCarta : NoCartas) {
            NoCarta = NoCarta.trim();
            
            Matcher NoCartaInfo = Pattern.compile("([0-9]+)([c|o|e|p])(o?)", Pattern.CASE_INSENSITIVE).matcher(NoCarta);
            
            if(NoCartaInfo.matches()) {
                int numNoCarta = Integer.parseInt(NoCartaInfo.group(1));
                Naipe naipe = null;
                switch(NoCartaInfo.group(2).toLowerCase().charAt(0)) {
                    case 'c':
                        naipe = Baralho.naipes[0];
                        break;
                    case 'o':
                        naipe = Baralho.naipes[1];
                        break;
                    case 'e':
                        naipe = Baralho.naipes[2];
                        break;
                    case 'p':
                    default:
                        naipe = Baralho.naipes[3];
                        break;
                }

                // Insere a NoCarta na lista
                NoCarta c = new NoCarta(numNoCarta, naipe);
                c.setHome(lista);
                lista.inserir(c);
                
                if(NoCartaInfo.group(3).toLowerCase().equals("o")) {
                    noAbrir.add(lista.elementoFinal());
                }
            }
        }
        
        // Abre as NoCartas
        for(NoCarta nc : noAbrir) {
            nc.setOpen(true);
        }
        
        return lista;
    }
}
