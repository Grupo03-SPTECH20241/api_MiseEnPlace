package school.sptech.apimiseenplace.poc.hash_table;

import school.sptech.apimiseenplace.dto.produto.ProdutoListagemDTO;

public class Node {

    private ProdutoListagemDTO info;

    private Node next;

    public Node(ProdutoListagemDTO info, Node next) {
        this.info = info;
        this.next = next;
    }

    public ProdutoListagemDTO getInfo() {
        return info;
    }

    public void setInfo(ProdutoListagemDTO info) {
        this.info = info;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" + "info=" + info + ", next=" + next + '}';
    }
}
