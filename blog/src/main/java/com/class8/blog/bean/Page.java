package com.class8.blog.bean;

import java.io.Serializable;
import java.util.List;
/**
 * 分页对象
 * @author Administrator
 *
 * @param <T>
 */
public class Page<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6502095630330234157L;
	
	public static final int DEFAULT_PAGE = 1;
	public static final int DEFAULT_PAGE_SIZE = 10;
	public static final int DEFAULT_NAVIGATE_PAGE = 8;
	public static final String SORT_DIRECTION_ASC = "ASC";
	public static final String SORT_DIRECTION_DESC = "DESC";
	
	private int page;
	private int rows;
	private String order;
	private String sort;
	private List<T> result;
	private boolean isFirstPage;
	private boolean isLastPage;
	private int prePage;
	private int nextPage;
	private boolean hasPrePage;
	private boolean hashNextPage;
	private int total;
	private int totalPage;
	private int navigatePage;
	private int[] navigatepageNums;
	
	public Page(){
		this(DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
	}
	
	public Page(int page,int rows){
		this(page, rows, DEFAULT_NAVIGATE_PAGE);
	}
	
	public Page(int page,int rows,int nevigatePage){
		this(page, rows, nevigatePage, null, null);
	}
	
	public Page(int page, int rows,int nevigatePage, String order, String sort){
		this.page = page;
		this.rows = rows;
		this.navigatePage = nevigatePage;
		this.order = order;
		this.sort = sort;
	}

	public int getPage() {
		if (page > totalPage) {
			page = totalPage;
		}
		return page;
	}

	public void setPage(int page) {
		this.page = page > 0 ? page : 1;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalPage() {
		if (this.total % this.rows == 0) {
			this.total = (this.total / this.rows);
		} else {
			this.total = (this.total / this.rows + 1);
		}
		return this.totalPage;
	}
	
	/**
	 * 获取分页起始索引
	 * @return
	 */
	public int getOffset(){
		return (this.getPage()-1) * this.getRows();
	}
	
	/**
	 * 是否为首页
	 * @return
	 */
	public boolean isFirstPage(){
		this.isFirstPage = page == 1;
		return this.isFirstPage;
	}
	
	/**
	 * 是否为末页
	 * @return
	 */
	public boolean isLastPage(){
        this.isLastPage = page == totalPage;
        return this.isLastPage;
	}
	
	/**
	 * 下一页
	 * @return
	 */
	public int getNextPage(){
		this.nextPage = this.page + 1;
		if (this.nextPage >= this.totalPage) {
			this.nextPage = this.totalPage;
		}
		return nextPage;
	}
	
	/**
	 * 上一页
	 * @return
	 */
	public int getPrePage(){
		int prePage = this.page - 1;
		if (prePage <= 1) {
			prePage = 1;
		}
		return prePage;
	}
	
	/**
	 * 是否有下一页
	 * @return
	 */
	public boolean hasNextPage(){
		if (this.page >= this.totalPage) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 是否有上一页
	 * @return
	 */
	public boolean hasPrePage(){
		if (this.page <= 1) {
			return false;
		} else {
			return true;
		}
	}

}
