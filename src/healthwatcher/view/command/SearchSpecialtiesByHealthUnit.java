
package healthwatcher.view.command;

import healthwatcher.model.healthguide.MedicalSpeciality;
import healthwatcher.view.IFacade;

import java.io.PrintWriter;

import lib.exceptions.CommunicationException;
import lib.exceptions.FacadeUnavailableException;
import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.RepositoryException;
import lib.exceptions.TransactionException;
import lib.util.HTMLCode;
import lib.util.IteratorDsk;


public class SearchSpecialtiesByHealthUnit extends Command {
	
    public SearchSpecialtiesByHealthUnit(IFacade f) {
		super(f);
		// TODO Auto-generated constructor stub
	}

    public void execute() throws Exception {
		PrintWriter out = response.getWriter();

        Long codigoUS = Long.parseLong(request.getInput("codUnidadeSaude"));
        try {

            IteratorDsk repEsp = facade.searchSpecialitiesByHealthUnit(codigoUS);
            
            out.println(HTMLCode.open("Queries - Especialties"));
            out.println("<body><h1>Querie result</h1>");
            
            out.println("<P><h3>Health unit: " + codigoUS+ " </h3></P>");
            out.println("<h3>Especialties :</h3>");

            while (repEsp.hasNext()) {
                MedicalSpeciality esp = (MedicalSpeciality) repEsp.next();
                out.println("<dd><dd>" + esp.getDescricao());
            
            }
            out.println(HTMLCode.closeQueries());            

        } catch (ObjectNotFoundException e) {
        	out.println(HTMLCode.errorPageQueries("This health unit does not have registered specialties."));
        } catch (RepositoryException e) {
            out.println("<P> " + e.getMessage() + " </P>");
        } catch (TransactionException e) {
            out.println("<P> " + e.getMessage() + " </P>");
        } catch (CommunicationException e) {
        	throw new FacadeUnavailableException();
        }catch(Exception e){
            out.println(lib.util.HTMLCode.errorPage("Comunitation error, please try again later."));
            e.printStackTrace(out);
        }finally {out.close();}
	}
}