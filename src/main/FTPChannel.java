package main;

import java.io.IOException;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public class FTPChannel{

	private ChannelSftp channelSftp = null;
	private Connection connection = null;
	
	public FTPChannel(ConnectionData connectionData){
		setUpConnection(connectionData);
	}
	
	public FTPChannel(){
		//Make a new Instance 
	};
	
	public void setUpConnection(ConnectionData connectionData){
		if(connection != null)
			this.close();
		connection = new Connection(connectionData);
	}
	
	public void connect() throws JSchException, SftpException, IOException, FTPClientException{
		if(connection != null)
			channelSftp = connection.connect();
		else
			throw new FTPClientException("Cannot create connection, because ConnectionData is null.");
	}
	
	ChannelSftp getConnection(){
		return this.channelSftp;
	}
	
	public void close(){
		channelSftp.disconnect();
		connection.close();
	}
	
	
}
