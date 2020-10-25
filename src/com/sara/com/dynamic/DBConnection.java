package com.sara.com.dynamic;

import java.sql.*; 

public class DBConnection {
	
	public Connection getConnection(){
		Connection connection = null;
		System.out.println("Connection called");
		try{
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/trans","root","root"); 
		} catch(ClassNotFoundException e){
			e.printStackTrace();
		} catch(SQLException e){
			e.printStackTrace();
		}
		return connection;
	}

}