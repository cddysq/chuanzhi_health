package com.czjk.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 封装查询条件
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/5/18 14:24
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryPageBean implements Serializable {
    private static final long serialVersionUID = 3829443707933619143L;

    /**
     * 页码
     */
    private Integer currentPage;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 查询条件
     */
    private String queryString;
}