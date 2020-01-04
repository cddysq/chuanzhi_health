package com.czjk.service;

import java.util.Map;

/**
 * @Author: Haotian
 * @Date: 2020/1/3 21:30
 * @Description: 运营服务接口
 */
public interface ReportService {
    /**
     * 获取运营数据
     *
     * @return 运营数据
     */
    Map<Object, Object> getBusinessReportData();
}