package healthwatcher.view;

import healthwatcher.model.complaint.Complaint;
import healthwatcher.model.complaint.DiseaseType;
import healthwatcher.model.complaint.Symptom;
import healthwatcher.model.employee.Employee;
import healthwatcher.model.healthguide.HealthUnit;
import healthwatcher.model.healthguide.MedicalSpeciality;
import lib.exceptions.CommunicationException;
import lib.exceptions.InsertEntryException;
import lib.exceptions.ObjectAlreadyInsertedException;
import lib.exceptions.ObjectNotFoundException;
import lib.exceptions.ObjectNotValidException;
import lib.exceptions.RepositoryException;
import lib.exceptions.TransactionException;
import lib.exceptions.UpdateEntryException;
import lib.patterns.observer.Observer;
import lib.util.IteratorDsk;



public interface IFacade extends Observer {

	public void updateComplaint(Complaint q) throws TransactionException, RepositoryException,
			ObjectNotFoundException, ObjectNotValidException, CommunicationException;

	public IteratorDsk searchSpecialitiesByHealthUnit(Long code) throws ObjectNotFoundException,
			RepositoryException, CommunicationException, TransactionException;

	public Complaint searchComplaint(Long code) throws RepositoryException, ObjectNotFoundException,
			CommunicationException, TransactionException;

	public DiseaseType searchDiseaseType(Long code) throws RepositoryException,
			ObjectNotFoundException, CommunicationException, TransactionException;

	public IteratorDsk searchHealthUnitsBySpeciality(Long code) throws ObjectNotFoundException,
			RepositoryException, TransactionException, CommunicationException;

	public IteratorDsk getSpecialityList() throws RepositoryException, ObjectNotFoundException,
			CommunicationException, TransactionException;

	public IteratorDsk getDiseaseTypeList() throws RepositoryException, ObjectNotFoundException,
			CommunicationException, TransactionException;

	public IteratorDsk getHealthUnitList() throws RepositoryException, ObjectNotFoundException,
			CommunicationException, TransactionException;

	public IteratorDsk getPartialHealthUnitList() throws RepositoryException,
			ObjectNotFoundException, CommunicationException, TransactionException;

	public Long insertComplaint(Complaint complaint) throws RepositoryException,
			ObjectAlreadyInsertedException, CommunicationException, TransactionException,
			ObjectNotValidException;

	public void updateHealthUnit(HealthUnit unit) throws RepositoryException, TransactionException,
			ObjectNotFoundException, CommunicationException;

	public IteratorDsk getComplaintList() throws ObjectNotFoundException, TransactionException, 
			CommunicationException, RepositoryException;

	public void insert(Employee e) throws ObjectAlreadyInsertedException, ObjectNotValidException,
			InsertEntryException, TransactionException, CommunicationException, RepositoryException;

	public void updateEmployee(Employee e) throws TransactionException, RepositoryException,
			ObjectNotFoundException, ObjectNotValidException, UpdateEntryException,
			CommunicationException;

	public Employee searchEmployee(String login) throws TransactionException, RepositoryException,
			ObjectNotFoundException, ObjectNotValidException, UpdateEntryException,
			CommunicationException;

	public HealthUnit searchHealthUnit(Long healthUnitCode) throws ObjectNotFoundException,
			RepositoryException, CommunicationException, TransactionException;
	
	public void insert(HealthUnit us) throws InsertEntryException, ObjectAlreadyInsertedException, 
			ObjectNotValidException, TransactionException, CommunicationException, RepositoryException;

	public void insert(MedicalSpeciality speciality) throws ObjectAlreadyInsertedException,
			ObjectNotValidException, InsertEntryException, TransactionException,
			CommunicationException, RepositoryException;

	public void insert(Symptom symptom) throws ObjectAlreadyInsertedException,
			InsertEntryException, ObjectNotValidException, TransactionException,
			CommunicationException, RepositoryException;

	public Symptom searchSymptom(Long numSymptom) throws ObjectNotFoundException,
			RepositoryException, CommunicationException, TransactionException;

	public IteratorDsk getSymptomList() throws RepositoryException, ObjectNotFoundException,
			CommunicationException, TransactionException;

	public void updateSymptom(Symptom symptom) throws RepositoryException, TransactionException,
			ObjectNotFoundException, CommunicationException, ObjectNotValidException;

	public MedicalSpeciality searchSpecialitiesByCode(Long numSpeciality)
			throws ObjectNotFoundException, RepositoryException, CommunicationException,
			TransactionException;

	public void updateMedicalSpeciality(MedicalSpeciality speciality) throws RepositoryException,
			TransactionException, ObjectNotFoundException, CommunicationException,
			ObjectNotValidException;

	public void insert(DiseaseType diseaseType) throws InsertEntryException,
			ObjectAlreadyInsertedException, ObjectNotValidException, TransactionException,
			CommunicationException, RepositoryException;
}
