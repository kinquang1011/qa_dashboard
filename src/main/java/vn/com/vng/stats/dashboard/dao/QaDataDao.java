package vn.com.vng.stats.dashboard.dao;

import vn.com.vng.stats.dashboard.model.QaData;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Tanaye on 5/28/17.
 */
public interface QaDataDao {
    String getMaxDate() throws SQLException, Exception;

    String getMinDate() throws SQLException, Exception;

    List<QaData> getData4Qa() throws Exception;

    void writeData(List<QaData> qaDataList) throws SQLException, Exception;

    List<QaData> getPieData4Qa() throws Exception;

    List<QaData> refreshData(String fromDate, String toDate) throws Exception;

    void updateValue(List<QaData> qaDataList) throws Exception;

    List<QaData> getData4FooTable(String fromDate, String toDate) throws Exception;

    List<QaData> getDataToday() throws Exception;
}
