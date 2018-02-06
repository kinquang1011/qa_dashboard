package vn.com.vng.stats.dashboard.model;

/**
 * Created by Tanaye on 6/2/17.
 */
public class QcData {
    private String date;
    private String gameCode;
    private String source;
    private Integer kpiId;
    private String kpiValue;

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getKpiId() {
        return kpiId;
    }

    public void setKpiId(Integer kpiId) {
        this.kpiId = kpiId;
    }

    public String getKpiValue() {
        return kpiValue;
    }

    public void setKpiValue(String kpiValue) {
        this.kpiValue = kpiValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QcData qcData = (QcData) o;

        if (date != null ? !date.equals(qcData.date) : qcData.date != null) return false;
        if (gameCode != null ? !gameCode.equals(qcData.gameCode) : qcData.gameCode != null) return false;
        if (source != null ? !source.equals(qcData.source) : qcData.source != null) return false;
        if (kpiId != null ? !kpiId.equals(qcData.kpiId) : qcData.kpiId != null) return false;
        return kpiValue != null ? kpiValue.equals(qcData.kpiValue) : qcData.kpiValue == null;
    }

}
