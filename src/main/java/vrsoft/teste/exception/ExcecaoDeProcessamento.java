package vrsoft.teste.exception;

public class ExcecaoDeProcessamento extends RuntimeException {
    public ExcecaoDeProcessamento(String mensagem) {
        super(mensagem);
    }
}
