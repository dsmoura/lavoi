package br.ufrgs.inf.dsmoura.repository.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import org.ajax4jsf.model.KeepAlive;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.solr.client.solrj.response.FacetField.Count;

import br.ufrgs.inf.dsmoura.repository.controller.asset.NavigationMB;
import br.ufrgs.inf.dsmoura.repository.controller.solr.SolrField;
import br.ufrgs.inf.dsmoura.repository.controller.solr.SolrServerUtil;
import br.ufrgs.inf.dsmoura.repository.model.entity.TagDTO;


@KeepAlive
public class TagsMB implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private List<TagDTO> tagsDTOValues;
	
	List<SelectItem> typesList;
	
	private String tagCloudType = SolrField.TAG.getName();
	
	private final Integer TAGS_INCREMENT = 50;
	private final Integer MIN_TAGS_SIZE = 100;
	private final Integer MAX_TAGS_SIZE = 500;
	
	private Integer tagsSize = MIN_TAGS_SIZE;
	
	private final Integer MAIN_TAGS_AMOUNT = 50;
	
	final Log logger = LogFactory.getLog(getClass());
	
	public String openTags() {
		tagCloudType = SolrField.TAG.getName();
		tagsSize = MIN_TAGS_SIZE;
		refreshTags();
		return NavigationMB.TAGS;
	}
	
	public void refreshMainTags() {
		tagCloudType = SolrField.TAG.getName();
		tagsSize = MAIN_TAGS_AMOUNT;
		refreshTags();
	}
	
	public synchronized List<TagDTO> getTags() {
		if (tagsDTOValues == null) {
			refreshMainTags();
		}
		return tagsDTOValues;
	}
	
	public void changeTagCloudType() {
		tagsSize = MIN_TAGS_SIZE;
		refreshTags();
	}
	
	public void refreshTags() {
		logger.info("refreshTags() with type: " + tagCloudType);
		tagsDTOValues = new ArrayList<TagDTO>();
		SolrField solrFieldFacet = SolrField.getFieldByName(tagCloudType);
		List<Count> tags = SolrServerUtil.getInstance().queryTags(tagsSize, solrFieldFacet);
		if (tags != null) {
			
			DescriptiveStatistics stats = new DescriptiveStatistics();
			
			long maxCount = 0;
			for (Count tagCount : tags) {
				if (tagCount.getCount() > maxCount) {
					maxCount = tagCount.getCount();
				}
				stats.addValue(tagCount.getCount());
			}
			
			logger.info("getMean = " + stats.getMean());
			logger.info("getStandardDeviation = " + stats.getStandardDeviation());
			logger.info("getPercentile = " + stats.getPercentile(50));
			logger.info("getMin = " + stats.getMin());
			logger.info("getMax = " + stats.getMax());

			double nMin = - (stats.getMin() - stats.getStandardDeviation() ) * 30;
			
			for (Count tagCount : tags) {
				TagDTO tagDTO = new TagDTO();
				tagDTO.setName(tagCount.getName());
				tagDTO.setCount(tagCount.getCount());
				double n = nMin + ( tagCount.getCount() - stats.getStandardDeviation() ) * 30;
				n = ( n * 23 ) / stats.getMax();
				tagDTO.setTotalProportion( (float) n );
				tagsDTOValues.add(tagDTO);
			}
			Collections.sort(tagsDTOValues);
		}
	}
	
	public String moreTags() {
		if (getCanIncreaseTags()) {
			tagsSize += TAGS_INCREMENT;
		}
		refreshTags();
		return NavigationMB.TAGS;
	}
	
	public String lessTags() {
		if (getCanReduceTags()) {
			tagsSize -= TAGS_INCREMENT;
		}
		refreshTags();
		return NavigationMB.TAGS;
	}
	
	public Boolean getCanReduceTags() {
		return tagsDTOValues.size() > 0 &&
					tagsSize > MIN_TAGS_SIZE;
	}
	
	public Boolean getCanIncreaseTags() {
		return tagsDTOValues.size() > 0 &&
 					tagsSize <= tagsDTOValues.size() &&
					tagsSize < MAX_TAGS_SIZE;
	}
	
	public List<SelectItem> getTagCloudTypeList() {
		if (typesList == null) {
			typesList = new ArrayList<SelectItem>();
			typesList.add( new SelectItem(SolrField.TAG.getName(), "Tags") );
//			typesList.add( new SelectItem(SolrField.NAME.getName(), "Asset Names") );
			typesList.add( new SelectItem(SolrField.PROGRAMMING_LANGUAGE.getName(), "Programming Languages") );
			typesList.add( new SelectItem(SolrField.APPLICATION_DOMAIN.getName(), "Application Domain") );
		}
		return typesList;
	}

	public String getTagCloudType() {
		return tagCloudType;
	}

	public void setTagCloudType(String tagCloudType) {
		this.tagCloudType = tagCloudType;
	}
	
}
