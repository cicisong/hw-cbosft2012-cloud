package healthwatcher.view.command;

import healthwatcher.Constants;
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

public class GetDataForSearchBySpeciality extends Command {

	public GetDataForSearchBySpeciality(IFacade f) {
		super(f);
		// TODO Auto-generated constructor stub
	}

	public void execute() throws Exception {
		PrintWriter out = response.getWriter();

		try {

			// The facade call must be the first thing, to be able to recover without spoiling the out
			IteratorDsk repEsp = facade.getSpecialityList();
			
			out.println(HTMLCode.open("Queries - Health Units"));
			out
				.println("<body><h1>Queries:<br>Search Health units by Medical specialty</h1>");
			out.println("<p>Choose a specialty: </p>");
			out.println("<form method=\"POST\"action=\""+Constants.SYSTEM_ACTION+"?operation=SearchHealthUnitsBySpecialty\">");

			out
					.println("<div align=\"center\"><center><p><select name=\"codEspecialidade\" size=\"1\">");


			if (!repEsp.hasNext()) {
				out.println("</select></p></center></div>");
				out.println("<P>There isn't registered specialties.</P>");
			} else {
				MedicalSpeciality esp;

				do {
					esp = (MedicalSpeciality) repEsp.next();
					
					//#if relacional
					out.println("<option value=\"" + esp.getId() + "\"> "
							+ esp.getDescricao() + " </OPTION>");
					//#endif
					
					//#if norelacional
//@					out.println("<option value=\"" + esp.getCode() + "\"> "//thiago alterou aqui
//@							+ esp.getDescricao() + " </OPTION>");
					//#endif
				} while (repEsp.hasNext());

				repEsp.close();
				out.println("</select></p></center></div>");
				out
						.println(" <div align=\"center\"><center><p><input type=\"submit\" value=\"Consultar\" name=\"B1\"></p></center></div></form>");
			}
			out.println(HTMLCode.closeQueries());
			out.close();
		
		} catch (ObjectNotFoundException e) {
			out.println("</select></p></center></div>");
			out.println("<P> " + e.getMessage() + " </P>");
			out.println("<P> Nenhuma especialidade foi cadastrada</P>");
		} catch (RepositoryException e) {
			out.println("</select></p></center></div>");
			out.println("<P> " + e.getMessage() + " </P>");
		} catch (TransactionException e) {
			out.println("</select></p></center></div>");
			out.println("<P> " + e.getMessage() + " </P>");
		} catch (CommunicationException e) {
			throw new FacadeUnavailableException();
		} catch (Exception e) {
			out.println("Error!");
			out.println(lib.util.HTMLCode
					.errorPage("Comunitation error, please try again later."));
			e.printStackTrace(out);
		}
	}
}
