package application;

import javax.swing.JOptionPane;

import application.template.NerdFlixApplication;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import persistence.dao.GenericDAO;
import persistence.dominio.Banco;
import persistence.dominio.Filme;
import persistence.dominio.Serie;
import persistence.dominio.Usuario;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class Login extends NerdFlixApplication {

	private static Stage stage;
	
	private AnchorPane anchorPane;
		
	private TextField loginInputBox;
	private PasswordField passwordInputBox;
	private Button entrarButton;
	private Button sairButton;
	
	private double WIDTH;
	
	private String FAREWELL_MESSAGE;
	private String FAILED_LOGIN_MESSAGE;
	
	private int SUCCESSFUL_EXIT;
	private int FAILED_EXIT;

	public double middleCordinates(double componentWidth, double anchorPaneWidth) {
		return ((anchorPaneWidth - componentWidth) / 2);
	}

	private void initComponents() {
		SUCCESSFUL_EXIT = 0;
		FAILED_EXIT = 1;
		WIDTH = 400;
		FAREWELL_MESSAGE = "Bye!";
		FAILED_LOGIN_MESSAGE = "Login ou Senha inválidos!";
		
		persistence.dominio.Banco.getInstance();
		
//		------------------------------------Inserts para Criação do banco--------------------------
//		Usuario u = new Usuario();
//		Filme f = new Filme();
//		Serie s = new Serie();
//		GenericDAO dao = new GenericDAO();
//		u.setNome("");
//		u.setEmail("outro@mail.com");
//		u.setLogin("");
//		u.setSenha("123");
		
//		f.setTiulo("Os intocáveis");
//		f.setDuracao(120);
//		f.setGenero("Ação");
//		f.setResolucao("HD");
//		f.setSinopse("Descrição de Os intocáveis.");
//		
//		s.setTiulo("Game of Thrones");
//		s.setDuracao(50);
//		s.setGenero("Épico");
//		s.setResolucao("Full HD");
//		s.setSinopse("Há muito tempo, em um tempo esquecido, uma força destruiu o equilíbrio das estações." +
//		"Em uma terra onde os verões podem durar vários anos e o inverno toda uma vida, as reivindicações " + 
//		"e as forças sobrenaturais correm as portas do Reino dos Sete Reinos.");
//		s.setEpisodio(1);
//		s.setTemporada(1);
//		
//		dao.inserir(f);
//		dao.inserir(s);
		
		anchorPane = new AnchorPane();
		anchorPane.setPrefSize(WIDTH, 300);
		
		loginInputBox = new TextField();
		passwordInputBox = new PasswordField();
		entrarButton = new Button("Entrar");
		sairButton = new Button("Sair");
		
		anchorPane.getChildren().addAll(loginInputBox, passwordInputBox, entrarButton, sairButton);
	}
	
	private void initLayout(){
		anchorPane.getStyleClass().add("pane");
		this.entrarButton.getStyleClass().add("btEntrar");
		this.sairButton.getStyleClass().add("btSair");
		
		loginInputBox.setPromptText("Digite aqui seu login");
		loginInputBox.setLayoutX(middleCordinates(loginInputBox.getWidth(), WIDTH));
		loginInputBox.setLayoutY(50);

		passwordInputBox.setPromptText("Digite aqui sua senha");
		passwordInputBox.setLayoutX(middleCordinates(passwordInputBox.getWidth(), WIDTH));
		passwordInputBox.setLayoutY(100);

		entrarButton.setLayoutX(middleCordinates(entrarButton.getWidth(), WIDTH));
		entrarButton.setLayoutY(150);

		sairButton.setLayoutX(middleCordinates(sairButton.getWidth(), WIDTH));
		sairButton.setLayoutY(200);
	}
	
	private void initListeners(){
		this.entrarButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				logar();		
			}
		});
		
		this.sairButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				fecharAplicacao();
			}
		});
		
		this.passwordInputBox.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                logar();
	            }
	        }
	    });
		
		this.loginInputBox.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                logar();
	            }
	        }
	    });
		
//		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent t) {
//                t.consume();
//                persistence.dominio.Banco.closeInstance();
//                stage.close();
//                System.exit(0);
//            }
//        });
	}
	
	private void logar(){
		EntityManager em = Banco.getInstance();
		Usuario user = null;
		String login = loginInputBox.getText();
		String password = passwordInputBox.getText();
		
		Query qLogin = em.createQuery("Select u from Usuario as u " + "where u.login = :param");
		qLogin.setParameter("param", login);
		try {
			user = (Usuario) qLogin.getSingleResult();	
		} catch (NoResultException e) {
			user = null;
		}
		
		if (user != null && user.getLogin().equalsIgnoreCase(login) && user.getSenha().equals(password)) {
			try {
				new SelectPageView().start(new Stage());
				Login.stage.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(FAILED_EXIT);
			}
		} else {
			JOptionPane.showMessageDialog(null, FAILED_LOGIN_MESSAGE);
		}
	}

	private void fecharAplicacao(){
		JOptionPane.showMessageDialog(null, FAREWELL_MESSAGE);
		persistence.dominio.Banco.closeInstance();
		System.exit(SUCCESSFUL_EXIT);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			initComponents();
			
			Scene scene = new Scene(anchorPane);
//			scene.getStylesheets().add("resources/css/Login.css");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Login - NerdFlix");
			primaryStage.show();
						
			initLayout();

			Login.stage = primaryStage;
			initListeners();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
