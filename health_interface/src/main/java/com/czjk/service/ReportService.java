package com.czjk.service;

import java.util.Map;

/**
 * 运营服务接口
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/5/28 16:31
 **/
public interface ReportService {
    /**
     * 获取运营数据
     *
     * @return 运营数据
     */
    Map<String, Object> getBusinessReportData();
}