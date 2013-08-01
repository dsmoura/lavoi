package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;

import javax.persistence.*;

@Entity
public class AssetIDSequenceDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "assetidsequence_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="assetidsequence_generator")
	private Integer assetidsequencePk;

	public Integer getAssetidsequencePk() {
		return assetidsequencePk;
	}
	public void setAssetidsequencePk(Integer assetidsequencePk) {
		this.assetidsequencePk = assetidsequencePk;
	}
}
