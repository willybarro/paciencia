/*
 * PacienciaUI.java
 *
 * Created on Oct 30, 2011, 2:34:27 PM
 */
package paciencia.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import paciencia.Paciencia;
import paciencia.helper.ResourceHelper;
import paciencia.model.base.ListaBase;
import paciencia.model.home.ListaHome;
import paciencia.model.home.MonteHome;
import paciencia.model.NoCarta;
import paciencia.model.base.PilhaBase;
import paciencia.model.home.PilhaHome;

/**
 *
 * @author willy
 */
public class PacienciaUI extends javax.swing.JFrame {
    public static JLabel warningBg;
    public static JLabel warningBox;
    public JLabel cartaHolder;
    public JLabel[] visualCartasMonte = new JLabel[3];
    private Paciencia jogo;
    public final static int cardYOffset = 16;
    public final static int cardXOffset = 105;

    /** Creates new form PacienciaUI */
    public PacienciaUI(Paciencia jogo) {
        // Icones
        ArrayList<Image> icons = new ArrayList<Image>();
        icons.add(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("paciencia/resources/ui/icon_56.gif")));
        icons.add(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("paciencia/resources/ui/icon_16.gif")));
        this.setIconImages(icons);
        
        // Seta o pane principal
        this.setContentPane(new PacienciaPane());
        
        // Inicia os componentes
        initComponents();
        this.getContentPane().setBackground(new Color(9, 87, 37));
        
        // Alinha a janela ao meio
        this.setLocationRelativeTo(null);
        
        // Painel de retorno
        this.iniciaWarningBox();
        
        // Inicia as partes visuais do jogo
        this.jogo = jogo;
        this.iniciaJogo();
    }
    
    private void iniciaJogo() {
        this.iniciaBaralho();
        this.iniciaBases();
        this.iniciaListas();
    }
    
    public void iniciaBaralho() {
        // Adiciona a cartinha clicável para pegar outras cartas
        int barX = 40;
        int barY = 22;
        for(int i=0; i<3; i++) {
            JLabel carta = new JLabel(ResourceHelper.getCarta("back.png"));
            visualCartasMonte[i] = carta;
                    
            carta.setBounds(0, 0, 70, 100);
            carta.setLocation(barX, barY);
            this.getContentPane().add(carta);
            this.getContentPane().setComponentZOrder(carta, 0);
            
            barX += 2;
            barY += 2;
            
            if(i == 2) {
                carta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                carta.addMouseListener(new MonteMouseListener(this));
            }
        }
        
        // Adiciona as cartas do monte no pane, invisiveis.
        MonteHome monteHome = this.jogo.getMonteHome();
        for(int i = 0; i < 8; i++) {
            Stack<NoCarta> monte = monteHome.retira3Cartas();
            for(NoCarta carta : monte) {
                this.getContentPane().add(carta);
                carta.addMouseListener(new CartaMouseListener(this, this.jogo));
                this.getContentPane().setComponentZOrder(carta, 0);
                carta.setVisible(false);
            }
        }
        
        // Adiciona a carta "vazia"
        cartaHolder = new JLabel(ResourceHelper.getCarta("empty_card.png"));
        cartaHolder.setBounds(0, 0, 70, 100);
        cartaHolder.setLocation(40, 22);
        cartaHolder.addMouseListener(new MonteMouseListener(this));
        cartaHolder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.getContentPane().add(cartaHolder);
        this.getContentPane().setComponentZOrder(cartaHolder, this.getContentPane().getComponentCount()-1);
    }
    
    private void iniciaWarningBox() {
        PacienciaUI.warningBg = new JLabel(ResourceHelper.getUi("caixa.png"));
        PacienciaUI.warningBg.setBounds(0, 0, 206, 106);
        PacienciaUI.warningBg.setLocation(40, 440);
        
        PacienciaUI.warningBox = new JLabel();
        PacienciaUI.warningBox.setForeground(Color.black);
        PacienciaUI.warningBox.setBounds(8, 26, PacienciaUI.warningBg.getWidth(), PacienciaUI.warningBg.getHeight());
        PacienciaUI.warningBox.setHorizontalAlignment(JLabel.LEFT);
        PacienciaUI.warningBox.setVerticalAlignment(JLabel.TOP);
        PacienciaUI.warningBg.add(PacienciaUI.warningBox);
        
        this.getContentPane().add(PacienciaUI.warningBg);
        
        PacienciaUI.hideWarning();
        
        PacienciaUI.warningBox.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {}

            @Override
            public void mousePressed(MouseEvent me) {
                PacienciaUI.hideWarning();
            }

            @Override
            public void mouseReleased(MouseEvent me) {}

            @Override
            public void mouseEntered(MouseEvent me) {}

            @Override
            public void mouseExited(MouseEvent me) {}
        });
    }
    
    public static void showWarning(String text) {
        PacienciaUI.warningBg.setVisible(true);
        PacienciaUI.warningBox.setVisible(true);
        PacienciaUI.warningBox.setText(text);
    }
    
    public static void hideWarning() {
        PacienciaUI.warningBg.setVisible(false);
        PacienciaUI.warningBox.setVisible(false);
    }
    
    private Stack<NoCarta> ult3Cartas = null;
    private boolean showingHolder = false;
    /**
     * Abre 3 cartas do baralho
     */
    public void abre3Cartas() {
        MonteHome monteHome = this.jogo.getMonteHome();
        
        // Esconde as 3 cartas anteriores
        if(ult3Cartas != null) {
            for(NoCarta carta : ult3Cartas) {
                carta.setVisible(false);
            }
        }
        
        if(showingHolder && monteHome.getMontes().size() > 1) {
            this.hideHolder();
        } else {
            if((monteHome.getNumMonteAtivo()+1 == monteHome.getMontes().size())
                || monteHome.getMontes().size() == 1
            ) {
                this.showHolder();
            } else {
                this.hideHolder();
            }
            
            // Habilita novas 3 cartas
            int baralhoX = PacienciaUI.cardXOffset + 40;
            int baralhoY = 22;
            ult3Cartas = monteHome.retira3Cartas();
            if(ult3Cartas != null) {
                int i = 1;
                for(NoCarta carta : ult3Cartas) {
                    carta.setOpen(true);
                    carta.setVisible(true);
                    carta.setLocation(baralhoX, baralhoY);
                    this.getContentPane().setComponentZOrder(carta, ult3Cartas.size()-i);

                    if(i == ult3Cartas.size())
                        carta.setDraggable(true);
                    else
                        carta.setDraggable(false);

                    i++;
                    baralhoX += 16;
                }
            }
        }
    }
    
    private void showHolder() {
        for(JLabel c : this.visualCartasMonte)
            c.setVisible(false);
        
        this.cartaHolder.setVisible(true);
        this.showingHolder = true;
    }
    
    private void hideHolder() {
        for(JLabel c : this.visualCartasMonte)
            c.setVisible(true);
        
        this.cartaHolder.setVisible(false);
        this.showingHolder = false;
    }
    
    /**
     * Inicia a interface das pilhas de base
     */
    private void iniciaBases() {
        int baseX = 355;
        int baseY = 22;
        for(PilhaHome pilha : this.jogo.getBases()) {
            // Coloca a imagem de meia-borda
            PilhaBase pilhaBase = new PilhaBase(baseX, baseY, pilha);
            pilha.setBase(pilhaBase);
            this.getContentPane().add(pilhaBase);
            
            baseX += PacienciaUI.cardXOffset;
        }
    }
    
    private void iniciaListas() {
        int cartaX = 40;
        int cartaY = 150;
        int i = 0;
        for(ListaHome lista : this.jogo.getListas()) {
            // Coloca a imagem de base inicial (meia-borda)
            ListaBase listaBase = new ListaBase(cartaX, cartaY, lista);
            lista.setBase(listaBase);
            this.getContentPane().add(listaBase);
            
            // Começa a colocar as cartas de fato
            for(int j = 0; j < lista.contarNos(); j++) {
                NoCarta carta = lista.recuperaNo(j+1);
                if(carta != null) {
                    NoCarta label = carta;
                    label.setLocation(cartaX, cartaY);
                    this.getContentPane().add(label);
                    label.addMouseListener(new CartaMouseListener(this, this.jogo));
                    this.getContentPane().setComponentZOrder(carta, lista.contarNos()-j-1);
                    
                    cartaY += PacienciaUI.cardYOffset;
                }
            }
            
            cartaX += PacienciaUI.cardXOffset;
            cartaY = 150;
            i++;
        }
    }
    
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        tb_arquivoNovojogo = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        tb_arquivoSair = new javax.swing.JMenuItem();
        tb_ajuda = new javax.swing.JMenu();
        tb_ajudaSobre = new javax.swing.JMenuItem();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Paciência");
        setBackground(new java.awt.Color(8, 87, 37));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setResizable(false);
        getContentPane().setLayout(null);

        jMenu1.setText("Arquivo");

        tb_arquivoNovojogo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        tb_arquivoNovojogo.setText("Novo jogo");
        tb_arquivoNovojogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tb_arquivoNovojogoMouseReleased(evt);
            }
        });
        tb_arquivoNovojogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb_arquivoNovojogoActionPerformed(evt);
            }
        });
        jMenu1.add(tb_arquivoNovojogo);
        jMenu1.add(jSeparator1);

        tb_arquivoSair.setText("Sair");
        tb_arquivoSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb_arquivoSairActionPerformed(evt);
            }
        });
        jMenu1.add(tb_arquivoSair);

        jMenuBar1.add(jMenu1);

        tb_ajuda.setText("Ajuda");
        tb_ajuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb_ajudaActionPerformed(evt);
            }
        });

        tb_ajudaSobre.setText("Sobre");
        tb_ajudaSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tb_ajudaSobreActionPerformed(evt);
            }
        });
        tb_ajuda.add(tb_ajudaSobre);

        jMenuBar1.add(tb_ajuda);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void tb_arquivoNovojogoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_arquivoNovojogoMouseReleased
    
}//GEN-LAST:event_tb_arquivoNovojogoMouseReleased

