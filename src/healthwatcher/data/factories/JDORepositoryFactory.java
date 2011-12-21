package healthwatcher.data.factories;

import lib.persistence.IPersistenceMechanism;
import healthwatcher.data.IComplaintRepository;
import healthwatcher.data.IDiseaseRepository;
import healthwatcher.data.IEmployeeRepository;
import healthwatcher.data.IHealthUnitRepository;
import healthwatcher.data.ISpecialityRepository;
import healthwatcher.data.ISymptomRepository;
import healthwatcher.data.jdo.ComplaintRepositoryJDO;
import healthwatcher.data.jdo.DiseaseTypeRepositoryJDO;
import healthwatcher.data.jdo.EmployeeRepositoryJDO;
import healthwatcher.data.jdo.HealthUnitRepositoryJDO;
import healthwatcher.data.jdo.SpecialityRepositoryJDO;
import healthwatcher.data.jdo.SymptomRepositoryJDO;

public class JDORepositoryFactory extends AbstractRepositoryFactory {
	
	protected IPersistenceMechanism pm = null;
	
	public JDORepositoryFactory(IPersistenceMechanism pm){
		this.pm = pm;
	}

	public IComplaintRepository createComplaintRepository() {
		return new ComplaintRepositoryJDO(pm);
	}
	
	public IHealthUnitRepository createHealthUnitRepository() {
		return new HealthUnitRepositoryJDO(pm);
	}
	
	public ISpecialityRepository createMedicalSpecialityRepository() {
		return new SpecialityRepositoryJDO(pm);
	}
	
	public IDiseaseRepository createDiseaseRepository() {
		return new DiseaseTypeRepositoryJDO(pm);
	}
	
	public IEmployeeRepository createEmployeeRepository() {
		return new EmployeeRepositoryJDO(pm);
	}
	
	public ISymptomRepository createSymptomRepository() {
		return new SymptomRepositoryJDO(pm);
	}
}
