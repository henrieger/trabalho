package com.paradigmas.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.paradigmas.Models.Disciplina;
import com.paradigmas.Models.Pedido;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.control.SingleSelectionModel;

public class PrincipalController implements Initializable {

	// Tab Pane pai
	@FXML private TabPane tPane;
	@FXML private Tab tabPedidos;
	
	// Campos de disciplina
	@FXML private ListView<Disciplina> list_ant_barreira;
	@FXML private ListView<Disciplina> list_disc_faltantes;
	@FXML private ListView<Disciplina> list_materias;

	// Campos de pedido
	@FXML private TableView<Disciplina> tabelaPedido;

	// Campos de histórico
	@FXML private Pane painelHistorico;

	// Campos de perfil
	@FXML private Label perfilNome;
    @FXML private Label perfilMaterias;
    @FXML private Label perfilAprovacao;
    @FXML private Label perfilReprovacaoNota;
    @FXML private Label perfilReprovacaoFalta;

	private DisciplinaViewController disciplinaController;
	private PedidoViewController pedidoViewController;
	private HistoricoViewController historicoViewController;
	private PerfilController perfilController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		disciplinaController = new DisciplinaViewController(list_ant_barreira, list_disc_faltantes, list_materias);
		pedidoViewController = new PedidoViewController(tabelaPedido);
		historicoViewController = new HistoricoViewController(painelHistorico);
		perfilController = new PerfilController(perfilNome, perfilMaterias, perfilAprovacao, perfilReprovacaoNota, perfilReprovacaoFalta);

		disciplinaController.initialize();
		pedidoViewController.initialize();
		historicoViewController.initialize();
		perfilController.initialize();
	}

	@FXML
	private void prosseguirParaPedido() throws Exception {
		ObservableList<Disciplina> disciplinas = disciplinaController.getList_materias().getItems();
		Pedido pedido = PedidoController.cria_pedido(disciplinas);
		pedidoViewController.updateTableData(pedido);
		SingleSelectionModel<Tab> selectionModel = tPane.getSelectionModel();
		selectionModel.select(tabPedidos);
		disciplinas.removeAll(disciplinas);
	}

	@FXML
	private void salvarPedido() throws IOException
	{
		PedidoController.salva_pedido(pedidoViewController.getPedidoAtual());
	}

	@FXML
	private void gerarPedido() throws Exception
	{
		PedidoController.gera_pedido(pedidoViewController.getPedidoAtual());
	}
}