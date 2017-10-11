package main;

import java.io.IOException;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public class FTPClient{
	private ChannelSftp channelSftp = null;
	private Connection connection = null;
	
	public FTPClient(ConnectionData connectionData){
		setUpConnection(connectionData);
	}
	
	public FTPClient(){};
	
	public void setUpConnection(ConnectionData connectionData){
		connection = new Connection(connectionData);
	}
	
	public void connect() throws JSchException, SftpException, IOException, FTPClientException{
		if(connection != null)
			channelSftp = connection.connect();
		else
			throw new FTPClientException("Cannot create connection, because ConnectionData is null.");
	}
	
	public void close(){
		channelSftp.disconnect();
		connection.close();
	}
	
	
}
