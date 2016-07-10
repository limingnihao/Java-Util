package org.limingnihao.util;

import java.util.List;

import org.limingnihao.model.SortBean;

/**
 * 分页处理
 * 
 * @author lishiming
 * 
 */
public class PageUtil {

	/**
	 * 分页操作 - 首页
	 */
	public static final String FIRST = "first";

	/**
	 * 分页操作 - 末页
	 */
	public static final String LAST = "last";

	/**
	 * 分页操作 - 下一页
	 */
	public static final String NEXT = "next";

	/**
	 * 分页操作 - 上一页
	 */
	public static final String PREVIOUS = "previous";

	public static final int MAX_SIZE = Integer.MAX_VALUE;

	/**
	 * 获取查询起始数
	 * 
	 * @param pageNow
	 * @param pageSize
	 * @return
	 */
	public static int getFirstResult(int pageNow, int pageSize) {
		if (pageSize <= 0) {
			return 0;
		}
		int firstResult = (pageNow - 1) * pageSize;
		if (firstResult < 0) {
			firstResult = 0;
		}
		return firstResult;
	}

	/**
	 * 获取查询总数量
	 * 
	 * @param pageSize
	 * @return
	 */
	public static int getMaxResults(int pageSize) {
		if (pageSize <= 0) {
			return Integer.MAX_VALUE;
		}
		return pageSize;
	}

	/**
	 * 获取总页数
	 * 
	 * @param total
	 *            总个数
	 * @param pageSize
	 *            每页数量
	 * @return
	 */
	public static int getPageTotal(int total, int pageSize) {
		if (pageSize <= 0) {
			return 1;
		}
		int pageTotal = total / pageSize;
		if (total % pageSize != 0) {
			pageTotal++;
		}
		if (pageTotal < 1) {
			pageTotal = 1;
		}
		return pageTotal;
	}

	/**
	 * 根据动作，计算出应该显示的页数
	 * 
	 * @param action
	 *            动作
	 * @param current
	 *            当前页
	 * @param pageTotal
	 *            总页数
	 * @return
	 */
	@Deprecated
	public static int getPageNow(String action, int pageNow, int pageTotal) {
		int pageNum = pageNow;
		if (action.equals(FIRST)) {
			pageNum = 0;
		} else if (action.equals(LAST)) {
			pageNum = pageTotal;
		} else if (action.equals(NEXT)) {
			pageNum++;
		} else if (action.equals(PREVIOUS)) {
			pageNum--;
		} else {
			pageNum = 1;
		}
		if (pageNum > pageTotal) {
			pageNum = pageTotal;
		}
		if (pageNum < 1) {
			pageNum = 1;
		}
		return pageNum;
	}

	public static int getPageNow(int pageNow, int pageTotal) {
//		if (pageNow > pageTotal) {
//			return pageTotal;
//		}
		if (pageNow < 1) {
			return 1;
		}
		return pageNow;
	}

	/**
	 * 排序List解析
	 * 
	 * @param jsonString
	 * @return
	 */
	public static List<SortBean> getSortBean(String jsonString) {
		List<SortBean> list = JsonUtil.jsonToList(SortBean.class, jsonString);
		return list;
	}

	/**
	 * 排序 - 字段
	 * 
	 * @param sort
	 * @return
	 */
	public static String getSortProperty(String sort) {
		List<SortBean> list = getSortBean(sort);
		if (list.size() > 0 && list.get(0) != null) {
			return list.get(0).getProperty();
		}
		return "";
	}

	/**
	 * 排序 - 类型 - ASC - DESC
	 * 
	 * @param sort
	 * @return
	 */
	public static String getSortDirection(String sort) {
		List<SortBean> list = getSortBean(sort);
		if (list.size() > 0 && list.get(0) != null) {
			return list.get(0).getDirection();
		}
		return "";
	}

	public static void main(String args[]) {
		List<SortBean> list = getSortBean("[{\"property\":\"programName\",\"direction\":\"ASC\"}]");
		for (SortBean bean : list) {
			System.out.println("" + bean.getProperty() + ", " + bean.getDirection());
		}
	}
}
