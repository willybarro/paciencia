/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paciencia.helper;

import javax.swing.ImageIcon;

/**
 *
 * @author willy
 */
public class ResourceHelper {
    public static ImageIcon getCarta(String nomeArquivo) {
        return new ImageIcon(ClassLoader.getSystemResource("paciencia/resources/cartas/" + nomeArquivo));
    }
    
    public static ImageIcon getUi(String nomeArquivo) {
        return new ImageIcon(ClassLoader.getSystemResource("paciencia/resources/ui/" + nomeArquivo));
    }
}
