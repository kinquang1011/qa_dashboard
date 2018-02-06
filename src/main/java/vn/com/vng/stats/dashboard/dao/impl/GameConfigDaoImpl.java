package vn.com.vng.stats.dashboard.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.com.vng.stats.dashboard.dao.GameConfigDao;
import vn.com.vng.stats.dashboard.form.GameConfigForm;
import vn.com.vng.stats.dashboard.model.GameInfo;
import vn.com.vng.stats.dashboard.model.QaData;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanaye on 5/27/17.
 */
@Repository("gameInfoDao")
public class GameConfigDaoImpl extends BaseDaoImpl implements GameConfigDao {

    @Autowired
    @Qualifier("jdbcRead")
    protected JdbcTemplate jdbcRead;


    @Autowired
    @Qualifier("jdbcWrite")
    protected JdbcTemplate jdbcWrite;

    @Override
    public boolean isContains(GameConfigForm gameConfigForm) throws Exception {
        String sql = "select count(*) as 'count' from game_config WHERE `key` = '" + createKey(gameConfigForm) + "'";

        return (getConnection(jdbcWrite)
                .query(sql, (rs, rownum) -> rs.getLong("count"))
                .stream().findFirst().orElse(null)) != 0;

    }

    @Override
    public boolean addNewGame(GameConfigForm gameConfigForm) throws Exception {
        List<GameConfigForm> gameConfigForms = new ArrayList<GameConfigForm>() {{
            add(gameConfigForm);
        }};

        String insertTable = "INSERT INTO game_config VALUES (?,?,?,?,?)";

        final int batchSize = 500;

        for (int j = 0; j < gameConfigForms.size(); j += batchSize) {
            final List<GameConfigForm> batchList = gameConfigForms.subList(j, j + batchSize > gameConfigForms.size() ? gameConfigForms.size() : j + batchSize);
            getConnection(jdbcWrite).batchUpdate(insertTable,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i)
                                throws SQLException {
                            GameConfigForm gameConfigForm = batchList.get(i);
                            ps.setString(1, createKey(gameConfigForm));
                            ps.setString(2, gameConfigForm.getGameCode());
                            ps.setString(3, gameConfigForm.getGameName());
                            ps.setInt(4, gameConfigForm.getKpiId());
                            ps.setString(5, gameConfigForm.getGameDesc());

                        }

                        @Override
                        public int getBatchSize() {
                            return batchList.size();
                        }
                    });
        }

        return true;
    }

    @Override
    public List<GameInfo> getGameConfig() throws Exception {
        String sql = "select game_code as gameCode, game_name as gameName, kpi_id as kpiId, `desc` from game_config";
        return queryForList(getConnection(jdbcWrite), sql, GameInfo.class);
    }

    @Override
    public boolean removeGame(GameConfigForm gameConfigForm) throws Exception {
        List<GameConfigForm> gameConfigForms = new ArrayList<GameConfigForm>() {{
            add(gameConfigForm);
        }};
        String insertTable = "DELETE FROM game_config  WHERE `key` = ?";

        final int batchSize = 500;

        for (int j = 0; j < gameConfigForms.size(); j += batchSize) {
            final List<GameConfigForm> batchList = gameConfigForms.subList(j, j + batchSize > gameConfigForms.size() ? gameConfigForms.size() : j + batchSize);
            getConnection(jdbcWrite).batchUpdate(insertTable,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i)
                                throws SQLException {
                            GameConfigForm gameConfigForm = batchList.get(i);
                            ps.setString(1, createKey(gameConfigForm));
                        }

                        @Override
                        public int getBatchSize() {
                            return batchList.size();
                        }
                    });

        }

        return true;

    }

    @Override
    public boolean updateGame(GameConfigForm gameConfigForm) throws Exception {
        List<GameConfigForm> gameConfigForms = new ArrayList<GameConfigForm>() {{
            add(gameConfigForm);
        }};
        String insertTable = "UPDATE game_config SET  game_code = ?, game_name = ?, kpi_id = ?, `desc` = ? WHERE `key` = ?";

        final int batchSize = 500;

        for (int j = 0; j < gameConfigForms.size(); j += batchSize) {
            final List<GameConfigForm> batchList = gameConfigForms.subList(j, j + batchSize > gameConfigForms.size() ? gameConfigForms.size() : j + batchSize);
            getConnection(jdbcWrite).batchUpdate(insertTable,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i)
                                throws SQLException {
                            GameConfigForm gameConfigForm = batchList.get(i);
                            ps.setString(1, gameConfigForm.getGameCode());
                            ps.setString(2, gameConfigForm.getGameName());
                            ps.setInt(3, gameConfigForm.getKpiId());
                            ps.setString(4, gameConfigForm.getGameDesc());
                            ps.setString(5, createKey(gameConfigForm));
                        }

                        @Override
                        public int getBatchSize() {
                            return batchList.size();
                        }
                    });

        }


        return true;
    }

    private String createKey(GameConfigForm gameConfigForm) {
        return gameConfigForm.getGameCode() + "-" + gameConfigForm.getKpiId();

    }
}
