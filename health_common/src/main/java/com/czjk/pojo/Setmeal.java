package com.czjk.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2019/12/22 14:01
 * @Description: 体检套餐
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Setmeal implements Serializable {
    private static final long serialVersionUID = 5637383289640811132L;

    private Integer id;

    /**
     * 套餐名
     */
    private String name;

    /**
     * 套餐编号
     */
    private String code;

    /**
     * 帮助编号
     */
    private String helpCode;

    /**
     * 套餐适用性别：0不限 1男 2女
     */
    private String sex;

    /**
     * 套餐适用年龄
     */
    private String age;

    /**
     * 套餐价格
     */
    private Float price;

    /**
     * 备注
     */
    private String remark;

    /**
     * 注意描述
     */
    private String attention;

    /**
     * 套餐对应图片存储路径
     */
    private String img;

    /**
     * 体检套餐对应的检查组，多对多关系
     */
    private List<CheckGroup> checkGroups;
}