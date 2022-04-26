package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

public class User {

	private String user;
	private String password;
	private String userInfoF;
	private ArrayList<String> que;
	private ArrayList<String> ans;

	/**
	 * User class constructor, creates a new user
	 * 
	 * @param user	The user name
	 */
	public User(String user) {
		this.user = user;

		userInfoF = "userInfo.txt";
	}

	/**
	 * User class constructor. Creates a new user with name and passwrod
	 * 
	 * @param user		the new user name
	 * @param password	the new user's password
	 */
	public User(String user, String password) {
		this.user = user;
		this.password = hashUser(password, "SHA-512");

		userInfoF = "userInfo.txt";
	}

	/**
	 * User class constructor. Creates a new user with user name, password,
	 * and questions and answers for password recovery.
	 * 
	 * @param user		the new user name
	 * @param password	the new user's password
	 * @param que		the password recovery questions
	 * @param ans		the password recovery answers
	 */
	public User(String user, String password, ArrayList<String> que, ArrayList<String> ans) {
		this.user = user;
		this.password = hashUser(password, "SHA-512");

		this.que = que;
		for (int ndx = 0; ndx < ans.size(); ndx++) {
			ans.set(ndx, hashUser(ans.get(ndx), "SHA-512"));
		}
		this.ans = ans;
		userInfoF = "userInfo.txt";
	}

