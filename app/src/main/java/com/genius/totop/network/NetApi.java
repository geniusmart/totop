package com.genius.totop.network;

import com.genius.totop.model.CacheData;
import com.genius.totop.model.Category;
import com.genius.totop.model.DataRes;
import com.genius.totop.model.DatasRes;
import com.genius.totop.model.Goods;
import com.genius.totop.model.Version;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
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
    void findGoodsByPrice(@Query("pi") int pageNo, @Query("ps") int pageCount, @Query("sort") int sortType,@Query("ptype") int priceType, Callback<DatasRes<Goods>> response);

    /**
     * 根据对象类型获取商品信息
     * @param pageNo
     * @param pageCount
     * @param sortType
     * @param objectType
     * @return
     */
    @GET("/interface/api.do?sk=1000")
    void findGoodsByObject(@Query("pi") int pageNo, @Query("ps") int pageCount, @Query("sort") int sortType,@Query("otype") int objectType,Callback<DatasRes<Goods>> response);

    /**
     * 搜索
     * @param pageNo
     * @param pageCount
     * @param key
     * @param response
     */
    @GET("/interface/api.do?sk=1006")
    void search(@Query("pi") int pageNo, @Query("ps") int pageCount,@Query("key") String key ,Callback<DatasRes<Goods>> response);

    /**
     * 获取帮助信息和分享地址
     */
    @GET("/interface/api.do?sk=1600")
    DataRes<CacheData> findCacheDatas() throws Exception;

    /**
     * 获取对象和价格等类别数据
     */
    @GET("/interface/api.do?sk=1510")
    DataRes<Category> findCategorys() throws Exception;

    /**
     * 提交用户访问商品信息
     * @param imei
     * @param area
     * @param id
     */
    @POST("/interface/api.do?sk=1300")
    DatasRes<Void> postVisit(@Query("imei")String imei,@Query("area")String area,@Query("id")String id) throws Exception;

    //按时间获取产品列表

    //系统更新
    DatasRes<Version> getVersion();

}
