package com.july.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions; //问题列表
    private boolean showPrevious; //是否显示向前按钮
    private boolean showFirstPage; //是否显示跳转首页按钮
    private boolean showNext; //是否显示向后按钮
    private boolean showEndPage ; //是否显示跳转末页按钮

    private Integer page; //当前页
    private List<Integer> pages= new ArrayList<>(); //显示的页数号码
    private  Integer totalPage; // 总页数

    public  void setPagination(Integer totalCount, Integer page, Integer size) {
        this.page = page;
        //计算可以展示多少页
        if (totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size +1;
        }

        //为可展示页码list赋值，当前页数的左右各3个数字，最大7个数字，最小4个数字
        pages.add(page);
        for (int i =1 ; i <= 3 ; i ++){
            if (page-i>0){
                pages.add(0,page-i);
            }
            if(page + i <= totalPage){
                pages.add(page+i);
            }
        }

        //对是否显示的标识进行判断赋值
        if (page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        if (totalPage == page){
            showNext = false;
        }else {
            showNext = true;
        }
        if(pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }
        if (pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }

    }



}
