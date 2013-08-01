package br.ufrgs.inf.dsmoura.repository.controller.asset;

import java.text.NumberFormat;

import br.ufrgs.inf.dsmoura.repository.model.entity.EffortDTO;


public class EffortMB {
	private AssetMB assetMB;
	
	public EffortMB(AssetMB assetMB) {
		this.assetMB = assetMB;
	}
	
	public EffortDTO getDTO() {
		return this.assetMB.getAsset().getEffortDTO();
	}
	
	public String getCurrencyCode() {
		return NumberFormat.getCurrencyInstance().getCurrency().getCurrencyCode();		
	}
	
	public Boolean getHasEffort() {
		return getDTO().getEstimatedTime() != null ||
				getDTO().getLinesOfCode() != null ||
				getDTO().getMonetary() != null ||
				getDTO().getRealTime() != null ||
				getDTO().getTeamMembers() != null;
	}

}
