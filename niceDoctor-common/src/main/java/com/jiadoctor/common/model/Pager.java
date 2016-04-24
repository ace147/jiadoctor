/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.jiadoctor.common.model.views.Views;


/**
 * @author Michael
 * @version 1.1
 * 
 */
@SuppressWarnings("rawtypes")
public class Pager implements Serializable {

	private static final long serialVersionUID = 6550357038191933684L;

	// 默认值不可去除
	@JsonView(Views.class)
	protected int pageSize = 10;
	@JsonView(Views.class)
	protected int pageNo = 1;
	@JsonView(Views.class)
	protected int rowCount;
	@JsonView(Views.class)
	protected int pageCount;
	@JsonView(Views.class)
	private int pageSizeList[] = { 5, 10, 30, 50, 100 };
	@JsonView(Views.class)
	protected List resultList;
	
	protected int startIndex;
	protected int endIndex;
	protected int firstPageNo;
	protected int prePageNo;
	protected int nextPageNo;
	protected int lastPageNo;
	
	

	public Pager() {
		super();
	}

	public Pager(int pageSize, int pageNo, int rowCount, List resultList) {

		super();
//		pageCount = 1;
//		this.pageSize = pageSize;
//		this.pageNo = pageNo;
//		this.rowCount = rowCount;
//		this.resultList = resultList;
//		if (rowCount % pageSize == 0)
//			pageCount = rowCount / pageSize;
//		else
//			pageCount = rowCount / pageSize + 1;
		this.pageSize = 10;
		this.pageNo = 1;
		this.rowCount = 0;
		pageCount = 1;
		startIndex = 1;
		endIndex = 1;
		firstPageNo = 1;
		prePageNo = 1;
		nextPageNo = 1;
		lastPageNo = 1;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.rowCount = rowCount;
		this.resultList = resultList;
		if (rowCount % pageSize == 0)
			pageCount = rowCount / pageSize;
		else
			pageCount = rowCount / pageSize + 1;
		startIndex = pageSize * (pageNo - 1);
		endIndex = startIndex + resultList.size();
		lastPageNo = pageCount;
		if (this.pageNo > 1)
			prePageNo = this.pageNo - 1;
		if (this.pageNo == lastPageNo)
			nextPageNo = lastPageNo;
		else
			nextPageNo = this.pageNo + 1;

	}
	
	public Object[] getPageSizeIndexs() {
		List result = new ArrayList(pageSizeList.length);
		for (int i = 0; i < pageSizeList.length; i++)
			result.add(String.valueOf(pageSizeList[i]));

		Object indexs[] = result.toArray();
		return indexs;
	}

	public Object[] getPageNoIndexs() {
		List result = new ArrayList(pageCount);
		for (int i = 0; i < pageCount; i++)
			result.add(String.valueOf(i + 1));

		Object indexs[] = result.toArray();
		return indexs;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int[] getPageSizeList() {
		return pageSizeList;
	}

	public void setPageSizeList(int pageSizeList[]) {
		this.pageSizeList = pageSizeList;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public int getFirstPageNo() {
		return firstPageNo;
	}

	public void setFirstPageNo(int firstPageNo) {
		this.firstPageNo = firstPageNo;
	}

	public int getPrePageNo() {
		return prePageNo;
	}

	public void setPrePageNo(int prePageNo) {
		this.prePageNo = prePageNo;
	}

	public int getNextPageNo() {
		return nextPageNo;
	}

	public void setNextPageNo(int nextPageNo) {
		this.nextPageNo = nextPageNo;
	}

	public int getLastPageNo() {
		return lastPageNo;
	}

	public void setLastPageNo(int lastPageNo) {
		this.lastPageNo = lastPageNo;
	}

}