package br.ufrgs.inf.dsmoura.repository.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.ufrgs.inf.dsmoura.repository.controller.solr.SolrFieldBoost;
import br.ufrgs.inf.dsmoura.repository.controller.solr.SolrServerUtil;
import br.ufrgs.inf.dsmoura.repository.model.entity.Asset;
import br.ufrgs.inf.dsmoura.repository.model.entity.FeedbackDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.UserDTO;



public class AssetDAO extends GenericDAO {
	
	private static AssetDAO instance;
	
	final Log logger = LogFactory.getLog(getClass());
	
	public static synchronized AssetDAO getInstance() {
		if (instance == null) {
			instance = new AssetDAO();
		}
		return instance;
	}

	private AssetDAO() {
	}
	
	/**
	 * Insert in the database and insert in the Solr.
	 */
	@Override
	public Asset insert(Serializable asset) {
		if (asset == null) {
			throw new IllegalArgumentException("Asset is null.");
		}
		if ( ! (asset instanceof Asset) ) {
			throw new IllegalArgumentException("asset variable is not a instance of Asset class.");
		}
		asset = super.insert(asset);
		SolrServerUtil.getInstance().saveAsset((Asset) asset);
		logger.info("Asset inserted and indexed with ID " + ((Asset) asset).getId());
		return (Asset) asset;
	}
	
	@Override
	public Asset update(Serializable asset) {
		if (asset == null) {
			throw new IllegalArgumentException("Asset is null.");
		}
		if ( ! (asset instanceof Asset) ) {
			throw new IllegalArgumentException("asset variable is not a instance of Asset class.");
		}
		if (((Asset)asset).getAssetPk() == null) {
			throw new IllegalArgumentException("Asset pk is null.");
		}
		((Asset)asset).getClassification().setAverageScore( evaluateQuality((Asset)asset) );
		asset = super.update(asset);
		SolrServerUtil.getInstance().saveAsset((Asset) asset);
		logger.info("Asset inserted and indexed with ID " + ((Asset) asset).getId());
		return (Asset) asset;  
	}
	
	public Asset findUniqueByPk(Integer assetPk) {
		if (assetPk == null) {
			throw new RuntimeException("assetPk is null");
		}
		EntityManager entityManager = createEntityManager();
		Query query = entityManager.createNamedQuery("Asset.findUniqueByPk");
		query.setParameter("assetPk", assetPk);
		Asset asset = (Asset) query.getSingleResult();
		return asset;
	}

	@SuppressWarnings("unchecked")
	public List<FeedbackDTO> listConsumptionFeedbacksByUser(UserDTO consumerUserDTO) {
		if (consumerUserDTO == null) {
			throw new RuntimeException("consumerUserDTO is null");
		}
		if (consumerUserDTO.getUsername() == null) {
			throw new RuntimeException("consumerUserDTO.username is null");
		}
		EntityManager entityManager = createEntityManager();
		Query query = entityManager.createNamedQuery("FeedbackDTO.listConsumptionFeedbacksByUser");
		query.setParameter("username", consumerUserDTO.getUsername());
		List<FeedbackDTO> feedbacks = query.getResultList();
		return feedbacks;
	}
	
