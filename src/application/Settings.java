package application;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Settings extends Application{

	TextField tf;
	TextField tf2;
	TextField tf3;
	PasswordField tf4;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane root = new AnchorPane();
		//root --> container
		VBox container = new VBox(10);
		container.setPadding(new Insets(10,10,10,10));
		tf = new TextField();
		tf.setMinWidth(300);
		tf.setPromptText("No. of days member can take book without fine");
		tf2 = new TextField();
		tf2.setMinWidth(300);
		tf2.setPromptText("Fine per day");
		tf3 = new TextField();
		tf3.setMinWidth(300);
		tf3.setPromptText("username");
		tf4 = new PasswordField();
		tf4.setMinWidth(300);
		tf4.setPromptText("password");
		container.getChildren().addAll(tf,tf2,tf3,tf4);
		
		//root --> container2
		HBox container2 = new HBox(40);
		container2.setMinWidth(320);
		container2.setMinHeight(50);
		container2.setAlignment(Pos.CENTER);
		Button bt = new Button("Save");
		bt.setMinWidth(100);
		bt.setStyle("-fx-background-color: \r\n"
				+ "        #090a0c,\r\n"
				+ "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\r\n"
				+ "        linear-gradient(#20262b, #191d22),\r\n"
				+ "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(17,101,196,1));\r\n"
				+ "    -fx-background-radius: 5,4,3,5;\r\n"
				+ "    -fx-background-insets: 0,1,2,0;\r\n"
				+ "    -fx-text-fill: white;\r\n"
				+ "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\r\n"
				+ "    -fx-font-family: \"Arial\";\r\n"
				+ "    -fx-text-fill: linear-gradient(white, #d0d0d0);");
		bt.setOnMouseEntered(mouseEvent -> {
			bt.setStyle("-fx-background-color: \r\n"
					+ "        #090a0c,\r\n"
					+ "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\r\n"
					+ "        linear-gradient(#20262b, #191d22),\r\n"
					+ "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(17,101,98,1));\r\n"
					+ "    -fx-background-radius: 5,4,3,5;\r\n"
					+ "    -fx-background-insets: 0,1,2,0;\r\n"
					+ "    -fx-text-fill: white;\r\n"
					+ "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\r\n"
					+ "    -fx-font-family: \"Arial\";\r\n"
					+ "    -fx-text-fill: linear-gradient(white, #d0d0d0);");
		});
		bt.setOnMouseExited(mouseEvent -> {
			bt.setStyle("-fx-background-color: \r\n"
					+ "        #090a0c,\r\n"
					+ "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\r\n"
					+ "        linear-gradient(#20262b, #191d22),\r\n"
					+ "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(17,101,196,1));\r\n"
					+ "    -fx-background-radius: 5,4,3,5;\r\n"
					+ "    -fx-background-insets: 0,1,2,0;\r\n"
					+ "    -fx-text-fill: white;\r\n"
					+ "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\r\n"
					+ "    -fx-font-family: \"Arial\";\r\n"
					+ "    -fx-text-fill: linear-gradient(white, #d0d0d0);");
		});
		
		Button bt2 = new Button("Cancel");
		bt2.setStyle("-fx-background-color: \r\n"
				+ "        #090a0c,\r\n"
				+ "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\r\n"
				+ "        linear-gradient(#20262b, #191d22),\r\n"
				+ "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(17,101,196,1));\r\n"
				+ "    -fx-background-radius: 5,4,3,5;\r\n"
				+ "    -fx-background-insets: 0,1,2,0;\r\n"
				+ "    -fx-text-fill: white;\r\n"
				+ "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\r\n"
				+ "    -fx-font-family: \"Arial\";\r\n"
				+ "    -fx-text-fill: linear-gradient(white, #d0d0d0);");
		bt2.setOnMouseEntered(mouseEvent -> {
			bt2.setStyle("-fx-background-color: \r\n"
					+ "        #090a0c,\r\n"
					+ "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\r\n"
					+ "        linear-gradient(#20262b, #191d22),\r\n"
					+ "        radial-gradient(center 50% 0%, radius 100%, rgba(121,131,148,0.9), rgba(214,19,19,1));\r\n"
					+ "    -fx-background-radius: 5,4,3,5;\r\n"
					+ "    -fx-background-insets: 0,1,2,0;\r\n"
					+ "    -fx-text-fill: white;\r\n"
					+ "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\r\n"
					+ "    -fx-font-family: \"Arial\";\r\n"
					+ "    -fx-text-fill: linear-gradient(white, #d0d0d0);");
		});
		bt2.setOnMouseExited(mouseEvent -> {
			bt2.setStyle("-fx-background-color: \r\n"
					+ "        #090a0c,\r\n"
					+ "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\r\n"
					+ "        linear-gradient(#20262b, #191d22),\r\n"
					+ "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(17,101,196,1));\r\n"
					+ "    -fx-background-radius: 5,4,3,5;\r\n"
					+ "    -fx-background-insets: 0,1,2,0;\r\n"
					+ "    -fx-text-fill: white;\r\n"
					+ "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\r\n"
					+ "    -fx-font-family: \"Arial\";\r\n"
					+ "    -fx-text-fill: linear-gradient(white, #d0d0d0);");
		});
		bt2.setMinWidth(100);
		container2.getChildren().addAll(bt,bt2);
		
		root.getChildren().add(container);
		root.getChildren().add(container2);
		AnchorPane.setBottomAnchor(container2, 0.0);
		
		initDefaultValues();
		
		EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				int nDays = Integer.parseInt(tf.getText());
				double finePerDay = Double.parseDouble(tf2.getText());
				String name = tf3.getText();
				String passsword = tf4.getText();
				
				if(e.getSource() == bt) {
					Preferences preferences = Preferences.getPreferences();
					preferences.setnDaysWithoutFine(nDays);
					preferences.setFinePerDay(finePerDay);;
					preferences.setUsername(name);
					preferences.setPassword(passsword);
					
					Preferences.writePreferences2File(preferences);
				}
				if(e.getSource() == bt2) {
					((Stage)tf.getScene().getWindow()).close();
				}
				e.consume();
			}
		};
		
		bt.setOnMouseClicked(handler);
		bt2.setOnMouseClicked(handler);
		
		new Thread(() -> {
			try {
				Database.getInstance();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}).start();
		
		Scene scene = new Scene(root, 320, 200);
		primaryStage.getIcons().add(new Image("free-settings-icon-778-thumb.png"));
		primaryStage.setTitle("Settings");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void initDefaultValues() {
		Preferences preferences = Preferences.getPreferences();
		tf.setText(String.valueOf(preferences.getnDaysWithoutFine()));
		tf2.setText(String.valueOf(preferences.getFinePerDay()));
		tf3.setText(String.valueOf(preferences.getUsername()));
		tf4.setText(String.valueOf(preferences.getPassword()));
	}
}
