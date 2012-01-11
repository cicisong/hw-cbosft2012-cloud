package healthwatcher.view.command;

import healthwatcher.Constants;
import healthwatcher.model.healthguide.HealthUnit;
import healthwatcher.view.IFacade;

import java.io.PrintWriter;

import lib.exceptions.CommunicationException;
import lib.exceptions.FacadeUnavailableException;
import lib.exceptions.InvalidSessionException;
import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.RepositoryException;
import lib.exceptions.TransactionException;
import lib.util.HTMLCode;
import lib.util.IteratorDsk;

public class UpdateHealthUnitList extends Command {

	public UpdateHealthUnitList(IFacade f) { 
	    super(f);
	  }

	public void execute() throws Exception {
		PrintWriter out = response.getWriter();

	        try {
	        
	            if (! request.isAuthorized()) {
	                throw new InvalidSessionException("Invalid Session! <br><a href=\""+Constants.SYSTEM_LOGIN+"\">Try again</a>");
	            }

	        	// Facade calls must come before using out
	            IteratorDsk repTP = facade.getHealthUnitList();
	            
		        out.println("Queries - Complaint information");
		        out.println("<body><h1>Queries:<br>Querie about complaint</h1>");
		        out.println("<p>Choose a complaint: </p>");
		        out.println("<form method=\"POST\" action=\""+Constants.SYSTEM_ACTION+"?operation=UpdateHealthUnitSearch\">");

	            out.println("<div align=\"center\"><center><p><select name=\"numUS\" size=\"1\">");
	           

	            if (repTP==null||!repTP.hasNext()) {
	                out.println("</select></p></center></div>");
	                out.println("<p><font color=\"red\"><b> There isn't any health units.</b></font></p>");
	            } else {
	                HealthUnit tp;
	                do {
	                    tp = (HealthUnit) repTP.next();
	                    out.println("<option value=\""      +
	                                tp.getId() + "\"> " + 
	                                tp.getDescription()           +
	                                " </OPTION>");
	                } while (repTP.hasNext());
	                repTP.close();
	                
	                out.println("</select></p></center></div>");
	                out.println("  <div align=\"center\"><center><p><input type=\"submit\" value=\"Search\" name=\"B1\"></p></center></div></form>");
	            }
	            out.println(HTMLCode.closeAdministrator());
	        } catch (ObjectNotFoundException e) {
	            out.println("There isn't any health units");
			} catch (RepositoryException e) {
				e.printStackTrace();
			} catch (TransactionException e) {
				e.printStackTrace();
			} catch (CommunicationException e) {
				throw new FacadeUnavailableException();
	        } catch (InvalidSessionException e) {
	            out.println(e.getMessage());
			} finally {out.close();}
		}
	}
