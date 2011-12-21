package healthwatcher.view.command;

import healthwatcher.model.employee.Employee;
import healthwatcher.view.IFacade;

import java.io.PrintWriter;

import lib.exceptions.CommunicationException;
import lib.exceptions.FacadeUnavailableException;
import lib.exceptions.InsertEntryException;
import lib.exceptions.InvalidSessionException;
import lib.exceptions.ObjectAlreadyInsertedException;
import lib.exceptions.ObjectNotValidException;
import lib.exceptions.RepositoryException;
import lib.exceptions.TransactionException;
import lib.util.HTMLCode;

public class InsertEmployee extends Command {

	public InsertEmployee(IFacade f) {
		super(f);
		// TODO Auto-generated constructor stub
	}

	public void execute() throws Exception {
		PrintWriter out = response.getWriter();
		
		Employee employee;

		try {
			if (! request.isAuthorized()) {
				throw new InvalidSessionException();
			}

			// Complaint Normal
			String name = request.getInput("name");
			String login = request.getInput("login");
			String password = request.getInput("password");

			employee = new Employee(login, password, name);

			facade.insert(employee);

			out.println(HTMLCode.htmlPageAdministrator("Operation executed",
					"Employee inserted"));
			out.close();
		} catch (ObjectAlreadyInsertedException e) {
			out.println(lib.util.HTMLCode
					.errorPageAdministrator(e.getMessage()));
			e.printStackTrace(out);
		} catch (ObjectNotValidException e) {
			out.println(lib.util.HTMLCode
					.errorPageAdministrator(e.getMessage()));
			e.printStackTrace(out);
		} catch (InvalidSessionException e) {
			out.println(lib.util.HTMLCode
					.errorPageAdministrator(e.getMessage()));
			e.printStackTrace(out);
		} catch (InsertEntryException e) {
			out.println(lib.util.HTMLCode
					.errorPageAdministrator(e.getMessage()));
			e.printStackTrace(out);
		} catch (TransactionException e) {
			out.println(lib.util.HTMLCode
					.errorPageAdministrator(e.getMessage()));
			e.printStackTrace(out);
		} catch (CommunicationException e) {
			 throw new FacadeUnavailableException();
        } catch(RepositoryException e){
			out.println(HTMLCode.errorPageAdministrator(e.getMessage()));
            e.printStackTrace(out); 
		}
	}
}