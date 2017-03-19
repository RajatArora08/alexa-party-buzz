package edu.sfhacks.alexa.eventbriteapi;

import java.util.HashMap;
import java.util.Map;

public class Pagination {
	
	private Integer objectCount;
	private Integer pageNumber;
	private Integer pageSize;
	private Integer pageCount;
	private String continuation;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Integer getObjectCount() {
	return objectCount;
	}

	public void setObjectCount(Integer objectCount) {
	this.objectCount = objectCount;
	}

	public Integer getPageNumber() {
	return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
	this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
	return pageSize;
	}

	public void setPageSize(Integer pageSize) {
	this.pageSize = pageSize;
	}

	public Integer getPageCount() {
	return pageCount;
	}

	public void setPageCount(Integer pageCount) {
	this.pageCount = pageCount;
	}

	public String getContinuation() {
	return continuation;
	}

	public void setContinuation(String continuation) {
	this.continuation = continuation;
	}

	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}
}
