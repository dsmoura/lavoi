package br.ufrgs.inf.dsmoura.repository.controller.validator;

import br.ufrgs.inf.dsmoura.repository.model.entity.ArtifactDependencyDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.Artifactable;

public class ValidationUtil {
	public static boolean validate(Artifactable artifact) {
		if (artifact.getName() == null ||
				artifact.getName().trim().isEmpty() ||
				artifact.getReference() == null ||
				artifact.getReference().trim().isEmpty()) {
			return false;
		}
		for (ArtifactDependencyDTO artifactDependencyDTO : artifact.getArtifactDependencyDTOs()) {
			if (artifactDependencyDTO.getArtifactID() == null ||
					artifactDependencyDTO.getArtifactID().trim().isEmpty() ||
					artifactDependencyDTO.getArtifactDependencyTypeDTO() == null) {
				return false;
			}
		}
		return true;
	}
}
