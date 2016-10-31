package application.view;

import javax.swing.JOptionPane;

import application.Login;
import application.Main;
import application.template.NerdFlixApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import persistence.dao.GenericDAO;
import persistence.dominio.Usuario;

public class CadastroUsuarioController extends NerdFlixApplication {
	private static Stage stage;
	private BorderPane root = new BorderPane();
	
	@FXML
	private TextField nomeTextF;
	@FXML
	private TextField emailTextF;
	@FXML
	private TextField loginTextF;
	@FXML
	private TextField senhaTextF;
	@FXML
	private TextField idadeTextF;
	
	@Override
	public void startEspecifico(Stage primaryStage) {
		try {
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../css/style.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Cadastro de usuários");
			CadastroUsuarioController.stage = primaryStage;
			initView();
			
			CadastroUsuarioController.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initView() {
    	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CadastroUsuario.fxml"));
            AnchorPane pageView = (AnchorPane) loader.load();
            root.setCenter(pageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	private void handleCancelarButton(){
		try {
			new Login().start(new Stage());
			CadastroUsuarioController.stage.close();
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
			Usuario user = new Usuario();
			
			user.setNome(nomeTextF.getText());
			user.setEmail(emailTextF.getText());
			user.setLogin(loginTextF.getText());
			user.setSenha(senhaTextF.getText());
			user.setIdade(Integer.parseInt(idadeTextF.getText()));
			dao.inserir(user);
			JOptionPane.showMessageDialog(null, "Usuario "+user.getNome()+" cadastrado com sucesso!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao tentar cadastrar um usuário!");
			e.printStackTrace();
		}
		finally {
			try {
				new Login().start(new Stage());
				CadastroUsuarioController.stage.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean validate(){
		boolean validado = true;
		StringBuilder erros = new StringBuilder();
		if (nomeTextF == null || nomeTextF.getText().isEmpty()) {
			erros.append("- Nome ");
		}
		if (emailTextF == null || emailTextF.getText().isEmpty()) {
			erros.append("- Email ");
		}
		if (loginTextF == null || loginTextF.getText().isEmpty()) {
			erros.append("- Login ");
		}
		if (senhaTextF == null || senhaTextF.getText().isEmpty()) {
			erros.append("- Senha ");
		}
		if (idadeTextF == null || idadeTextF.getText().isEmpty()) {
			erros.append("- Idade ");
		}
		
		if (erros.length() > 0) {
			erros.delete(0, 1);
			JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos: " + erros.toString());
			validado = false;
		}
		
		return validado;
	}
}
