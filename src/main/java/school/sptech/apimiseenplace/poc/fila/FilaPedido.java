package school.sptech.apimiseenplace.poc.fila;

import lombok.Getter;
import lombok.Setter;
import school.sptech.apimiseenplace.entity.Pedido;

@Getter
@Setter
public class FilaPedido {

    private int tamanho;
    private Pedido[] fila;

    public FilaPedido() {
        fila = new Pedido[5];
        tamanho = 0;
    }

    public boolean isEmpty() {
        if (tamanho == 0) {
            return true;
        }
        return false;
    }

    public boolean isFull() {
        if (tamanho == fila.length) {
            return true;
        }
        return false;
    }

    public void insert(Pedido info) {
        if (isFull()) {
            throw new IllegalStateException();
        } else {
            fila[tamanho] = info;
            tamanho++;
        }
    }

    public Pedido peek() {
        if (isEmpty()) {
            throw new IllegalStateException();
        } else {
            return fila[0];
        }
    }

    public Pedido poll() {
        if (isEmpty()) {
            throw new IllegalStateException();
        } else {
            Pedido retorno = fila[0];
            for (int i = 0; i < tamanho - 1; i++) {
                fila[i] = fila[i + 1];
            }
            fila[tamanho - 1] = null;
            tamanho--;
            return retorno;
        }
    }

    public void exibe() {
        if (isEmpty()) {
            System.out.println("Lista vazia");
        } else {
            for (Pedido string : fila) {
                System.out.println(string);
            }
        }
    }

}
