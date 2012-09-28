package healthwatcher.view.command;

import healthwatcher.model.healthguide.HealthUnit;
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

public class InsertHealthUnit extends Command {

	public InsertHealthUnit(IFacade f) { 
		super(f);
	}

	public void execute() throws Exception {
		PrintWriter out = response.getWriter();
		
		HealthUnit hu = null;
       
        try {
        	if (! request.isAuthorized()) {
                throw new InvalidSessionException();
            }            

            //Complaint Normal
			String code = request.getInput("code");
			String description = request.getInput("description");			
			
			hu = new HealthUnit(description, null);
			
			//#if relacional
			hu.setId(Long.parseLong(code)); 
			//#endif
			
			//#if norelacional
//@			hu.setCode(Integer.parseInt(code)); //Thiago alterou aqui
			//#endif
			
			facade.insert(hu);
			        
            out.println(HTMLCode.htmlPageAdministrator("Operation executed", "HealthUnit inserted"));
            out.close();
        } catch (ObjectAlreadyInsertedException e) {
        	out.println(HTMLCode.errorPageAdministrator(e.getMessage()));
            e.printStackTrace(out);
        } catch (ObjectNotValidException e) {
        	out.println(HTMLCode.errorPageAdministrator(e.getMessage()));
            e.printStackTrace(out);
        } catch(InsertEntryException e){ 
        	out.println(HTMLCode.errorPageAdministrator(e.getMessage()));
            e.printStackTrace(out);
        }catch (InvalidSessionException e) {
        	out.println(HTMLCode.errorPageAdministrator(e.getMessage()));
            e.printStackTrace(out);
        }catch(TransactionException e){
        	out.println(HTMLCode.errorPageAdministrator(e.getMessage()));
            e.printStackTrace(out);            
        } catch(CommunicationException e){
			throw new FacadeUnavailableException();
        } catch(RepositoryException e){
			out.println(HTMLCode.errorPageAdministrator(e.getMessage()));
            e.printStackTrace(out); 
		}
	}

}
