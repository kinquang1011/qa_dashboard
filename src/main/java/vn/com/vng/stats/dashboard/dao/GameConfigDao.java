package vn.com.vng.stats.dashboard.dao;

import vn.com.vng.stats.dashboard.form.GameConfigForm;
import vn.com.vng.stats.dashboard.model.GameInfo;

import java.util.List;

/**
 * Created by Tanaye on 5/27/17.
 */
public interface GameConfigDao {

    boolean isContains(GameConfigForm gameConfigForm) throws Exception;

    boolean addNewGame(GameConfigForm gameConfigForm) throws Exception;

    List<GameInfo> getGameConfig() throws Exception;

    boolean removeGame(GameConfigForm gameConfigForm) throws Exception;

    boolean updateGame(GameConfigForm gameConfigForm) throws Exception;
}
