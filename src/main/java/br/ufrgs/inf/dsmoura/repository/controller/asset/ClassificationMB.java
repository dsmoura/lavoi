package br.ufrgs.inf.dsmoura.repository.controller.asset;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.ufrgs.inf.dsmoura.repository.controller.ListMB;
import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;
import br.ufrgs.inf.dsmoura.repository.model.entity.ApplicationDomain;
import br.ufrgs.inf.dsmoura.repository.model.entity.ApplicationSubdomain;
import br.ufrgs.inf.dsmoura.repository.model.entity.Classification;
import br.ufrgs.inf.dsmoura.repository.model.entity.DescriptorGroupDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.FreeFormDescriptorDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.OrganizationDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.ProjectDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.TagDTO;



public class ClassificationMB implements Validation {
	private AssetMB assetMB;
	
	private ApplicationDomain applicationDomainAux;
	private ApplicationSubdomain applicationSubdomainAux;
	private OrganizationDTO organizationDTOAux;
	private ProjectDTO projectDTOAux;
	private DescriptorGroupDTO descriptorGroupDTOAux;
	
	public ClassificationMB(AssetMB assetMB) {
		this.assetMB = assetMB;
		this.initMB();
	}
	
	private void initMB() {
		applicationDomainAux = new ApplicationDomain();
		applicationSubdomainAux = new ApplicationSubdomain();
		organizationDTOAux = new OrganizationDTO();
		projectDTOAux = new ProjectDTO();
		descriptorGroupDTOAux = new DescriptorGroupDTO();
	}
	
	@Override
	public String validate() {
//		if (applicationDomainAux != null || applicationSubdomainAux != null) {
//			return "Select the application domain and subdomain then click in Add.";
//		}
		ListMB listMB = JSFUtil.findBean("listMB");
		boolean hasOrganizations = listMB.getOrganizationDTOValues().size() > 0;
		if ( hasOrganizations && ( organizationDTOAux != null || projectDTOAux != null)) {
			return "Select the organization and project then click in Add.";
		}
		if (getDTO().getTagDTOs().size() < 2) {
			return "Enter at least two tags.";
		}
		String messageDescriptor = "Enter all blank fields of descriptors groups.";
		for (DescriptorGroupDTO groupDTO : getDTO().getDescriptorGroupDTOs()) {
			if (groupDTO.getName().isEmpty()) {
				return messageDescriptor;
			}
			for (FreeFormDescriptorDTO descriptorDTO : groupDTO.getFreeFormDescriptorDTOs()) {
				if (descriptorDTO.getName().isEmpty() ||
						descriptorDTO.getFreeFormValue().isEmpty()) {
					return messageDescriptor;
				}
			}
		}
		return "";
	}
	
	public Integer getQualityStars() {
		if (this.getDTO().getAverageScore() != null) {
			if (this.getDTO().getAverageScore() <= 0) {
				return this.getDTO().getAverageScore().intValue();
			} else {
				return (int) (getDTO().getAverageScore()*2);
			}
		}
		return null;
	}
	
	/* SUBDOMAIN */
	
	public String addSubdomain() {
		if ((applicationSubdomainAux != null) && (applicationSubdomainAux.getApplicationsubdomainPk() != null)) {
			if ( ! getDTO().getApplicationSubdomains().contains( applicationSubdomainAux ) ) {
				getDTO().getApplicationSubdomains().add( applicationSubdomainAux );
			}
			applicationDomainAux = new ApplicationDomain();
			applicationSubdomainAux = new ApplicationSubdomain();
		}
		return "";
	}
	
	public String removeSubdomain() {
		int appSubdomainPkToRemove = Integer.parseInt( JSFUtil.getRequestParameter("appSubdomainPkToRemove") );
		for (ApplicationSubdomain appSubdomain : getDTO().getApplicationSubdomains()) {
			if (appSubdomain.getApplicationsubdomainPk().intValue() == appSubdomainPkToRemove) {
				getDTO().getApplicationSubdomains().remove( appSubdomain );
				break;
			}
		}
		return "";
	}
	
	/* PROJECT */
	
