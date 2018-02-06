package vn.com.vng.stats.dashboard.form;

import org.springframework.util.StringUtils;

/**
 * Created by cpu10865 on 28/09/2016.
 */
public class UserForm {
    private String domainName;
    private Long permissionId;
    private String permission;
    private boolean update;
    private Integer length;
    private Integer start;
    private Integer draw;

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }


    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public boolean isValid() {
        return !(this.domainName == null || "".equals(this.domainName) || "admin".equalsIgnoreCase(this.domainName));
    }

    @Override
    public String toString() {
        return "UserForm{" +
                "domainName='" + domainName + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }

    public boolean isNullValue() {
        return StringUtils.isEmpty(domainName) || StringUtils.isEmpty(permission);
    }
}
