package server;

import java.util.HashSet;
import java.util.Set;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import shared.model.*;

@XStreamAlias("indexerdata")
public class IndexerData
{
	private Set<User> users;
	private Set<Project> projects;
	
	public IndexerData()
	{
		users = new HashSet<User>();
		projects = new HashSet<Project>();
	}
	
	public boolean addUser(User u)
	{
		return users.add(u);
	}
	
	public boolean addProject(Project p)
	{
		return projects.add(p);
	}

	/**
	 * @return the users
	 */
	public Set<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/**
	 * @return the projects
	 */
	public Set<Project> getProjects() {
		return projects;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		User[] userArray = users.toArray(new User[0]);
		
		for (int i = 0; i < userArray.length; i++)
		{
			result.append(userArray[i].toString() + "\n");
		}
		
		Project[] projectArray = projects.toArray(new Project[0]);
		
		for (int i = 0; i < projectArray.length; i++)
		{
			result.append(projectArray[i].toString() + "\n");
		}
		
		
		
		return result.toString();
	}
}
