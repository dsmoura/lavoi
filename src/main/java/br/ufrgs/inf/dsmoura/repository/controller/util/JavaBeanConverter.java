package br.ufrgs.inf.dsmoura.repository.controller.util;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

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
import br.ufrgs.inf.dsmoura.repository.model.entity.ProjectDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.RelatedAssetTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.SoftwareLicenseDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.SourceCodeTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.TestMethodTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.TestTypeDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserInterfaceTypeDTO;


public class JavaBeanConverter implements Converter {

	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		if (value != null) {
			return this.getAttributesFrom(component).get(value);
		}
		return null;
	}

	public String getAsString(FacesContext ctx, UIComponent component, Object value) {
		if (value != null && !"".equals(value)) {
			this.addAttribute(component, value);
			String codigo = getPk(value);
			if (codigo != null) {
				return codigo;
			}
		}
		return (String) value;
	}

	protected void addAttribute(UIComponent component, Object o) {
		String key = getPk(o);
		component.getAttributes().put(key, o);
	}

	protected Map<String, Object> getAttributesFrom(UIComponent component) {
		return component.getAttributes();
	}

	private String getPk(Object o) {
		if (o instanceof ApplicationDomain) {
			return ((ApplicationDomain) o).getApplicationdomainPk().toString();
		}
		if (o instanceof ApplicationSubdomain) {
			return ((ApplicationSubdomain) o).getApplicationsubdomainPk().toString();
		}
		if (o instanceof OrganizationDTO) {
			return ((OrganizationDTO) o).getOrganizationPk().toString();
		}
		if (o instanceof ProjectDTO) {
			return ((ProjectDTO) o).getProjectPk().toString();
		}
		if (o instanceof AssetStateType) {
			return ((AssetStateType) o).getAssetstatetypePk().toString();
		}
		if (o instanceof DesignPatternDTO) {
			return ((DesignPatternDTO) o).getDesignpatternPk().toString();
		}
		if (o instanceof ProgrammingLanguageDTO) {
			return ((ProgrammingLanguageDTO) o).getProgramminglanguagePk().toString();
		}
		if (o instanceof SourceCodeTypeDTO) {
			return ((SourceCodeTypeDTO) o).getSourcecodetypePk().toString();
		}
		if (o instanceof UserInterfaceTypeDTO) {
			return ((UserInterfaceTypeDTO) o).getUserinterfacetypePk().toString();
		}
		if (o instanceof SoftwareLicenseDTO) {
			return ((SoftwareLicenseDTO) o).getSoftwarelicensePk().toString();
		}
		if (o instanceof FunctionalRequirementTypeDTO) {
			return ((FunctionalRequirementTypeDTO) o).getFunctionalrequirementtypePk().toString();
		}
		if (o instanceof NonFunctionalRequirementTypeDTO) {
			return ((NonFunctionalRequirementTypeDTO) o).getNonfunctionalrequirementtypePk().toString();
		}
		if (o instanceof InternationalizationTypeDTO) {
			return ((InternationalizationTypeDTO) o).getInternationalizationtypePk().toString();
		}
		if (o instanceof OperationalSystemTypeDTO) {
			return ((OperationalSystemTypeDTO) o).getOperationalsystemtypePk().toString();
		}
		if (o instanceof TestTypeDTO) {
			return ((TestTypeDTO) o).getTesttypePk().toString();
		}
		if (o instanceof TestMethodTypeDTO) {
			return ((TestMethodTypeDTO) o).getTestmethodtypePk().toString();
		}
		if (o instanceof RelatedAssetTypeDTO) {
			return ((RelatedAssetTypeDTO) o).getRelatedassettypePk().toString();
		}
		if (o instanceof AssetType) {
			return ((AssetType) o).getAssettypePk().toString();
		}
		if (o instanceof ArtifactDependencyTypeDTO) {
			return ((ArtifactDependencyTypeDTO) o).getArtifactdependencytypePk().toString();
		}
		
		throw new RuntimeException("getPk method in JavaBeanConverter is not defined to " + o.getClass());
		
		//TODO refactoring
		
//		Object value;
//		String getPkMethod = "get";
//		try {
//			getPkMethod += o.getClass().getSimpleName().substring(0, 1).toUpperCase();
//			getPkMethod += o.getClass().getSimpleName().substring(1).toLowerCase();
//			getPkMethod += "Pk";
//			value = o.getClass().getMethod(getPkMethod, null).invoke(o, null);
//		} catch (Exception e) {
//			throw new RuntimeException("Error on call method " + getPkMethod + " of object " + o, e);
//		}
//		if (value != null) {
//			value.toString();
//		}
//		return null;
	}
}
