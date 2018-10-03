/*
This is a restaurant system program which is having three classes.

Password class is used to check whether the user input the correct password and the first time of running this programme.
If it is the first time of running this programme, it would ask user to create new user account and password first. And then ask user to login.
If it is not the first time of running this programme, it would ask user to login directly.
Also, I assume that there will have more than one worker in the restaurant, which means number groups of user account and password is more the one.
Therefore, this class can also work for checking password when there are many groups of user account and password which are created before.
The passwprd should more than 5-digi or 5-character, and the password cannot equal to the user account.

ChangePW class is usde to help the user to change password or add new user.
When the user need to change the password, he should input the correct user account and password.
However, I assume that the user account and password of every user should be unique, which means there are no same user acount and same password.

Restaurant class is also the main programme of this programme.
I assume that there are 12 desks in the restaurant. Desk number 1-8 is the small desk that only available for 2-4 customers. Desk number 9-12 is the large desk that only availabe for 5-8 customers.
And there are 10 item of the menu with price can be ordered. There are 7 items of food and 3 item of drink
Item of food:
	Caesar Salad $39.9
	Tomato and Mozzarella $49.9
	Spicy Chicken Wings $49.9
	Fish and Chips $79.9
	Sirloin Steak $119.9
	CheeseBurger with Chips $99.9
	Tiramisu $59.9
Item of drink:
	Soft Drinks $19.9
	Beer $24.9
	Wine $29.9
With the using of menu bar, the user can use the ChangePW class, show the current income, the list of transaction, or exit the programme.
The user can also logout. No matter logout or exit the programme, all data will no loss.
The user can reboot the system, all the data that saved for the Restaurant class will be reset. However, the password will still here.
Moreover, all the data that saved for the Restaurant class will be reset when the day changed.

BY: LEO KONG CHI WAI 11650109
DATE: 7-5-2013
*/

import java.awt.event.*;
import javax.swing.*;
//import javax.swing.JOptionPane.*;
import java.util.*;
import java.util.Date;
import java.io.*;
//import java.sql.*;
import java.awt.*;
import java.text.*;
//import java.text.DecimalFormat;

public class TermProject
{		static Password pw = new Password();  //using the class for asking user to login
		static ChangePW cpw = new ChangePW();  //using the class for helping user to add user, to change pw or to delete user
	    static Restaurant rest = new Restaurant();  //using the class for showing the main system and the main function of restaurant system is performed here
		public static void main (String[] args)
	{

		pw.setTitle("System Login");  //the window setting
		pw.setBounds(200,200,400,150);
		pw.setVisible(true); //when running this programme, the class will be shown first
		pw.setResizable(false);
		
		cpw.setTitle("Password Management");  //the window setting
		cpw.setBounds(200,200,400,200);
		cpw.setResizable(false);
		
		rest.setTitle("LeoKong's Restaurant System"); //the window setting
		rest.setBounds(300,300,1024,768);
		rest.setResizable(false);
		
	}
}

class Password extends JFrame implements ActionListener
{
	Boolean first;
	Boolean correct;
	JLabel acLabel = new JLabel ("User Account: ");
	JTextField acField = new JTextField(20);
	JLabel pwLabel = new JLabel ("Password: ");
	JPasswordField pwField = new JPasswordField(20);
	JButton loginButton = new JButton ("Login");
	JButton createButton = new JButton ("Create");
	JPanel pwPanel = new JPanel();
	JPanel loginPanel = new JPanel();
	String pwfilename = "pw.bin";
	String corpw;
	String corac;
	String inputac;
	String inputpw;
	
