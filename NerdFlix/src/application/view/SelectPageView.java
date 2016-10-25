package application.view;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import application.Login;
import application.Main;
import application.template.NerdFlixApplication;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import persistence.dao.GenericDAO;
import persistence.dominio.Banco;
import persistence.dominio.Filme;
import persistence.dominio.Usuario;

public class SelectPageView extends NerdFlixApplication {
	private static Stage stage;
	private BorderPane root = new BorderPane();
	private ObservableList<Filme> filmeItens = FXCollections.observableArrayList();

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
		} catch(Exception e) {
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
	private void initialize(){
		Usuario u = new Usuario();
		u = Banco.getCurrentUser();
		
		if (u.getLogin().equalsIgnoreCase("Admin")) {
			this.uploadBT.setVisible(true);
		}
	}
	
	@FXML
	private void handleVoltarButton(){
		try {
			new Login().start(new Stage());
			SelectPageView.stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleUploadButton(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Videos File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Video Files", "*.avi", "*.mp4", "*.rmvb", "*.mkv"));
		 File selectedFile = fileChooser.showOpenDialog(stage);
		 if (selectedFile != null) {
			 JOptionPane.showMessageDialog(null, selectedFile.getName() + " - adicionado com sucesso!");
		 }
	}
	
	@FXML
	private void handlePesquisa(){
		if(!this.pesquisaTextField.getText().equals("")){
			JOptionPane.showMessageDialog(null, "Aplicar o filtro da pesquisa para: " + this.pesquisaTextField.getText());
//			table.setItems(findItems());
		}
	}
	
	private ObservableList<Filme> findItems(){
		ObservableList<Filme> foundItems = FXCollections.observableArrayList();
		
		for(Filme filme : this.filmeItens){
			if(filme.getTitulo().contains(this.pesquisaTextField.getText())){
				foundItems.add(filme);
			}
		}
		
		return foundItems;
	}
}
