package application;

import java.sql.*;
import javax.swing.JOptionPane;
import application.ListBook.Book;
import application.ListMember.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

public final class Database {

	private static Database handler = null;
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/lolol?useSSL=false";
	private static Connection conn = null;
	private static Statement stmt = null;
	
	public Database() throws SQLException {
		createConnection();
		setupBookTable();
		setupMemberTable();
		setupIssueTable();
	}
	
	public static Database getInstance() throws SQLException {
		if(handler == null) {
			handler = new Database();
		}
		return handler;
	}
	
	public void createConnection(){
		try {
			conn = DriverManager.getConnection(DB_URL,"root","DdMmyY312138!");
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Can't Load Database", "Database Error!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	void setupBookTable() throws SQLException {
		String tableName = "BOOKS";
		try {
			stmt = conn.createStatement();
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, tableName.toUpperCase(), null);
			if(tables.next()) {
				System.out.println("Table " +  tableName + " already exists. Ready for go!");
			} else {
				stmt.execute("CREATE TABLE " + tableName + "("
						+ "      id varchar(200) primary key,\n"
						+ "      title varchar(200), \n"
						+ "      author varchar(200),\n"
						+ "      publisher varchar(100),\n"
						+ "      isAvail boolean default true" 
						+ ")");
			}
		} finally {
			
		}
	}
	void setupMemberTable() throws SQLException {
		String tableName = "MEMBER";
		try {
			stmt = conn.createStatement();
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, tableName.toUpperCase(), null);
			if(tables.next()) {
				System.out.println("Table " +  tableName + " already exists. Ready for go!");
			} else {
				stmt.execute("CREATE TABLE " + tableName + "("
						+ "      id varchar(200) primary key, \n"
						+ "      name varchar(200),\n"
						+ "      mobile varchar(20),\n"
						+ "      email varchar(100)"
						+ ")");
			}
		} finally {
			
		}
	}
	
	void setupIssueTable() throws SQLException {
		String issueTable = "ISSUE";
		try {
			stmt = conn.createStatement();
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, issueTable.toUpperCase(), null);
			if(tables.next()) {
				System.out.println("Table " + issueTable + " already exists. Ready for go!");
			} else {
				stmt.execute("CREATE TABLE " + issueTable + "("
				+ "      bookID varchar(200) primary key, \n"
				+ "      memberID varchar (200), \n"
				+ "      issueTime timestamp default CURRENT_TIMESTAMP, \n"
				+ "      renew_count integer default 0, \n"
				+ "      FOREIGN KEY (bookID) REFERENCES BOOKS(id), \n"
				+ "      FOREIGN KEY (memberID) REFERENCES MEMBER(id)"
				+ " )");
			}
		} finally {
			
		}
	}
	
	public ResultSet execQuery(String query) {
		ResultSet result;
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
		} catch(SQLException ex) {
			System.out.println("Exception at execQuery:dataHandler " + ex.getLocalizedMessage());
			return null;
		} finally {
			
		}
		return result;
	}
	
	public boolean execAction(String qu) {
		try {
			stmt = conn.createStatement();
			stmt.execute(qu);
			return true;
		} catch(SQLException ex){
			JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
			return false;
		} finally {
			
		}
	}
	
	public boolean deleteBook(Book book){
		try {
			String deleteStmt = "DELETE FROM BOOKS WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(deleteStmt);
			stmt.setString(1, book.getId());
			int res = stmt.executeUpdate();
			System.out.println(res);
			if(res == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteMember(Member member) {
		try {
			String deleteStmt = "DELETE FROM MEMBER WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(deleteStmt);
			stmt.setString(1, member.getId());
			int res = stmt.executeUpdate();
			System.out.println(res);
			if(res == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isBookAlreadyIssued(Book book) {
		try {
			String checkedBook = "SELECT COUNT(*) FROM ISSUE WHERE bookID=?";
			PreparedStatement stmt = conn.prepareStatement(checkedBook);
			stmt.setString(1, book.getId());
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				int count = rs.getInt(1);
				return (count>0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateBook(Book book){
		String update = "UPDATE BOOKS SET title=?, author=?, publisher=? WHERE id=?";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(update);
			stmt.setString(1, book.getTitle());
			stmt.setString(2, book.getAuthor());
			stmt.setString(3, book.getPublisher());
			stmt.setString(4, book.getId());
			int res = stmt.executeUpdate();
			return (res>0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateMember(Member member) {
		String update = "UPDATE MEMBER SET name=?, mobile=?, email=? WHERE id=?";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(update);
			stmt.setString(1, member.getName());
			stmt.setString(2, member.getMobile());
			stmt.setString(3, member.getEmail());
			stmt.setString(4, member.getId());
			int res = stmt.executeUpdate();
			return (res>0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		
	}
	
	public ObservableList<PieChart.Data> getBookGraphStats(){
		ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
		try {
			String qu1 = "SELECT COUNT(*) FROM BOOKS";
			String qu2 = "SELECT COUNT(*) FROM ISSUE";
			ResultSet rs = execQuery(qu1);
			if(rs.next()) {
				int count = rs.getInt(1);
				data.add(new PieChart.Data("Total Books (" + count + ")", count));
			}
			rs = execQuery(qu2);
			if(rs.next()) {
				int count = rs.getInt(1);
				data.add(new PieChart.Data("Issued Books (" + count + ")", count));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public ObservableList<PieChart.Data> getMemberGraphStats(){
		ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
		try {
			String qu1 = "SELECT COUNT(*) FROM MEMBER";
			String qu2 = "SELECT COUNT(DISTINCT memberID) FROM ISSUE";
			ResultSet rs = execQuery(qu1);
			if(rs.next()) {
				int count = rs.getInt(1);
				data.add(new PieChart.Data("Total Member (" + count + ")", count));
			}
			rs = execQuery(qu2);
			if(rs.next()) {
				int count = rs.getInt(1);
				data.add(new PieChart.Data("Member with books (" + count + ")", count));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
}
