package com.coolweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * Created by ASUS on 2018/9/4.
 */

public class Province extends DataSupport{
    private int id;
    private String provinceName;
    private int provinceCode;

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public void setProvinceName(String provinceName) {

        this.provinceName = provinceName;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getId() {

        return id;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public String getProvinceName() {

        return provinceName;
    }
}
