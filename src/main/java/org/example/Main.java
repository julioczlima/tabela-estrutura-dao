package org.example;

import java.sql.Connection;
import java.util.List;

public class Main {

    public static void main(String[] args) {

//        CriadorTabela.criarTabela(); // já foi criada
//
//        try (Connection conexao = ConexaoDB.conectar()) {
//            ProdutoDAO produtoDAO = new ProdutoDAO(conexao);
//
//            mostrarProdutos(produtoDAO);
//
//            Produto novoProduto1 = new Produto("Notebook", 10, 1999.99, "Em estoque");
//            Produto novoProduto2 = new Produto("Smartphone", 20, 1499.99, "Estoque baixo");
//            Produto novoProduto3 = new Produto("Tablet", 15, 799.99, "Estoque baixo");
//
//            produtoDAO.inserir(novoProduto1);
//            produtoDAO.inserir(novoProduto2);
//            produtoDAO.inserir(novoProduto3);
//
//            mostrarProdutos(produtoDAO);
//
//            Produto produtoConsultado = produtoDAO.consultarPorId(1);
//            if(produtoConsultado != null) {
//                System.out.println("Produto encontrado: " + produtoConsultado.getNome());
//            } else {
//                System.out.println("Produto não encontrado.");
//            }

//        } catch (Exception e) {
//            System.err.println("Erro geral: " + e.getMessage());
//        }

        ProdutoGUI.main(args);


    }

    private static void mostrarProdutos(ProdutoDAO produtoDAO) {
        List<Produto> todosProdutos = produtoDAO.listarTodos();

        if(todosProdutos.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
        } else {
            for (Produto produto : todosProdutos) {
                System.out.println("Produto: " + produto.getNome() + " R$" + produto.getPreco());
            }
        }

    }

}