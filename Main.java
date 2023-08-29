package com.amdocs.propertysearch.main;

import java.util.ArrayList;
import java.util.Scanner;

import com.admocs.propertySearch.exception.PropertyNotFoundException;
import com.amdocs.propertysearch.dao.PropertyDAO;
import com.amdocs.propertysearch.model.Property;



public class Main {
    public static void main(String[] args) {
        try {
        	PropertyDAO propertyDAO=new PropertyDAO();
            Scanner sc = new Scanner(System.in);
            boolean exit=false;
            while (!exit) {
                System.out.println("\n-- Property Search System Menu --");
                System.out.println("1. Add new property");
                System.out.println("2. Update property cost");
                System.out.println("3. Delete property");
                System.out.println("4. Find properties by city");
                System.out.println("5. View all properties");
                System.out.println("6. Find properties by cost range");
                System.out.println("7. Find properties by no of rooms and city");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine(); 

                switch (choice) {
                    case 1:
                        addProperty(propertyDAO, sc);
                        break;
                    case 2:
                        updatePropertyCost(propertyDAO, sc);
                        break;
                    case 3:
                        deleteProperty(propertyDAO, sc);
                        break;
                    case 4:
                        searchByCity(propertyDAO, sc);
                        break;
                    case 5:
                        showAllProperties(propertyDAO);
                        break;
                    case 6:
                        searchByCost(propertyDAO, sc);
                        break;
                    case 7:
                        searchByNoOfRoomsAndCity(propertyDAO, sc);
                        break;
                    case 8:
                        exit=true;
                        System.out.println("Exiting Property Search System.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                        break;
                }
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addProperty(PropertyDAO propertyDAO, Scanner sc) {
        System.out.print("Enter property ID: ");
        int propertyId = sc.nextInt();
        sc.nextLine(); 

        System.out.print("Enter no of rooms (e.g. 1BHK, 2BHK): ");
        String noOfRooms = sc.nextLine().toUpperCase();

        System.out.print("Enter area in sqft: ");
        double areaInSqft = sc.nextDouble();
        sc.nextLine(); 

        System.out.print("Enter floor number: ");
        int floorNo = sc.nextInt();
        sc.nextLine(); 

        System.out.print("Enter city: ");
        String city = sc.nextLine();

        System.out.print("Enter state: ");
        String state = sc.nextLine();

        System.out.print("Enter cost: ");
        double cost = sc.nextDouble();
        sc.nextLine(); 

        System.out.print("Enter owner name: ");
        String ownerName = sc.nextLine();

        System.out.print("Enter owner contact number: ");
        String ownerContactNo = sc.nextLine();

        Property newProperty = new Property(propertyId, noOfRooms, areaInSqft, floorNo, city, state, cost, ownerName, ownerContactNo);

        int addedPropertyId = propertyDAO.addProperty(newProperty);
        if (addedPropertyId != -1) {
            System.out.println("Property added successfully");
        } else {
            System.out.println("Failed to add property.");
        }
    }

    private static void updatePropertyCost(PropertyDAO propertyDAO, Scanner scanner) {
        System.out.print("Enter property ID to update cost: ");
        int propertyId = scanner.nextInt();
        System.out.print("Enter new cost: ");
        double newCost = scanner.nextDouble();

        boolean success = propertyDAO.updatePropertyCost(propertyId, newCost);
        if (success) {
            System.out.println("Property cost updated successfully.");
        } else {
            System.out.println("Failed to update property cost.");
        }
    }

    private static void deleteProperty(PropertyDAO propertyDAO, Scanner scanner) throws PropertyNotFoundException {
        System.out.print("Enter property ID to delete: ");
        int propertyId = scanner.nextInt();
        int c=0;
        c = propertyDAO.deleteProperty(propertyId);
        if (c>0) {
            System.out.println("Property deleted successfully.");
        } else {
        	throw new PropertyNotFoundException();
        	
        }
    }

    private static void searchByCity(PropertyDAO propertyDAO, Scanner scanner) throws PropertyNotFoundException {
        System.out.print("Enter city to search for properties: ");
        String city = scanner.nextLine();

        ArrayList<Property> properties = propertyDAO.searchByCity(city);
        if (!properties.isEmpty()) {
            System.out.println("Properties in " + city + ":");
            for (Property property : properties) {
                System.out.println(property);
            }
        } else {
        	throw new PropertyNotFoundException();
           
        }
    }

    private static void showAllProperties(PropertyDAO propertyDAO) {
        ArrayList<Property> properties = propertyDAO.showAllProperties();
        if (!properties.isEmpty()) {
            System.out.println("All Properties:");
            for (Property property : properties) {
                System.out.println(property);
            }
        } else {
            System.out.println("No properties found.");
        }
    }

    private static void searchByCost(PropertyDAO propertyDAO, Scanner scanner) {
        System.out.print("Enter minimum cost: ");
        double minCost = scanner.nextDouble();
        System.out.print("Enter maximum cost: ");
        double maxCost = scanner.nextDouble();

        ArrayList<Property> properties = propertyDAO.searchByCost(minCost, maxCost);
        if (!properties.isEmpty()) {
            System.out.println("Properties in cost range " + minCost + " - " + maxCost + ":");
            for (Property property : properties) {
                System.out.println(property);
            }
        } else {
            System.out.println("No properties found in the specified cost range.");
        }
    }

    private static void searchByNoOfRoomsAndCity(PropertyDAO propertyDAO, Scanner sc) {
        System.out.print("Enter no of rooms (e.g. 1BHK, 2BHK): ");
        String noOfRooms = sc.nextLine().toUpperCase();
        
        System.out.print("Enter city: ");
        String city = sc.nextLine();

        ArrayList<Property> properties = propertyDAO.searchByNoOfRoomsAndCity(noOfRooms, city);
        if (!properties.isEmpty()) {
            System.out.println("Properties with " + noOfRooms + " rooms in " + city + ":");
            for (Property property : properties) {
                System.out.println(property);
            }
        } else {
            System.out.println("No properties found with the specified criteria.");
        }
    }
}