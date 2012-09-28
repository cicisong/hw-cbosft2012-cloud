package healthwatcher.view.command;

import healthwatcher.Constants;
import healthwatcher.model.healthguide.HealthUnit;
import healthwatcher.view.IFacade;

import java.io.PrintWriter;

import lib.exceptions.CommunicationException;
import lib.exceptions.FacadeUnavailableException;
import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.RepositoryException;
import lib.exceptions.TransactionException;
import lib.util.HTMLCode;
import lib.util.IteratorDsk;

public class GetDataForSearchByHealthUnit extends Command {

	public GetDataForSearchByHealthUnit(IFacade f) {
		super(f);
		// TODO Auto-generated constructor stub
	}

	public void execute() throws Exception {
		PrintWriter out = response.getWriter();

		try {
			// The facade call must be the first thing, to be able to recover without spoiling the out
			IteratorDsk repUS = facade.getPartialHealthUnitList();
			
			out.println(HTMLCode.open("Queries - Specialties"));
			out
					.println("<body><h1>Queries:<br>Search Specialties of a Health unit</h1>");
			out.println("<p>Choose a health unit: </p>");
			out.println("<form method=\"POST\"action=\""+Constants.SYSTEM_ACTION+"?operation=SearchSpecialtiesByHealthUnit\">");
			
			out
					.println("<div align=\"center\"><center><p><select name=\"codUnidadeSaude\" size=\"1\">");

			if (!repUS.hasNext()) {
				out.println("</select></p></center></div>");
				out.println("<P> There isn't registered health units.</P>");
			} else {
				HealthUnit us;
				do {
					us = (HealthUnit) repUS.next();
					
					//#if relacional
					out.println("<option value=\"" + us.getId() + "\"> " 
							+ us.getDescription() + " </OPTION>");
					//#endif
					
					//#if norelacional
//@					out.println("<option value=\"" + us.getCode() + "\"> " //Thiago alterou aqui
//@							+ us.getDescription() + " </OPTION>");
					//#endif
				} while (repUS.hasNext());

				repUS.close();
				out.println("</select></p></center></div>");
				out
						.println("<div align=\"center\"><center><p><input type=\"submit\" value=\"Consultar\" name=\"B1\"></p></center></div></form>");
			}
			out.println(HTMLCode.closeQueries());
			out.close();
			
		} catch (ObjectNotFoundException e) {
			out.println("</select></p></center></div>");
			out.println("<P> " + e.getMessage() + " </P>");
			out.println("<P> Nenhuma unidade de saude foi cadastrada</P>");
		} catch (RepositoryException e) {
			out.println("</select></p></center></div>");
			out.println("<P> " + e.getMessage() + " </P>");
		} catch (TransactionException e) {
			out.println("</select></p></center></div>");
			out.println("<P> " + e.getMessage() + " </P>");
		} catch (CommunicationException e) {
			throw new FacadeUnavailableException();
		} catch (Exception e) {
			out.println(lib.util.HTMLCode
					.errorPage("Comunitation error, please try again later."));
			e.printStackTrace(out);
		}
	}
}
