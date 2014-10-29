package shared.model;

import java.util.Random;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


/**
 * An user that can download and index records
 * 
 * <pre>
 * 		<b>Domain:</b>
 * 			userKey : int
 * 			username : String;
 * 			password : String
 * 			firstName : String
 * 			lastName : String
 * 			email : String
 * 			recordsIndexed : int
 * 			batchAssigned : boolean
 * 			batch : Image
 * </pre>
 * @author Justin Shattuck
 */
@XStreamAlias("user")
public class User
{
	
	/**
	 * Unique identifier associated with this field.
	 */
	@XStreamOmitField
	private int userKey;
	/**
	 * Unique name chosen by user.
	 */
	private String username;
	/**
	 * Password chosen by user.
	 */
	private String password;
	/**
	 * User's first name.
	 */
	private String firstname;
	/**
	 * User's last name.
	 */
	private String lastname;
	/**
	 * User's email address.
	 */
	private String email;
	/**
	 * Number of records the user has indexed.
	 */
	private int indexedrecords;
	/**
	 * Indicates whether or not the user has been assigned a batch/image.
	 */
	@XStreamOmitField
	private boolean batchAssigned;
	/**
	 * The batch/image assigned to this user.
	 */
	@XStreamOmitField
	private Image batch;
	
	/**
	 * Creates a user object
	 * 
	 * @param key Unique identifier
	 * @param u username
	 * @param p password
	 * @param f first name
	 * @param l last name
	 * @param e email address
	 * @param r number of records indexed
	 */
	public User(int key, String u, String p, String f, String l, String e, int r)
	{
		this.userKey = key;
		this.username = u;
		this.password = p;
		this.firstname = f;
		this.lastname = l;
		this.email = e;
		this.indexedrecords = r;
	}
	
	public User(String u, String p, String f, String l, String e, int r)
	{
		this.userKey = -1;
		this.username = u;
		this.password = p;
		this.firstname = f;
		this.lastname = l;
		this.email = e;
		this.indexedrecords = r;
		this.batchAssigned = false;
		this.batch = null;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastName
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the recordsIndexed
	 */
	public int getIndexedrecords() {
		return indexedrecords;
	}

	/**
	 * @param recordsIndexed the recordsIndexed to set
	 */
	public void setIndexedrecords(int indexedrecords) {
		this.indexedrecords = indexedrecords;
	}

	/**
	 * @return the batchAssigned
	 */
	public boolean isBatchAssigned() {
		return batchAssigned;
	}

	/**
	 * @param batchAssigned the batchAssigned to set
	 */
	public void setBatchAssigned(boolean batchAssigned) {
		this.batchAssigned = batchAssigned;
	}

	/**
	 * @return the batch
	 */
	public Image getBatch() {
		return batch;
	}

	/**
	 * @param batch the batch to set
	 */
	public void setBatch(Image batch) {
		this.batch = batch;
	}

	/**
	 * @return the userKey
	 */
	public int getUserKey() {
		return userKey;
	}

	/**
	 * Validates a User
	 * @param u User to validate
	 */
	public static void validateUser(User u)
	{
		
	}
	
	
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append("UserID: " + this.userKey + "\n");
		result.append("Username: " + this.username + "\n");
		result.append("Password: " + this.password + "\n");
		result.append("First Name: " + this.firstname + "\n");
		result.append("Last Name: " + this.lastname + "\n");
		result.append("Email: " + this.email + "\n");
		result.append("Indexed Records: " + this.indexedrecords + "\n");
		result.append("Batch Assigned: " + this.batchAssigned + "\n");
		result.append("Batch: " + this.batch + "\n");
		
		return result.toString();
	}
}