	public String addProject() {
		if ((projectDTOAux != null) && (projectDTOAux.getProjectPk() != null)) {
			if ( ! getDTO().getProjectDTOs().contains( projectDTOAux ) ) {
				getDTO().getProjectDTOs().add( projectDTOAux );
			}
			organizationDTOAux = new OrganizationDTO();
			projectDTOAux = new ProjectDTO();
		}
		return "";
	}
	
	public String removeProject() {
		int appSubdomainPkToRemove = Integer.parseInt( JSFUtil.getRequestParameter("projectPkToRemove") );
		for (ProjectDTO projDTO : getDTO().getProjectDTOs()) {
			if (projDTO.getProjectPk().intValue() == appSubdomainPkToRemove) {
				getDTO().getProjectDTOs().remove( projDTO );
				break;
			}
		}
		return "";
	}
	
	/* DESCRIPTORS GROUPS */
	
	public String addNewDescriptorGroup() {
		DescriptorGroupDTO descriptorGroupDTO = new DescriptorGroupDTO();
		descriptorGroupDTO.getFreeFormDescriptorDTOs().add( new FreeFormDescriptorDTO() );
		getDTO().getDescriptorGroupDTOs().add( descriptorGroupDTO );
		return "";
	}
	
	public String addDescriptorGroup() {
		Long descriptorGroupRandomIDToAdd = Long.valueOf( JSFUtil.getRequestParameter("descriptorGroupRandomIDToAdd") );
		for (DescriptorGroupDTO groupDTO : getDTO().getDescriptorGroupDTOs()) {
			if (groupDTO.getRandomID().equals(descriptorGroupRandomIDToAdd)) {
				int index = getDTO().getDescriptorGroupDTOs().indexOf(groupDTO);
				DescriptorGroupDTO descriptorGroupDTO = new DescriptorGroupDTO();
				descriptorGroupDTO.getFreeFormDescriptorDTOs().add( new FreeFormDescriptorDTO() );
				getDTO().getDescriptorGroupDTOs().add( index + 1, descriptorGroupDTO );
				break;
			}
		}
		return "";
	}
	
	public String removeDescriptorGroup() {
		Long descriptorGroupRandomIDToRemove = Long.valueOf( JSFUtil.getRequestParameter("descriptorGroupRandomIDToRemove") );
		for (DescriptorGroupDTO groupDTO : getDTO().getDescriptorGroupDTOs()) {
			if (groupDTO.getRandomID().equals(descriptorGroupRandomIDToRemove)) {
				getDTO().getDescriptorGroupDTOs().remove(groupDTO);
				break;
			}
		}
		return "";
	}
	
	public String addDescriptor() {
		Long descriptorGroupRandomIDToAdd = Long.valueOf( JSFUtil.getRequestParameter("descriptorGroupRandomIDToAdd") );
		Long descriptorRandomIDToAdd = Long.valueOf( JSFUtil.getRequestParameter("descriptorRandomIDToAdd") );
		for (DescriptorGroupDTO groupDTO : getDTO().getDescriptorGroupDTOs()) {
			if (groupDTO.getRandomID().equals(descriptorGroupRandomIDToAdd)) {
				for (FreeFormDescriptorDTO descriptorDTO : groupDTO.getFreeFormDescriptorDTOs()) {
					if (descriptorDTO.getRandomID().equals( descriptorRandomIDToAdd )) {
						int index = groupDTO.getFreeFormDescriptorDTOs().indexOf(descriptorDTO);
						groupDTO.getFreeFormDescriptorDTOs().add(index + 1, new FreeFormDescriptorDTO() );
						break;
					}
				}
			}
		}
		return "";
	}
	