	public Integer findAssetPk(String id, String version) {
		EntityManager entityManager = createEntityManager();
		Query query = entityManager.createNamedQuery("Asset.findAssetPk");
		query.setParameter("id", id);
		query.setParameter("version", version);
		try {
			return (Integer) query.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	public Asset findAssetByIDVersion(String id, String version) {
		if (id == null) {
			throw new RuntimeException("id is null");
		}
		if (version == null) {
			throw new RuntimeException("version is null");
		}
		EntityManager entityManager = createEntityManager();
		Query query = entityManager.createNamedQuery("Asset.findAssetByIDVersion");
		query.setParameter("id", id);
		query.setParameter("version", version);
		
		try {
			return (Asset) query.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	public Float getBoost(Asset asset) {
		
		double boost;
		if (asset.getState().getName().equalsIgnoreCase( TypesDAO.getInstance().getCertifiedAssetStateType().getName() )) {
			boost = SolrFieldBoost.CERTIFIED_BOOST;
		}
		else if (asset.getState().getName().equalsIgnoreCase( TypesDAO.getInstance().getDiscontinuedAssetStateType().getName() )) {
			boost = SolrFieldBoost.DISCONTINUED_BOOST;
		}
		else {
			boost = SolrFieldBoost.DEFAULT_BOOST;
		}
		
		if (asset.getClassification().getAverageScore() != null &&
				asset.getClassification().getReuseCounter() != null) {
   		int F = asset.getFeedbackDTOs().size();
   		int R = asset.getClassification().getReuseCounter();
   		
   		/* y->x : [1->0.5; 2->0.75; 3->1.0; 4-> 1.25; 5->1.5] */
   		float avgQ = (asset.getClassification().getAverageScore() / 4f) + 0.25f;
   		float K = F + (R-F)/2f;
   		double L = Math.log(K + Math.E);
   		
   		boost = boost * Math.pow(avgQ, L);
   		logger.info("F = " + F + "\n" +
         					"R = " + R + "\n" +
         					"AVG(Q) = " + avgQ + "\n" +
         					"K = F + (R-F)/2 = " + K + "\n" +
         					"L = ln(K + e) = " + L + "\n");
		}
		
		logger.info("boost = " + boost);
		
		return (float) boost;
	}
	
	private Float evaluateQuality(Asset asset) {
		if (asset.getFeedbackDTOs().size() == 0) {
			return null;
		}
		float qualityAcc = 0;
		float sizeCount = 0;
		for (FeedbackDTO feedbackDTO : asset.getFeedbackDTOs()) {
			if (feedbackDTO.getHasFeedback()) {
				qualityAcc += evaluateQuality(feedbackDTO);
				sizeCount++;
			}
		}
		if (sizeCount == 0) {
			return null;
		} else {
			return qualityAcc / sizeCount;
		}
	}
	
	public float evaluateQuality(FeedbackDTO feedbackDTO) {
		int counter = 0;
		float accScores = 0f;
		
		/* general score */
		Integer valueAux = feedbackDTO.getGeneralScore();				//TODO increase the general score relevancy
		if (valueAux != null) { accScores += valueAux; counter++; }
		
		/* software product quality */
		valueAux = feedbackDTO.getSoftwareProductQualityDTO().getFunctionalSuitabilityScore();
		if (valueAux != null) { accScores += valueAux; counter++; }
		
		valueAux = feedbackDTO.getSoftwareProductQualityDTO().getPerformanceEfficiencyScore();
		if (valueAux != null) { accScores += valueAux; counter++; }
		
		valueAux = feedbackDTO.getSoftwareProductQualityDTO().getCompatibilityScore();
		if (valueAux != null) { accScores += valueAux; counter++; }
		
		valueAux = feedbackDTO.getSoftwareProductQualityDTO().getUsabilityScore();
		if (valueAux != null) { accScores += valueAux; counter++; }
		
		valueAux = feedbackDTO.getSoftwareProductQualityDTO().getReliabilityScore();
		if (valueAux != null) { accScores += valueAux; counter++; }

		valueAux = feedbackDTO.getSoftwareProductQualityDTO().getSecurityScore();
		if (valueAux != null) { accScores += valueAux; counter++; }
		
		valueAux = feedbackDTO.getSoftwareProductQualityDTO().getMaintainabilityScore();
		if (valueAux != null) { accScores += valueAux; counter++; }
		
		valueAux = feedbackDTO.getSoftwareProductQualityDTO().getPortabilityScore();
		if (valueAux != null) { accScores += valueAux; counter++; }
		
		/* quality in use */
		valueAux = feedbackDTO.getQualityInUseDTO().getEffectivenessScore();
		if (valueAux != null) { accScores += valueAux; counter++; }
		
		valueAux = feedbackDTO.getQualityInUseDTO().getEfficiencyScore();
		if (valueAux != null) { accScores += valueAux; counter++; }
		
		valueAux = feedbackDTO.getQualityInUseDTO().getSatisfactionScore();
		if (valueAux != null) { accScores += valueAux; counter++; }
		
		valueAux = feedbackDTO.getQualityInUseDTO().getSafetyScore();
		if (valueAux != null) { accScores += valueAux; counter++; }
		
		valueAux = feedbackDTO.getQualityInUseDTO().getContextCoverageScore();
		if (valueAux != null) { accScores += valueAux; counter++; }
		
		if (logger.isInfoEnabled()) {
			logger.info(" accScores = " + accScores + " / " + counter + " = " + accScores/counter);
		}
		
		return accScores / counter;
	}
	
}
