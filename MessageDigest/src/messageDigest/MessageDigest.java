package messageDigest;

import java.util.Scanner;

public class MessageDigest {
	public static void main(String[] args){
		
		System.out.println("Welcome! Which kind of function do you want?");
		System.out.println("MD5: press '1'");
		System.out.println("SHA: press '2'"); 
		System.out.println("QUIT: press '0'"); 
		
		Scanner input = new Scanner(System.in);
		
		int choice = input.nextInt();

			switch(choice){
			case 1:
				System.out.println("Please enter the plaintext:");
				Scanner input1 = new Scanner(System.in);
				MD5 plaintext1 = new MD5();
				String in1 = input1.nextLine();
				String result1 = plaintext1.getMD5(in1.getBytes());
				System.out.println("In the function of MD5 the result is");
				System.out.println(result1);
				break;
				
			case 2:
				System.out.println("Please enter the plaintext:");
				Scanner input2 = new Scanner(System.in);
				SHA plaintext2 = new SHA();
				String in2 = input2.nextLine();
				String result2 = plaintext2.getSHA(in2.getBytes());
				System.out.println("In the function of MD5 the result is");
				System.out.println(result2);
				break;				
			}
			System.out.println("^^ Thank you for using ^^");
			input.close();
	}
}
