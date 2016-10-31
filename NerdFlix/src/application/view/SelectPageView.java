package application.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import application.Login;
import application.Main;
import application.template.NerdFlixApplication;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import persistence.dao.GenericDAO;
import persistence.dominio.Banco;
import persistence.dominio.Filme;
import persistence.dominio.Serie;
import persistence.dominio.Usuario;

public class SelectPageView extends NerdFlixApplication {
	private static Stage stage;
	private BorderPane root = new BorderPane();

	List<Filme> filmes = new ArrayList<Filme>();
	List<Serie> series = new ArrayList<Serie>();

	@FXML
	private TilePane tileFilmes;
	@FXML
	private TilePane tileSeries;

	@FXML
	private ComboBox<String> comboBox;

	@FXML
	private TextField pesquisaTextField;

	@FXML
	private Button uploadBT;

	@Override
	public void startEspecifico(Stage primaryStage) {
		try {
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
			primaryStage.setScene(scene);
			SelectPageView.stage = primaryStage;
			initView();

			SelectPageView.stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/PageView.fxml"));
			ScrollPane pageView = (ScrollPane) loader.load();
			root.setCenter(pageView);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void initialize() {
		Usuario u = new Usuario();
		GenericDAO dao = new GenericDAO();
		u = Banco.getCurrentUser();

		if (u.getLogin().equalsIgnoreCase("Admin")) {
			this.uploadBT.setVisible(true);
		}

		ObservableList<String> values = FXCollections.observableArrayList();
		values.add("-");
		values.add("Título");
		values.add("Gênero");
		values.add("Resolução");
		comboBox.setItems(values);

		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					filmes = dao.findAll(Filme.class);
					for (int i = 0; i < filmes.size(); i++) {
						Filme f = filmes.get(i);
						if (f instanceof Serie) {
							filmes.remove(f);
							i--;
						}
					}

					series = dao.findAll(Serie.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		task.run();

		initFilmes(u, filmes);
		initSeries(u, series);
	}

	@FXML
	private void handleVoltarButton() {
		try {
			new Login().start(new Stage());
			SelectPageView.stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleUploadButton() {
		try {
			new CadastroFilmeController().start(new Stage());
			SelectPageView.stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handlePesquisa() {
		findFilmes();
		findSeries();
	}

	@FXML
	private void handleSelecionarFilme() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void findFilmes() {
		List<Filme> foundItems = new ArrayList<Filme>();
		String buscarPor = comboBox.getSelectionModel().getSelectedItem();

		switch (buscarPor) {
		case "Título":
			for (Filme filme : this.filmes) {
				if (filme.getTitulo().contains(this.pesquisaTextField.getText())) {
					foundItems.add(filme);
				}
			}
			break;
		case "Gênero":
			for (Filme filme : this.filmes) {
				if (filme.getGenero().contains(this.pesquisaTextField.getText())) {
					foundItems.add(filme);
				}
			}
			break;
		case "Resolução":
			for (Filme filme : this.filmes) {
				if (filme.getResolucao().contains(this.pesquisaTextField.getText())) {
					foundItems.add(filme);
				}
			}
			break;
		default:
			for (Filme filme : this.filmes) {
				foundItems.add(filme);
			}
			break;
		}

		tileFilmes.getChildren().clear();
		initFilmes(Banco.getCurrentUser(), foundItems);
	}

	private void findSeries() {
		List<Serie> foundItems = new ArrayList<Serie>();
		String buscarPor = comboBox.getSelectionModel().getSelectedItem();

		switch (buscarPor) {
		case "Título":
			for (Serie serie : this.series) {
				if (serie.getTitulo().contains(this.pesquisaTextField.getText())) {
					foundItems.add(serie);
				}
			}
			break;
		case "Gênero":
			for (Serie serie : this.series) {
				if (serie.getGenero().contains(this.pesquisaTextField.getText())) {
					foundItems.add(serie);
				}
			}
			break;
		case "Resolução":
			for (Serie serie : this.series) {
				if (serie.getResolucao().contains(this.pesquisaTextField.getText())) {
					foundItems.add(serie);
				}
			}
			break;
		default:
			for (Serie serie : this.series) {
				foundItems.add(serie);
			}
			break;
		}

		tileSeries.getChildren().clear();
		initSeries(Banco.getCurrentUser(), foundItems);
	}

	private void initFilmes(Usuario u, List<Filme> filmes) {
		for (Filme filme : filmes) {
			if (filme == null || filme.getThumb() == null || filme.getThumb().isEmpty()) {
				continue;
			} else if (u != null && u.getIdade() < 18 && filme.getGenero().equalsIgnoreCase("épico")) {
				continue;
			}

			ImageView novo = new ImageView(filme.getThumb());

			novo.setPreserveRatio(true);
			novo.setFitWidth(180);

			novo.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					PlayerController player = new PlayerController();

					player.setParameters(filme.getPath());
					player.start(new Stage());
					SelectPageView.stage.close();
				}
			});

			tileFilmes.getChildren().add(novo);
		}
	}

	private void initSeries(Usuario u, List<Serie> series) {
		for (Serie serie : series) {
			if (serie == null || serie.getThumb() == null || serie.getThumb().isEmpty()) {
				continue;
			} else if (u != null && u.getIdade() < 18 && serie.getGenero().equalsIgnoreCase("épico")) {
				continue;
			}

			ImageView novo = new ImageView(serie.getThumb());

			novo.setPreserveRatio(true);
			novo.setFitWidth(180);

			novo.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					PlayerController player = new PlayerController();

					player.setParameters(serie.getPath());
					player.start(new Stage());
					SelectPageView.stage.close();
				}
			});

			tileSeries.getChildren().add(novo);
		}
	}
}
