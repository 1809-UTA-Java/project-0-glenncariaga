package com.revature.BankingApp.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
		return "Success";
	}
}