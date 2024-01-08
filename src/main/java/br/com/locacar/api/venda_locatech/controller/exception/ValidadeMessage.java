package br.com.locacar.api.venda_locatech.controller.exception;

public class ValidadeMessage {
    private String campo;
    private String mensagem;
    public ValidadeMessage() {}

    public ValidadeMessage(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
