package healthwatcher.view.command;

import healthwatcher.model.healthguide.MedicalSpeciality;
import healthwatcher.view.IFacade;

import java.io.PrintWriter;

import lib.exceptions.InvalidSessionException;
import lib.util.HTMLCode;

public class UpdateMedicalSpecialityData extends Command {
	
	public UpdateMedicalSpecialityData(IFacade f) { 
    super(f);
  }

	public void execute() throws Exception {
		PrintWriter out = response.getWriter();
		
		try {
        	MedicalSpeciality speciality;       
        
        	if (! request.isAuthorized()) {
                throw new InvalidSessionException();
            }        	
        	speciality = (MedicalSpeciality) request.get(UpdateMedicalSpecialitySearch.SPECIALITY);
            String descricao = request.getInput("descricao");            
            
            speciality.setDescricao(descricao);
        	facade.updateMedicalSpeciality(speciality);
            out.println(HTMLCode.htmlPageAdministrator("Operation executed", "Medical Speciality updated"));

        }catch(Exception e){
            out.println(HTMLCode.errorPage("Communication error, please try again later."));
            e.printStackTrace(out);
        } finally {out.close();}
	}

}
