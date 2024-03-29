package healthwatcher.view.command;

import healthwatcher.Constants;
import healthwatcher.model.complaint.Complaint;
import healthwatcher.view.IFacade;

import java.io.PrintWriter;

import lib.exceptions.InvalidSessionException;
import lib.exceptions.ObjectNotFoundException;
import lib.util.HTMLCode;
import lib.util.IteratorDsk;

public class UpdateComplaintList extends Command {

	public UpdateComplaintList(IFacade f) { 
	    super(f);
	  }

	public void execute() throws Exception {
		PrintWriter out = response.getWriter();
		
			try {
		    	if (! request.isAuthorized()) {
	                throw new InvalidSessionException();
	            }
	        	// Facade calls must come before using out
	        	IteratorDsk repTP = facade.getComplaintList();
	        	
	        	out.println(HTMLCode.open("Queries - Complaint information"));
	            out.println("<body><h1>Queries:<br>Querie about complaint</h1>");
	            out.println("<p>Choose a complaint: </p>");
	            out.println("<form method=\"POST\" action=\""+Constants.SYSTEM_ACTION+"?operation=UpdateComplaintSearch\">");
	               

	            if (repTP==null||!repTP.hasNext()) {                
	                out.println("<p><center><font color=\"red\"><b> There isn't any complaints.</b></font></center></p>");
	            } else {
	            	out.println("<div align=\"center\"><center><p><select name=\"numQueixa\" size=\"1\">");            	
	                Complaint tp;
	                do {
	                    tp = (Complaint) repTP.next();
	                    out.println("<option value=\""      +
	                                tp.getCodigo() + "\"> " + 
	                                tp.getDescricao()      +
	                                " </OPTION>");                    
	                } while (repTP.hasNext());
	                repTP.close();
	                
	                out.println("</select></p></center></div>");
	                out.println("  <div align=\"center\"><center><p><input type=\"submit\" value=\"Search\" name=\"B1\"></p></center></div></form>");
	            }
	            out.println(HTMLCode.closeAdministrator());            
	        } catch (ObjectNotFoundException e) {
	            out.println(HTMLCode.errorPageQueries("There isn't any complaints"));
	        }catch(InvalidSessionException e){
	        	out.println(HTMLCode.errorPageAdministrator("<p>Ivalid Session! <br>You must <a href=\""+Constants.SYSTEM_LOGIN+"\">login</a> again!"));
	        }catch(Exception e){
	        	out.println(HTMLCode.errorPageQueries("There isn't any complaints"));
	        	e.printStackTrace(out);
	        }finally {out.close();}
		}
}