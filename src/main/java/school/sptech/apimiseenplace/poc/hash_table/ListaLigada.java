package school.sptech.apimiseenplace.poc.hash_table;

import school.sptech.apimiseenplace.dto.produto.ProdutoListagemDTO;

import java.util.ArrayList;
import java.util.List;

public class ListaLigada {
    private Node head;

    public ListaLigada() {
        head = new Node(null, null);
    }

    public void insereNode(ProdutoListagemDTO info) {
        Node newNode = new Node(info, head.getNext());
        this.head.setNext(newNode);
    }

    public void exibe() {
        Node currentNode = head.getNext();

        while (currentNode != null) {
            System.out.println(currentNode.getInfo());
            currentNode = currentNode.getNext();
        }
    }

    public Node buscaNode(int idProduto) {
        Node currentNode = head.getNext();

        while (currentNode != null) {
            if (currentNode.getInfo().getId() == idProduto) {
                return currentNode;
            }
            currentNode = currentNode.getNext();
        }

        return null;
    }

    public boolean removeNode(int idProduto) {
        Node previousNode = head;
        Node currentNode = head.getNext();

        while (currentNode != null) {
            if (currentNode.getInfo().getId() == idProduto) {
                previousNode.setNext(currentNode.getNext());
                return true;
            }
            else {
                previousNode = currentNode;
                currentNode = currentNode.getNext();
            }
        }

        return false;
    }

    public int getTamanho() {
        Node currentNode = head.getNext();
        int tamanho = 0;

        while (currentNode != null) {
            tamanho++;
            currentNode = currentNode.getNext();
        }

        return tamanho;
    }

    public List<ProdutoListagemDTO> buscarNodes(String nome) {
        List<ProdutoListagemDTO> produtoListagemDTOS = new ArrayList<>();

        Node currentNode = head.getNext();

        while (currentNode != null) {
            if (currentNode.getInfo().getNome().toLowerCase().contains(nome.toLowerCase())) {
                produtoListagemDTOS.add(currentNode.getInfo());
            }
            currentNode = currentNode.getNext();
        }

        return produtoListagemDTOS;
    }
}
