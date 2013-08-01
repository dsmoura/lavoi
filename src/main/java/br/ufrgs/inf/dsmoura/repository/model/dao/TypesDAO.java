package br.ufrgs.inf.dsmoura.repository.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.ufrgs.inf.dsmoura.repository.controller.SystemPropertyEnum;
import br.ufrgs.inf.dsmoura.repository.model.entity.ApplicationDomain;
import br.ufrgs.inf.dsmoura.repository.model.entity.ApplicationSubdomain;
import br.ufrgs.inf.dsmoura.repository.model.entity.ArtifactDependencyTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.AssetStateType;
import br.ufrgs.inf.dsmoura.repository.model.entity.AssetType;
import br.ufrgs.inf.dsmoura.repository.model.entity.DesignPatternDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.FunctionalRequirementTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.InternationalizationTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.NonFunctionalRequirementTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.OperationalSystemTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.OrganizationDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ProgrammingLanguageDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.RelatedAssetTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.SoftwareLicenseDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.SourceCodeTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.SystemPropertyDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.TestMethodTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.TestTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserInterfaceTypeDTO;


public class TypesDAO extends GenericDAO {
	
	public static final String GNUv3SoftwareLicenseName = "GNU GPLv3 - GNU General Public License version 3";
	
	private static TypesDAO instance;
	
	final Log logger = LogFactory.getLog(getClass());
	
	protected EntityManager createEntityManager() {
		return Persistence.createEntityManagerFactory("lavoi").createEntityManager();
	}
	
	public static synchronized TypesDAO getInstance() {
		if (instance == null) {
			instance = new TypesDAO();
		}
		return instance;
	}
	
	public String getSystemPropertyValue(SystemPropertyEnum enumKey) {
		try {
			SystemPropertyDTO systemPropertyDTO = getSystemProperty(enumKey.getKey());
			return systemPropertyDTO.getValue();
		}
		catch(NoResultException nre) {
			logger.info("value not found in database for the key: " + enumKey.getKey());
			return null;
		}
	}
	
	public SystemPropertyDTO getSystemProperty(String key) {
		Query query = createEntityManager().createNamedQuery("SystemPropertyDTO.getByKey");
		query.setParameter("key", key);
		return  (SystemPropertyDTO) query.getSingleResult();
	}
	
	public List<AssetType> getAssetTypeList() {
		TypedQuery<AssetType> query = createEntityManager().createQuery("SELECT t FROM AssetType t",
																						AssetType.class);
		return query.getResultList();
	}
	
	public List<AssetStateType> getAssetStateTypeList() {
		TypedQuery<AssetStateType> query = createEntityManager().createQuery("SELECT t FROM AssetStateType t",
																							AssetStateType.class);
		return query.getResultList();
	}
	
	public AssetStateType getCertifiedAssetStateType() {
		List<AssetStateType> states = this.getAssetStateTypeList();
		for (AssetStateType state : states) {
			if (state.getName().equalsIgnoreCase("Certified")) {
				return state;
			}
		}
		throw new RuntimeException("Certified asset state type was not found.");
	}
	
	public AssetStateType getReadyForReuseAssetStateType() {
		List<AssetStateType> states = this.getAssetStateTypeList();
		for (AssetStateType state : states) {
			if (state.getName().equalsIgnoreCase("Ready For Reuse")) {
				return state;
			}
		}
		throw new RuntimeException("Ready For Reuse asset state type was not found.");
	}
	
	public AssetStateType getInDefinitionAssetStateType() {
		List<AssetStateType> states = this.getAssetStateTypeList();
		for (AssetStateType state : states) {
			if (state.getName().equalsIgnoreCase("In Definition")) {
				return state;
			}
		}
		throw new RuntimeException("In Definition asset state type was not found. You may need to load the lists in database in the page lavoi/load.jsf");
	}
	
	public AssetStateType getInReviewAssetStateType() {
		List<AssetStateType> states = this.getAssetStateTypeList();
		for (AssetStateType state : states) {
			if (state.getName().equalsIgnoreCase("In Review")) {
				return state;
			}
		}
		throw new RuntimeException("In Review asset state type was not found.");
	}
	
	public AssetStateType getDiscontinuedAssetStateType() {
		List<AssetStateType> states = this.getAssetStateTypeList();
		for (AssetStateType state : states) {
			if (state.getName().equalsIgnoreCase("Discontinued")) {
				return state;
			}
		}
		throw new RuntimeException("Discontinued asset state type was not found.");
	}
	
	public List<ApplicationDomain> getApplicationsDomainList() {
		TypedQuery<ApplicationDomain> query = createEntityManager().createQuery("SELECT t FROM ApplicationDomain t",
																								ApplicationDomain.class);
		return query.getResultList();
	}
	
	public ApplicationDomain getApplicationDomainByPk(String domainName) {
		Query query = createEntityManager().createNamedQuery("ApplicationDomain.getByPk");
		query.setParameter("pk", domainName);
		return  (ApplicationDomain) query.getSingleResult();
	}
	
