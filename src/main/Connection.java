package main;
import java.io.IOException;

import com.jcraft.jsch.*;


class Connection{
	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;
	private ConnectionData connectionData = null;
	
	public Connection(ConnectionData connectionData){
		this.connectionData  = connectionData;
	}

	public ChannelSftp connect() throws JSchException, SftpException, IOException{
		JSch jSch = new JSch();
		session = jSch.getSession(connectionData.getUserName(),
				connectionData.getHostName(),Integer.parseInt(connectionData.getPortNumber()));

		java.util.Properties config = new java.util.Properties(); 
		config.put("StrictHostKeyChecking", "no");

		session.setConfig(config);
		session.setPassword(connectionData.getPassword());
		session.connect();

		channel = session.openChannel("sftp");
		channel.connect();

		channelSftp = (ChannelSftp) channel;

		return channelSftp;
	}
	
	public void close(){
		channel.disconnect();
        session.disconnect();
	}
}
