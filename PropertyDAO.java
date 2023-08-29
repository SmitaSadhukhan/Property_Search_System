package com.amdocs.propertysearch.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.amdocs.propertysearch.model.Property;

import java.sql.*;

public class PropertyDAO {
	Connection con;
	public PropertyDAO() {
	
	}
	
	public int addProperty(Property p) {
		try {
			DBConnection dbObj=new DBConnection();
			con=dbObj.getConnection();
			String query="INSERT INTO PROPERTIES2 VALUES(?,?,?,?,?,?,?,?,?)";
			PreparedStatement pst=con.prepareStatement(query);
			pst.setInt(1, p.getPropertyId());
			pst.setString(2, p.getNoOfRooms());
			pst.setDouble(3, p.getAreaInSqft());
			pst.setInt(4, p.getFloorNo());
			pst.setString(5, p.getCity());
			pst.setString(6, p.getState());
			pst.setDouble(7, p.getCost());
			pst.setString(8, p.getOwnerName());
			pst.setString(9, p.getOwnerContactNo());
			
			return pst.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public boolean updatePropertyCost(int propertyId,double newCost) {
		try {
			DBConnection dbObj=new DBConnection();
			con=dbObj.getConnection();
			String query="UPDATE PROPERTIES2 SET COST=? WHERE PROPERTY_ID=?";
			PreparedStatement pst=con.prepareStatement(query);
			pst.setDouble(1, newCost);
			pst.setInt(2, propertyId);
			
			return pst.executeUpdate()>0;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	public int deleteProperty(int propertyId) {
		try {
			DBConnection dbObj=new DBConnection();
			con=dbObj.getConnection();
			String query="DELETE FROM PROPERTIES2 WHERE PROPERTY_ID=?";
			PreparedStatement pst=con.prepareStatement(query);
			pst.setInt(1, propertyId);
			return pst.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return propertyId;
	}
	
	public ArrayList<Property> searchByCity(String city){
		ArrayList<Property> matchingProperties=new ArrayList<Property>();
		try {
			DBConnection dbObj=new DBConnection();
			con=dbObj.getConnection();
			String query="SELECT * FROM PROPERTIES2 WHERE CITY=?";
			PreparedStatement pst=con.prepareStatement(query);
			pst.setString(1, city);
			
			ResultSet rs= pst.executeQuery();
			while(rs.next()) {
				Property p=createPropertyFromResultSet(rs);
				matchingProperties.add(p);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return matchingProperties;
	}
	public ArrayList<Property> showAllProperties(){
		ArrayList<Property> allProperties=new ArrayList<Property>();
		try {
			DBConnection dbObj=new DBConnection();
			con=dbObj.getConnection();
			String query="SELECT * FROM PROPERTIES2";
			Statement st=con.createStatement();
			
			ResultSet rs= st.executeQuery(query);
			while(rs.next()) {
				Property p=createPropertyFromResultSet(rs);
				allProperties.add(p);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return allProperties;
	}
	public ArrayList<Property> searchByCost(double minCost,double maxCost){
		ArrayList<Property> matchingProperties=new ArrayList<Property>();
		try {
			DBConnection dbObj=new DBConnection();
			con=dbObj.getConnection();
			String query="SELECT * FROM PROPERTIES2 WHERE COST BETWEEN ? AND ?";
			PreparedStatement pst=con.prepareStatement(query);
			pst.setDouble(1, minCost);
			pst.setDouble(2, maxCost);
			
			ResultSet rs= pst.executeQuery();
			while(rs.next()) {
				Property p=createPropertyFromResultSet(rs);
				matchingProperties.add(p);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return matchingProperties;
	}
	public ArrayList<Property> searchByNoOfRoomsAndCity(String noOfRooms, String city){
		ArrayList<Property> matchingProperties=new ArrayList<Property>();
		try {
			DBConnection dbObj=new DBConnection();
			con=dbObj.getConnection();
			String query="SELECT * FROM PROPERTIES2 WHERE NO_OF_ROOMS=? AND CITY=?";
			PreparedStatement pst=con.prepareStatement(query);
			pst.setString(1, noOfRooms);
			pst.setString(2, city);
			
			ResultSet rs= pst.executeQuery();
			while(rs.next()) {
				Property p=createPropertyFromResultSet(rs);
				matchingProperties.add(p);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return matchingProperties;
	}
	private Property createPropertyFromResultSet(ResultSet rs) throws SQLException{
		int propertyId=rs.getInt("PROPERTY_ID");
		String noOfRooms=rs.getString("NO_OF_ROOMS");
		double areaInSqft=rs.getDouble("AREA_IN_SQFT");
		int floorNo=rs.getInt("FLOOR_NO");
		String city=rs.getString("CITY");
		String state=rs.getString("STATE");
		double cost=rs.getDouble("COST");
		String ownerName=rs.getString("OWNER_NAME");
		String ownerContactNo=rs.getString("OWNER_CONTACT_NO");
		
		return new Property(propertyId, noOfRooms, areaInSqft, floorNo, city, state, cost, ownerName, ownerContactNo);
	}
}
