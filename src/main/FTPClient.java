package main;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

public class FTPClient implements Closeable{

	private FTPChannel ftpChannel = null;
	private ChannelSftp channelSftp = null;
	
	public FTPClient(FTPChannel ftpChannel) throws FTPClientException {
		this.ftpChannel = ftpChannel;
		channelSftp = this.ftpChannel.getConnection();
		if(channelSftp==null)
			throw new FTPClientException("It seems there is no connection established. Run FTPChannnel.connect() befor sending it to the FTPClient");
	}
	
	public void cd(String path) throws FTPClientException{
		try{
			if(channelSftp.lstat(path).isDir() || channelSftp.lstat(path).isLink()){
				channelSftp.cd(path);
			}else
				throw new FTPClientException("File is not a directory!");
		}catch(SftpException e){
			throw new FTPClientException(e.toString());
		}
	}
	
	public InputStream get(String src) throws FTPClientException {
		InputStream is = null;
		try {
			is = channelSftp.get(src);
		} catch (SftpException e) {
			throw new FTPClientException("Error while trying to open an InputStream.",e);
		}
		return is;
	}
	
	public OutputStream put(String dst) throws FTPClientException{
		OutputStream os = null;
		try {
			os = channelSftp.put(dst);
		} catch (SftpException e) {
			throw new FTPClientException("Error while trying to open an OutputStream.",e);
		}
		return os;
	}

	@SuppressWarnings("unchecked")
	public Vector<ChannelSftp.LsEntry> getCurrentDirectoryFiles() throws SftpException{
		return channelSftp.ls("*");
	}
	
	@SuppressWarnings("unchecked")
	public Vector<ChannelSftp.LsEntry> ls(String path) throws SftpException{
		return channelSftp.ls(path);
	}
	
	public void close(){
		channelSftp.disconnect();
		ftpChannel.close();
	}
	
}
