package vn.com.vng.stats.dashboard.model;

import org.springframework.util.StringUtils;
import vn.com.vng.stats.dashboard.service.impl.QaDataServiceImpl;

import java.util.logging.Logger;

/**
 * Created by Tanaye on 5/26/17.
 */
public class QaData {

    private static final Logger logger = Logger.getLogger(QaData.class.getName());

    private String date;
    private String gameCode;
    private String gameName;
    private Integer kpiId;
    private Long kpiValueOther;
    private String otherSource;
    private Long kpiValueSource;
    private String toolSource;
    private Float diff;

    public String getOtherSource() {
        return otherSource;
    }

    public void setOtherSource(String otherSource) {
        if (StringUtils.isEmpty(otherSource)) this.otherSource = "nodata";
        else this.otherSource = otherSource;
    }

    public String getToolSource() {
        return toolSource;
    }

    public void setToolSource(String toolSource) {
        if (StringUtils.isEmpty(toolSource)) this.toolSource = "nodata";
        else this.toolSource = toolSource;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Integer getKpiId() {
        return kpiId;
    }

    public void setKpiId(Integer kpiId) {
        this.kpiId = kpiId;
    }

    public Long getKpiValueOther() {
        return kpiValueOther;
    }

    public void setKpiValueOther(Long kpiValueOther) {
        if (kpiValueOther == null) this.kpiValueOther = 0l;
        else this.kpiValueOther = kpiValueOther;

    }

    public Long getKpiValueSource() {
        return kpiValueSource;
    }

    public void setKpiValueSource(Long kpiValueSource) {

        if (kpiValueSource == null) this.kpiValueSource = 0l;
        else this.kpiValueSource = kpiValueSource;


    }

    public Float getDiff() {
        return diff;
    }

    public void setDiff(Float diff) {
        this.diff = diff;
    }

    @Override
    public String toString() {
        return "QaData{" +
                "date='" + date + '\'' +
                ", gameCode='" + gameCode + '\'' +
                ", gameName='" + gameName + '\'' +
                ", kpiId=" + kpiId +
                ", kpiValueOther=" + kpiValueOther +
                ", otherSource='" + otherSource + '\'' +
                ", kpiValueSource=" + kpiValueSource +
                ", toolSource='" + toolSource + '\'' +
                ", diff=" + diff +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QaData qaData = (QaData) o;

        if (date != null ? !date.equals(qaData.date) : qaData.date != null) return false;
        if (gameCode != null ? !gameCode.equals(qaData.gameCode) : qaData.gameCode != null) return false;
        if (gameName != null ? !gameName.equals(qaData.gameName) : qaData.gameName != null) return false;
        if (kpiId != null ? !kpiId.equals(qaData.kpiId) : qaData.kpiId != null) return false;
        if (kpiValueOther != null ? !kpiValueOther.equals(qaData.kpiValueOther) : qaData.kpiValueOther != null)
            return false;
        if (otherSource != null ? !otherSource.equals(qaData.otherSource) : qaData.otherSource != null) return false;
        if (kpiValueSource != null ? !kpiValueSource.equals(qaData.kpiValueSource) : qaData.kpiValueSource != null)
            return false;
        if (toolSource != null ? !toolSource.equals(qaData.toolSource) : qaData.toolSource != null) return false;
        return diff != null ? diff.equals(qaData.diff) : qaData.diff == null;

    }


    public boolean isNullValue() {
        if (diff == null || gameCode == null || kpiId == null) {
            return true;
        }
        return false;
    }
}
