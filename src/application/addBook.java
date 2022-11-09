package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import application.ListBook.Book;
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

public class addBook extends Application {
	Database database;
	private String bookTitle;
	private String bookID;
	private String bookAuthor;
	private String bookPublisher;
	Boolean isInEditMode = Boolean.FALSE;
	
	TextField tf = new TextField();
	TextField tf2 = new TextField();
	TextField tf3 = new TextField();
	TextField tf4 = new TextField();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			VBox panel = new VBox(5);
			panel.setPadding(new Insets(5,5,5,5));
			tf.setPromptText("Book Title");
			tf2.setPromptText("Book ID");
			tf3.setPromptText("Book Author");
			tf4.setPromptText("Book Publisher");
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
			checkData();
			
			EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					if(e.getSource() == saveB) {
						
						bookTitle = tf.getText();
						bookID = tf2.getText();
						bookAuthor = tf3.getText();
						bookPublisher = tf4.getText();
						
						if(bookTitle.isEmpty() || bookID.isEmpty() || bookAuthor.isEmpty() || bookPublisher.isEmpty()) {
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setHeaderText(null);
							alert.setContentText("Please Enter in all fields!");
							alert.showAndWait();
							return;
						}
						
						String qu = "INSERT INTO BOOKS VALUES (" +
									"'" + bookID + "'," +
									"'" + bookTitle + "'," +
									"'" + bookAuthor + "'," +
									"'" + bookPublisher + "'," +
									"" + "true" + ""
									+ ")";
						System.out.println(qu);
						if(database.execAction(qu)) {
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setHeaderText(null);
							alert.setContentText("SUCCESS");
							alert.showAndWait();
						} else {
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setHeaderText(null);
							alert.setContentText("FAILED");
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
			primaryStage.getIcons().add(new Image("225932.png"));
			primaryStage.setTitle("Book Registration");
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void handleEditOperation() {
		Book book = new Book(tf.getText(), tf2.getText(), tf3.getText(), tf4.getText(), true);
		if(database.updateBook(book)) {
			Alert alert = new Alert(Alert.AlertType.NONE);
			alert.setTitle("SUCCESS");
			alert.setHeaderText(null);
			alert.setContentText("Book Updated!");
			alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
			alert.showAndWait();
			return;
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle(" Book Edit Failed");
			alert.setHeaderText(null);
			alert.setContentText("Can't Update Book!");
			alert.showAndWait();
		}
	}

	private void checkData() throws SQLException {
		String qu = "SELECT title FROM BOOKS";
		ResultSet rs = database.execQuery(qu);
		while(rs.next()) {
			String titleX = rs.getString("title");
			System.out.println(titleX);
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void inflateUI(ListBook.Book book, Stage stage) throws SQLException {
		tf.setText(book.getTitle());
		tf2.setText(book.getId());
		tf3.setText(book.getAuthor());
		tf4.setText(book.getPublisher());
		tf2.setEditable(false);
		
		BorderPane root = new BorderPane();
		
		VBox panel = new VBox(5);
		panel.setPadding(new Insets(5,5,5,5));
		tf.setPromptText("Book Title");
		tf2.setPromptText("Book ID");
		tf3.setPromptText("Book Author");
		tf4.setPromptText("Book Publisher");
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
		stage.getIcons().add(new Image("225932.png"));
		stage.setTitle("Edit Book Information");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
}
