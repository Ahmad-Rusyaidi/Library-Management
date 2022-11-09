package application;

import org.apache.commons.codec.digest.DigestUtils;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Login extends Application{
	
	Preferences preferences;

	@Override
	public void start(Stage e) throws Exception {
		AnchorPane root = new AnchorPane();
		Image image = new Image("output-onlinepngtools (6).png");
		ImageView iv = new ImageView();
		iv.setImage(image);
		Image image2 = new Image("output-onlinepngtools (7).png");
		ImageView iv2 = new ImageView();
		iv2.setImage(image2);
		Image image3 = new Image("output-onlinepngtools (9).png");
		ImageView iv3 = new ImageView();
		iv3.setImage(image3);
		JFXTextField tf = new JFXTextField();
		tf.setLabelFloat(true);
		tf.setPromptText("Username");
		tf.setMinWidth(200);
		tf.setStyle("-fx-text-inner-color: #FFFF8D;"
				+ "-fx-prompt-text-fill: #FFFF8D;"
				+ "-fx-background-color: #FFFF8D, #2A2E37, #2A2E37;"
				+ "-fx-background-insets: 0 0 -1 0, -1 -1 0 -1, -1 -1 0 -1");
		JFXPasswordField pf = new JFXPasswordField();
		pf.setLabelFloat(true);
		pf.setPromptText("Password");
		pf.setMinWidth(200);
		pf.setStyle("-fx-text-inner-color: #FFFF8D;"
				+ "-fx-prompt-text-fill: #FFFF8D;"
				+ "-fx-background-color: #FFFF8D, #2A2E37, #2A2E37;"
				+ "-fx-background-insets: 0 0 -1 0, -1 -1 0 -1, -1 -1 0 -1");
		pf.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {
				mainWeb main = new mainWeb();
				main.start(new Stage());
				e.close();
			}
		});
		//login button
		Button bt = new Button("Login");
		bt.setMinWidth(100);
		bt.setStyle("-fx-background-color: \r\n"
				+ "        linear-gradient(#ffef84, #f2ba44),\r\n"
				+ "        linear-gradient(#ffea6a, #efaa22),\r\n"
				+ "        linear-gradient(#2a2e37 0%, #2a2e37 100%, #eea10b 50%),\r\n"
				+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
				+ "    -fx-background-radius: 30;\r\n"
				+ "    -fx-text-fill: #FFFF8D;\r\n"
				+ "    -fx-font-weight: bold;\r\n");
		bt.setOnMouseEntered(mouseEvent -> {
			bt.setStyle("-fx-background-color: \r\n"
					+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
					+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
					+ "        linear-gradient(#FFFF8D 0%, #FFFF8D 100%, #FFFF8D 50%),\r\n"
					+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
					+ "    -fx-background-radius: 30;\r\n"
					+ "    -fx-text-fill: #0f0f0f;\r\n"
					+ "    -fx-font-weight: bold;\r\n");
		});
		bt.setOnMouseExited(mouseEvent -> {
			bt.setStyle("-fx-background-color: \r\n"
					+ "        linear-gradient(#ffef84, #f2ba44),\r\n"
					+ "        linear-gradient(#ffea6a, #efaa22),\r\n"
					+ "        linear-gradient(#2a2e37 0%, #2a2e37 100%, #eea10b 50%),\r\n"
					+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
					+ "    -fx-background-radius: 30;\r\n"
					+ "    -fx-text-fill: #FFFF8D;\r\n"
					+ "    -fx-font-weight: bold;\r\n");
		});
		//Cancel button
		Button bt2 = new Button("Cancel");
		bt2.setMinWidth(100);
		bt2.setStyle("-fx-background-color: \r\n"
				+ "        linear-gradient(#ffef84, #f2ba44),\r\n"
				+ "        linear-gradient(#ffea6a, #efaa22),\r\n"
				+ "        linear-gradient(#2a2e37 0%, #2a2e37 100%, #eea10b 50%),\r\n"
				+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
				+ "    -fx-background-radius: 30;\r\n"
				+ "    -fx-text-fill: #FFFF8D;\r\n"
				+ "    -fx-font-weight: bold;\r\n");
		bt2.setOnMouseEntered(mouseEvent -> {
			bt2.setStyle("-fx-background-color: \r\n"
					+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
					+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
					+ "        linear-gradient(#FFFF8D 0%, #FFFF8D 100%, #FFFF8D 50%),\r\n"
					+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
					+ "    -fx-background-radius: 30;\r\n"
					+ "    -fx-text-fill: #0f0f0f;\r\n"
					+ "    -fx-font-weight: bold;\r\n");
		});
		bt2.setOnMouseExited(mouseEvent -> {
			bt2.setStyle("-fx-background-color: \r\n"
					+ "        linear-gradient(#ffef84, #f2ba44),\r\n"
					+ "        linear-gradient(#ffea6a, #efaa22),\r\n"
					+ "        linear-gradient(#2a2e37 0%, #2a2e37 100%, #eea10b 50%),\r\n"
					+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
					+ "    -fx-background-radius: 30;\r\n"
					+ "    -fx-text-fill: #FFFF8D;\r\n"
					+ "    -fx-font-weight: bold;\r\n");
		});		
		
		//event-handling
		EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent ex) {
				
				if(ex.getSource() == bt) {
					String userName = tf.getText();
					String passWord = DigestUtils.sha1Hex(pf.getText());
					
					if(userName.equals(preferences.getUsername()) && passWord.equals(preferences.getPassword())) {
						mainWeb main = new mainWeb();
						main.start(new Stage());
						e.close();
					} else {
						tf.setStyle("-fx-text-inner-color: #FC0505;"
								+ "-fx-prompt-text-fill: #FC0505;"
								+ "-fx-background-color: #FC0505, #2A2E37, #2A2E37;"
								+ "-fx-background-insets: 0 0 -1 0, -1 -1 0 -1, -1 -1 0 -1");
						pf.setStyle("-fx-text-inner-color: #FC0505;"
								+ "-fx-prompt-text-fill: #FC0505;"
								+ "-fx-background-color: #FC0505, #2A2E37, #2A2E37;"
								+ "-fx-background-insets: 0 0 -1 0, -1 -1 0 -1, -1 -1 0 -1");
						tf.clear();
						pf.clear();
					}
				}
				
				if(ex.getSource() == bt2) {
					System.exit(0);
				}
				ex.consume();
			}
			
		};
		bt.setOnMouseClicked(handler);
		bt2.setOnMouseClicked(handler);
		
		preferences = Preferences.getPreferences();
		
		root.getChildren().addAll(iv,iv2,iv3,tf,pf,bt,bt2);
		
		AnchorPane.setBottomAnchor(tf, 105.0);
		AnchorPane.setLeftAnchor(tf, 51.0);
		AnchorPane.setBottomAnchor(iv2, 101.5);
		AnchorPane.setLeftAnchor(iv2, 17.0);
		AnchorPane.setBottomAnchor(pf, 65.0);
		AnchorPane.setLeftAnchor(pf, 51.0);
		AnchorPane.setBottomAnchor(iv3, 64.0);
		AnchorPane.setLeftAnchor(iv3, 18.0);
		AnchorPane.setBottomAnchor(bt, 20.5);
		AnchorPane.setLeftAnchor(bt, 31.0);
		AnchorPane.setBottomAnchor(bt2, 20.5);
		AnchorPane.setLeftAnchor(bt2, 170.0);
		AnchorPane.setTopAnchor(iv, 10.0);
		AnchorPane.setLeftAnchor(iv, 135.0);
		
		Scene scene = new Scene(root, 300, 190);
		//e.setTitle("Library Assistant Login");
		e.initStyle(StageStyle.UNDECORATED);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		e.setScene(scene);
		e.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
