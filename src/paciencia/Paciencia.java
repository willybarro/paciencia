package paciencia;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import paciencia.model.Baralho;
import paciencia.model.NoCarta;
import paciencia.model.home.ListaHome;
import paciencia.model.home.MonteHome;
import paciencia.model.home.PilhaHome;

public final class Paciencia {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
            
            // Inicia o jogo
            Paciencia.novoJogo();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu algum erro ao iniciar o jogo.\nSeu sistema operacional não é suportado.");
        }
    }
    
    private Baralho baralho;
    private PilhaHome[] bases = new PilhaHome[4];
    private ListaHome[] listas = new ListaHome[7];
    private MonteHome monteHome;
    public static JFrame f;

    public static void novoJogo() {
        // Se já existir um ui criado, remover
        // @TODO trocar
        if (Paciencia.f != null)
            Paciencia.f.dispose();

        // Inicia o jogo
        Paciencia p = new Paciencia();
    }

    private Paciencia() {
        // Inicia o jogo
        baralho = new Baralho();

        // Cria as pilhas base (que terão as cartas finais montadas)
        bases[0] = new PilhaHome("Pilha 1");
        bases[1] = new PilhaHome("Pilha 2");
        bases[2] = new PilhaHome("Pilha 3");
        bases[3] = new PilhaHome("Pilha 4");

        // Inicia as listas
        this.iniciaMonte();
        this.iniciaListas();

        // Inicia a UI
        Paciencia.f = new paciencia.ui.PacienciaUI(this);
        Paciencia.f.setVisible(true);
    }
    
    /**
     * Inicia os montes de carta
     */
    public void iniciaMonte() {
        // Separa os 8 montes de 3 cartas que não vão pras listas
        this.monteHome = new MonteHome();
        for(int j = 0; j < 24; j++) {
            NoCarta carta = this.baralho.retiraCartaTopo();
            monteHome.inserir(carta);
        }
    }

    /**
     * Inicia as listas de cartas
     */
    private void iniciaListas() {
        for (int i = 0; i < 7; i++) {
            ListaHome lista = new ListaHome("Lista " + (i + 1));

            // Retira do baralho e receberNo nas listas de cartas
            for (int j = 0; j < (i + 1); j++) {
                NoCarta nc = this.baralho.retiraCartaTopo();
                nc.setHome(lista);
                lista.inserir(nc);
            }

            this.listas[i] = lista;
        }
    }

    /**
     * Valida se a carta1 é a sequencia da carta2 (crescentemente).
     * 
     * @param carta1
     * @param carta2
     * @return 
     */
    public static boolean cartaSequenciaValida(NoCarta carta1, NoCarta carta2) {
        if (carta1 == null || carta2 == null)
            return false;

        return ((carta1.getNumero() == carta2.getNumero() + 1)
                && (carta1.getNaipe().getCor() != carta2.getNaipe().getCor()));
    }

    public ListaHome[] getListas() {
        return this.listas;
    }
    
    public MonteHome getMonteHome() {
        return this.monteHome;
    }
    
    public PilhaHome[] getBases() {
        return this.bases;
    }
    
    public Baralho getBaralho() {
        return this.baralho;
    }
    
    /**
     * Verifica se o jogo acabou.
     * O jogo termina quando o topo de todas as pilhas-base são K
     */
    public void verificaFimDeJogo() {
        int numK = 0;
        for(PilhaHome ph : this.bases) {
            if(ph.elementoTopo() != null && ph.elementoTopo().getNumRep().equals("K")) {
                numK++;
            }
        }
        
        if(numK == 4) {
            JOptionPane.showMessageDialog(null, "Parabéns, você terminou o jogo!");
            paciencia.Paciencia.novoJogo();
        }
    }
}