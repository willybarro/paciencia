package paciencia.exceptions;

/**
 * Exception que será jogada quando algum movimento inválido for jogado
 * @author willy
 */
public class InvalidMoveException extends java.io.IOException {
    public final static int GENERICO = 0;
    public final static int LISTA_NO_FECHADO = 1;
    public final static int LISTA_NO_INVALIDO = 2;
    public final static int LISTA_VAZIA_APENAS_REIS = 3;
    public final static int LISTA_SEQUENCIA_INVALIDA = 4;
    public final static int PILHA_NO_SEQUENCIA_INVALIDA = 5;
    
    private int code = 0;
    
    public InvalidMoveException(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return this.code;
    }
    
    @Override
    public String getMessage() {
        String msg = "<span style=\"color: #0000ff;\">Movimento Inválido</span><br />";
        switch(this.code) {
            case LISTA_NO_FECHADO:
                msg += "Você não pode arrastar um nó fechado.";
                break;
            case LISTA_NO_INVALIDO:
                msg += "O nó que você está tentando<br>arrastar está na ordem errada.";
                break;
            case LISTA_VAZIA_APENAS_REIS:
                msg += "Você só pode iniciar uma lista com um REIS.";
                break;
            case LISTA_SEQUENCIA_INVALIDA:
                msg += "Você deve alternar entre as cores e<br>seguir a sequencia: K, Q, J, 10, 9, 8, 7, 6, 5, 4, 3, 2, A.";
                break;
            case PILHA_NO_SEQUENCIA_INVALIDA: 
                msg += "Você só pode iniciar uma pilha com<br>Ases.";
                break;
            case GENERICO:
            default:
                msg += "Movimento inválido.";
                break;
        }
        
        return msg;
    }
}
