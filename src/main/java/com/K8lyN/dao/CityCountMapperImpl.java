package com.K8lyN.dao;

import com.K8lyN.model.CityCount;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

/**
 * @Author K8lyN
 * @Date 2021/04/09 23:17
 * @Version 1.0
 */
public class CityCountMapperImpl implements CityCountMapper{

    private SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public int insertCity(CityCount cc) {
        return sqlSession.getMapper(CityCountMapper.class).insertCity(cc);
    }

    @Override
    public int deleteCity(String cityName) {
        return sqlSession.getMapper(CityCountMapper.class).deleteCity(cityName);
    }

    @Override
    public int updateCity(CityCount cc) {
        return sqlSession.getMapper(CityCountMapper.class).updateCity(cc);
    }

    @Override
    public CityCount selectCity(String cityName) {
        return sqlSession.getMapper(CityCountMapper.class).selectCity(cityName);
    }

    @Override
    public List<CityCount> getCities() {
        return sqlSession.getMapper(CityCountMapper.class).getCities();
    }

    @Override
    public int getNum() {
        return sqlSession.getMapper(CityCountMapper.class).getNum();
    }

    @Override
    public List<CityCount> getMax(int num) {
        return sqlSession.getMapper(CityCountMapper.class).getMax(num);
    }
}
