package vn.com.vng.stats.dashboard.model;


public class User {
    private Integer id;
    private String domainName;
    private String createDate;
    private String permission;

    public User () {}

    public User(Integer id, String domainName, String createDate, String permission) {
        this.id = id;
        this.domainName = domainName;
        this.createDate = createDate;
        this.permission = permission;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
