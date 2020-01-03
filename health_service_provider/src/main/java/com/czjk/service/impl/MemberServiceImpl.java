package com.czjk.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.czjk.dao.MemberDao;
import com.czjk.pojo.Member;
import com.czjk.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Haotian
 * @Date: 2020/1/1 12:15
 * @Description: 会员服务
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone( telephone );
    }

    @Override
    public void add(Member member) {
        String password = member.getPassword();
        if (password != null) {
            //存在密码，使用md5将明文密码进行加密
            member.setPassword( SecureUtil.md5( password ) );
        }
        memberDao.add( member );
    }

    @Override
    public List<Integer> findMemberCountByMonths(List<String> months) {
        return months.stream()
                .map( month -> memberDao.findMemberCountBeforeDate( month + ".31" ) )
                .collect( Collectors.toList() );
    }
}