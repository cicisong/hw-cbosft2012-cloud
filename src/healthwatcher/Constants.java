package healthwatcher;

import java.io.IOException;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;


/**
 * This constants define system specific configurations.
 */
public class Constants {

	// Database Configuration
	//Modificar aqui
	public static final String DB_URL = ""; // "jdbc:odbc:test";
	public static final String DB_LOGIN = "root"; // "orbi2";
	public static final String DB_PASS = ""; //"orbi2";
	public static final String DB_DRIVER = "com.mysql.jdbc.Driver"; //"sun.jdbc.odbc.JdbcOdbcDriver";

	// RMI Configuration
	//public static final String SERVER_NAME = "testehealthwather.appspot.com";
	public static final String SERVER_NAME = "localhost:8080/";
	public static final String SYSTEM_NAME = "HealthWatcher";

	// SERVLETS Configuration
	
	// You should point this path to the base of the forms in your system
	public static final String FORM_PATH = "";
	//public static final String FORM_PATH = "c:\\eclipse\\workspace\\HealthWatcherOO_1_Base\\web\\healthwatcher\\formularios\\";	
	
	//public static final String SERVLET_SERVER_PATH = "testehealthwather.appspot.com/";
	public static final String SERVLET_SERVER_PATH = "localhost:8080/HealthWatcher/";
	//public static final String SERVLET_SERVER_PATH = "localhost:8080/servlet/healthwatcher.view.servlets.";

	//public static final String SYSTEM_ROOT = "http://localhost:8080/healthwatcher/";
	public static final String SYSTEM_ROOT = "http://" + SERVLET_SERVER_PATH;

	//public static final String SYSTEM_ROOT = "http://localhost:8080/healthwatcher/";
	public static final String SYSTEM_ACTION = "http://" + SERVLET_SERVER_PATH + "HWServlet";

	//public static final String SYSTEM_INDEX = "http://"+SERVLET_SERVER_PATH + "ServletWebServer?file=index.html";
	public static final String SYSTEM_INDEX = "index.html";

	//public static final String SYSTEM_INDEX_ADMINISTRATOR = "http://"+SERVLET_SERVER_PATH+"ServletLogin";
	public static final String SYSTEM_INDEX_ADMINISTRATOR = SYSTEM_ACTION + "?operation=LoginMenu";

	public static final String SYSTEM_LOGIN = SYSTEM_ROOT + "Login.html";
	public static final String SYSTEM_QUERIES = SYSTEM_ROOT + "QueriesMenu.html";
	public static final String DOMAINSDB="HWSIMPLEDB";
	public static final String S3BUCKET="hwcbsoft";
	private static AmazonSimpleDB SDB;
	private static AmazonS3 s3;
	
	// Where to store the logs
	//public static final String LOG_PATH = "c:\\healthwatcherLog.log";
	public static final String LOG_PATH = "/tmp/mulato/healthwatcherLog.log";
	
	/**
	 * Defines if the system should be persistent or not (use DB or not)
	 * 
	 * @return true, if the system should use DB, false otherwise
	 */
	public static boolean isPersistent() {
		return true; //Nelio mudou aqui
	}
	
	public static AmazonSimpleDB getSDB(){
		if (SDB==null){
			try {
				SDB=new AmazonSimpleDBClient(new PropertiesCredentials(
						Constants.class.
						getResourceAsStream("../AwsCredentials.properties")));
				return SDB;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return SDB;	
	}
	
	public static  AmazonS3 getS3(){
		if (s3==null){
			try {
				s3=new AmazonS3Client(new PropertiesCredentials(Constants.class.getResourceAsStream("../AwsCredentials.properties")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return s3;
	}
}
