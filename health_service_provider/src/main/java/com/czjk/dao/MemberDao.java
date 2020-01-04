package com.czjk.dao;

import com.czjk.pojo.Member;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @Author: Haotian
 * @Date: 2019/12/30 21:40
 * @Description: 会员接口
 */
public interface MemberDao {
    List<Member> findAll();

    Page<Member> selectByCondition(String queryString);

    /**
     * 添加会员
     *
     * @param member 会员信息
     */
    void add(Member member);

    void deleteById(Integer id);

    Member findById(Integer id);

    /**
     * 根据用户手机号查询会员
     *
     * @param telephone 手机号
     * @return 会员信息
     */
    Member findByTelephone(String telephone);

    void edit(Member member);

    /**
     * 根据月份统计会员数量
     *
     * @param date 当前月最后一天时间
     * @return 当月会员总数
     */
    Integer findMemberCountBeforeDate(String date);

    /**
     * 统计指定日期会员数
     *
     * @param date 日期
     * @return 当前日期会员数
     */
    Integer findMemberCountByDate(String date);

    /**
     * 统计指定时间之后的会员数
     *
     * @param date 指定时间
     * @return 会员总数
     */
    Integer findMemberCountAfterDate(String date);

    /**
     * 统计总会员数
     *
     * @return 总会员数
     */
    Integer findMemberTotalCount();
}