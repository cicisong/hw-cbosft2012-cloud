package healthwatcher.view.command;

import healthwatcher.Constants;
import healthwatcher.model.employee.Employee;
import healthwatcher.view.IFacade;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import lib.exceptions.CommunicationException;
import lib.exceptions.FacadeUnavailableException;
import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.TransactionException;
import lib.util.HTMLCode;
import lib.util.Library;

public class Login extends Command {

	public Login(IFacade f) {
		super(f);
		// TODO Auto-generated constructor stub
	}

	private String[] keywords = { "##SYSTEM_ROOT##", "##SERVLET_SERVER_PATH##",
			"##CLOSE##", "##SYSTEM_ACTION##" };

	private String[] newWords = { Constants.SYSTEM_ROOT,
			Constants.SERVLET_SERVER_PATH, HTMLCode.closeAdministrator(), Constants.SYSTEM_ACTION };

	public static final String EMPLOYEE = "employee";

	public void execute() throws Exception {
		PrintWriter out = response.getWriter();

        String login = request.getInput("login");
        String password = request.getInput("password");        

        try {
            Employee employee = facade.searchEmployee(login);
            
            if (employee.validatePassword(password)) {
            	//employee.addObserver(facade); //Thiago alterou aqui
            	
            	request.setAuthorized(true);
                request.put(Login.EMPLOYEE, employee);
                //#ifdef relacional 
//@                	out.println(Library.getFileListReplace(keywords, newWords, Constants.FORM_PATH+"MenuEmployee.html"));
                //#endif
                //#ifdef norelacional
                	out.println(Library.getFileListReplace(keywords, newWords, "MenuEmployee.html"));//Thiago alterou aqui
                //#endif
            } else {                              
            	//#ifdef relacional
//@            		out.println(HTMLCode.errorPage("Invalid password! <br><a href=\""+Constants.SYSTEM_LOGIN+"\">Try again</a>"));
            	//#endif
            	//#ifdef norelacional
            		out.println(HTMLCode.errorPage("Invalid password! <br><a href=\"Login.html\">Try again</a>"));//Thiago alterou aqui
            	//#endif
            }
        } catch (ObjectNotFoundException e) {
            out.println(HTMLCode.errorPage("Invalid login! <br><a href=\""+Constants.SYSTEM_LOGIN+"\">Try again</a>"));
        } catch (FileNotFoundException e) {
            out.println(HTMLCode.errorPage(e.getMessage()));
        } catch (TransactionException e) {
        	out.println(HTMLCode.errorPage(e.getMessage()));
        } catch (CommunicationException e) {
			throw new FacadeUnavailableException();
		} catch (Exception e) {
        	out.println(HTMLCode.errorPage(e.getMessage()));//null
		} finally{
			out.close();
		}
	}
}
