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

	public User(String user) {
		this.user = user;

		userInfoF = "userInfo.txt";
	}

	public User(String user, String password) {
		this.user = user;
		this.password = hashUser(password, "SHA-512");

		userInfoF = "userInfo.txt";
	}

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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
