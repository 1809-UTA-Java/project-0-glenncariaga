package com.revature.BankingApp.controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Services {
	public static String cliInput(String msg) {
		Scanner sc = new Scanner(System.in);
		System.out.print(msg);
		String input = sc.nextLine();
		
		sc.close();
		return input;
	}
	
	public static String writeToFile(Object obj, String toFile) {
		try(ObjectOutputStream oos = 
				new ObjectOutputStream(
						new FileOutputStream(toFile))){
			oos.writeObject(obj);
			return "Success";
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
		return "Error has occurred";
	}
	
	public static <T> ArrayList<T> readToObj(String fromFile) {
		ArrayList<T> obj = null;
		try(ObjectInputStream ois = 
				new ObjectInputStream( 
						new FileInputStream(fromFile))){
			obj =  (ArrayList<T>) ois.readObject();
		}catch(IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
}