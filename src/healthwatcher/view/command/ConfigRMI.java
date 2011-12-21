package healthwatcher.view.command;

import healthwatcher.view.IFacade;

import java.io.PrintWriter;

import lib.util.HTMLCode;


public class ConfigRMI extends Command {

	public ConfigRMI(IFacade f) {
		super(f);
		// TODO Auto-generated constructor stub
	}

	public void execute() throws Exception {
		PrintWriter out = response.getWriter();

		try {
			out.println(HTMLCode.htmlPage("Health-Watcher 2003", "Server name stored"));
		} catch (Exception e) {
			out.println(HTMLCode.errorPageAdministrator(e.getMessage()));
		}
	}
}