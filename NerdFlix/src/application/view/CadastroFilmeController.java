package application.view;

import java.io.File;

import javax.swing.JOptionPane;

import application.Login;
import application.Main;
import application.template.NerdFlixApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import persistence.dao.GenericDAO;
import persistence.dominio.Filme;
import persistence.dominio.Serie;
import persistence.dominio.Usuario;

public class CadastroFilmeController extends NerdFlixApplication {
	private static Stage stage;
	private BorderPane root = new BorderPane();
	
	@FXML
	private TextField tituloTextF;
	@FXML
	private TextField sinopseTextF;
	@FXML
	private TextField generoTextF;
	@FXML
	private TextField duracaoTextF;
	@FXML
	private TextField resolucaoTextF;
	@FXML
	private TextField thumbTextF;
	@FXML
	private TextField pathTextF;
	@FXML
	private RadioButton filmeRadioB;
	@FXML
	private RadioButton serieRadioB;
	
	@Override
	public void startEspecifico(Stage primaryStage) {
		try {
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Cadastro de Filmes e Séries");
			CadastroFilmeController.stage = primaryStage;
			initView();
			
			CadastroFilmeController.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initView() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CadastroFilme.fxml"));
            AnchorPane pageView = (AnchorPane) loader.load();
            root.setCenter(pageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	private void handleCancelarButton(){
		try {
			new SelectPageView().start(new Stage());
			CadastroFilmeController.stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleCadastrarButton(){
		try {
			if (!validate()) {
				return;
			}
			
			GenericDAO dao = new GenericDAO();
			Filme film = new Filme();
			Serie serie = new Serie();
			
			if (filmeRadioB.isSelected()) {
				film.setTitulo(tituloTextF.getText());
				film.setSinopse(sinopseTextF.getText());
				film.setGenero(generoTextF.getText());
				film.setDuracao(Integer.parseInt(duracaoTextF.getText()));
				film.setResolucao(resolucaoTextF.getText());
				film.setThumb(thumbTextF.getText());
				film.setPath(pathTextF.getText());
				dao.inserir(film);
				JOptionPane.showMessageDialog(null, "Filme "+film.getTitulo()+" cadastrado com sucesso!");
			}
			else if (serieRadioB.isSelected()){
				serie.setTitulo(tituloTextF.getText());
				serie.setSinopse(sinopseTextF.getText());
				serie.setGenero(generoTextF.getText());
				serie.setDuracao(Integer.parseInt(duracaoTextF.getText()));
				serie.setResolucao(resolucaoTextF.getText());
				serie.setThumb(thumbTextF.getText());
				serie.setPath(pathTextF.getText());
				dao.inserir(serie);
				JOptionPane.showMessageDialog(null, "Série "+serie.getTitulo()+" cadastrada com sucesso!");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao tentar cadastrar o vídeo!");
			e.printStackTrace();
		}
		finally {
			try {
				new SelectPageView().start(new Stage());
				CadastroFilmeController.stage.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void handleChooseThumbButton(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Picture File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Picture Files", "*.jpg", "*.png", "*.gif", "*.jpeg"));
		 File selectedFile = fileChooser.showOpenDialog(stage);
		 if (selectedFile != null) {
			 String formatar = selectedFile.getAbsolutePath();
			 formatar = formatar.replace('\\', '/');
			 
			 formatar = new File(formatar).toURI().toString();
			 thumbTextF.setText(formatar);	
		 }
	}
	
	@FXML
	private void handleChoosePathButton(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Videos File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Video Files", "*.avi", "*.mp4", "*.rmvb", "*.mkv"),
				new ExtensionFilter("All Files", "*.*"));
		 File selectedFile = fileChooser.showOpenDialog(stage);
		 if (selectedFile != null) {
			 String formatar = selectedFile.getAbsolutePath();
			 formatar = formatar.replace('\\', '/');
			 
			 formatar = new File(formatar).toURI().toString();
			 pathTextF.setText(formatar);	
		 }
	}
	
	private boolean validate(){
		boolean validado = true;
		StringBuilder erros = new StringBuilder();
		if (tituloTextF == null || tituloTextF.getText().isEmpty()) {
			erros.append("- Título ");
		}
		if (generoTextF == null || generoTextF.getText().isEmpty()) {
			erros.append("- Gênero ");
		}
		if (thumbTextF == null || thumbTextF.getText().isEmpty()) {
			erros.append("- Thumb ");
		}
		if (pathTextF == null || pathTextF.getText().isEmpty()) {
			erros.append("- Path ");
		}
		
		if (erros.length() > 0) {
			erros.delete(0, 1);
			JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos: " + erros.toString());
			validado = false;
		}
		
		return validado;
	}
}
