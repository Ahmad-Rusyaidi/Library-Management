package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.application.Application;
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

public class ListMember extends Application{

	TableView<Member> table = new TableView<>();
	TableColumn<Member, String> nameCol = new TableColumn<>("Username");
	TableColumn<Member, String> idCol = new TableColumn<>("User ID");
	TableColumn<Member, String> contCol = new TableColumn<>("Contact");
	TableColumn<Member, String> eCol = new TableColumn<>("Email");
	
	ObservableList<Member> list = FXCollections.observableArrayList();

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
		
		table.getColumns().add(nameCol);
		table.getColumns().add(idCol);
		table.getColumns().add(contCol);
		table.getColumns().add(eCol);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent eh) {
				if(eh.getButton() == MouseButton.SECONDARY) {
					deleteList.show(table, eh.getSceneX(), eh.getScreenY());
					del.setOnAction((ActionEvent e) -> {
						Member selectedForDeletion = table.getSelectionModel().getSelectedItem();
						if(selectedForDeletion == null) {
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("No Member Selected");
							alert.setHeaderText(null);
							alert.setContentText("Please select a member for deletion");
							alert.showAndWait();
							return;
						}
						Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
						alert2.setTitle("Deleting Member....");
						alert2.setContentText("Are you sure want to delete the member " + selectedForDeletion.getName() + "?");
						Optional<ButtonType> answer = alert2.showAndWait();
						if(answer.get() == ButtonType.OK) {
							//Do deletion process
							try {
								Boolean result = Database.getInstance().deleteMember(selectedForDeletion);
								if(result) {
									Alert alert = new Alert(Alert.AlertType.NONE);
									alert.setTitle("Member Deleted");
									alert.setHeaderText(null);
									alert.setContentText(selectedForDeletion.getName() + " was deleted successfully!");
									alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
									alert.showAndWait();
									list.remove(selectedForDeletion);
								} else {
									Alert alert = new Alert(Alert.AlertType.NONE);
									alert.setTitle("Member Deletion Failed");
									alert.setHeaderText(null);
									alert.setContentText(selectedForDeletion.getName() + " could not be deleted!");
									alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
									alert.showAndWait();
								}
							} catch (SQLException ex) {
								ex.printStackTrace();
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
					});
					edit.setOnAction((ActionEvent e) -> {
						Member selectedForEdit = table.getSelectionModel().getSelectedItem();
						if(selectedForEdit == null) {
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("No Member Selected");
							alert.setHeaderText(null);
							alert.setContentText("Please select a member to edit");
							alert.showAndWait();
							return;
						}
						addMember addMember = new addMember();
						try {
							Stage stage = new Stage();
							addMember.inflateUI(selectedForEdit, stage);
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
					refresh.setOnAction((ActionEvent e) -> {
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
		
		Scene scene = new Scene(root, 800, 300);
		primaryStage.getIcons().add(new Image("2851439.png"));
		primaryStage.setTitle("Member List");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void loadData() throws SQLException {
		list.clear();
		Database handler = new Database();
		String qu = "SELECT * FROM MEMBER";
		ResultSet rs = handler.execQuery(qu);
		while(rs.next()) {
			String nameM = rs.getString("name");
			String idM = rs.getString("id");
			String mobileM = rs.getString("mobile");
			String emailM = rs.getString("email");
			
			list.add(new Member(nameM, idM, mobileM, emailM));
		}
		
		table.setItems(list);
	}

	private void initCol() {
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		contCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
		eCol.setCellValueFactory(new PropertyValueFactory<>("email"));
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static class Member{
		private final SimpleStringProperty name;
		private final SimpleStringProperty id;
		private final SimpleStringProperty mobile;
		private final SimpleStringProperty email;
		
		public String getName() {
			return name.get();
		}

		public String getId() {
			return id.get();
		}

		public String getMobile() {
			return mobile.get();
		}

		public String getEmail() {
			return email.get();
		}
		
		Member(String nameM, String idM, String cont, String emaiL){
			this.name = new SimpleStringProperty(nameM);
			this.id = new SimpleStringProperty(idM);
			this.mobile = new SimpleStringProperty(cont);
			this.email = new SimpleStringProperty(emaiL);
		}
	}
	
}
