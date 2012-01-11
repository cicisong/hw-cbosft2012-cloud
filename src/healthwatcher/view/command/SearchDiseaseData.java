package healthwatcher.view.command;

import healthwatcher.model.complaint.DiseaseType;
import healthwatcher.model.complaint.Symptom;
import healthwatcher.view.IFacade;

import java.io.PrintWriter;
import java.util.Iterator;

import lib.exceptions.CommunicationException;
import lib.exceptions.FacadeUnavailableException;
import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.RepositoryException;
import lib.exceptions.TransactionException;
import lib.util.HTMLCode;

public class SearchDiseaseData extends Command {

	public SearchDiseaseData(IFacade f) {
		super(f);
		// TODO Auto-generated constructor stub
	}

	public void execute() throws Exception {
		PrintWriter out = response.getWriter();

		Long codigoTipoDoenca = Long.parseLong(request
				.getInput("codTipoDoenca"));
		
		System.out.println(codigoTipoDoenca);
		try {
			DiseaseType tp = facade.searchDiseaseType(codigoTipoDoenca);
			System.out.println("Descricao: "+tp.getDescription());
			
			out.println(HTMLCode.open("Queries - Diseases"));
			out.println("<body><h1>Querie result<br>Disease</h1>");

			out.println("<P><h3>Name: " + tp.getName() + "</h3></P>");
			out.println("<P>Description: " + tp.getDescription() + "</P>");
			out.println("<P>How manifests: " + tp.getManifestation() + " </P>");
			out.println("<P>Duration: " + tp.getDuration() + " </P>");
			out.println("<P>Symptoms: </P>");

			Iterator i = tp.getSymptoms().iterator();

			if (!i.hasNext()) {
				out.println("<P>There isn't registered symptoms.</P>");
			} else {
				while (i.hasNext()) {
					Symptom s = (Symptom) i.next();
					out.println("<li> " + s.getDescription() + " </li>");
				}
			}
			out.println(HTMLCode.closeQueries());

		} catch (ObjectNotFoundException e) {
			out.println("<P> " + e.getMessage() + " </P>");
		} catch (RepositoryException e) {
			out.println("<P> " + e.getMessage() + " </P>");
		} catch (TransactionException e) {
			out.println("<P> " + e.getMessage() + " </P>");
		} catch (CommunicationException e) {
			throw new FacadeUnavailableException();
		} catch (Exception e) {
			out.println(lib.util.HTMLCode
					.errorPage("Comunitation error, please try again later."));
			e.printStackTrace(out);
		}finally {out.close();}
	}
}