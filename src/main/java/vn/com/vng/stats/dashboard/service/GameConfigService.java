package vn.com.vng.stats.dashboard.service;

import vn.com.vng.stats.dashboard.form.GameConfigForm;
import vn.com.vng.stats.dashboard.model.GameInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Tanaye on 6/8/17.
 */
public interface GameConfigService {

    Map<String, Object> addGame(GameConfigForm gameConfigForm) throws Exception;

    List<GameInfo> getGames() throws Exception;

    Map<String, Object> removeGame(GameConfigForm gameConfigForm) throws Exception;
}
