package application;

import java.sql.SQLException;
import application.ListMember.Member;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;  

public class addMember extends Application {
	Database database;
	TextField tf = new TextField();
	TextField tf2 = new TextField();
	TextField tf3 = new TextField();
	TextField tf4 = new TextField();
	
	private String name;
	private String memberID;
	private String mobile;
	private String email;
	Boolean isInEditMode = Boolean.FALSE;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			VBox panel = new VBox(5);
			panel.setPadding(new Insets(5,5,5,5));
			tf.setPromptText("Name");
			tf2.setPromptText("Member ID");
			tf3.setPromptText("Mobile");
			tf4.setPromptText("Email");
			panel.getChildren().addAll(tf, tf2, tf3, tf4);
			root.setTop(panel);
			
			HBox panel2 = new HBox();
			panel2.setPadding(new Insets(10,10,20,10));
			Button saveB = new Button("Save");
			saveB.setPrefWidth(175);
			Button cancelB = new Button("Cancel");
			cancelB.setPrefWidth(175);
			panel2.getChildren().addAll(saveB, cancelB);
			root.setBottom(panel2);
			
			database = Database.getInstance();
			
			EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					if(e.getSource() == saveB) {
						
						name = tf.getText();
						memberID = tf2.getText();
						mobile = tf3.getText();
						email = tf4.getText();
						
						if(name.isEmpty() || memberID.isEmpty() || mobile.isEmpty() || email.isEmpty()) {
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setHeaderText(null);
							alert.setContentText("Please Enter in all fields!");
							alert.showAndWait();
							return;
						}
						String st = "INSERT INTO MEMBER VALUES (" +
									"'" + memberID + "'," +
									"'" + name + "'," +
									"'" + mobile + "'," +
									"'" + email + "'"
									+ ")";
						System.out.println(st);
						if(database.execAction(st)) {
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setHeaderText(null);
							alert.setContentText("SAVED");
							alert.showAndWait();
						} else {
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setHeaderText(null);
							alert.setContentText("ERROR OCCURED");
							alert.showAndWait();
						}
						tf.clear();
						tf2.clear();
						tf3.clear();
						tf4.clear();
					}
					
					if(e.getSource() == cancelB) {
						Stage stage1 = (Stage) root.getScene().getWindow();
						stage1.close();
					}
				}
				
			};
			saveB.setOnMouseClicked(handler);
			cancelB.setOnMouseClicked(handler);
			
			Scene scene = new Scene(root, 350, 170);
			primaryStage.getIcons().add(new Image("133-1331478_add-new-member-icon-clipart.png"));
			primaryStage.setTitle("Member Registration");
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void inflateUI(Member member, Stage stage) throws SQLException {
		tf.setText(member.getName());
		tf2.setText(member.getId());
		tf3.setText(member.getMobile());
		tf4.setText(member.getEmail());
		tf2.setEditable(false);
		
		BorderPane root = new BorderPane();
		
		VBox panel = new VBox(5);
		panel.setPadding(new Insets(5,5,5,5));
		tf.setPromptText("name");
		tf2.setPromptText("MemberID");
		tf3.setPromptText("Contact");
		tf4.setPromptText("email");
		panel.getChildren().addAll(tf, tf2, tf3, tf4);
		root.setTop(panel);
		
		HBox panel2 = new HBox();
		panel2.setPadding(new Insets(10,10,20,10));
		Button saveB = new Button("Save");
		saveB.setPrefWidth(175);
		Button cancelB = new Button("Cancel");
		cancelB.setPrefWidth(175);
		panel2.getChildren().addAll(saveB, cancelB);
		root.setBottom(panel2);
		
		database = Database.getInstance();
		
		EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				if(e.getSource() == saveB) {
					isInEditMode = Boolean.TRUE;
					if(isInEditMode) {
						handleEditOperation();
						return;
					}
				}
				
				if(e.getSource() == cancelB) {
					Stage stage1 = (Stage) root.getScene().getWindow();
					stage1.close();
				}
			}
			
		};
		saveB.setOnMouseClicked(handler);
		cancelB.setOnMouseClicked(handler);
		
		Scene scene = new Scene(root, 350, 170);
		stage.setTitle("Edit Member Information");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	protected void handleEditOperation() {
		Member member = new Member(tf.getText(), tf2.getText(), tf3.getText(), tf4.getText());
		if(database.updateMember(member)) {
			Alert alert = new Alert(Alert.AlertType.NONE);
			alert.setTitle("SUCCESS");
			alert.setHeaderText(null);
			alert.setContentText("Member Updated!");
			alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
			alert.showAndWait();
			return;
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle(" Member Edit Failed");
			alert.setHeaderText(null);
			alert.setContentText("Can't Update Member!");
			alert.showAndWait();
		}
	}
}
