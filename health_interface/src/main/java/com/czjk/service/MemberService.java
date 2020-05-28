package com.czjk.service;

import com.czjk.pojo.Member;

import java.util.List;

/**
 * 会员服务接口
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/5/28 16:30
 **/
public interface MemberService {
    /**
     * 根据手机号查询会员
     *
     * @param telephone 用户输入手机号
     * @return 会员信息
     */
    Member findByTelephone(String telephone);

    /**
     * 新增会员
     *
     * @param member 会员信息
     */
    void add(Member member);

    /**
     * 根据月份统计会员数量
     *
     * @param months 当前月份
     * @return 每月会员总数
     */
    List<Integer> findMemberCountByMonths(List<String> months);
}