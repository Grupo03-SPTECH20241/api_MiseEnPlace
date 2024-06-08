package school.sptech.apimiseenplace.poc.pilha;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PilhaBreadcrumbs {

    private String[] pilha;
    private int topo;

    public PilhaBreadcrumbs() {
        this.pilha = new String[20];
        this.topo = -1;
    }

    public Boolean isEmpty() {
        return topo == -1;
    }

    public Boolean isFull() {
        return topo == pilha.length - 1;
    }

    public void push(String info) {
        if (isFull()) {
            throw new IllegalStateException();
        }
        else {
            pilha[++topo] = info;
        }
    }

    public String pop() {
        if (isEmpty()) {
            return null;
        }
        String retorno = this.pilha[topo--];
        this.pilha[topo + 1] = null;
        return retorno;
    }

    public String peek() {
        if (isEmpty()) {
            return null;
        }
        return pilha[topo];
    }

    public void exibe() {
        if (isEmpty()) {
            System.out.println("Pilha vazia");
        }
        else {
            for (int i = topo; i >= 0; i--) {
                System.out.println(pilha[i]);
            }
        }
    }

    public void limpar() {
        this.pilha = new String[20];
        this.topo = -1;
    }

}
