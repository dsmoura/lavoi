package br.ufrgs.inf.dsmoura.repository.controller.asset;

import br.ufrgs.inf.dsmoura.repository.controller.search.SearchMB;
import br.ufrgs.inf.dsmoura.repository.controller.util.JSFUtil;
import br.ufrgs.inf.dsmoura.repository.model.dao.AssetDAO;
import br.ufrgs.inf.dsmoura.repository.model.entity.Asset;
import br.ufrgs.inf.dsmoura.repository.model.entity.RelatedAsset;

public class RelatedAssetsMB {
	
	private AssetMB assetMB;
	private RelatedAsset relatedAssetAux;
	
	private String relatedAssetIDMessage;
	private String relatedAssetNameMessage;
	private String relatedAssetVersionMessage;
	private String relatedAssetReferenceMessage;
	private String relatedAssetTypeMessage;
	
	public RelatedAssetsMB(AssetMB assetMB) {
		this.assetMB = assetMB;
		this.initMB();
	}
	
	public Asset getDTO() {
		return this.assetMB.getAsset();
	}
	
	private void initMB() {
		relatedAssetAux = new RelatedAsset();
		SearchMB searchMB = JSFUtil.findBean("searchMB");
		if (searchMB != null) {
			searchMB.clearSuggestedAssetsList();
		}
	}
	
	public String addRelatedAsset() {
		if(!this.validation()) {
			getDTO().getRelatedAssets().add( relatedAssetAux );
			relatedAssetAux = new RelatedAsset();
			this.loadSuggestedAssets();
		}
		return "";
	}
	
	private boolean validation() {
		relatedAssetIDMessage = "";
		relatedAssetNameMessage = "";
		relatedAssetVersionMessage = "";
		relatedAssetReferenceMessage = "";
		relatedAssetTypeMessage = "";
		
		boolean containsError = false;
		if ((relatedAssetAux.getId() == null) ||
				(relatedAssetAux.getId().trim().length() == 0)) {
			containsError = true;
			relatedAssetIDMessage = "Enter the ID.";
		}
		if ((relatedAssetAux.getName() == null) ||
				relatedAssetAux.getName().trim().length() == 0) {
			containsError = true;
			relatedAssetNameMessage = "Enter the Name.";
		}
		if ((relatedAssetAux.getVersion() == null) ||
				relatedAssetAux.getVersion().trim().length() == 0) {
			containsError = true;
			relatedAssetVersionMessage = "Enter the Version.";
		}
		if ((relatedAssetAux.getReference() == null) ||
				relatedAssetAux.getReference().trim().length() == 0) {
			containsError = true;
			relatedAssetReferenceMessage = "Enter the Reference.";
		}
		if ((relatedAssetAux.getRelatedAssetTypeDTO() == null) ||
				(relatedAssetAux.getRelatedAssetTypeDTO().getRelatedassettypePk() == null)) {
			containsError = true;
			relatedAssetTypeMessage = "Enter the Type.";
		}
		return containsError;
	}
	
	public String validate() {
		if (relatedAssetAux != null) {
			if (relatedAssetAux.getId().trim().length() > 0 ||
					relatedAssetAux.getName().trim().length() > 0 ||
					relatedAssetAux.getVersion().trim().length() > 0 ||
					relatedAssetAux.getReference().trim().length() > 0 ||
					relatedAssetAux.getRelatedAssetTypeDTO() != null) {
				return "Enter all fields then click in Add.";
			}
		}
		return "";
	}
	
	public String removeRelatedAsset() {
		Long relatedAssetRandomIDToRemove = Long.parseLong( JSFUtil.getRequestParameter("relatedAssetRandomIDToRemove") );
		for (RelatedAsset relatedAsset : getDTO().getRelatedAssets()) {
			if (relatedAsset.getRandomID().equals(relatedAssetRandomIDToRemove)) {
				getDTO().getRelatedAssets().remove( relatedAsset );
				break;
			}
		}
		this.loadSuggestedAssets();
		return "";
	}
	
	public String addSuggestedAsset() {
		String pkParam = JSFUtil.getRequestParameter("suggestedAssetPkToAdd");
		Asset suggestedAssetToAdd = AssetDAO.getInstance().findUniqueByPk(Integer.valueOf(pkParam));
		relatedAssetAux = new RelatedAsset();
		relatedAssetAux.setId(suggestedAssetToAdd.getId());
		relatedAssetAux.setName(suggestedAssetToAdd.getName());
		relatedAssetAux.setVersion(suggestedAssetToAdd.getVersion());
		relatedAssetAux.setReference(AssetMB.REFERENCE_MODEL);
		return "";
	}
	
	public String loadSuggestedAssets() {
		SearchMB searchMB = JSFUtil.findBean("searchMB");
		searchMB.searchSuggestedAssets(this.getDTO(), 10);
		return "";
	}
	
	public RelatedAsset getRelatedAssetAux() {
		return relatedAssetAux;
	}
	public void setRelatedAssetAux(RelatedAsset relatedAssetAux) {
		this.relatedAssetAux = relatedAssetAux;
	}

	public String getRelatedAssetIDMessage() {
		return relatedAssetIDMessage;
	}

	public void setRelatedAssetIDMessage(String relatedAssetIDMessage) {
		this.relatedAssetIDMessage = relatedAssetIDMessage;
	}

	public String getRelatedAssetNameMessage() {
		return relatedAssetNameMessage;
	}

	public void setRelatedAssetNameMessage(String relatedAssetNameMessage) {
		this.relatedAssetNameMessage = relatedAssetNameMessage;
	}

	public String getRelatedAssetVersionMessage() {
		return relatedAssetVersionMessage;
	}

	public void setRelatedAssetVersionMessage(String relatedAssetVersionMessage) {
		this.relatedAssetVersionMessage = relatedAssetVersionMessage;
	}

	public String getRelatedAssetReferenceMessage() {
		return relatedAssetReferenceMessage;
	}

	public void setRelatedAssetReferenceMessage(String relatedAssetReferenceMessage) {
		this.relatedAssetReferenceMessage = relatedAssetReferenceMessage;
	}

	public String getRelatedAssetTypeMessage() {
		return relatedAssetTypeMessage;
	}

	public void setRelatedAssetTypeMessage(String relatedAssetTypeMessage) {
		this.relatedAssetTypeMessage = relatedAssetTypeMessage;
	}

}
