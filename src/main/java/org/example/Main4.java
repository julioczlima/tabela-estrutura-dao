package org.example;

import java.sql.Connection;
import java.util.List;

public class Main4 {

     public static void main(String[] args) {
        try (Connection conexao = ConexaoDB.conectar()) {
            ProdutoDAO produtoDAO = new ProdutoDAO(conexao);

            produtoDAO.excluirTodos();
            mostrarProdutos(produtoDAO);

            Produto novoProduto1 = new Produto("Notebook", 10, 1999.99, "Em estoque");
            Produto novoProduto2 = new Produto("Smartphone", 20, 1499.99, "Estoque baixo");
            Produto novoProduto3 = new Produto("Tablet", 15, 799.99, "Estoque baixo");

            produtoDAO.inserir(novoProduto1);
            produtoDAO.inserir(novoProduto2);
            produtoDAO.inserir(novoProduto3);

            mostrarProdutos(produtoDAO);

            produtoDAO.excluir(1);
            mostrarProdutos(produtoDAO);

            Produto novoProduto4 = new Produto("Mouse", 50, 199.99, "Em estoque");
            produtoDAO.inserir(novoProduto4);
            mostrarProdutos(produtoDAO);


        } catch (Exception e) {
            System.err.println("Erro geral: " + e.getMessage());
        }

    }

    private static void mostrarProdutos(ProdutoDAO produtoDAO) {
        List<Produto> todosProdutos = produtoDAO.listarTodos();

        if (todosProdutos.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
        } else {
            for (Produto produto : todosProdutos) {
                System.out.printf("%s | Produto: %s | R$%.2f\n",
                        produto.getId(), produto.getNome(), produto.getPreco());
            }
        }

    }
}
