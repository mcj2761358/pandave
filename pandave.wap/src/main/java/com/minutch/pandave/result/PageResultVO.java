package com.minutch.pandave.result;

import lombok.Data;

import java.util.List;

/**
 * Created by Minutch on 16/3/19.
 */
@Data
public class PageResultVO<T> {

    private Integer pageSize;
    private Integer curPage;
    private Integer totalSize;


    public List<T> dataList;
}
