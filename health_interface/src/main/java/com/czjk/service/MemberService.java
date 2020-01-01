package com.czjk.service;

import com.czjk.pojo.Member;

/**
 * @Author: Haotian
 * @Date: 2020/1/1 11:48
 * @Description: 会员服务接口
 */
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
}