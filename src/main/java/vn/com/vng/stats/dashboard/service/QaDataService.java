package vn.com.vng.stats.dashboard.service;

import org.apache.commons.configuration.ConfigurationException;
import vn.com.vng.stats.dashboard.form.QaDataForm;
import vn.com.vng.stats.dashboard.model.QaData;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by Tanaye on 5/26/17.
 */
public interface QaDataService {

    void compareData() throws Exception;

    void refreshData(String rangeDate) throws Exception;

    List<List<Object>> getIssuesOfGame(String rangeDate, boolean isNoIssues) throws Exception;

}
