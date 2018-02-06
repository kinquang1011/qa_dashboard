package vn.com.vng.stats.dashboard.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.vng.stats.dashboard.dao.GameConfigDao;
import vn.com.vng.stats.dashboard.form.GameConfigForm;
import vn.com.vng.stats.dashboard.model.GameInfo;
import vn.com.vng.stats.dashboard.service.GameConfigService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tanaye on 6/8/17.
 */

@Service("gameConfigServiceImpl")
public class GameConfigServiceImpl implements GameConfigService {

    @Autowired
    private GameConfigDao gameConfigDao;

    @Override
    public Map<String, Object> addGame(GameConfigForm gameConfigForm) throws Exception {
        Map<String, Object> result = new HashMap<>();
        if (!gameConfigForm.isNullValue()) {
            if (gameConfigForm.isUpdate()) {
                gameConfigDao.updateGame(gameConfigForm);
                result.put("result", true);
                result.put("message", "Success");
            } else if (gameConfigDao.isContains(gameConfigForm)) {
                result.put("result", false);
                result.put("message", "Game code : " + gameConfigForm.getGameCode() + "\n"
                        + "Kpi Id : " + gameConfigForm.getKpiId() + "\n Đã tồn tại ...");
            } else {
                gameConfigDao.addNewGame(gameConfigForm);
                result.put("result", true);
                result.put("message", "Success");
            }
        }

        return result;

    }

    @Override
    public List<GameInfo> getGames() throws Exception {
        return gameConfigDao.getGameConfig();
    }

    @Override
    public Map<String, Object> removeGame(GameConfigForm gameConfigForm) throws Exception {
        Map<String, Object> result = new HashMap<>();
        if (!gameConfigForm.isNullValue()) {
            if (!gameConfigDao.isContains(gameConfigForm)) {
                result.put("result", false);
                result.put("message", "Game code : " + gameConfigForm.getGameCode() + "\n"
                        + "Kpi Id : " + gameConfigForm.getKpiId() + "\n Không tồn tại ...");
            } else {
                gameConfigDao.removeGame(gameConfigForm);
                result.put("result", true);
                result.put("message", "Success");
            }
        }

        return result;
    }
}
