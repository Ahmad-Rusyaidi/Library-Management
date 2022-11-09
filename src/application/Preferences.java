package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import org.apache.commons.codec.digest.DigestUtils;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Preferences {
	public static final String CONFIG_FILE = "config.txt";

	double finePerDay;
	String username;
	String password;
	int nDaysWithoutFine;
	
	public int getnDaysWithoutFine() {
		return nDaysWithoutFine;
	}

	public void setnDaysWithoutFine(int nDaysWithoutFine) {
		this.nDaysWithoutFine = nDaysWithoutFine;
	}

	public double getFinePerDay() {
		return finePerDay;
	}
	

	public void setFinePerDay(double finePerDay) {
		this.finePerDay = finePerDay;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if(password.length() < 16) {
			this.password = DigestUtils.sha1Hex(password);
		} else {
			this.password = password;
		}
	}
	
	public Preferences(){
		nDaysWithoutFine = 14;
		finePerDay = 0.2;
		username = "admin";
		setPassword("admin");
	}
	
	public static void initConfig() {
		Writer writer = null;
		try {
			Preferences preference = new Preferences();
			Gson gson = new Gson();
			writer = new FileWriter(CONFIG_FILE);
			gson.toJson(preference, writer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static Preferences getPreferences() {
		Gson gson = new Gson();
		Preferences preferences = new Preferences();
		try {
			preferences = gson.fromJson(new FileReader(CONFIG_FILE), Preferences.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			initConfig();
			e.printStackTrace();
		}
		return preferences;
	}
	
	public static void writePreferences2File(Preferences preferences) {
		Writer writer = null;
		try {
			Gson gson = new Gson();
			writer = new FileWriter(CONFIG_FILE);
			gson.toJson(preferences, writer);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("SUCCESS");
			alert.setHeaderText(null);
			alert.setContentText("Settings Updated!");
			alert.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			Alert alert1 = new Alert(Alert.AlertType.ERROR);
			alert1.setTitle("FAILED");
			alert1.setHeaderText(null);
			alert1.setContentText("Cannot Save Configuration");
			alert1.showAndWait();
		} finally {
			try {
				writer.close();
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
