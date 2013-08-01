package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import br.ufrgs.inf.dsmoura.repository.controller.util.FieldsUtil;


public abstract class Artifactable implements Comparable<Artifactable> {
	
	public abstract String getId();
	public abstract void setId(String id);

	public abstract String getName();
	public abstract void setName(String name);

	public abstract String getType();
	public abstract void setType(String type);
	
	public abstract String getDescription();
	public abstract void setDescription(String description);
	
	public abstract String getReference();
	public abstract void setReference(String reference);

	public abstract byte[] getFile();
	public abstract void setFile(byte[] file);
	
	public abstract Integer getSize();

	public abstract SoftwareLicenseDTO getSoftwareLicenseDTO();
	public abstract void setSoftwareLicenseDTO(SoftwareLicenseDTO softwareLicenseDTO);

	public abstract Long getRandomID();
	public abstract void setRandomID(Long randomID);

	public abstract String getLocation();
	public abstract void setLocation(String location);
	
	public abstract List<ArtifactDependencyDTO> getArtifactDependencyDTOs();
	public abstract void setArtifactDependencyDTOs(List<ArtifactDependencyDTO> artifactDependencyDTOs);
	
	public String getDescriptionNorm() {
		return FieldsUtil.normalize(getDescription());
	}
	
	@Override
	public int compareTo(Artifactable o) {
		try {
			return Long.valueOf( this.getId() ).compareTo( Long.valueOf( o.getId() ) );
		}
		catch(Exception e) {
			return -1;
		}
	}
	
	public boolean isValidURI() {
		if (this.getReference() != null &&
				this.getReference().length() > 0) {
			try {
				new URI(this.getReference());
				return true;
			} catch (URISyntaxException e) {
				return false;
			}
		}
		return false;
	}
}