	public Password()
	{
		setLayout (new BorderLayout()); //setting of the layout
			pwPanel.setLayout(new GridLayout(2,4)); //there are two panels
			loginPanel.setLayout(new FlowLayout());
		add(loginPanel, BorderLayout.SOUTH);
		add(pwPanel, BorderLayout.CENTER);
		
		pwPanel.add(acLabel);
		pwPanel.add(acField);
		pwPanel.add(pwLabel);
		pwPanel.add(pwField);
			pwField.setEchoChar('*');
		
		loginPanel.add(loginButton);
			loginButton.addActionListener(this);
		loginPanel.add(createButton);
			createButton.addActionListener(this);
			
		addWindowListener //when clicking the 'x' of top right corner of the windows, the programme will shut down
		(
			new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				}
			}
		);
		loadpw(); //to load the pw from the file, for checking whether it is the first time or not.
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == loginButton)
		{
			if (first)
				JOptionPane.showMessageDialog(null, "This is the first time you use this programme.\nPlease created a new useer account and password. ","System Message", JOptionPane.INFORMATION_MESSAGE);
			else
			{
				loadpw(); //to load first the pw again for checking
				inputac = acField.getText();
				inputpw = pwField.getText();
				checkallpw(); //to load and check all the pw the find in the file
				if (!correct)
				{
					JOptionPane.showMessageDialog(null, "Wrong user account or password. ","System Message", JOptionPane.ERROR_MESSAGE);
				}
				else 
				{
					this.setVisible(false);
					TermProject.rest.setVisible(true);
				}
			}
			acField.setText(""); //to reset the textfield
			pwField.setText("");
		}
		if (e.getSource() == createButton)
		{
			if (first) //only accept that to create pw when it is the first time to run this programme
			{
				inputac = acField.getText();
				inputpw = pwField.getText();
				if (inputpw.length() == 0 || inputac.length()==0) //if nothing input, show error
					JOptionPane.showMessageDialog(null, "The user account or password cannot be blank. ","System Message", JOptionPane.ERROR_MESSAGE);
				else if (inputpw.length() < 5)
				{
					JOptionPane.showMessageDialog(null, "The password should be more than 5 digi or character. ","System Message", JOptionPane.ERROR_MESSAGE);
					pwField.setText("");
				}
				else if (inputac.compareTo(inputpw) == 0) //if ac equals pw, show error
				{
					JOptionPane.showMessageDialog(null, "The user account and the password cannot be the same. ","System Message", JOptionPane.ERROR_MESSAGE);
					acField.setText("");
					pwField.setText("");
				}
				else //if no error, create the pw
				{
					createpw();
					acField.setText("");
					pwField.setText("");
					JOptionPane.showMessageDialog(null, "The password is created ","System Message", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else //if it is not the first time, show error
			{
				JOptionPane.showMessageDialog(null, "There has password already.\nYou cannot created a new useer account or password. ","System Message", JOptionPane.ERROR_MESSAGE);
				acField.setText("");
				pwField.setText("");
			}
		}
	}
	
	void loadpw()
	{
		Boolean read;
		Scanner pw = null;
		try //if it can read the pw file
		{
			pw = new Scanner(new FileReader(pwfilename));
			read = true;
			first = false;
		}
		catch (FileNotFoundException e) //if it cannot read the pw file
		{
			JOptionPane.showMessageDialog(null, "This is the first time you use this programme.\nPlease created a new useer account and password. ","System Message", JOptionPane.INFORMATION_MESSAGE);
			read = false;
			first = true;
		}
		
		//starting to read
		if (read)
		{
			corac = pw.nextLine();
			corpw = pw.nextLine();
			pw.close();
		}	
	}
	void checkallpw()
	{
		Boolean read;
		Scanner pw = null;
		try
		{
			pw = new Scanner(new FileReader(pwfilename));
			read = true;
		}
		catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "This is the first time you use this programme.\nPlease created a new useer account and password. ","System Message", JOptionPane.INFORMATION_MESSAGE);
			read = false;
		}
		
		if (read)
		{
			while (inputac.compareTo(corac)!=0 || inputpw.compareTo(corpw)!=0) //if the pw is not correct, then check the next group of pw, util all pw is checked
			{
				if (pw.hasNextLine())
				{
					corac = pw.nextLine();
					corpw = pw.nextLine();
				}
				else //stop when no mroe group of pw
					break;
			}
				
				if (inputac.compareTo(corac)==0 && inputpw.compareTo(corpw)==0) //if the pw is correct, allow user to login
					correct = true;
				if(inputac.compareTo(corac)!=0 || inputpw.compareTo(corpw)!=0) //if the pw is wrong, not allow to login
					correct = false;
			pw.close();
		}
	}
	
	void createpw()
	{
		PrintWriter pw = null;
		try //to creat a new pw file
		{
			pw = new PrintWriter(pwfilename);
		}
		catch (FileNotFoundException x)
		{
		}
		// to start to print record
		pw.print(inputac+"\n"); //print the ac
		pw.print(inputpw+"\n"); //print the pw
		
		pw.close();	
		first = false; //no more first time for running the programme
	}
}

class ChangePW extends JFrame implements ActionListener
{
	JLabel acLabel = new JLabel ("User Account: ");
	JTextField acField = new JTextField(20);
	JLabel pwLabel = new JLabel ("Password: ");
	JPasswordField pwField = new JPasswordField(20);
	JLabel nacLabel = new JLabel ("New User Account: ");
	JTextField nacField = new JTextField(20);
	JLabel npwLabel = new JLabel ("New Password: ");
	JPasswordField npwField = new JPasswordField(20);
	JButton cancelButton = new JButton ("Cancel");
	JButton changeButton = new JButton ("Change");
	JButton anuButton = new JButton ("Add New User");
	JButton delButton = new JButton ("Delete User");
	JPanel pwPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	String pwfilename = "pw.bin";
	String corpw;
	String corac;
	String inputac;
	String inputpw;
	String oac;
	String opw;
	
