package paciencia.model.home;

import paciencia.model.interfaces.IBase;
import paciencia.model.interfaces.IHome;
import paciencia.Paciencia;
import paciencia.exceptions.InvalidMoveException;
import paciencia.model.NoCarta;
import paciencia.model.base.ListaCarta;


/**
 * Classe de lista de cartas.
 * 7 instancias dessa classe são criadas para o jogo funcionar.
 * Elas são a área de trabalho principal do paciência.
 * 
 * @author Willy G. M. Barro Raffel
 */
public class ListaHome extends ListaCarta implements IHome {
    private int x = 0;
    private int y = 0;
    private IBase base = null;
    public ListaHome(String nome) {
        super(nome);
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
     * Insere uma carta na lista de cartas.
     * Este método deve ser usado apenas no inicio do jogo, para colocar
     * cartas aleatórias e sem validação na lista.
     * 
     * @param c 
     */
    public void inserir(NoCarta noCarta) {
        NoCarta antigoFinal = this.elementoFinal();
        this.inserirFinal(noCarta);
        
        // Vira as cartas (fecha a penultima, abre a ultima)
        if(antigoFinal != null)
            antigoFinal.setOpen(false);
        
        this.elementoFinal().setOpen(true);
    }
    
    /**
     * Recebe um nó, valida e armazena na lista atual
     * 
     * @param carta No (sublista) de cartas a serem inseridas na lista.
     * @return boolean Se verdadeiro, inseriu com sucesso.
     */
    @Override
    public boolean receberNo(NoCarta carta)
        throws InvalidMoveException
    {
        if(carta == null || !carta.isOpen())
            throw new InvalidMoveException(InvalidMoveException.LISTA_NO_FECHADO);
        
        // Valida o nó sendo arrastado (deve estar na ordem certa)
        if(!validaConjuntoNo(carta))
            throw new InvalidMoveException(InvalidMoveException.LISTA_NO_INVALIDO);
        
        /**
         * Verifica se a carta do topo da sequencia encaixa
         * com a do topo da lista que queremos inserir.
         */
        if(!this.listaVazia() && !Paciencia.cartaSequenciaValida(this.elementoFinal(), carta))
            throw new InvalidMoveException(InvalidMoveException.LISTA_SEQUENCIA_INVALIDA);
        
        // Se for uma sublista indo para uma lista vazia, o topo deve ser obrigatoriamente um rei
        if(this.listaVazia() && carta.getNumero() != 13)
            throw new InvalidMoveException(InvalidMoveException.LISTA_VAZIA_APENAS_REIS);
        
        // Tudo certo, agora sim inserimos na nova lista e removemos ele da lista antiga
        IHome listaFrom = (IHome) carta.getHome();
        listaFrom.remover(carta);
        this.inserirFinal(carta);
        carta.setHome(this);
        
        return true;
    }
    
    public void moverNo(int num, ListaHome lh)
            throws InvalidMoveException
    {
        NoCarta no = this.buscarNo(num);
        lh.receberNo(no);
    }
    
    /**
     * Valida um conjunto de nós.
     * Para um conjunto de nós ser válido e poder ser arrastado,
     * todas as cartas depois dele devem estar em ordem decrescente,
     * com cores intercaladas.
     */
    private boolean validaConjuntoNo(NoCarta no) {
        NoCarta aux = no;
        
        // Verifica se os nós estão na sequencia correta
        while(aux != null) {
            if(
                aux.getProx() != null &&
                !Paciencia.cartaSequenciaValida(aux, aux.getProx())
            )
                return false;
            
            aux = aux.getProx();
        }
        
        return true;
    }
    
    @Override
    public void remover(NoCarta nc) {
        super.remover(nc);
        
        // Abre a ultima carta da lista
        if(!this.listaVazia())
            this.elementoFinal().setOpen(true);
    }
}