private void tb_arquivoSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb_arquivoSairActionPerformed
    System.exit(0);
}//GEN-LAST:event_tb_arquivoSairActionPerformed

private void tb_arquivoNovojogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb_arquivoNovojogoActionPerformed
    String[] options = new String[] {"Sim", "Não"};
    int resposta = JOptionPane.showOptionDialog(
        null,
        "Tem certeza que deseja iniciar um novo jogo?",
        "Novo jogo",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[0]
    );
    
    if(resposta == 0)
        paciencia.Paciencia.novoJogo();
}//GEN-LAST:event_tb_arquivoNovojogoActionPerformed

private void tb_ajudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb_ajudaActionPerformed
    
}//GEN-LAST:event_tb_ajudaActionPerformed

private void tb_ajudaSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tb_ajudaSobreActionPerformed
    JOptionPane.showMessageDialog(
            null,
            "Paciência\n"
            + "Professora: Lucy Mari Tabuti\n"
            + "Matéria: Estrutura de Dados\n\n"
            + "Criado por: Willy G M B Raffel - 2CCO1 2011",
            "Sobre",
            JOptionPane.PLAIN_MESSAGE
    );
}//GEN-LAST:event_tb_ajudaSobreActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PacienciaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PacienciaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PacienciaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PacienciaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                //new PacienciaUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JMenu tb_ajuda;
    private javax.swing.JMenuItem tb_ajudaSobre;
    private javax.swing.JMenuItem tb_arquivoNovojogo;
    private javax.swing.JMenuItem tb_arquivoSair;
    // End of variables declaration//GEN-END:variables
}