	public ChangePW()
	{
		setLayout (new BorderLayout()); //setting the layout
			pwPanel.setLayout(new GridLayout(4,4));
			buttonPanel.setLayout(new FlowLayout());
		add(buttonPanel, BorderLayout.SOUTH);
		add(pwPanel, BorderLayout.CENTER);
		
		pwPanel.add(acLabel);
		pwPanel.add(acField);
		pwPanel.add(pwLabel);
		pwPanel.add(pwField);
		pwPanel.add(nacLabel);
		pwPanel.add(nacField);
		pwPanel.add(npwLabel);
		pwPanel.add(npwField);
			pwField.setEchoChar('*');
			npwField.setEchoChar('*');
		
		buttonPanel.add(anuButton);
			anuButton.addActionListener(this);
		buttonPanel.add(changeButton);
			changeButton.addActionListener(this);
		buttonPanel.add(delButton);
			delButton.addActionListener(this);
		buttonPanel.add(cancelButton);
			cancelButton.addActionListener(this);
		addWindowListener
		(
			new WindowAdapter() //when closing the windows, return to the login stage
			{
				public void windowClosing(WindowEvent e)
				{
					TermProject.cpw.setVisible(false);
					TermProject.pw.setVisible(true);
				}
			}
		);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == cancelButton)//return to the login stage
		{
			this.setVisible(false);
			TermProject.pw.setVisible(true);
		}
		if (e.getSource() == changeButton)
		{
			Boolean correct = false;
			Boolean read = false;
			Scanner rpw = null;
			PrintWriter pw = null;
			inputac = nacField.getText();
			inputpw = npwField.getText();
			oac = acField.getText();
			opw = pwField.getText();
			String temppwfilename = "pwtemp.bin";
			String temppw = "";
			Boolean same = false;
			String checking = "";
			if (inputpw.length() == 0) //no input, show error
				JOptionPane.showMessageDialog(null, "The new password cannot be blank. ","System Message", JOptionPane.ERROR_MESSAGE);
			else if (inputpw.length() < 5) //new pw is less than 5-digi or 5-char, show error
			{
				JOptionPane.showMessageDialog(null, "The password should be more than 5 digi or character. ","System Message", JOptionPane.ERROR_MESSAGE);
				pwField.setText("");
			}
			else if (opw.length() == 0 || oac.length()==0) //user show enter their current ac and pw, otherwise show error
				JOptionPane.showMessageDialog(null, "The user account or password cannot be blank. ","System Message", JOptionPane.ERROR_MESSAGE);
				
			else //no error, then check if the new pw equals othe pw of user name
			{
				try
				{
					rpw = new Scanner(new FileReader(pwfilename));
					read = true;
				}
				catch (FileNotFoundException x)
				{
				}
				if (read)
				{
					while(rpw.hasNextLine())
					{
						checking = rpw.nextLine();
						if (checking.compareTo(inputpw) == 0) //if the new pw equals the pw or ac of other user
							same = true; //set it to true
					}
					rpw.close();			
				}
				if (!same) //if nothing is not the same
				{
					loadpwmod(); //load the pw from the file
					correct = checkallpwmod(); //check if the original pw is correct
					if (correct) //if the original pw is correct
					{
						deluser(); //delete the user first, and then create the account with using the original ac and new pw
						File temp = new File(temppwfilename);
						File nontemp = new File(pwfilename);
						nontemp.delete(); //to delete the original pw file
						temp.renameTo(nontemp); //to rename the temp file that bring by the deluser()
						try
						{
							rpw = new Scanner(new FileReader(pwfilename));
							read = true;
						}
						catch (FileNotFoundException x)
						{
						}
						if (read)
						{
							if (rpw.hasNextLine())
								temppw = rpw.useDelimiter("\\Z").next(); //read in all the pw file including the "\n"
							rpw.close();
						}
				
						try
						{
							pw = new PrintWriter(pwfilename);
						}
						catch (FileNotFoundException x)
						{
						}
						// to start to print record
						if (temppw!="")
							pw.print(temppw+"\n"+oac+"\n"+inputpw+"\n"); //print out the read in pw and the original ac and the new pw
						else
							pw.print(oac+"\n"+inputpw+"\n");
						pw.close();	
						JOptionPane.showMessageDialog(null, "The password is changed. ","System Message", JOptionPane.INFORMATION_MESSAGE);
						this.setVisible(false);
						TermProject.pw.setVisible(true);
					}
					else
						JOptionPane.showMessageDialog(null, "The original user account or password is wrong. ","System Message", JOptionPane.ERROR_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(null, "You cannot use other user information to be yours\nYour user new password cannot be the same as the account and password of other.","System Message", JOptionPane.ERROR_MESSAGE);
							
			}
		}
		if (e.getSource() == delButton) //to delete a user
		{
			Boolean correct = false;;
			oac = acField.getText();
			opw = pwField.getText();
			String temppwfilename = "pwtemp.bin";
			
			if (opw.length() == 0 || oac.length()==0) //nothing enterd, show error
				JOptionPane.showMessageDialog(null, "The user account or password cannot be blank. ","System Message", JOptionPane.ERROR_MESSAGE);
				
			else
			{
				loadpwmod();
				correct = checkallpwmod();
				if (correct) //if the checking is ok that means the ac is found and the pw is correct, start to delete user
				{
					deluser();
					File temp = new File(temppwfilename);
					File nontemp = new File(pwfilename);
					nontemp.delete();
					temp.renameTo(nontemp);	//same as changing pw, but nothing will be add after the delete process
					JOptionPane.showMessageDialog(null, "The user is deleted. ","System Message", JOptionPane.INFORMATION_MESSAGE);
					this.setVisible(false);
					TermProject.pw.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(null, "The original user account or password is wrong. ","System Message", JOptionPane.ERROR_MESSAGE);					
			}
		}
		
		if (e.getSource() == anuButton) //to create new user ac
		{
			inputac = nacField.getText();
			inputpw = npwField.getText();
			Scanner rpw = null;
			Boolean read = false;
			Boolean same = false;
			String temppw = "";
			String checking = "";
			if (inputpw.length() == 0 || inputac.length()==0) //nothing entered, show error
				JOptionPane.showMessageDialog(null, "The user account or password cannot be blank. ","System Message", JOptionPane.ERROR_MESSAGE);
			else if (inputpw.length() < 5) //pw is less than 5-digi, show error
			{
				JOptionPane.showMessageDialog(null, "The password should be more than 5 digi or character. ","System Message", JOptionPane.ERROR_MESSAGE);
				pwField.setText("");
			}
			else if (inputac.compareTo(inputpw) == 0) //pw is equal to the ac, show error
			{
				JOptionPane.showMessageDialog(null, "The user account and the password cannot be the same. ","System Message", JOptionPane.ERROR_MESSAGE);
				acField.setText("");
				pwField.setText("");
			}
			else //if nothing wrong
			{
				try
				{
					rpw = new Scanner(new FileReader(pwfilename));
					read = true;
				}
				catch (FileNotFoundException x)
				{
				}
				if (read)
				{
					while(rpw.hasNextLine())
					{
						checking = rpw.nextLine();
						if (checking.compareTo(inputac) == 0 || checking.compareTo(inputpw) == 0)
							same = true; //check if there is any same user ac or pw
					}
					rpw.close();			
				}
				
				if (!same) //if nothing same
				{
					try
					{
						rpw = new Scanner(new FileReader(pwfilename));
						read = true;
					}
					catch (FileNotFoundException x)
					{
					}
					if (read)
					{
						temppw = rpw.useDelimiter("\\Z").next(); //read in all file including the "\n"
						rpw.close();
					}
					
					PrintWriter pw = null;
					try
					{
						pw = new PrintWriter(pwfilename);
					}
					catch (FileNotFoundException x)
					{
					}
					// to start to print record
					pw.print(temppw+"\n"+inputac+"\n"+inputpw+"\n"); //print all ac and pw, then print the new ac and pw	
					pw.close();	
					JOptionPane.showMessageDialog(null, "The new user account is created. ","System Message", JOptionPane.INFORMATION_MESSAGE);
					this.setVisible(false);
					TermProject.pw.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(null, "You cannot use other user information to be yours\nYour user account cannot be the same as the account and password of other. \nYour password also.","System Message", JOptionPane.ERROR_MESSAGE);
			}
		}
		//reset all the text field
		acField.setText("");
		nacField.setText("");
		pwField.setText("");
		npwField.setText("");

	}
	void loadpwmod() //quite similar to the loadpw() from the Password class, but it will not check the first time
	{
		Boolean read;
		Scanner pw = null;
		try
		{
			pw = new Scanner(new FileReader(pwfilename));
			read = true;
		}
		catch (FileNotFoundException e)
		{
			read = false;
		}
		
		if (read)
		{
			corac = pw.nextLine();
			corpw = pw.nextLine();
			pw.close();
		}	
	}
	
	Boolean checkallpwmod() //quite similar to the checkall() from the Password class, but this will return a boolean
	{
		Boolean cor = false;
		Boolean read;
		Scanner pw = null;
		try
		{
			pw = new Scanner(new FileReader(pwfilename));
			read = true;
		}
		catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "This is the first time you use this programme.\nPlease created a new useer account and password. ","System Message", JOptionPane.INFORMATION_MESSAGE);
			read = false;
		}
		
		if (read)
		{
			while (oac.compareTo(corac)!=0 || opw.compareTo(corpw)!=0)
			{
				if (pw.hasNextLine())
				{
					corac = pw.nextLine();
					corpw = pw.nextLine();
				}
				else
				{
					break;
				}
			}
				
				if (oac.compareTo(corac)==0 && opw.compareTo(corpw)==0)
					cor = true;
				if(oac.compareTo(corac)!=0 || opw.compareTo(corpw)!=0)
					cor = false;
			pw.close();
		}
		return cor;
	}
	
	void deluser()
	{
		Boolean read = false;
		Boolean deluser = false;
		Boolean delpw = false;
		Scanner rpw = null;
		PrintWriter pw = null;
		String temppwfilename = "pwtemp.bin";
		String temppw = "";
		try
		{
			rpw = new Scanner(new FileReader(pwfilename));
			pw = new PrintWriter(temppwfilename);
			read = true;					
		}
		catch (FileNotFoundException x)
		{
		}

		if (read)
		{
			int count = 0;
			while(rpw.hasNextLine()) 
			{
				corac = rpw.nextLine();
   			 	String tempcorac = corac.trim();
   		 		if(tempcorac.compareTo(oac) == 0 && count < 1) //read all user ac and pw, except the one is equal to the entered ac
   		 		{
   		 			count++;
   		 			deluser = true;
   		 			continue;
				}
   		 		pw.print(corac+"\n"); //print out what its read
			}
			rpw.close();
			pw.close();
			
			if (deluser)
			{
				File temp = new File(temppwfilename);
				File nontemp = new File(pwfilename);
				nontemp.delete();
				temp.renameTo(nontemp);
						
				try
				{
					rpw = new Scanner(new FileReader(pwfilename));
					pw = new PrintWriter(temppwfilename);
					read = true;
				}
				catch (FileNotFoundException x)
				{
				}
		
				if (read)
				{
					count = 0;
					while(rpw.hasNextLine()) 
					{
						corpw = rpw.nextLine();
   		 				String tempcorpw = corpw.trim();
		   		 		if(tempcorpw.compareTo(opw) == 0 && count < 1) //read all user ac and pw, except the one is equal to the entered pw
    					{
	 						count++;
	 						delpw = true;
		   	 				continue;
						}
		   		 		pw.print(corpw+"\n"); //print out what its read
					}
					rpw.close();
					pw.close();
				}

			}
					

		}
	}
}

class Restaurant extends JFrame implements ActionListener //the main programme of the restaurant
{
	Date today = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
	String filename = "record"+dateFormat.format(today)+".DAT";
	String listfilename = "listrecord"+dateFormat.format(today)+".DAT";
	String deskfilename[] = new String [13];
	String list = "List of transaction";
	
