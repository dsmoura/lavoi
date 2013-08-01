package br.ufrgs.inf.dsmoura.repository.controller.asset;

import java.io.Serializable;

import org.ajax4jsf.model.KeepAlive;

import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;


@KeepAlive
public class WizardMB implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer state;
	private Integer maxState;
	
	private String validationMessage;
	
	private final static Integer GENERAL_STATE = 1;
	private final static Integer CLASSIFICATION_STATE = 2;
	private final static Integer EFFORT_STATE = 3;
	private final static Integer REQUIREMENTS_STATE = 4;
	private final static Integer ANALYSIS_STATE = 5;
	private final static Integer DESIGN_STATE = 6;
	private final static Integer IMPLEMENTATION_STATE = 7;
	private final static Integer TEST_STATE = 8;
	private final static Integer USAGE_STATE = 9;
	private final static Integer RELATED_ASSETS_STATE = 10;
	private final static Integer PUBLISH_STATE = 11;
	
	private final static Integer NUMBER_OF_STATES = 11;
	
	public WizardMB() {
		this.maxState = 0;
		openGeneral();
	}
	
	public WizardMB(Boolean isFullState) {
		this.maxState = isFullState ? NUMBER_OF_STATES : 0;
		openGeneral();
	}
	
	private boolean validate() {
		validationMessage = "";
		if (state == null) {
			return true;
		}
		else if(state.equals(GENERAL_STATE)) {
			AssetMB assetMB = JSFUtil.findBean("assetMB");
			validationMessage = assetMB.validate();
		}
		else if (state.equals(CLASSIFICATION_STATE)) {
			AssetMB assetMB = JSFUtil.findBean("assetMB");
			validationMessage = assetMB.getClassificationMB().validate();
		}
		else if (state.equals(REQUIREMENTS_STATE)) {
			AssetMB assetMB = JSFUtil.findBean("assetMB");
			validationMessage = assetMB.getRequirementsMB().validate();
		}
		else if (state.equals(ANALYSIS_STATE)) {
			AssetMB assetMB = JSFUtil.findBean("assetMB");
			validationMessage = assetMB.getAnalysisMB().validate();
		}
		else if (state.equals(DESIGN_STATE)) {
			AssetMB assetMB = JSFUtil.findBean("assetMB");
			validationMessage = assetMB.getDesignMB().validate();
		}
		else if (state.equals(IMPLEMENTATION_STATE)) {
			AssetMB assetMB = JSFUtil.findBean("assetMB");
			validationMessage = assetMB.getImplementationMB().validate();
		}
		else if (state.equals(TEST_STATE)) {
			AssetMB assetMB = JSFUtil.findBean("assetMB");
			validationMessage = assetMB.getTestMB().validate();
		}
		else if (state.equals(USAGE_STATE)) {
			AssetMB assetMB = JSFUtil.findBean("assetMB");
			validationMessage = assetMB.getUsageMB().validate();
		}
		else if (state.equals(RELATED_ASSETS_STATE)) {
			AssetMB assetMB = JSFUtil.findBean("assetMB");
			validationMessage = assetMB.getRelatedAssetsMB().validate();
		}
		return (validationMessage == null) ||
					(validationMessage.length() == 0);
	}
	
	public String back() {
		if (validate() && getCanBack()) {
			this.setState(this.state - 1);
		}
		return "";
	}
	
	public String next() {
		if (validate() && getCanNext()) {
			this.setState(this.state + 1);
		}
		return "";
	}
	
	public Boolean getCanBack() {
		return this.state > 1;
	}
	
	public Boolean getCanNext() {
		return this.state < NUMBER_OF_STATES;
	}
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		if (validate()) {
			this.state = state;
			if (this.state > maxState) {
				maxState = this.state;
			}
		}
	}
	
	public String openGeneral() {
		this.setState(GENERAL_STATE);
		return "";
	}
	public String openClassification() {
		this.setState(CLASSIFICATION_STATE);
		return "";
	}
	public String openEffort() {
		this.setState(EFFORT_STATE);
		return "";
	}
	public String openRequirements() {
		this.setState(REQUIREMENTS_STATE);
		return "";
	}
	public String openAnalysis() {
		this.setState(ANALYSIS_STATE);
		return "";
	}
	public String openDesign() {
		this.setState(DESIGN_STATE);
		return "";
	}
	public String openImplementation() {
		this.setState(IMPLEMENTATION_STATE);
		return "";
	}
	public String openTest() {
		this.setState(TEST_STATE);
		return "";
	}
	public String openUsage() {
		this.setState(USAGE_STATE);
		return "";
	}
	public String openRelatedAssets() {
		this.setState(RELATED_ASSETS_STATE);
		return "";
	}
	public String openPublish() {
		this.setState(PUBLISH_STATE);
		return "";
	}
	
	public Boolean getIsGeneralEnabled() {
		return maxState >= GENERAL_STATE;
	}
	public Boolean getIsClassificationEnabled() {
		return maxState >= CLASSIFICATION_STATE;
	}
	public Boolean getIsEffortEnabled() {
		return maxState >= EFFORT_STATE;
	}
	public Boolean getIsRequirementsEnabled() {
		return maxState >= REQUIREMENTS_STATE;
	}
	public Boolean getIsAnalysisEnabled() {
		return maxState >= ANALYSIS_STATE;
	}
	public Boolean getIsDesignEnabled() {
		return maxState >= DESIGN_STATE;
	}
	public Boolean getIsImplementationEnabled() {
		return maxState >= IMPLEMENTATION_STATE;
	}
	public Boolean getIsTestEnabled() {
		return maxState >= TEST_STATE;
	}
	public Boolean getIsUsageEnabled() {
		return maxState >= USAGE_STATE;
	}
	public Boolean getIsRelatedAssetsEnabled() {
		return maxState >= RELATED_ASSETS_STATE;
	}
	public Boolean getIsPublishEnabled() {
		return maxState >= PUBLISH_STATE;
	}
	
	public Boolean getIsGeneralOpened() {
		return state.equals(GENERAL_STATE);
	}
	public Boolean getIsClassificationOpened() {
		return state.equals(CLASSIFICATION_STATE);
	}
	public Boolean getIsEffortOpened() {
		return state.equals(EFFORT_STATE);
	}
	public Boolean getIsRequirementsOpened() {
		return state.equals(REQUIREMENTS_STATE);
	}
	public Boolean getIsAnalysisOpened() {
		return state.equals(ANALYSIS_STATE);
	}
	public Boolean getIsDesignOpened() {
		return state.equals(DESIGN_STATE);
	}
	public Boolean getIsImplementationOpened() {
		return state.equals(IMPLEMENTATION_STATE);
	}
	public Boolean getIsTestOpened() {
		return state.equals(TEST_STATE);
	}
	public Boolean getIsUsageOpened() {
		return state.equals(USAGE_STATE);
	}
	public Boolean getIsRelatedAssetsOpened() {
		return state.equals(RELATED_ASSETS_STATE);
	}
	public Boolean getIsPublishOpened() {
		return state.equals(PUBLISH_STATE);
	}
	
	public String getGeneralLabel() {
		return GENERAL_STATE + ".Description";
	}
	public String getClassificationLabel() {
		return CLASSIFICATION_STATE + ".Classification";
	}
	public String getEffortLabel() {
		return EFFORT_STATE + ".Effort";
	}
	public String getRequirementsLabel() {
		return REQUIREMENTS_STATE + ".Requirements";
	}
	public String getAnalysisLabel() {
		return ANALYSIS_STATE + ".Analysis";
	}
	public String getDesignLabel() {
		return DESIGN_STATE + ".Design";
	}
	public String getImplementationLabel() {
		return IMPLEMENTATION_STATE + ".Implementation";
	}
	public String getTestLabel() {
		return TEST_STATE + ".Test";
	}
	public String getUsageLabel() {
		return USAGE_STATE + ".Usage";
	}
	public String getRelatedAssetsLabel() {
		return RELATED_ASSETS_STATE + ".Related Assets";
	}
	public String getPublishLabel() {
		return PUBLISH_STATE + ".Publish and Final";
	}

	public String getValidationMessage() {
		return validationMessage;
	}
	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}
	
}
