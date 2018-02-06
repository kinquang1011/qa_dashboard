package vn.com.vng.stats.dashboard.form;

import org.springframework.util.StringUtils;

/**
 * Created by Tanaye on 6/8/17.
 */
public class GameConfigForm {

    private String gameCode;
    private String gameName;
    private String gameDesc;
    private Integer kpiId;
    private boolean update;

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public Integer getKpiId() {
        return kpiId;
    }

    public void setKpiId(Integer kpiId) {
        this.kpiId = kpiId;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameDesc() {
        return gameDesc;
    }

    public void setGameDesc(String gameDesc) {
        this.gameDesc = gameDesc;
    }

    public boolean isNullValue() {
        return StringUtils.isEmpty(gameCode) || StringUtils.isEmpty(kpiId);
    }

    @Override
    public String toString() {
        return "GameConfigForm{" +
                "gameCode='" + gameCode + '\'' +
                ", gameName='" + gameName + '\'' +
                ", gameDesc='" + gameDesc + '\'' +
                ", kpiId=" + kpiId +
                '}';
    }
}