	JPanel logoutPanel = new JPanel();
		JButton logoutButton = new JButton("Logout");
	JPanel deskPanel = new JPanel();
		JTextArea deskDisplay[] = new JTextArea[13];
		JScrollPane scrollingResult[] = new JScrollPane[13]; //to enable the using of scrolling bar of the text area
		Boolean ok[] = new Boolean[13]; 
		double billdrink[] = new double [13];
		double billfood[] = new double [13];
		double bill[] = new double[13];
	JPanel inputPanel = new JPanel();
		JLabel deskno = new JLabel("Desk Number: ");
		JComboBox noOfDesk = new JComboBox();
		JLabel peopleno = new JLabel ("Number of customer: ");
		JComboBox noOfPeople = new JComboBox();
		JButton reserveButton = new JButton("Reserve");
		JLabel order = new JLabel("Order: ");
		JComboBox orderDish = new JComboBox();
		JLabel quantity = new JLabel("Quantity: ");
		JTextField quan = new JTextField(15);
		JButton orderButton = new JButton("Order");
		JButton billButton = new JButton("Bill");
		JLabel invi = new JLabel("");
		JLabel invi2 = new JLabel("");
		JLabel invi3 = new JLabel("");
		JLabel invi4 = new JLabel("");
		
		double totaldrink = 0;
		double totalfood = 0;
		double total = 0;
		int totalpeople = 0;
		int billcount = 0;
		int dishcount[] = new int [10];
		
		
		
