package main;

public class FTPClientException  extends Exception{
	private static final long serialVersionUID = 1L;
	private Throwable cause=null;
	  
	  public FTPClientException () {
	    super();
	  }
	  
	  public FTPClientException (String s) {
	    super(s);
	  }
	  
	  public FTPClientException (String s, Throwable e) {
	    super(s);
	    this.cause=e;
	  }
	  
	  public Throwable getCause(){
	    return this.cause;
	  }
}
