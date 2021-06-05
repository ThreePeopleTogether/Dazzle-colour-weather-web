package com.K8lyN.dao;

import com.K8lyN.model.CityCount;

import java.util.List;

/**
 * @Author K8lyN
 * @Date 2021/04/09 23:06
 * @Version 1.0
 */
public interface CityCountMapper {

    /**
     * 插入数据的实体类到数据库当中。
     * @param cc 将要插入进数据库的数据实体类。
     * @return 是否插入成功。
     * */
    public int insertCity(CityCount cc);

    /**
     * 从数据库当中删除指定数据。
     * @param cityName 将要从数据库当中被删除数据的城市名。
     * @return 是否删除成功。
     * */
    public int deleteCity(String cityName);

    /**
     * 更新指定数据的信息。
     * @param cc 将要更新数据库中数据的实体类。
     * @return 是否更新成功。
     * */
    public int updateCity(CityCount cc);

    /**
     * 根据城市名查询数据。
     * @param cityName 从数据库当中查询搜索次数的城市名。
     * @return 查询成功返回实体类，否则返回null。
     * */
    public CityCount selectCity(String cityName);

    /**
     * 查询数据库当中的所有城市数据。
     * @return 查询成功返回包含所有城市的列表，否则返回null。
     * */
    public List<CityCount> getCities();

    /**
     * 查询数据库当中一共有多少组数据。
     * @return 查询到的结果。
     * */
    public int getNum();

    /**
     * 查询数据库当中搜索次数前几的数据。
     * @param num 具体前几。
     * @return 查询成功返回包含所有城市的列表，否则返回null。
     * */
    public List<CityCount> getMax(int num);

}