		public Restaurant()
		{
			//setting of the layout and the interface that using menu bar
			for(int i=1; i<13; i++)
				deskfilename[i] = "deskrecord"+i+dateFormat.format(today)+".DAT";
			for(int i=0; i<10; i++)
				dishcount[i]=0;
			JMenuBar mnuBar = new JMenuBar();
			setJMenuBar(mnuBar);
			JMenu mnuFile = new JMenu("Management Function");
				mnuFile.setMnemonic(KeyEvent.VK_F);
				mnuFile.setDisplayedMnemonicIndex(11);
				mnuBar.add(mnuFile);				
				JMenuItem mnuFileTotal = new JMenuItem("Current Received Income & Total Number Customers");
					mnuFileTotal.setMnemonic(KeyEvent.VK_C);
					mnuFileTotal.setDisplayedMnemonicIndex(0);
					mnuFile.add(mnuFileTotal);
					mnuFileTotal.setActionCommand("Current");
					mnuFileTotal.addActionListener(this);
				JMenuItem mnuFileOrdered = new JMenuItem("Current Ordered Item");
					mnuFileOrdered.setMnemonic(KeyEvent.VK_O);
					mnuFileOrdered.setDisplayedMnemonicIndex(8);
					mnuFile.add(mnuFileOrdered);
					mnuFileOrdered.setActionCommand("Ordered");
					mnuFileOrdered.addActionListener(this);
				JMenuItem mnuFileList = new JMenuItem("Current List of Transaction");
					mnuFileList.setMnemonic(KeyEvent.VK_T);
					mnuFileList.setDisplayedMnemonicIndex(16);
					mnuFile.add(mnuFileList);
					mnuFileList.setActionCommand("List");
					mnuFileList.addActionListener(this);
				JMenuItem mnuFileReboot = new JMenuItem("Reboot System");
					mnuFileReboot.setMnemonic(KeyEvent.VK_R);
					mnuFileReboot.setDisplayedMnemonicIndex(0);
					mnuFile.add(mnuFileReboot);
					mnuFileReboot.setActionCommand("Reboot");
					mnuFileReboot.addActionListener(this);
				JMenuItem mnuFileExit = new JMenuItem("Exit");
					mnuFileExit.setMnemonic(KeyEvent.VK_X);
					mnuFileExit.setDisplayedMnemonicIndex(1);
					mnuFile.add(mnuFileExit);
					mnuFileExit.setActionCommand("Exit");
					mnuFileExit.addActionListener(this);
			JMenu mnuPW = new JMenu("Password Function");
				mnuPW.setMnemonic(KeyEvent.VK_P);
				mnuPW.setDisplayedMnemonicIndex(0);
				mnuBar.add(mnuPW);
				JMenuItem mnuPWPM = new JMenuItem("Password Managemet");
					mnuPWPM.setMnemonic(KeyEvent.VK_M);
					mnuPWPM.setDisplayedMnemonicIndex(9);
					mnuPW.add(mnuPWPM);
					mnuPWPM.setActionCommand("PM");
					mnuPWPM.addActionListener(this);
					
			
			setLayout(new BorderLayout());
				logoutPanel.setLayout(new GridLayout(1,1));
				deskPanel.setLayout(new GridLayout(2,5,15,15));
				inputPanel.setLayout(new GridLayout(3,5));
			add(logoutPanel, BorderLayout.SOUTH);
			add(deskPanel, BorderLayout.NORTH);
			add(inputPanel, BorderLayout.CENTER);
			
			logoutPanel.add(logoutButton);
				logoutButton.addActionListener(this);
			
			for (int i=1; i<13; i++)
			{
				deskDisplay[i] = new JTextArea (13,13);
				scrollingResult[i] = new JScrollPane(deskDisplay[i]);
				deskDisplay[i].setText("Desk "+i+" is avaliable.");
				deskDisplay[i].setEditable(false);
				deskDisplay[i].setBackground(Color.green);			
				deskPanel.add(scrollingResult[i]); //to enable the using of the scrolling bar of the teat area
				ok[i] = true;
				billfood[i] = 0;
				billdrink[i] = 0;
				bill[i] = 0;
			}
			for(int i=1; i<9; i++)
				deskDisplay[i].setText(deskDisplay[i].getText()+"\nDesk for 2-4 people.");	
			for(int i=9; i<13; i++)
				deskDisplay[i].setText(deskDisplay[i].getText()+"\nDesk for 5-8 people.");
			
			inputPanel.add(deskno);
			inputPanel.add(noOfDesk);
				for(int i=1; i<13; i++)
					noOfDesk.addItem(String.valueOf(i));
			inputPanel.add(peopleno);
			inputPanel.add(noOfPeople);
				for(int i=2; i<9; i++)
					noOfPeople.addItem(String.valueOf(i));
			inputPanel.add(reserveButton);
				reserveButton.addActionListener(this);
			inputPanel.add(order);
			inputPanel.add(orderDish);
				orderDish.addItem("Caesar Salad $39.9");
				orderDish.addItem("Tomato and Mozzarella $49.9");
				orderDish.addItem("Spicy Chicken Wings $49.9");
				orderDish.addItem("Fish and Chips $79.9");
				orderDish.addItem("Sirloin Steak $119.9");
				orderDish.addItem("CheeseBurger with Chips $99.9");
				orderDish.addItem("Tiramisu $59.9");
				orderDish.addItem("Soft Drinks $19.9");
				orderDish.addItem("Beer $24.9");
				orderDish.addItem("Wine $29.9");
			inputPanel.add(quantity);
			inputPanel.add(quan);
			inputPanel.add(orderButton);
				orderButton.addActionListener(this);
			inputPanel.add(invi);
			inputPanel.add(invi2);
			inputPanel.add(invi3);
			inputPanel.add(invi4);
			inputPanel.add(billButton);
				billButton.addActionListener(this);
				
			addWindowListener
			(
				new WindowAdapter()
				{
					public void windowClosing(WindowEvent e) //when closing the windows, save data and return to the login stage
					{
						save();
						TermProject.rest.setVisible(false);
						TermProject.pw.setVisible(true);
					}
				}
			);
			load();	//load the pervious save
			colour(); //change the status colour of the desk
		}
		
