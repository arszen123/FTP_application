package main;

import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

public class FTPClient {

	private FTPChannel ftpChannel = null;
	private ChannelSftp channelSftp = null;
	
	public FTPClient(FTPChannel ftpChannel) throws FTPClientException {
		this.ftpChannel = ftpChannel;
		channelSftp = this.ftpChannel.getConnection();
		if(channelSftp==null)
			throw new FTPClientException("It seems there is no connection established. Run FTPChannnel.connect() befor sending it to the FTPClient");
	}
	
	public Vector<ChannelSftp.LsEntry> getCurrentDirectoryFiles() throws SftpException{
		return channelSftp.ls("*");
	}
	
	public void close(){
		channelSftp.disconnect();
		ftpChannel.close();
	}
	
}