	public List<OrganizationDTO> getOrganizationDTOList() {
		TypedQuery<OrganizationDTO> query = createEntityManager().createQuery("SELECT t FROM OrganizationDTO t",
																							OrganizationDTO.class);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ApplicationSubdomain> getApplicationSubdomainList(ApplicationDomain applicationDomain) {
		Query query = createEntityManager().createNamedQuery("ApplicationSubdomain.listByApplicationDomainPk");
		query.setParameter("pk", applicationDomain.getApplicationdomainPk());
		List<ApplicationSubdomain> aSList = query.getResultList();
		return aSList;
	}
	
	public List<DesignPatternDTO> getDesignPatternDTOList() {
		TypedQuery<DesignPatternDTO> query = createEntityManager().createQuery("SELECT t FROM DesignPatternDTO t",
																								DesignPatternDTO.class);
		return query.getResultList();
	}
	
	public List<ProgrammingLanguageDTO> getProgrammingLanguageDTOList() {
		TypedQuery<ProgrammingLanguageDTO> query = createEntityManager().createQuery("SELECT t FROM ProgrammingLanguageDTO t",
																										ProgrammingLanguageDTO.class);
		return query.getResultList();
	}

	public List<SourceCodeTypeDTO> getSourceCodeTypeDTOList() {
		TypedQuery<SourceCodeTypeDTO> query = createEntityManager().createQuery("SELECT t FROM SourceCodeTypeDTO t",
																								SourceCodeTypeDTO.class);
		return query.getResultList();
	}

	public List<UserInterfaceTypeDTO> getUserInterfaceTypeDTOList() {
		TypedQuery<UserInterfaceTypeDTO> query = createEntityManager().createQuery("SELECT t FROM UserInterfaceTypeDTO t",
																									UserInterfaceTypeDTO.class);
		return query.getResultList();
	}
	
	public List<SoftwareLicenseDTO> getSoftwareLicenseDTOList() {
		TypedQuery<SoftwareLicenseDTO> query = createEntityManager().createQuery("SELECT t FROM SoftwareLicenseDTO t",
																								SoftwareLicenseDTO.class);
		return query.getResultList();
	}

	public List<FunctionalRequirementTypeDTO> getFunctionalRequirementTypeDTOList() {
		TypedQuery<FunctionalRequirementTypeDTO> query = createEntityManager().createQuery("SELECT t FROM FunctionalRequirementTypeDTO t",
																												FunctionalRequirementTypeDTO.class);
		return query.getResultList();
	}

	public List<NonFunctionalRequirementTypeDTO> getNonFunctionalRequirementTypeDTOList() {
		TypedQuery<NonFunctionalRequirementTypeDTO> query = createEntityManager().createQuery("SELECT t FROM NonFunctionalRequirementTypeDTO t",
																													NonFunctionalRequirementTypeDTO.class);
		return query.getResultList();
	}

	public List<InternationalizationTypeDTO> getInternationalizationTypeDTOList() {
		TypedQuery<InternationalizationTypeDTO> query = createEntityManager().createQuery("SELECT t FROM InternationalizationTypeDTO t",
																												InternationalizationTypeDTO.class);
		return query.getResultList();
	}

	public List<OperationalSystemTypeDTO> getOperationalSystemTypeDTOList() {
		TypedQuery<OperationalSystemTypeDTO> query = createEntityManager().createQuery("SELECT t FROM OperationalSystemTypeDTO t",
																											OperationalSystemTypeDTO.class);
		return query.getResultList();
	}

	public List<TestTypeDTO> getTestTypeDTOList() {
		TypedQuery<TestTypeDTO> query = createEntityManager().createQuery("SELECT t FROM TestTypeDTO t",
																						TestTypeDTO.class);
      return query.getResultList();
	}

	public List<TestMethodTypeDTO> getTestMethodTypeDTOList() {
		TypedQuery<TestMethodTypeDTO> query = createEntityManager().createQuery("SELECT t FROM TestMethodTypeDTO t",
																								TestMethodTypeDTO.class);
		return query.getResultList();
	}

	public List<RelatedAssetTypeDTO> getRelatedAssetTypeDTOList() {
		TypedQuery<RelatedAssetTypeDTO> query = createEntityManager().createQuery("SELECT t FROM RelatedAssetTypeDTO t",
																									RelatedAssetTypeDTO.class);
		return query.getResultList();
	}
	
	public List<ArtifactDependencyTypeDTO> getArtifactDependencyTypeDTOList() {
		TypedQuery<ArtifactDependencyTypeDTO> query = createEntityManager().createQuery("SELECT t FROM ArtifactDependencyTypeDTO t",
																											ArtifactDependencyTypeDTO.class);
		return query.getResultList();
	}
	
	public SoftwareLicenseDTO getGNUv3SoftwareLicenseDTO() {
		Query query = createEntityManager().createNamedQuery("SoftwareLicenseDTO.findByName");
		query.setParameter("name", GNUv3SoftwareLicenseName);
		return (SoftwareLicenseDTO) query.getSingleResult();
	}

}
