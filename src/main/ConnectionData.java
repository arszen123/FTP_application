package main;

import java.util.HashMap;

public class ConnectionData {
	private String hostName;
	private String portNumber;
	private String userName;
	private String password;
	private String connectionType;
	
	public void setUpData(HashMap<String, String> data){
		setHostName(data.get("host"));
		setPortNumber(data.get("port"));
		setUserName(data.get("username"));
		setPassword(data.get("password"));
		setConnectionType(data.get("type"));
	}
	
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getPortNumber() {
		return portNumber;
	}
	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConnectionType() {
		return connectionType;
	}
	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}
	
	
}
