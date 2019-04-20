package com.xieweihao.jpa;

import java.io.Serializable;
import java.util.List;

/**
 * 分页实体类
 * 
 * @author liuxg
 * @date 2015年4月23日
 * @time 下午5:22:35
 * @param <T>
 */

public class Page<T> implements Serializable{

	private Integer current_page = 1; //当前页
	private Integer total_count = 0; //记录总数
	private Integer next_page = 0;  //下一页
	private Integer pre_page = 0;   //上一页
	private Integer page_count = 0; //总页数
	private Integer page_size = 20; //每页多少条记录
	
	private List<T> list;   //数据集合

	public Page(Integer current_page ,Integer page_size){
		this.current_page = current_page ;
		this.page_size = page_size ;
		this.pre_page = this.getPre_page();
	}
	
	public Page(){} ;
	
	public Integer getCurrent_page() {
		return current_page;
	}

	public void setCurrent_page(Integer current_page) {
		this.current_page = current_page;
		this.pre_page = this.getPre_page();
		
	}

	public Integer getTotal_count() {
		return total_count;
	}

	public void setTotal_count(Integer total_count) {
		this.total_count = total_count;
		this.page_count = this.getTotal_page() ;
		this.next_page = this.getNext_page() ;
	}

	/**
	 * 获取下一页
	 * 
	 * @return
	 */
	public Integer getNext_page() {
		if (this.next_page.equals(this.page_count)) {
			this.next_page = this.page_count;
		} else {
			this.next_page = this.current_page + 1;
		}
		return next_page;
	}

	/**
	 * 获取上一页
	 * 
	 * @return
	 */
	public Integer getPre_page() {
		if (current_page == 1) {
			pre_page = 1;
		} else {
			pre_page = current_page - 1;
		}
		return pre_page;
	}

	/**
	 * 获取总页数
	 * 
	 * @return
	 */
	public Integer getTotal_page() {

		if (this.total_count < 0) {
			return -1;
		}
		int count = this.total_count / this.page_size;
		if (this.total_count % this.page_size > 0) {
			count++;
		}
		return count;

	}

	public Integer getPage_size() {
		return page_size;
	}

	public void setPage_size(Integer page_size) {
		this.page_size = page_size;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Integer getPage_count() {
		return page_count;
	}

	public void setPage_count(Integer page_count) {
		this.page_count = page_count;
	}


}

