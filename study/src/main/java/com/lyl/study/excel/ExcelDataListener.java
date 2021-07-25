package com.lyl.study.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 直接用map接收数据
 */
public class ExcelDataListener<T> extends AnalysisEventListener<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelDataListener.class);

    List<T> list = Lists.newArrayList();
    Map<Integer, String> headMap = Maps.newHashMap();

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSONObject.toJSONString(data));
        list.add(data);
    }

    /**
     * 这里会一行行的返回头
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.headMap.putAll(headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println(analysisContext.toString());
    }

    public List<T> getData() {
        return list;
    }

    public Map<Integer, String> getHeadMap() {
        return headMap;
    }

}