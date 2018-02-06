package vn.com.vng.stats.dashboard.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.com.vng.stats.dashboard.dao.DetectionDao;
import vn.com.vng.stats.dashboard.model.NewGame;

import java.util.List;

/**
 * Created by Tanaye on 6/2/17.
 */

@Repository("detectionDaoImpl")
public class DetectionDaoImpl extends BaseDaoImpl implements DetectionDao {

    @Autowired
    @Qualifier("jdbcRead")
    protected JdbcTemplate jdbcRead;

    @Override
    public List<NewGame> getNewGameToday() throws Exception {
        String sql = "select report_date as date, game_code as gameCode, source from new_games where report_date >=  DATE(NOW() - INTERVAL 1 DAY) ";
        List<NewGame> newGames = queryForList(getConnection(jdbcRead), sql, NewGame.class);
        return newGames;
    }

    @Override
    public List<NewGame> getNewGameByDate(String date) throws Exception {
        String sql = "select report_date as date, game_code as gameCode, source from new_games where report_date >= '%s' ";
        List<NewGame> newGames = queryForList(getConnection(jdbcRead), String.format(sql, date), NewGame.class);
        return newGames;
    }
}
