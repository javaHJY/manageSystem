package util;

public class Pagination {
	private int pageNow;// 当前页码
	private int start;// 开始页码
	private int end;// 结尾页码
	private int totalPage;// 总页数
	private int begin;// 每一页第一条数据的编号
	private final int dataNums = 6;// 一页显示的数据条数

	public Pagination(int pageNow, int dataCount) {
		int pageCount = 5;// 一次显示的页码个数
		// 计算总页数
		if (dataCount % dataNums == 0) {
			this.totalPage = dataCount / dataNums;
		} else {
			this.totalPage = dataCount / dataNums + 1;
		}
		if (pageNow >= 1)
			this.pageNow = pageNow;
		else
			this.pageNow = 1;
		if (pageNow > totalPage)
			this.pageNow = totalPage;
		begin = (this.pageNow - 1) * dataNums;
		start = 1;
		end = pageCount;
		if (totalPage <= pageCount) {
			end = totalPage;
		} else {
			start = this.pageNow - pageCount / 2;
			end = this.pageNow + pageCount / 2;
			if (start <= 0) {
				start = 1;
				end = start + pageCount - 1;
			}
			if (end > totalPage) {
				end = totalPage;
				start = end - pageCount + 1;
			}
		}
	}

	public int getPageNow() {
		return pageNow;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getBegin() {
		return begin;
	}

	public int getDataNums() {
		return dataNums;
	}

}
