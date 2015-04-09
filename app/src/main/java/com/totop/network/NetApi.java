package com.totop.network;

import com.totop.model.DataRes;
import com.totop.model.Goods;

import retrofit.http.GET;
import retrofit.http.Query;

public interface NetApi {

    /**
     *根据价格类型获取商品信息
     * @param pageNo
     * @param pageCount
     * @param sortType
     * @param priceType
     * @return
     */
    @GET("/interface/api.do?sk=1000")
    DataRes<Goods> findGoodsByPrice(@Query("pi") int pageNo, @Query("ps") int pageCount, @Query("sort") int sortType,@Query("ptype") int priceType);

    /**
     * 根据对象类型获取商品信息
     * @param pageNo
     * @param pageCount
     * @param sortType
     * @param objectType
     * @return
     */
    @GET("/interface/api.do?sk=1000")
    DataRes<Goods> findGoodsByObject(@Query("pi") int pageNo, @Query("ps") int pageCount, @Query("sort") int sortType,@Query("otype") int objectType);

    //搜索

    //按时间获取产品列表

    //系统更新

}