		public void actionPerformed(ActionEvent e)
		{			
			DecimalFormat twodigi = new DecimalFormat("$###,##0.00");
			
			if (e.getActionCommand() == "Exit") //exit the programme
			{
				save();
				System.exit(0);
			}
			if (e.getActionCommand() == "Current") // to show the current income and number of people
			{
				JOptionPane.showMessageDialog(null, "The current food income is "+twodigi.format(totalfood)+
													"\nThe current drink income is "+twodigi.format(totaldrink)+
													"\nThe current total income is "+twodigi.format(total)+
													"\n\nThere are "+totalpeople+ " people came to restaurant."
													, "Current Received Total Income", JOptionPane.INFORMATION_MESSAGE);
			}
			
			if (e.getActionCommand() == "Ordered") //to show the current ordered items
			{
				JOptionPane.showMessageDialog(null, "Caesar Salad $39.9                          x "+dishcount[0]+
													"\nTomato and Mozzarella $49.9        x "+dishcount[1]+
													"\nSpicy Chicken Wings $49.9            x "+dishcount[2]+
													"\nFish and Chips $79.9                        x "+dishcount[3]+
													"\nSirloin Steak $119.9                         x "+dishcount[4]+
													"\nCheeseBurger with Chips $99.9   x "+dishcount[5]+
													"\nTiramisu $59.9                                  x "+dishcount[6]+
													"\nSoft Drinks $19.9                              x "+dishcount[7]+
													"\nSoft Drinks $19.9                              x "+dishcount[8]+
													"\nWine $29.9                                         x "+dishcount[9], "Current Ordered Item", JOptionPane.INFORMATION_MESSAGE);
			}
			
			if (e.getActionCommand() == "Reboot") //to reset all the data, excluding pw
			{
				JOptionPane.showMessageDialog(null, "System has been rebooted", "System Message", JOptionPane.INFORMATION_MESSAGE);
				for (int i=1; i<13; i++)
				{
					ok[i] = true;
					deskDisplay[i].setText("Desk "+i+" is avaliable.");
					//deskDisplay[i].setBackground(Color.green);
					billdrink[i] = 0;
					billfood[i] = 0;
					bill[i] = 0;
				}
				for(int i=1; i<9; i++)
					deskDisplay[i].setText(deskDisplay[i].getText()+"\nDesk for 2-4 people.");	
				for(int i=9; i<13; i++)
					deskDisplay[i].setText(deskDisplay[i].getText()+"\nDesk for 5-8 people.");
				for (int i=0; i<10; i++)
					dishcount[i] = 0;
				totalfood = 0;
				totaldrink = 0;
				total = 0;
				totalpeople = 0;
				list = "List of transaction";
				billcount = 0;
			}
			
			if (e.getActionCommand() == "List") //showing the list of transcation
			{
				JTextArea textArea = new JTextArea(20,20);
				textArea.setEditable(false);
				textArea.setText(list);
				JScrollPane scrollPane = new JScrollPane(textArea);
				if (list == "List of transaction")
					JOptionPane.showMessageDialog(null, "No Transaction now.", "Current List of Transaction", JOptionPane.ERROR_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, scrollPane, "Current List of Transaction", JOptionPane.INFORMATION_MESSAGE);
			}
			
			if (e.getSource() == logoutButton) //When user is clicking the logout button, save data and go to tthe login stage
			{
				save();
				this.setVisible(false);
				TermProject.pw.setVisible(true);
			}
			
			if (e.getActionCommand() == "PM") //When user is clicking the change pw on menubar, save data and go to the change pw stage
			{
				save();
				this.setVisible(false);
				TermProject.cpw.setVisible(true);
			}
				
			if (e.getSource() == reserveButton) //When user is clicking the reserve button
			{
				int i = noOfDesk.getSelectedIndex()+1;
				if (!ok[i]) //When the desk is reserved, show error message
				{
					JOptionPane.showMessageDialog(null, "Desk "+i+" is not avaiable.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
				else
				{
					int num = noOfPeople.getSelectedIndex()+2;
					
					if (i<9 && num>4) //except the wrong using of small or large desk
						JOptionPane.showMessageDialog(null, "Desk "+i+" is not avaiable for more than 4 people.", "ERROR", JOptionPane.ERROR_MESSAGE);
					else if (i>8 && num<5) //except the wrong using of small or large desk
						JOptionPane.showMessageDialog(null, "Desk "+i+" is not avaiable for less than 5 people.", "ERROR", JOptionPane.ERROR_MESSAGE);
					else //if nothing wrong, change status colour, print out the number of people and time
					{
						Date time = new Date();
							SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
						deskDisplay[i].setText("Desk "+i+"\nThere are "+num+" people.\nCheck in time: "+timeFormat.format(time)+"\n\nOrder:");
						ok[i] = false;
					
						totalpeople += num;
					}
					
				}
			}
			
			if (e.getSource() == orderButton) //When user is clicking the order buton
			{
				int i = noOfDesk.getSelectedIndex()+1;
				//System.out.println(i);
				if (ok[i]) //When the desk is avaliable, show error message
				{
					JOptionPane.showMessageDialog(null, "Desk "+i+" does not have any customers.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
					
				else
				{
					int dish = orderDish.getSelectedIndex();
					int order = 0;
					try
					{
						order = Integer.parseInt(quan.getText());
			
						if (order == 0)
							throw new Exception();
						else
						//get the pervious text and adding the text that telling the ordered item and quantity
						deskDisplay[i].setText(deskDisplay[i].getText()+"\n"+orderDish.getSelectedItem() + " x " + quan.getText());
						//quan.setText("");
						
					}
					
					catch (Exception x) //nothing is entered or not integer
					{
						JOptionPane.showMessageDialog(null, "Please enter correct number of the quantity.", "ERROR", JOptionPane.ERROR_MESSAGE);
						quan.setText("");
					}
					// calculate the bill
					if (dish == 0)
					{
						bill[i] += 39.9*order;
						billfood[i] += 39.9*order;
					}
					if (dish == 1)
					{
						bill[i] += 49.9*order;
						billfood[i] += 49.9*order;
					}
					if (dish == 2)
					{
						bill[i] += 49.9*order;						
						billfood[i] += 49.9*order;
					}
					if (dish == 3)
					{
						bill[i] += 79.9*order;
						billfood[i] += 79.9*order;
					}
					if (dish == 4)
					{
						bill[i] += 119.9*order;
						billfood[i] += 119.9*order;
					}
					if (dish == 5)
					{
						bill[i] += 99.9*order;
						billfood[i] += 99.9*order;
					}
					if (dish == 6)
					{
						bill[i] += 59.9*order;
						billfood[i] += 59.9*order;
					}
					if (dish == 7)
					{
						bill[i] += 19.9*order;
						billdrink[i] += 19.9*order;
					}
					if (dish == 8)
					{
						bill[i] += 24.9*order;
						billdrink[i] += 24.9*order;
					}
					if (dish == 9)
					{
						bill[i] += 29.9*order;
						billdrink[i] += 29.9*order;
					}
					dishcount[dish]+=order; //calculate the total ordered number of the items that can show by the menubar
				}
			}
			
			if (e.getSource() == billButton) //calculate the total price
			{
				int i = noOfDesk.getSelectedIndex()+1;
				if (ok[i]) //if desk is availiable, that is no one, show error
				{
					JOptionPane.showMessageDialog(null, "Desk "+i+" does not have any customers.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				else if (!ok[i] && bill[i] == 0) //some one here, but not ordered yet
				{
					JOptionPane.showMessageDialog(null, "The customers are not ordered yet.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				else //no error, print out the bill with the number of desk and people, order items, quantity, walk in time, leaving time and the total cost
				{
					Date time = new Date();
						SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
					JOptionPane.showMessageDialog(null, deskDisplay[i].getText()+
												"\n------------------------------------------------\n"+
												"The food bill of desk "+i+" is "+twodigi.format(billfood[i])+"\n"+
												"The drink bill of desk "+i+" is "+twodigi.format(billdrink[i])+
												"\n------------------------------------------------\n"+
												"The total bill of desk "+i+" is "+twodigi.format(bill[i])+
												"\n------------------------------------------------\n"+
												"Check out time: "+timeFormat.format(time), "BILL", JOptionPane.INFORMATION_MESSAGE);
					billcount ++; //counting the number of transcation
					
					String templist = list; //creting the list of transcation that print out the bill with the number of desk and people, order items, quantity, walk in time, leaving time and the total cost
					list = templist+"\n\n*****************************************\nTranscation "+billcount+"\n"+deskDisplay[i].getText()+
												"\n------------------------------------------------\n"+
												"The food bill of desk "+i+" is "+twodigi.format(billfood[i])+"\n"+
												"The drink bill of desk "+i+" is "+twodigi.format(billdrink[i])+
												"\n------------------------------------------------\n"+
												"The total bill of desk "+i+" is "+twodigi.format(bill[i])+
												"\n------------------------------------------------\n"+
												"Check out time: "+timeFormat.format(time)+"\n"+
												"End of Transaction "+billcount+"\n*****************************************";
					
					deskDisplay[i].setText("Desk "+i+" is avaliable.");
					if (i<9)
						deskDisplay[i].setText(deskDisplay[i].getText()+"\nDesk for 2-4 people.");	
					else
						deskDisplay[i].setText(deskDisplay[i].getText()+"\nDesk for 5-8 people.");
					ok[i]=true;
					totalfood += billfood[i];
					totaldrink += billdrink[i];
					total += bill[i];
					bill[i] = 0;
					billfood[i] = 0;
					billdrink[i] = 0;
					
				}
			}
			colour(); //changing the status color 
			
			noOfPeople.setSelectedItem("2"); //error found
			noOfDesk.setSelectedItem("1"); // error found
			quan.setText(""); //reset the text field
			orderDish.setSelectedItem("Caesar Salad $39.9"); //error found
		}
		
		void colour()
		{
			for (int i=1; i<13; i++)
			{
				if (ok[i]) //if desk is avaliable, show green colour
					deskDisplay[i].setBackground(Color.green);
				else //if not, show red colour
					deskDisplay[i].setBackground(Color.red);
			}
		}
		
		void save() //three types of file should be print
		//print the record without the data contain text
		{
			PrintWriter record = null;
			PrintWriter listrecord = null;
			try
			{
				record = new PrintWriter(filename);
			}
			catch (FileNotFoundException x)
			{
			}
			//start to print out
			record.println(totalfood);
			record.println(totaldrink);
			record.println(total);
			record.println(totalpeople);
			record.println(billcount);
			for (int i=0; i<10; i++)
				record.println(dishcount[i]);
			for (int i=1; i<13; i++)
			{
				record.println(ok[i]);
				record.println(billfood[i]);
				record.println(billdrink[i]);
				record.println(bill[i]);
			}
			record.close();
			
			for (int i=1; i<13; i++) //print the displayed text of every desk
			{
				PrintWriter deskrecord = null;
				try
				{
					deskrecord = new PrintWriter(deskfilename[i]);	
				}
				catch (FileNotFoundException x)
				{
				}
				//start to print out
				deskrecord.print(deskDisplay[i].getText());
				deskrecord.close();
				
			}
			//print out the list of transcation 
			try
			{
				listrecord = new PrintWriter(listfilename);
			}
			catch (FileNotFoundException x)
			{
			}
			//start to print out
			listrecord.print(list);
			listrecord.close();
		}
		
		void load() //read all three types of file
		{
			Boolean read;
			Scanner record = null;
			Scanner listrecord = null;
			try
			{
				record = new Scanner(new FileReader(filename));
				read = true;
			}
			catch (FileNotFoundException e)
			{
				JOptionPane.showMessageDialog(null, "There is no record of today. \nNew record will be created after login the system.","System Message", JOptionPane.INFORMATION_MESSAGE);
				read = false;
			}
			
			if (read) //changing the string to boolean, integer and double
			{
				totalfood = Double.parseDouble(record.nextLine());
				totaldrink = Double.parseDouble(record.nextLine());
				total = Double.parseDouble(record.nextLine());
				totalpeople = Integer.parseInt(record.nextLine());
				billcount = Integer.parseInt(record.nextLine());
				for (int i=0; i<10; i++)
					dishcount[i] = Integer.parseInt(record.nextLine());
				for (int i=1; i<13; i++)
				{
					ok[i] = new Boolean(record.nextLine()).booleanValue();
					billfood[i] = Double.parseDouble(record.nextLine());
					billdrink[i] = Double.parseDouble(record.nextLine());
					bill[i] = Double.parseDouble(record.nextLine());
				}
				record.close();
			}
			
			for (int i=1; i<13; i++) //reading the text of each desk, and show it again
			{
				Scanner deskrecord = null;
				try
				{
					deskrecord = new Scanner(new FileReader(deskfilename[i]));
				}
				catch (FileNotFoundException e)
				{
				}
				if (read)
				{
					deskDisplay[i].setText(deskrecord.useDelimiter("\\Z").next());
					deskrecord.close();
				}
			}
			
			try //reading the list of transcation, and show it again
			{
				listrecord = new Scanner(new FileReader(listfilename));
			}
			catch (FileNotFoundException e)
			{
			}
			if (read)
			{
				list = listrecord.useDelimiter("\\Z").next();
				listrecord.close();
			}
		}
}
