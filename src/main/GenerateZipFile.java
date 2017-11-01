package main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.smartcardio.CardNotPresentException;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

public class GenerateZipFile {

	private FTPClient ftpClient = null;
	private ZipOutputStream zos = null;
	private OutputStream oStream = null;
	private InputStream iStream = null;
	private String path = null;
	private byte[] buffer = new byte[1024];
	private String curDir = "";
	
	public GenerateZipFile(FTPClient ftpClient, String path, OutputStream os) {
		this.ftpClient = ftpClient;
		this.path = path;
		this.oStream = os;
	}
	
	public void getZipFile() throws Exception{
		zos = new ZipOutputStream(oStream);
		if(path.equalsIgnoreCase(".") || path.equalsIgnoreCase("..") || ftpClient.ls(path).get(0).getAttrs().isDir()){
			ftpClient.cd(path);
			generateZip();
		}else{
			generateZipForAFile();
		}
		close();
	}
	
	private void generateZip() throws Exception{
		for(ChannelSftp.LsEntry iEntry : ftpClient.getCurrentDirectoryFiles()){
			
			if(iEntry.getAttrs().isDir()){
				createNextDir(iEntry);
				cdNext(iEntry);
				generateZip();
				cdBack();
				
			}else{
				putFile(iEntry);
			}
		}
	}
	
	private void generateZipForAFile() throws Exception{
		putFile(ftpClient.ls(path).get(0));
	}
	
	private void createNextDir(ChannelSftp.LsEntry iEntry) throws IOException{
		zos.putNextEntry(new ZipEntry(iEntry.getFilename()+"/"));
	}

	private void cdNext(ChannelSftp.LsEntry iEntry) throws FTPClientException{
		ftpClient.cd(iEntry.getFilename());
		curDir += "/" +iEntry.getFilename();
	}
	
	private void cdBack() throws FTPClientException{
		ftpClient.cd("..");
		String[] slStrings = curDir.split("/");
		curDir = "";
		for(int i=1;i<slStrings.length-1;i++){
			curDir += "/" + slStrings[i];
		}
	}
	
	private void putFile(ChannelSftp.LsEntry iEntry) throws Exception{
			zos.putNextEntry(new ZipEntry(curDir + "/" + iEntry.getFilename()));
			iStream = ftpClient.get(iEntry.getFilename());
			
			int b = 0;
			while((b = iStream.read(buffer)) > 0){
				zos.write(buffer, 0, b);
			}
	}
	
	public void close() throws IOException{
		zos.close();
		oStream.close();
	}
	
}