	/**
	 * Returns a hash string for the given content using the specified
	 * hashing algorithm.
	 * 
	 * @param content	The string to hash
	 * @param algo		The hash algorithm to use
	 * @return The hashed string content
	 */
	public String hashUser(String content, String algo) {
		String hashedContent = "";

		try {
			MessageDigest mD = MessageDigest.getInstance(algo);
			mD.update(content.getBytes());

			byte[] digestB = mD.digest();

			hashedContent = DatatypeConverter.printHexBinary(digestB).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return hashedContent;
	}

	/**
	 * Adds this user to the user info file
	 */
	public void newAddition() throws Exception {
		if (userPresent()) {
			throw new Exception(
					"This user already exist. Please use another e-mail or click on \"Forgot Password?\" in the home screen.");
		} else {
			try (FileWriter writeFile = new FileWriter("data\\" + userInfoF, true);
					BufferedWriter writerBuffer = new BufferedWriter(writeFile)) {

				writerBuffer.write(user + " , " + password);

				for (int ndx = 0; ndx < que.size(); ndx++) {
					writerBuffer.write(" , " + que.get(ndx) + " , " + ans.get(ndx));
				}

				writerBuffer.newLine();

				writerBuffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Checks if this user is present in the user info file
	 *
	 * @return boolean indicating whether the user exists or not
	 */
	public boolean userPresent() {
		String currentName = "";
		String[] usersInfo;
		try (FileReader readFile = new FileReader("data\\" + userInfoF);
				BufferedReader readBuffer = new BufferedReader(readFile)) {

			while ((currentName = readBuffer.readLine()) != null) {
				// Separation of inv file data into a String array
				usersInfo = currentName.split(" , ");

				if (usersInfo[0].equalsIgnoreCase(user)) {
					return true;
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Verify's the user and password combination
	 * 
	 * @return boolean indicating matching password
	 */
	public boolean userVerification() throws Exception {

		if (!userPresent()) {
			throw new Exception(
					"User does not exist. Please try again or if you are a new user click on the designated \"New User?\" link"
							+ " in the home screen.");
		} else {
			String selectedUser = "";
			String[] usersInfo;
			try (FileReader readFile = new FileReader("data\\" + userInfoF);
					BufferedReader readBuffer = new BufferedReader(readFile)) {

				while ((selectedUser = readBuffer.readLine()) != null) {
					// Separation of inv file data into a String array
					usersInfo = selectedUser.split(" , ");

					if (usersInfo[0].equalsIgnoreCase(user)) {
						if (usersInfo[1].equals(password)) {
							// System.out.println("Login Successful!");

							return true;
						} else {
							throw new Exception("Password is incorrect. Please try again or click on the designated "
									+ "\"Forgot Password?\" link.");
						}
					}

				}

			}
		}

		return false;
	}

	/**
	 * Selects a random question from the list of password recovery questions
	 * for this user.
	 * 
	 * @return The password recovery question
	 */
	public String randQuestion() throws IOException {

		String selectedUser = "";
		String[] usersInfo;
		String[] questions = new String[3];
		try (FileReader readFile = new FileReader("data\\" + userInfoF);
				BufferedReader readBuffer = new BufferedReader(readFile)) {

			while ((selectedUser = readBuffer.readLine()) != null) {
				// Separation of inv file data into a String array
				usersInfo = selectedUser.split(" , ");

				if (usersInfo[0].equalsIgnoreCase(user)) {
					questions[0] = usersInfo[2];
					questions[1] = usersInfo[4];
					questions[2] = usersInfo[6];

					Random rand = new Random();
					int randNum = rand.nextInt(3);

					return questions[randNum];
				}

			}

		}

		return "";

	}

	/**
	 * Verifies the correct answers for the password recovery questions
	 * 
	 * @param que	The password recovery question
	 * @param ans	The password recovery answer
	 * @return boolean indicating if the answer matches the question
	 */
	public boolean verifyAnswer(String que, String ans) throws FileNotFoundException, IOException {
		String selectedUser = "";
		String[] usersInfo;

		try (FileReader readFile = new FileReader("data\\" + userInfoF);
				BufferedReader readBuffer = new BufferedReader(readFile)) {

			while ((selectedUser = readBuffer.readLine()) != null) {
				// Separation of inv file data into a String array
				usersInfo = selectedUser.split(" , ");

				if (usersInfo[0].equalsIgnoreCase(user)) {
					if (que.equalsIgnoreCase(usersInfo[2])) {
						if (usersInfo[3].equals(hashUser(ans, "SHA-512"))) {

							return true;
						}

					} else if (que.equalsIgnoreCase(usersInfo[4])) {
						if (usersInfo[5].equals(hashUser(ans, "SHA-512"))) {
							return true;
						}

					} else if (que.equalsIgnoreCase(usersInfo[6])) {
						if (usersInfo[7].equals(hashUser(ans, "SHA-512"))) {
							return true;
						}

					}

					return false;
				}

			}

		}

		return false;
	}

	/**
	 * Sets a new password password for this user, saving it to the user
	 * info file
	 * 
	 * @param password	The new password
	 */
	public void newPassword(String password) {
		String selectedUser = "";
		String[] usersInfo;
		String listOfUsers = "";
		String[] data = null;
		try (FileReader readFile = new FileReader("data\\" + userInfoF);
				BufferedReader readBuffer = new BufferedReader(readFile)) {

			while ((selectedUser = readBuffer.readLine()) != null) {
				// Separation of inv file data into a String array
				usersInfo = selectedUser.split(" , ");

				if (usersInfo[0].equalsIgnoreCase(user)) {
					usersInfo[1] = hashUser(password, "SHA-512");

					for (int ndx = 0; ndx < usersInfo.length - 1; ndx++) {
						listOfUsers += usersInfo[ndx] + " , ";
					}

					listOfUsers += usersInfo[usersInfo.length - 1] + " ::: ";
				} else {
					listOfUsers += selectedUser + " ::: ";
				}

				data = listOfUsers.split(" ::: ");

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		try (FileWriter writeFile = new FileWriter("data\\" + userInfoF);
				BufferedWriter writerBuffer = new BufferedWriter(writeFile)) {

			for (int i = 0; i < data.length; i++) {
				writerBuffer.write(data[i]);

				writerBuffer.newLine();
			}

			writerBuffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter for the user name
	 * 
	 * @return The current user name
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Setter for the user name
	 * 
	 * @param user	The new user name
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Getter for the password
	 * 
	 * @return the current user's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for the password
	 * 
	 * @param password	The password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
