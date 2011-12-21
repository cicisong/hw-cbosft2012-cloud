package healthwatcher.view.command;

import healthwatcher.model.address.Address;
import healthwatcher.model.complaint.Complaint;
import healthwatcher.model.complaint.SpecialComplaint;
import healthwatcher.view.IFacade;

import java.io.PrintWriter;
import java.util.Calendar;

import lib.exceptions.CommunicationException;
import lib.exceptions.FacadeUnavailableException;
import lib.exceptions.ObjectAlreadyInsertedException;
import lib.exceptions.ObjectNotValidException;
import lib.exceptions.RepositoryException;
import lib.exceptions.TransactionException;
import lib.util.Date;
import lib.util.HTMLCode;

public class InsertSpecialComplaint extends Command {

	public InsertSpecialComplaint(IFacade f) {
		super(f);
		// TODO Auto-generated constructor stub
	}

	public void execute() throws Exception {
		PrintWriter out = response.getWriter();

		try {
			// Queixa Normal
			String descricaoQueixa = request.getInput("descricaoQueixa");
			String observacaoQueixa = request.getInput("observacaoQueixa");
			String nomeSolicitante = request.getInput("nomeSolicitante");
			String ruaSolicitante = request.getInput("ruaSolicitante");
			String compSolicitante = request.getInput("compSolicitante");
			String bairroSolicitante = request
					.getInput("bairroSolicitante");
			String cidadeSolicitante = request
					.getInput("cidadeSolicitante");
			String ufSolicitante = request.getInput("ufSolicitante");
			String cepSolicitante = request.getInput("cepSolicitante");
			String telefoneSolicitante = request
					.getInput("telefoneSolicitante");
			Address endSolicitante = new Address(ruaSolicitante,
					compSolicitante, cepSolicitante, ufSolicitante,
					telefoneSolicitante, cidadeSolicitante, bairroSolicitante);
			String emailSolicitante = request.getInput("emailSolicitante");

			// Queixa Diversa
			short idade = Short.parseShort(request.getInput("idade"));
			String instrucao = request.getInput("instrucao");
			String ocupacao = request.getInput("ocupacao");
			String ruaOcorrencia = request.getInput("ruaOcorrencia");
			String compOcorrencia = request.getInput("compOcorrencia");
			String bairroOcorrencia = request.getInput("bairroOcorrencia");
			String cidadeOcorrencia = request.getInput("cidadeOcorrencia");
			String ufOcorrencia = request.getInput("ufOcorrencia");
			String cepOcorrencia = request.getInput("cepOcorrencia");
			String telefoneOcorrencia = request
					.getInput("telefoneOcorrencia");
			Address endOcorrencia = new Address(ruaOcorrencia, compOcorrencia,
					cepOcorrencia, ufOcorrencia, telefoneOcorrencia,
					cidadeOcorrencia, bairroOcorrencia);
			Calendar agora = Calendar.getInstance();

			Complaint queixa = new SpecialComplaint(nomeSolicitante,
					descricaoQueixa, observacaoQueixa, emailSolicitante, null,
					1, null, new Date(agora.get(Calendar.DAY_OF_MONTH), agora
							.get(Calendar.MONTH), agora.get(Calendar.YEAR)),
					endSolicitante, idade, instrucao, ocupacao, endOcorrencia);

			Long codigo = facade.insertComplaint(queixa);

			out.println(HTMLCode.htmlPage("Complaint inserted",
					"<p> <h2> Special Complaint inserted</h2> </p>"
							+ "<p> <h2> Save the complaint number: " + codigo
							+ "</h2> </p>"));
			out.close();

		} catch (RepositoryException e) {
			out.println(HTMLCode.errorPage("Problemas com o banco de dados"));
			e.printStackTrace(out);
		} catch (TransactionException e) {
			out
					.println(HTMLCode
							.errorPage("Erro no mecanismo de persist?ncia"));
			e.printStackTrace(out);
		} catch (ObjectAlreadyInsertedException e) {
			out.println(HTMLCode.errorPage("Esta queixa jah existe no BD"));
			e.printStackTrace(out);
		} catch (ObjectNotValidException e) {
			out.println(HTMLCode.errorPage("Erro ao inserir esta queixa"));
			e.printStackTrace(out);
		} catch (CommunicationException e) {
			throw new FacadeUnavailableException();
		} catch (Exception e) {
			out.println(lib.util.HTMLCode
					.errorPage("Comunitation error, please try again later."));
			e.printStackTrace(out);
		}
	}
}