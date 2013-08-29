package yp.tibco.com;

import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * Hello world!
 *
 */
public class App 
{
	  public static int PORT = 33789;
	  public static String HOST = "localhost";
	  
    public static void main( String[] args )
    {
//        System.out.println( "Hello World!" );
//    	Properties properties = new Properties();
//    	
//    	try {
//			properties.load(App.class.getClassLoader().getResourceAsStream("settings.properties"));
////			System.out.println(properties.get("ip"));
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
//    	String str_host = (String) properties.get("host");
//		  String str_port = (String) properties.get("port");
//		  if(str_host!=null && !str_host.equals("") && isValidAddress(str_host)) {
//			  
//			  HOST = str_host;
//		  }
//		  if(str_port!=null && !str_port.equals("")) {
//			  try {
//				  int i_port = Integer.parseInt(str_port);
//				  if(i_port < 65536) {
//					  PORT = i_port;
//				  }
//			  } catch(NumberFormatException e) {
////				  e.printStackTrace();
//			  }
//		  }
//		  
//		  System.out.println("host: " + HOST +" --  port: " + PORT);
    	Integer i = 2147483600;
    	while(true) {
    		if(i < Integer.MAX_VALUE){
    			i++;
    			
    		}else {
    			i = 0;
    		}
    		System.out.println(i);
    		try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    private static boolean isValidAddress(String serverAddress){
		if(serverAddress.equals("localhost"))
			return true;
		else if(serverAddress.contains(".")){ //$NON-NLS-1$
			StringTokenizer tokenizer = null;
			String token = null;
			tokenizer = new StringTokenizer(serverAddress,".");
			int i = 0;
			while(tokenizer.hasMoreTokens()){
				if(i > 3)
					return false;
				token = tokenizer.nextToken();
				if(!isValidInteger(token))
					return false;
				if(token.length()>3) {
					return false;
				}
				i++;
			}
			return true;
		}
		return true;
	}
    
    private static boolean isValidInteger(String no){
		try {
			Integer.parseInt(no);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
