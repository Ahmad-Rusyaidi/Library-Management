package application;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class mainWeb extends Application {
	Database handler;
	Boolean isReady4Submission = false;
	ObservableList<String> issueData = FXCollections.observableArrayList();
	HBox kotakTengah = new HBox(20);
	PieChart bookChart = new PieChart();
	PieChart memberChart = new PieChart();
	
	JFXTextField tf = new JFXTextField();
	JFXTextField tf2 = new JFXTextField();
	
	JFXButton bt = new JFXButton("Add Member");
	JFXButton bt2 = new JFXButton("Add Book");
	JFXButton bt3 = new JFXButton("View Member");
	JFXButton bt4 = new JFXButton("View Books");
	JFXButton bt5 = new JFXButton("Setting");
	Button renewB = new Button("Renew");
	Button submitB = new Button("Submit");
	
	Text textBook = new Text("Book Name");
	Text authorText = new Text("Author");
	Text statusText = new Text("Status");
	Text textMember = new Text("Member Name");
	Text mobileText = new Text("Contact");
	Text mnHolder = new Text("Member Name");
	Text meHolder = new Text("Member Email");
	Text mcHolder = new Text("Member Contact");
	Text bnHolder = new Text("Book Name");
	Text baHolder = new Text("Book Author");
	Text bpHolder = new Text("Book Publisher");
	Text idHolder = new Text("Issue Date");
	Text ndHolder = new Text("No of Days");
	Text fHolder = new Text("Fine accumulated");
	
	@Override
	public void start(Stage primaryStage) {
		try {
			StackPane root = new StackPane();
			handler = Database.getInstance();
			//top
			BorderPane pane = new BorderPane();
			MenuBar menuBar = new MenuBar();
			menuBar.setStyle("-fx-background-color: #2A2E37");
			Menu menu1 = new Menu("File");
			MenuItem closeBar = new MenuItem("Close");
			menu1.getItems().add(closeBar);
			Menu menu2 = new Menu("Add");
			MenuItem tambahBuku = new MenuItem("Add Book");
			MenuItem tambahMember = new MenuItem("Add Member");
			menu2.getItems().addAll(tambahBuku, tambahMember);
			Menu menu3 = new Menu("View");
			MenuItem viewBuku = new MenuItem("View Book");
			MenuItem viewMember = new MenuItem("View Member");
			MenuItem fullView = new MenuItem("Full Screen");
			menu3.getItems().addAll(viewBuku, viewMember, fullView);
			Menu menu4 = new Menu("Help");
			MenuItem itemHelp = new MenuItem("About");
			menu4.getItems().add(itemHelp);
			EventHandler<ActionEvent> handler2 = new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent ec) {
					if(ec.getSource() == closeBar) {
						System.exit(0);
						//primaryStage.close();
					}
					if(ec.getSource() == tambahBuku) {
						addBook addBook = new addBook();
						addBook.start(new Stage());
					}
					if(ec.getSource() == tambahMember) {
						addMember am = new addMember();
						am.start(new Stage());
					}
					if(ec.getSource() == viewBuku) {
						ListBook lb = new ListBook();
						try {
							lb.start(new Stage());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if(ec.getSource() == viewMember) {
						ListMember lm = new ListMember();
						try {
							lm.start(new Stage());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if(ec.getSource() == fullView) {
						primaryStage.setFullScreen(!primaryStage.isFullScreen());
					}
					ec.consume();
				}
			};
			closeBar.setOnAction(handler2);
			tambahBuku.setOnAction(handler2);
			tambahMember.setOnAction(handler2);
			viewBuku.setOnAction(handler2);
			viewMember.setOnAction(handler2);
			fullView.setOnAction(handler2);
			
			menuBar.getMenus().addAll(menu1, menu2, menu3, menu4);
			
			// vvvvvvvvvvvvvvvvvvvvvvvvvvvv    border-center  vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv //
			AnchorPane anchor = new AnchorPane();
			JFXTabPane tabpane = new JFXTabPane();
		
			anchor.getChildren().add(tabpane);
			AnchorPane.setTopAnchor(tabpane, 0.0);
			AnchorPane.setBottomAnchor(tabpane, 40.0);
			AnchorPane.setLeftAnchor(tabpane, 0.0);
			AnchorPane.setRightAnchor(tabpane, 0.0);
						
			Tab tab = new Tab("Book Issue");
			
			Tab tab2 = new Tab("Renew / Submission");
			
			//vvvvvvvvvvvvvvvvvvvv    untuk tab book issue     vvvvvvvvvvvvvvvvvvvvvvvvvvvv//
			VBox vTab = new VBox();
			vTab.setPadding(new Insets(20,20,20,20));
			
			//untuk book
			HBox hVBox = new HBox(50);
			hVBox.setPadding(new Insets(20,20,20,20));
			hVBox.setPrefHeight(150);
			hVBox.setAlignment(Pos.CENTER);
			tf.setStyle("-fx-text-inner-color: #FFFF8D;"
					+ "-fx-prompt-text-fill: #FFFF8D;"
					+ "-fx-background-color: #FFFF8D, #2A2E37, #2A2E37;"
					+ "-fx-background-insets: 0 0 -1 0, -1 -1 0 -1, -1 -1 0 -1");
			tf.setPromptText("Enter Book ID");
			StackPane stack = new StackPane();
			stack.setPrefWidth(300);
			bookChart.setData(handler.getBookGraphStats());
			stack.getChildren().add(bookChart);
			
			VBox dalamBox = new VBox(10);
			dalamBox.setAlignment(Pos.CENTER);
			dalamBox.setPrefHeight(50);
			dalamBox.setPrefWidth(300);
			dalamBox.setPadding(new Insets(20,20,20,20));

			textBook.setTextAlignment(TextAlignment.CENTER);
			textBook.setFill(Color.rgb(255, 255, 141));
			authorText.setTextAlignment(TextAlignment.CENTER);
			authorText.setFill(Color.rgb(255, 255, 141));
			statusText.setTextAlignment(TextAlignment.CENTER);
			statusText.setFill(Color.rgb(255, 255, 141));
			
			//untuk member
			HBox hVBox2 = new HBox(50);
			hVBox2.setPadding(new Insets(20,20,20,20));
			hVBox2.setPrefHeight(150);
			hVBox2.setAlignment(Pos.CENTER);
			StackPane stack2 = new StackPane();
			stack2.setPrefWidth(300);
			memberChart.setData(handler.getMemberGraphStats());
			stack2.getChildren().add(memberChart);

			tf2.setStyle("-fx-text-inner-color: #FFFF8D;"
					+ "-fx-prompt-text-fill: #FFFF8D;"
					+ "-fx-background-color: #FFFF8D, #2A2E37, #2A2E37;"
					+ "-fx-background-insets: 0 0 -1 0, -1 -1 0 -1, -1 -1 0 -1");
			tf2.setPromptText("Enter Member ID");
			VBox dalamBox2 = new VBox(10);
			dalamBox2.setAlignment(Pos.CENTER);
			dalamBox2.setPrefHeight(50);
			dalamBox2.setPrefWidth(300);
			dalamBox2.setPadding(new Insets(20,20,20,20));
			
			textMember.setTextAlignment(TextAlignment.CENTER);
			textMember.setFill(Color.rgb(255, 255, 141));
			mobileText.setTextAlignment(TextAlignment.CENTER);
			mobileText.setFill(Color.rgb(255, 255, 141));
			
			
			dalamBox.getChildren().add(textBook);
			dalamBox.getChildren().add(authorText);
			dalamBox.getChildren().add(statusText);
			stack.getChildren().add(dalamBox);
			dalamBox2.getChildren().add(textMember);
			dalamBox2.getChildren().add(mobileText);
			stack2.getChildren().add(dalamBox2);
			hVBox.getChildren().add(tf);
			hVBox.getChildren().add(stack);
			hVBox2.getChildren().add(tf2);
			hVBox2.getChildren().add(stack2);
			
			//HBox untuk button
			HBox butHBox = new HBox();
			butHBox.setAlignment(Pos.CENTER);
			butHBox.setPadding(new Insets(0,0,20,0));
			Button issue = new Button("Issue");
			issue.setMinWidth(80);
			issue.setMinHeight(30);
			issue.setStyle("-fx-background-color: \r\n"
					+ "        linear-gradient(#ffef84, #f2ba44),\r\n"
					+ "        linear-gradient(#ffea6a, #efaa22),\r\n"
					+ "        linear-gradient(#2a2e37 0%, #2a2e37 100%, #eea10b 50%),\r\n"
					+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
					+ "    -fx-background-radius: 30;\r\n"
					+ "    -fx-text-fill: #FFFF8D;\r\n"
					+ "    -fx-font-weight: bold;\r\n");
			butHBox.getChildren().add(issue);
			
			//masukkan dalam tab book issue
			vTab.getChildren().add(hVBox);
			vTab.getChildren().add(hVBox2);
			vTab.getChildren().add(butHBox);

			tab.setContent(vTab);
			tab.setOnSelectionChanged(new EventHandler<Event>() {

				@Override
				public void handle(Event arg0) {
					clearIssueEntries();
					if(tab.isSelected()) {
						refreshGraph();
					}
				}
				
			});
			
			//^^^^^^^^^^^^^^^     untuk tab book issue     ^^^^^^^^^^^^^^^^^^^^^^^^^^^//
			
			//vvvvvvvvvvvvvvvvvvvv    untuk tab renew / submission     vvvvvvvvvvvvvvvvvvvvvvvvvvvv//
			BorderPane tab2Pane = new BorderPane();
			tab2Pane.setPadding(new Insets(20,0,0,10));
			
			//tab renew submission -> border Top
			JFXTextField tf3 = new JFXTextField();
			tf3.setStyle("-fx-text-inner-color: #FFFF8D;"
					+ "-fx-prompt-text-fill: #FFFF8D;"
					+ "-fx-background-color: #FFFF8D, #2A2E37, #2A2E37;"
					+ "-fx-background-insets: 0 0 -1 0, -1 -1 0 -1, -1 -1 0 -1");
			tf3.setLabelFloat(true);
			tf3.setPromptText("Enter BOOK ID");
			tf3.setMaxWidth(200);
			BorderPane.setAlignment(tf3, Pos.CENTER);
			
			tf.setLabelFloat(true);
			tf2.setLabelFloat(true);
			
			//tab renew submission -> border center
			kotakTengah.setAlignment(Pos.CENTER);
			
			VBox kotakSatu = new VBox(10);
			kotakSatu.setStyle("-fx-background-color: derived(#2A2E37, 20%)");
			kotakSatu.setAlignment(Pos.CENTER);
			HBox.setMargin(kotakSatu, new Insets(50,0,0,0));
			kotakSatu.setMinWidth(150);
			kotakSatu.setPadding(new Insets(0,30,20,30));
			
			mnHolder.setFill(Color.rgb(255, 255, 141));
			meHolder.setFill(Color.rgb(255, 255, 141));
			Image img2 = new Image("output-onlinepngtools (10).png");
			ImageView imv2 = new ImageView(img2);
			mcHolder.setFill(Color.rgb(255, 255, 141));
			kotakSatu.getChildren().addAll(imv2,mnHolder, meHolder, mcHolder);
			
			VBox kotakDua = new VBox(10);
			kotakDua.setStyle("-fx-background-color: derived(#2A2E37, 20%)");
			kotakDua.setAlignment(Pos.CENTER);
			HBox.setMargin(kotakDua, new Insets(50,0,0,0));
			kotakDua.setMinWidth(150);
			kotakDua.setPadding(new Insets(0,30,20,30));
			
			bnHolder.setFill(Color.rgb(255, 255, 141));
			baHolder.setFill(Color.rgb(255, 255, 141));
			bpHolder.setFill(Color.rgb(255, 255, 141));
			Image img = new Image("output-onlinepngtools (8).png");
			ImageView imv = new ImageView(img);
			kotakDua.getChildren().addAll(imv,bnHolder, baHolder, bpHolder);
			
			VBox kotakTiga = new VBox(10);
			kotakTiga.setStyle("-fx-background-color: derived(#2A2E37, 20%)");
			kotakTiga.setAlignment(Pos.CENTER);
			HBox.setMargin(kotakTiga, new Insets(50,0,0,0));
			kotakTiga.setMinWidth(150);
			kotakTiga.setPadding(new Insets(0,30,20,30));

			idHolder.setFill(Color.rgb(255, 255, 141));
			ndHolder.setFill(Color.rgb(255, 255, 141));
			fHolder.setFill(Color.rgb(255, 255, 141));
			kotakTiga.getChildren().addAll(idHolder, ndHolder, fHolder);
			
			kotakTengah.getChildren().addAll(kotakSatu, kotakDua, kotakTiga);
			
			//tab renew submission -> border bottom
			HBox butBox = new HBox(50);
			butBox.setAlignment(Pos.CENTER);
			butBox.setMinHeight(100);

			renewB.setMinWidth(80);
			submitB.setMinWidth(80);
			renewB.setMinHeight(30);
			submitB.setMinHeight(30);
			renewB.setStyle("-fx-background-color: \r\n"
					+ "        linear-gradient(#ffef84, #f2ba44),\r\n"
					+ "        linear-gradient(#ffea6a, #efaa22),\r\n"
					+ "        linear-gradient(#2a2e37 0%, #2a2e37 100%, #eea10b 50%),\r\n"
					+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
					+ "    -fx-background-radius: 30;\r\n"
					+ "    -fx-text-fill: #FFFF8D;\r\n"
					+ "    -fx-font-weight: bold;\r\n");
			submitB.setStyle("-fx-background-color: \r\n"
					+ "        linear-gradient(#ffef84, #f2ba44),\r\n"
					+ "        linear-gradient(#ffea6a, #efaa22),\r\n"
					+ "        linear-gradient(#2a2e37 0%, #2a2e37 100%, #eea10b 50%),\r\n"
					+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
					+ "    -fx-background-radius: 30;\r\n"
					+ "    -fx-text-fill: #FFFF8D;\r\n"
					+ "    -fx-font-weight: bold;\r\n");
			
			butBox.getChildren().addAll(renewB, submitB);
			
			//masukkan dalam tab renew / submission
			tab2Pane.setTop(tf3);
			tab2Pane.setCenter(kotakTengah);
			tab2Pane.setBottom(butBox);
			tab2.setContent(tab2Pane);
			
			//^^^^^^^^^^^^^^^     untuk tab book issue     ^^^^^^^^^^^^^^^^^^^^^^^^^^^//
			
			tabpane.getTabs().addAll(tab, tab2);
			// ^^^^^^^^^^^^^^^^^^^^^^^^^^^      untuk border -> center ^^^^^^^^^^^^^^^^^^^^^^^^^  //
			
			//border-right
			JFXHamburger hamburger = new JFXHamburger();
			AnchorPane.setBottomAnchor(hamburger, 360.0);
			AnchorPane.setTopAnchor(hamburger, 0.0);
			AnchorPane.setRightAnchor(hamburger, 0.0);
			AnchorPane.setLeftAnchor(hamburger, 650.0);
			
			JFXDrawer drawer = new JFXDrawer();
			hamburger.setStyle("-fx-spacing:5;"
					+ "-fx-background-icon-color: #FFFF8D;");
			VBox vButton = new VBox();
			vButton.getChildren().addAll(bt,bt2,bt3,bt4,bt5);
			VBox toolbar = new VBox(vButton);
			toolbar.setMinWidth(150);
			
			drawer.setSidePane(toolbar);
			drawer.setStyle("-fx-background-color: #FFFF8D;");
			HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
	        task.setRate(-1);
	        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
	            drawer.toggle();
	        });
	        drawer.close();
	        drawer.setOnDrawerOpening((event) -> {
	            task.setRate(task.getRate() * -1);
	            drawer.setMinWidth(150);
	            task.play();
	        });
	        drawer.setOnDrawerClosed((event) -> {
	        	task.setRate(task.getRate() * -1);
	            task.play();
	            drawer.setMinWidth(0);
	        });
			anchor.getChildren().add(hamburger);
			
			
			bt.setPrefWidth(150);
			bt.setPrefHeight(100);
			bt.setGraphic(new ImageView(new Image("output-onlinepngtools (1).png")));
			bt.setOnMouseEntered(mouseEvent -> {
				bt.setStyle("-fx-background-color: \r\n"
						+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
						+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
						+ "        linear-gradient(#FFFF8D 0%, #FFFF8D 100%, #FFFF8D 50%),\r\n"
						+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
						+ "    -fx-text-fill: #0f0f0f;\r\n"
						+ "    -fx-font-weight: bold;\r\n");
			});
			bt.setOnMouseExited(mouseEvent -> {
				bt.setStyle("-fx-background-color: derived(#2A2E37,20%);"
						+ "    -fx-font-weight: bold;\r\n");
			});

			bt2.setPrefWidth(150);
			bt2.setPrefHeight(100);
			bt2.setGraphic(new ImageView(new Image("output-onlinepngtools (3).png")));
			bt2.setOnMouseEntered(mouseEvent -> {
				bt2.setStyle("-fx-background-color: \r\n"
						+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
						+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
						+ "        linear-gradient(#FFFF8D 0%, #FFFF8D 100%, #FFFF8D 50%),\r\n"
						+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
						+ "    -fx-text-fill: #0f0f0f;\r\n"
						+ "    -fx-font-weight: bold;\r\n");
			});
			bt2.setOnMouseExited(mouseEvent -> {
				bt2.setStyle("-fx-background-color: derived(#2A2E37,20%);"
						+ "    -fx-font-weight: bold;\r\n");
			});

			bt3.setPrefWidth(150);
			bt3.setPrefHeight(100);
			bt3.setOnMouseEntered(mouseEvent -> {
				bt3.setStyle("-fx-background-color: \r\n"
						+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
						+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
						+ "        linear-gradient(#FFFF8D 0%, #FFFF8D 100%, #FFFF8D 50%),\r\n"
						+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
						+ "    -fx-text-fill: #0f0f0f;\r\n"
						+ "    -fx-font-weight: bold;\r\n");
			});
			bt3.setOnMouseExited(mouseEvent -> {
				bt3.setStyle("-fx-background-color: derived(#2A2E37,20%);"
						+ "    -fx-font-weight: bold;\r\n");
			});
			bt3.setGraphic(new ImageView(new Image("output-onlinepngtools (4).png")));

			bt4.setPrefWidth(150);
			bt4.setPrefHeight(100);
			bt4.setOnMouseEntered(mouseEvent -> {
				bt4.setStyle("-fx-background-color: \r\n"
						+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
						+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
						+ "        linear-gradient(#FFFF8D 0%, #FFFF8D 100%, #FFFF8D 50%),\r\n"
						+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
						+ "    -fx-text-fill: #0f0f0f;\r\n"
						+ "    -fx-font-weight: bold;\r\n");
			});
			bt4.setOnMouseExited(mouseEvent -> {
				bt4.setStyle("-fx-background-color: derived(#2A2E37,20%);"
						+ "    -fx-font-weight: bold;\r\n");
			});
			bt4.setGraphic(new ImageView(new Image("output-onlinepngtools (5).png")));

			bt5.setPrefWidth(150);
			bt5.setPrefHeight(100);
			bt5.setOnMouseEntered(mouseEvent -> {
				bt5.setStyle("-fx-background-color: \r\n"
						+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
						+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
						+ "        linear-gradient(#FFFF8D 0%, #FFFF8D 100%, #FFFF8D 50%),\r\n"
						+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
						+ "    -fx-text-fill: #0f0f0f;\r\n"
						+ "    -fx-font-weight: bold;\r\n");
			});
			bt5.setOnMouseExited(mouseEvent -> {
				bt5.setStyle("-fx-background-color: derived(#2A2E37,20%);"
						+ "    -fx-font-weight: bold;\r\n");
			});
			bt5.setGraphic(new ImageView(new Image("output-onlinepngtools (2).png")));
			
			pane.setTop(menuBar);
			pane.setCenter(anchor);
			pane.setLeft(drawer);
			BorderPane.clearConstraints(drawer);
			BorderPane.setMargin(drawer, new Insets(0,0,0,-150));
			drawer.setDirection(DrawerDirection.RIGHT);

			root.getChildren().add(pane);
			
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						handler = Database.getInstance();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}).start();
			bookChart.setData(handler.getBookGraphStats());
			//event
			EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {

				Database handler = Database.getInstance();
				
				@Override
				public void handle(MouseEvent e) {
					if(e.getSource() == bt) {
						addMember am = new addMember();
						am.start(new Stage());
					}
					
					if(e.getSource() == bt2) {
						addBook addBook = new addBook();
						addBook.start(new Stage());
					}
					
					//view members
					if(e.getSource() == bt3) {
						ListMember lm = new ListMember();
						try {
							lm.start(new Stage());
						} catch (Exception er) {
							er.printStackTrace();
						}
					}
					
					//view books
					if(e.getSource() == bt4) {
						ListBook lb = new ListBook();
						try {
							lb.start(new Stage());
						} catch (Exception em) {
							em.printStackTrace();
						}
					}
					
					//setting button
					if(e.getSource() == bt5) {
						Settings setting = new Settings();
						try {
							setting.start(new Stage());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					
					if(e.getSource() == issue) {
						String idBuku = tf.getText();
						String idMember = tf2.getText();
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("Confirm Issue Operation");
						alert.setHeaderText(null);
						alert.setContentText("Are you sure want to issue the book " + textBook.getText() + "\n to " + textMember.getText() + "?");
						
						Optional<ButtonType> response = alert.showAndWait();
						if(response.get() == ButtonType.OK) {
							String str = "INSERT INTO ISSUE (memberID,bookID) VALUES ("
									+ "'" + idMember + "',"
									+ "'" + idBuku + "')";
							String str2 = "UPDATE BOOKS SET isAvail = false WHERE id = '" + idBuku + "'";
							System.out.println(str + " and " + str2);
							
							if(handler.execAction(str) && handler.execAction(str2)) {
								Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
								alert2.setTitle("SUCCESS");
								alert2.setHeaderText(null);
								alert2.setContentText("Book Issue Complete");
								alert2.showAndWait();
								refreshGraph();
							} else {
								Alert alert2 = new Alert(Alert.AlertType.ERROR);
								alert2.setTitle("FAILED");
								alert2.setHeaderText(null);
								alert2.setContentText("ISSUE OPERATION FAILED");
								alert2.showAndWait();
							}
						} else {
							Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
							alert2.setTitle("CANCELLED");
							alert2.setHeaderText(null);
							alert2.setContentText("Issue Operation Cancelled");
							alert2.showAndWait();
						}
						clearIssueEntries();
					}
					
					if(e.getSource() == renewB) {
						if(!isReady4Submission) {
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("FAILED");
							alert.setHeaderText(null);
							alert.setContentText("Please select a book to renew");
							alert.showAndWait();
							return;
						}
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("Confirm Renew Operation");
						alert.setHeaderText(null);
						alert.setContentText("Are you sure want to renew the book?");
						Optional<ButtonType> response = alert.showAndWait();
						if(response.get() == ButtonType.OK) {
							String ac = "UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP, renew_count = renew_count+1 WHERE BOOKID = '" + tf3.getText() +"'";
							System.out.println(ac);
							if(handler.execAction(ac)) {
								Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
								alert1.setTitle("SUCCESS");
								alert1.setHeaderText(null);
								alert1.setContentText("Book Has Been Renewed");
								alert1.showAndWait();
							} else {
							Alert alert1 = new Alert(Alert.AlertType.ERROR);
							alert1.setTitle("FAILED");
							alert1.setHeaderText(null);
							alert1.setContentText("Renew Has Been Failed");
							alert1.showAndWait();
							}
						} else {
							Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
							alert2.setTitle("CANCELLED");
							alert2.setHeaderText(null);
							alert2.setContentText("Submission Operation Cancelled");
							alert2.showAndWait();
						}
						issueData.clear();
						tf3.clear();
						
					}
					
					if(e.getSource() == submitB) {
						if(!isReady4Submission) {
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("FAILED");
							alert.setHeaderText(null);
							alert.setContentText("Please select a book to submit");
							alert.showAndWait();
							return;
						}
						
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("Confirm Submission Operation");
						alert.setHeaderText(null);
						alert.setContentText("Are you sure want to return the book?");
						
						Optional<ButtonType> response = alert.showAndWait();
						if(response.get() == ButtonType.OK) {
							String id = tf3.getText();
							String ac1 = "DELETE FROM issue WHERE BOOKID = '" + id + "'";
							String ac2 = "UPDATE books SET isAvail = TRUE WHERE ID = '" + id + "'";
						
							if(handler.execAction(ac1) && handler.execAction(ac2)) {
								Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
								alert1.setTitle("SUCCESS");
								alert1.setHeaderText(null);
								alert1.setContentText("Book Has Been Submitted");
								alert1.showAndWait();
								enableDisableControls(false);
								kotakTengah.setOpacity(0);
							} else {
							Alert alert1 = new Alert(Alert.AlertType.ERROR);
							alert1.setTitle("FAILED");
							alert1.setHeaderText(null);
							alert1.setContentText("Submission Has Been Failed");
							alert1.showAndWait();
							}
						} else {
							Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
							alert2.setTitle("CANCELLED");
							alert2.setHeaderText(null);
							alert2.setContentText("Submission Operation Cancelled");
							alert2.showAndWait();
						}
						issueData.clear();

						tf3.clear();
						clearEntries();
					}
					e.consume();
				}
				
			};
			bt.setOnMouseClicked(handler);
			bt2.setOnMouseClicked(handler);
			bt3.setOnMouseClicked(handler);
			bt4.setOnMouseClicked(handler);
			bt5.setOnMouseClicked(handler);
			issue.setOnMouseClicked(handler);
			renewB.setOnMouseClicked(handler);
			submitB.setOnMouseClicked(handler);
			issue.setOnMouseEntered(mouseEvent -> {
				issue.setStyle("-fx-background-color: \r\n"
						+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
						+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
						+ "        linear-gradient(#FFFF8D 0%, #FFFF8D 100%, #FFFF8D 50%),\r\n"
						+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
						+ "    -fx-background-radius: 30;\r\n"
						+ "    -fx-text-fill: #0f0f0f;\r\n"
						+ "    -fx-font-weight: bold;\r\n");
			});
			issue.setOnMouseExited(mouseEvent -> {
				issue.setStyle("-fx-background-color: \r\n"
						+ "        linear-gradient(#ffef84, #f2ba44),\r\n"
						+ "        linear-gradient(#ffea6a, #efaa22),\r\n"
						+ "        linear-gradient(#2a2e37 0%, #2a2e37 100%, #eea10b 50%),\r\n"
						+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
						+ "    -fx-background-radius: 30;\r\n"
						+ "    -fx-text-fill: #FFFF8D;\r\n"
						+ "    -fx-font-weight: bold;\r\n");
			});
			submitB.setOnMouseEntered(mouseEvent -> {
				submitB.setStyle("-fx-background-color: \r\n"
						+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
						+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
						+ "        linear-gradient(#FFFF8D 0%, #FFFF8D 100%, #FFFF8D 50%),\r\n"
						+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
						+ "    -fx-background-radius: 30;\r\n"
						+ "    -fx-text-fill: #0f0f0f;\r\n"
						+ "    -fx-font-weight: bold;\r\n");
			});
			submitB.setOnMouseExited(mouseEvent -> {
				submitB.setStyle("-fx-background-color: \r\n"
						+ "        linear-gradient(#ffef84, #f2ba44),\r\n"
						+ "        linear-gradient(#ffea6a, #efaa22),\r\n"
						+ "        linear-gradient(#2a2e37 0%, #2a2e37 100%, #eea10b 50%),\r\n"
						+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
						+ "    -fx-background-radius: 30;\r\n"
						+ "    -fx-text-fill: #FFFF8D;\r\n"
						+ "    -fx-font-weight: bold;\r\n");
			});
			renewB.setOnMouseEntered(mouseEvent -> {
				renewB.setStyle("-fx-background-color: \r\n"
						+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
						+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
						+ "        linear-gradient(#FFFF8D 0%, #FFFF8D 100%, #FFFF8D 50%),\r\n"
						+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
						+ "    -fx-background-radius: 30;\r\n"
						+ "    -fx-text-fill: #0f0f0f;\r\n"
						+ "    -fx-font-weight: bold;\r\n");
			});
			renewB.setOnMouseExited(mouseEvent -> {
				renewB.setStyle("-fx-background-color: \r\n"
						+ "        linear-gradient(#ffef84, #f2ba44),\r\n"
						+ "        linear-gradient(#ffea6a, #efaa22),\r\n"
						+ "        linear-gradient(#2a2e37 0%, #2a2e37 100%, #eea10b 50%),\r\n"
						+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
						+ "    -fx-background-radius: 30;\r\n"
						+ "    -fx-text-fill: #FFFF8D;\r\n"
						+ "    -fx-font-weight: bold;\r\n");
			});

			tf.setOnAction(new EventHandler<ActionEvent>() {
				Database handler = Database.getInstance();

				@Override
				public void handle(ActionEvent e) {
					String idBuku = tf.getText();
					String idMember = tf2.getText();
					Boolean flag = false;
					bookChart.setOpacity(0);
					
					if(idMember.isEmpty()) {
						//for book
						String qu = "SELECT * FROM books WHERE id = '" + idBuku + "'";
						ResultSet rs = handler.execQuery(qu);
						try {
							while(rs.next()) {
								authorText.setOpacity(100);
								String bName = rs.getString("title");
								String bAuthor = rs.getString("author");
								Boolean bStatus = rs.getBoolean("isAvail");
								textBook.setText(bName);
								authorText.setText(bAuthor);
								String status = (bStatus)?"Available":"Not Available";
								statusText.setText(status);
								flag = true;
							}
							if(!flag) {
									textBook.setText("No such book available");
									authorText.setOpacity(0);
									statusText.setText("Not Available");
								
							}
						} catch(SQLException ex) {
							ex.printStackTrace();
						}
					} 
				}
			});
			
			tf2.setOnAction(new EventHandler<ActionEvent>() {

				Database handler = Database.getInstance();
				
				@Override
				public void handle(ActionEvent e) {
					String idMember = tf2.getText();
					Boolean flag = false;
					memberChart.setOpacity(0);
					
					String st = "SELECT * FROM member WHERE id = '" + idMember + "'";
					ResultSet sr = handler.execQuery(st);
					try {
						while(sr.next()) {
							mobileText.setOpacity(100);
							String mName = sr.getString("name");
							String mAuthor = sr.getString("mobile");
						
							textMember.setText(mName);
							mobileText.setText(mAuthor);
							flag = true;
						}
						if(!flag) {
							textMember.setText("No such member");
							mobileText.setOpacity(0);
							
						}
					} catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
			});
			
			tf3.setOnAction(new EventHandler<ActionEvent>() {


				Database handler = Database.getInstance();
				
				@Override
				public void handle(ActionEvent e) {
					clearEntries();
					isReady4Submission = false;
					String id = tf3.getText();
					String myQuery = "SELECT ISSUE.bookID, ISSUE.memberID, ISSUE.issueTime, ISSUE.renew_count,\n"
							+ "MEMBER.name, MEMBER.mobile, MEMBER.email,\n"
							+ "BOOKS.title, BOOKS.author, BOOKS.publisher\n"
							+ "FROM ISSUE\n"
							+ "LEFT JOIN MEMBER\n"
							+ "ON ISSUE.memberID=MEMBER.id\n"
							+ "LEFT JOIN BOOKS\n"
							+ "ON ISSUE.bookID=BOOKS.id\n"
							+ "WHERE ISSUE.bookID='" + id + "'";
					ResultSet rs2 = handler.execQuery(myQuery);
					try {
						if(rs2.next()) {
							mnHolder.setText(rs2.getString("name"));
							meHolder.setText(rs2.getString("email"));
							mcHolder.setText(rs2.getString("mobile"));
							
							bnHolder.setText(rs2.getString("title"));
							baHolder.setText(rs2.getString("author"));
							bpHolder.setText(rs2.getString("publisher"));
							
							Timestamp mIssueTime = rs2.getTimestamp("issueTime");
							Date dateOfIssue = new Date(mIssueTime.getTime());
							idHolder.setText(dateOfIssue.toString());
							Long timeElapsed = System.currentTimeMillis() - mIssueTime.getTime();
							Long daysElapsed = TimeUnit.DAYS.convert(timeElapsed, TimeUnit.MILLISECONDS);
							ndHolder.setText(daysElapsed.toString());
							fHolder.setText("wait");
							
							isReady4Submission = true;
							enableDisableControls(true);
							kotakTengah.setOpacity(1);
						} else {
							BoxBlur blur = new BoxBlur(3,3,3);
							pane.setEffect(blur);
							JFXButton but = new JFXButton("Okay! I'll Check.");
							but.setStyle("-fx-background-color: \r\n"
									+ "        linear-gradient(#ffef84, #f2ba44),\r\n"
									+ "        linear-gradient(#ffea6a, #efaa22),\r\n"
									+ "        linear-gradient(#2a2e37 0%, #2a2e37 100%, #eea10b 50%),\r\n"
									+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
									+ "    -fx-background-radius: 30;\r\n"
									+ "    -fx-text-fill: #FFFF8D;\r\n"
									+ "    -fx-font-weight: bold;\r\n");
							but.setOnMouseEntered(mouseEvent -> {
								but.setStyle("-fx-background-color: \r\n"
										+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
										+ "        linear-gradient(#FFFF8D, #FFFF8D),\r\n"
										+ "        linear-gradient(#FFFF8D 0%, #FFFF8D 100%, #FFFF8D 50%),\r\n"
										+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
										+ "    -fx-background-radius: 30;\r\n"
										+ "    -fx-text-fill: #0f0f0f;\r\n"
										+ "    -fx-font-weight: bold;\r\n");
							});
							but.setOnMouseExited(mouseEvent -> {
								but.setStyle("-fx-background-color: \r\n"
										+ "        linear-gradient(#ffef84, #f2ba44),\r\n"
										+ "        linear-gradient(#ffea6a, #efaa22),\r\n"
										+ "        linear-gradient(#2a2e37 0%, #2a2e37 100%, #eea10b 50%),\r\n"
										+ "        linear-gradient(from 0% 0% to 15% 50%, rgba(42,46,55,0), rgba(42,46,55,0));\r\n"
										+ "    -fx-background-radius: 30;\r\n"
										+ "    -fx-text-fill: #FFFF8D;\r\n"
										+ "    -fx-font-weight: bold;\r\n");
							});
							JFXDialogLayout messageLayout = new JFXDialogLayout();
							JFXDialog dialog = new JFXDialog(root, messageLayout, JFXDialog.DialogTransition.TOP);
							
							but.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->{
								dialog.close();
							});
							messageLayout.setBody(new Label("No Such Book Exist in Issue Record!"));
							messageLayout.setActions(but);
							dialog.show();
							dialog.setOnDialogClosed((JFXDialogEvent event1) ->{
								pane.setEffect(null);
							});
							tf3.clear();
						}
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
				}
			});
			clearIssueEntries();

			Scene scene = new Scene(root,700,500);
			primaryStage.getIcons().add(new Image("library-icon-vector-20894793.jpg"));
			primaryStage.setTitle("Library Management System");
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void clearIssueEntries() {
		tf.clear();
		tf2.clear();
		textBook.setText("");
		authorText.setText("");
		statusText.setText("");
		textMember.setText("");
		mobileText.setText("");
		bookChart.setOpacity(1);
		memberChart.setOpacity(1);
	}

	private void clearEntries() {
		mnHolder.setText("");
		meHolder.setText("");
		mcHolder.setText("");
		
		bnHolder.setText("");
		baHolder.setText("");
		bpHolder.setText("");
		
		idHolder.setText("");
		ndHolder.setText("");
		fHolder.setText("");
		enableDisableControls(false);
		kotakTengah.setOpacity(0);
	}
	
	private void enableDisableControls(Boolean enableFlag) {
		if(enableFlag) {
			renewB.setDisable(false);
			submitB.setDisable(false);
		} else {
			renewB.setDisable(true);
			submitB.setDisable(true);
		}
	}
	
	private void refreshGraph() {
		bookChart.setData(handler.getBookGraphStats());
		memberChart.setData(handler.getMemberGraphStats());
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
