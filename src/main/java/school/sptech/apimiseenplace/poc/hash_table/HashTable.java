package school.sptech.apimiseenplace.poc.hash_table;

import school.sptech.apimiseenplace.dto.produto.ProdutoListagemDTO;

public class HashTable {
    private ListaLigada[] tab;

    public HashTable() {
        this.tab = new ListaLigada[26];
        for (int i = 0; i < 26; i++) {
            this.tab[i] = new ListaLigada();
        }
    }

    public Integer funcaoHash(String nomeProduto) {
        char character = nomeProduto.toUpperCase().charAt(0);
        if (character >= 'A' && character <= 'Z') {
            return character - 65;
        }
        return 25;
    }

    public void insere(ProdutoListagemDTO produto) {
        this.tab[funcaoHash(produto.getNome())].insereNode(produto);
    }

    public boolean busca(ProdutoListagemDTO produto) {
        return this.tab[funcaoHash(produto.getNome())].buscaNode(produto.getId()) != null ? true : false;
    }

    public void remove(ProdutoListagemDTO produto) {
        this.tab[funcaoHash(produto.getNome())].removeNode(produto.getId());
    }

    public void exibe() {
        for (int i = 0; i < this.tab.length; i++) {
            System.out.printf("Fila %s: \n", i);
            this.tab[i].exibe();
        }
    }

    public ListaLigada[] getTab() {
        return tab;
    }

    public void setTab(ListaLigada[] tab) {
        this.tab = tab;
    }
}
