module LibraryManagement {
	requires transitive javafx.controls;
	requires transitive javafx.graphics;
	requires javafx.base;
	requires transitive java.sql;
	requires java.desktop;
	requires javafx.fxml;
	requires javafx.media;
	requires gson;
	requires jdk.jartool;
	requires org.apache.commons.codec;
	requires com.jfoenix;
	requires java.base;
	requires java.xml.crypto;
	
	exports application to javafx.graphics;
	opens application to javafx.base, gson;
}