	public String removeDescriptor() {
		Long descriptorGroupRandomIDToAdd = Long.valueOf( JSFUtil.getRequestParameter("descriptorGroupRandomIDToRemove") );
		Long descriptorRandomIDToRemove = Long.valueOf( JSFUtil.getRequestParameter("descriptorRandomIDToRemove") );
		outFor:
		for (DescriptorGroupDTO groupDTO : getDTO().getDescriptorGroupDTOs()) {
			if (groupDTO.getRandomID().equals(descriptorGroupRandomIDToAdd)) {
				for (FreeFormDescriptorDTO descriptorDTO : groupDTO.getFreeFormDescriptorDTOs()) {
					if (descriptorDTO.getRandomID().equals( descriptorRandomIDToRemove )) {
						groupDTO.getFreeFormDescriptorDTOs().remove(descriptorDTO);
						if (groupDTO.getFreeFormDescriptorDTOs().isEmpty()) {
							groupDTO.getFreeFormDescriptorDTOs().add( new FreeFormDescriptorDTO() );
						}
						break outFor;
					}
				}
			}
		}
		return "";
	}
	
	/* LISTS */
	
	public List<SelectItem> getApplicationSubdomainList() {
		if (applicationDomainAux == null) {
			applicationDomainAux = new ApplicationDomain();
		}
		if ((applicationDomainAux.getApplicationSubdomains() == null) ||
				(applicationDomainAux.getApplicationSubdomains().isEmpty())) {
			List<SelectItem> list = new ArrayList<SelectItem>();
			list.add(new SelectItem(null, "Select application domain."));
			return list;
		}
		return JSFUtil.toSelectItem(applicationDomainAux.getApplicationSubdomains(), "name");
	}
	
	public List<SelectItem> getProjectDTOList() {
		if (organizationDTOAux == null) {
			organizationDTOAux = new OrganizationDTO();
		}
		if ((organizationDTOAux.getProjectDTOs() == null) ||
				(organizationDTOAux.getProjectDTOs().isEmpty())) {
			List<SelectItem> list = new ArrayList<SelectItem>();
			list.add(new SelectItem(null, "Select organization."));
			return list;
		}
		return JSFUtil.toSelectItem(organizationDTOAux.getProjectDTOs(), "name");
	}
	
	public Boolean getContainsApplicationSubdomains() {
		return (getDTO().getApplicationSubdomains() != null) &&
					(getDTO().getApplicationSubdomains().size() > 0); 
	}
	
	public Boolean getContainsProjectDTOs() {
		return (getDTO().getProjectDTOs() != null) &&
					(getDTO().getProjectDTOs().size() > 0); 
	}
	
	public Classification getDTO() {
		return this.assetMB.getAsset().getClassification();
	}

	public ApplicationDomain getApplicationDomainAux() {
		return applicationDomainAux;
	}

	public void setApplicationDomainAux(ApplicationDomain applicationDomainAux) {
		this.applicationDomainAux = applicationDomainAux;
	}

	public ApplicationSubdomain getApplicationSubdomainAux() {
		return applicationSubdomainAux;
	}

	public void setApplicationSubdomainAux(
			ApplicationSubdomain applicationSubdomainAux) {
		this.applicationSubdomainAux = applicationSubdomainAux;
	}

	public OrganizationDTO getOrganizationDTOAux() {
		return organizationDTOAux;
	}

	public void setOrganizationDTOAux(OrganizationDTO organizationDTOAux) {
		this.organizationDTOAux = organizationDTOAux;
	}

	public ProjectDTO getProjectDTOAux() {
		return projectDTOAux;
	}

	public void setProjectDTOAux(ProjectDTO projectDTOAux) {
		this.projectDTOAux = projectDTOAux;
	}
	
	public DescriptorGroupDTO getDescriptorGroupDTOAux() {
		return descriptorGroupDTOAux;
	}

	public void setDescriptorGroupDTOAux(DescriptorGroupDTO descriptorGroupDTOAux) {
		this.descriptorGroupDTOAux = descriptorGroupDTOAux;
	}
	
	public String getTags() {
		String s = "";
		for (TagDTO t : this.getDTO().getTagDTOs()) {
			s += t.getName() + ", ";
		}
		if (s.endsWith(", ")) {
			s = s.substring(0, s.length()-2);
		}
		return s;
	}

	public void setTags(String tags) {
		this.getDTO().getTagDTOs().clear();
		String[] tagList = tags.split(",");
		for (String s : tagList) {
			if (s.trim().length() > 0) {
				TagDTO tagDTO = new TagDTO(); 
				tagDTO.setName(s.trim());
				this.getDTO().getTagDTOs().add(tagDTO);
			}
		}
	}

}
