package healthwatcher.view.command;


import healthwatcher.model.healthguide.HealthUnit;
import healthwatcher.view.IFacade;

import java.io.PrintWriter;

import lib.exceptions.InvalidSessionException;
import lib.util.HTMLCode;




public class UpdateHealthUnitData extends Command {

    public UpdateHealthUnitData(IFacade f) {
		super(f);
		// TODO Auto-generated constructor stub
	}

    public void execute() throws Exception {
		PrintWriter out = response.getWriter();

        HealthUnit unit;
        
        try {
        	if (! request.isAuthorized()) {
                throw new InvalidSessionException();
            }
        	
        	unit = (HealthUnit) request.get(UpdateHealthUnitSearch.HEALTH_UNIT);                        
            
            String descricao = request.getInput("descricao");            
            unit.setDescription(descricao);
            facade.updateHealthUnit(unit);
        	
            out.println(HTMLCode.htmlPageAdministrator("Operation executed", "Health Unit updated"));

        } catch(Exception e){
            out.println(lib.util.HTMLCode.errorPage("Comunitation error, please try again later."));
        } finally {out.close();}
	}
}
