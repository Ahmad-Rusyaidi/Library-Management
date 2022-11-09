package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ListBook extends Application{
	
	TableView<Book> table = new TableView<>();
	TableColumn<Book, String> titleCol = new TableColumn<>("Title");
	TableColumn<Book, String> idCol = new TableColumn<>("Book ID");
	TableColumn<Book, String> authorCol = new TableColumn<>("Author");
	TableColumn<Book, String> pubCol = new TableColumn<>("Publisher");
	TableColumn<Book, Boolean> avaCol = new TableColumn<>("Availability");
	
	ObservableList<Book> list = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane root = new AnchorPane();
		ContextMenu deleteList = new ContextMenu();
		MenuItem del = new MenuItem("Delete");
		MenuItem edit = new MenuItem("Edit");
		MenuItem refresh = new MenuItem("Refresh");
		deleteList.getItems().add(del);
		deleteList.getItems().add(edit);
		deleteList.getItems().add(refresh);
		
		table.getColumns().add(titleCol);
		table.getColumns().add(idCol);
		table.getColumns().add(authorCol);
		table.getColumns().add(pubCol);
		table.getColumns().add(avaCol);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent eh) {
				if(eh.getButton() == MouseButton.SECONDARY) {
					deleteList.show(table, eh.getSceneX(), eh.getScreenY());
					del.setOnAction((ActionEvent e) -> {
						Book selectedForDeletion = table.getSelectionModel().getSelectedItem();
						if(selectedForDeletion == null) {
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("No Book Selected");
							alert.setHeaderText(null);
							alert.setContentText("Please select a book for deletion");
							alert.showAndWait();
							return;
						}
						try {
							Boolean result2 = Database.getInstance().isBookAlreadyIssued(selectedForDeletion);
							if(result2){
								Alert alert = new Alert(Alert.AlertType.NONE);
								alert.setTitle("Book Deletion Failed");
								alert.setHeaderText(null);
								alert.setContentText(selectedForDeletion.getTitle() + " is already issued!");
								alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
								alert.showAndWait();
							} else {
								Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
								alert2.setTitle("Deleting Book....");
								alert2.setContentText("Are you sure want to delete the book " + selectedForDeletion.getTitle() + "?");
								Optional<ButtonType> answer = alert2.showAndWait();
								if(answer.get() == ButtonType.OK) {
									//Do deletion process
									try {
										Boolean result = Database.getInstance().deleteBook(selectedForDeletion);
										if(result) {
											Alert alert = new Alert(Alert.AlertType.NONE);
											alert.setTitle("Book Deleted");
											alert.setHeaderText(null);
											alert.setContentText(selectedForDeletion.getTitle() + " was deleted successfully!");
											alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
											alert.showAndWait();
											list.remove(selectedForDeletion);
										} else {
											Alert alert = new Alert(Alert.AlertType.NONE);
											alert.setTitle("Book Deletion Failed");
											alert.setHeaderText(null);
											alert.setContentText(selectedForDeletion.getTitle() + " could not be deleted!");
											alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
											alert.showAndWait();
										}
									} catch (SQLException er) {
										er.printStackTrace();
									}
								} else {
									Alert alert = new Alert(Alert.AlertType.NONE);
									alert.setTitle("Deletion Cancel");
									alert.setHeaderText(null);
									alert.setContentText("Deletion Process Cancelled");
									alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
									alert.showAndWait();
									return;
								}
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					});
					edit.setOnAction((ActionEvent e) -> {
						Book selectedForEdit = table.getSelectionModel().getSelectedItem();
						if(selectedForEdit == null) {
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("No Book Selected");
							alert.setHeaderText(null);
							alert.setContentText("Please select a book to edit");
							alert.showAndWait();
							return;
						}
						addBook addBook = new addBook();
						try {
							Stage stage = new Stage();
							addBook.inflateUI(selectedForEdit, stage);
							stage.setOnCloseRequest((ee) ->{
								try {
									loadData();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							});
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
					});
					refresh.setOnAction((ActionEvent e) ->{
						try {
							loadData();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					});
				}
			}
		});
		
		AnchorPane.setBottomAnchor(table, 0.0);
		AnchorPane.setLeftAnchor(table, 0.0);
		AnchorPane.setRightAnchor(table, 0.0);
		AnchorPane.setTopAnchor(table, 0.0);
		root.getChildren().add(table);
		
		initCol();

		loadData();
		
		Scene scene = new Scene(root, 1000, 300);
		primaryStage.getIcons().add(new Image("bookshelf+library+icon-1320087270870761354.png"));
		primaryStage.setTitle("Book List");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void loadData() throws SQLException {
		list.clear();
		Database handler = new Database();
		String qu = "SELECT * FROM BOOKS";
		ResultSet rs = handler.execQuery(qu);
		while(rs.next()) {
			String titleX = rs.getString("title");
			String idX = rs.getString("id");
			String authorX = rs.getString("author");
			String publisherX = rs.getString("publisher");
			Boolean availX = rs.getBoolean("isAvail");
			
			list.add(new Book(titleX, idX, authorX, publisherX, availX));
		}
		
		table.setItems(list);
	}

	private void initCol() {
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
		pubCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
		avaCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static class Book{
		private final SimpleStringProperty title;
		private final SimpleStringProperty id;
		private final SimpleStringProperty author;
		private final SimpleStringProperty publisher;
		private final SimpleBooleanProperty availability;
		
		public String getTitle() {
			return title.get();
		}

		public String getId() {
			return id.get();
		}

		public String getAuthor() {
			return author.get();
		}

		public String getPublisher() {
			return publisher.get();
		}

		public Boolean getAvailability() {
			return availability.get();
		}
		
		Book(String titleB, String idB, String aut, String pub, Boolean avail){
			this.title = new SimpleStringProperty(titleB);
			this.id = new SimpleStringProperty(idB);
			this.author = new SimpleStringProperty(aut);
			this.publisher = new SimpleStringProperty(pub);
			this.availability = new SimpleBooleanProperty(avail);
		}
	}

}
