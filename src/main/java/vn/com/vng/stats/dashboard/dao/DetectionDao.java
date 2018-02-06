package vn.com.vng.stats.dashboard.dao;

import vn.com.vng.stats.dashboard.model.NewGame;
import vn.com.vng.stats.dashboard.model.QcData;

import java.util.List;

/**
 * Created by Tanaye on 6/2/17.
 */
public interface DetectionDao {
    List<NewGame> getNewGameToday() throws Exception;

    List<NewGame> getNewGameByDate(String date) throws Exception;
}
