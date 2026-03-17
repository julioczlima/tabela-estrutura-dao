package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProdutoGUI extends Application {
    private ProdutoDAO produtoDAO;
    private ObservableList<Produto> produtos;
    private TableView<Produto> tableView;
    private TextField nomeInput, quantidadeInput, precoInput;
    private ComboBox<String> statusComboBox;
    private Connection conexaoDB;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        conexaoDB = ConexaoDB.conectar();   // conecta com o DB
        produtoDAO = new ProdutoDAO(conexaoDB);     // estabelece a conexãoDB com o DAO
        produtos = FXCollections.observableArrayList(produtoDAO.listarTodos());

        stage.setTitle("Gerenciamento de Estoque de Produtos");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);

        HBox nomeProdutoBox = new HBox();
        nomeProdutoBox.setSpacing(10);
        Label nomeLabel = new Label("Produto:");
        nomeInput = new TextField();
        nomeProdutoBox.getChildren().addAll(nomeLabel, nomeInput);

        HBox quantidadeProdutoBox = new HBox();
        quantidadeProdutoBox.setSpacing(10);
        Label quantidadeLabel = new Label("Quantidade:");
        quantidadeInput = new TextField();
        quantidadeProdutoBox.getChildren().addAll(quantidadeLabel, quantidadeInput);

        HBox precoProdutoBox = new HBox();
        precoProdutoBox.setSpacing(10);
        Label precoLabel = new Label("Preço:");
        precoInput = new TextField();
        precoProdutoBox.getChildren().addAll(precoLabel, precoInput);

        HBox statusProdutoBox = new HBox();
        statusProdutoBox.setSpacing(10);
        Label statusLabel = new Label("Status:");
        statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("Estoque Normal", "Estoque Baixo");
        statusProdutoBox.getChildren().addAll(statusLabel, statusComboBox);

        Button addButton = new Button("Adicionar");
        addButton.setOnAction(e -> {

            String preco = precoInput.getText().replace(',', '.');
            Produto produto = new Produto(nomeInput.getText(),
                    Integer.parseInt(quantidadeInput.getText()),
                    Double.parseDouble(preco),
                    statusComboBox.getValue());

            produtoDAO.inserir(produto);
            produtos.setAll(produtoDAO.listarTodos());  // atualiza a lista visualizável
            limparCampos();
        });

        Button updateButton = new Button("Atualizar");
        updateButton.setOnAction(e -> {

            Produto selectedProduto = tableView.getSelectionModel().getSelectedItem();
            if (selectedProduto != null) {
                selectedProduto.setNome(nomeInput.getText());
                selectedProduto.setQuantidade(Integer.parseInt(quantidadeInput.getText()));
                String preco = precoInput.getText().replace(',', '.');
                selectedProduto.setPreco(Double.parseDouble(preco));
                selectedProduto.setStatus(statusComboBox.getValue());
                produtoDAO.atualizar(selectedProduto);  // atualiza o produto no DB
                produtos.setAll(produtoDAO.listarTodos());  // atualiza a lista visualizável
                limparCampos();
            }

        });

        Button deleteButton = new Button("Deletar");
        deleteButton.setOnAction(e -> {
            Produto selectedProduct = tableView.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                produtoDAO.excluir(selectedProduct.getId());
                produtos.setAll(produtoDAO.listarTodos());
                limparCampos();
            }
        });

        Button clearButton = new Button("Limpar");
        clearButton.setOnAction(e -> limparCampos());

        tableView = new TableView<>();
        tableView.setItems(produtos);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        List<TableColumn<Produto, ?>> columns = List.of(
                criarColuna("ID", "id"),
                criarColuna("Produto", "nome"),
                criarColuna("Quantidade", "quantidade"),
                criarColuna("Preço", "preco"),
                criarColuna("Status", "status")
        );
        tableView.getColumns().addAll(columns);

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        nomeInput.setText(newSelection.getNome());
                        quantidadeInput.setText(String.valueOf(newSelection.getQuantidade()));
                        precoInput.setText(String.valueOf(newSelection.getPreco()));
                        statusComboBox.setValue(newSelection.getStatus());
                    }
                });

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton, clearButton);

        vBox.getChildren().addAll(nomeProdutoBox, quantidadeProdutoBox, precoProdutoBox, statusProdutoBox,
                buttonBox, tableView);

        Scene scene = new Scene(vBox, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        try {
            conexaoDB.close();
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }

    private void limparCampos() {
        nomeInput.clear();
        quantidadeInput.clear();
        precoInput.clear();
        statusComboBox.setValue(null);
    }

    /**
     * Cria uma coluna para o TableView
     *
     * @param title    O título da coluna que será criada e exibida no cabeçalho.
     * @param property A propriedade do objeto Produto que esta coluna deve exibir.
     * @return A coluna configurada para a TableView.
     */
    private TableColumn<Produto, String> criarColuna(String title, String property) {
        TableColumn<Produto, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }

}
