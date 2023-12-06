package com.project.lolchess.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PagingUtil {
    private int maxNum;
    private int pageNum;
    private int listCnt;
    private int pageCnt;
    private String listName;

    public String makePaging() {
        String page = null;
        StringBuffer sb = new StringBuffer();
        int totalPage = (maxNum % listCnt) > 0 ? (maxNum / listCnt) + 1 : maxNum / listCnt;
        if (totalPage == 0) totalPage = 1;
        int curGroup = (pageNum % pageCnt) > 0 ? (pageNum / pageCnt) + 1 : pageNum / pageCnt;
        int start = (curGroup * pageCnt) - (pageCnt - 1);
        int end = (curGroup * pageCnt) >= totalPage ? totalPage : curGroup * pageCnt;
        if (start != 1) {
            sb.append("<a class='pno' href='/" + listName + "pageNum=" + (start - 1) + "'>◀</a>");
        }
        for (int i = start; i <= end; i++) {
            if (i != pageNum) {
                sb.append("<a class='pno' href='/" + listName + "pageNum=" + i + "'>" + i + "</a>");
            } else {
                sb.append("<font class='pno'>" + i + "</font>");
            }
        }
        if (end != totalPage) {
            sb.append("<a class='pno' href='/" + listName + "pageNum=" + (end + 1) + "'>▶</a>");
        }
        page = sb.toString();
        return page;
    }
}