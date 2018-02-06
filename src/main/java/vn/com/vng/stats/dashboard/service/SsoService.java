package vn.com.vng.stats.dashboard.service;


/**
 * Created by cpu10865 on 28/09/2016.
 */
public interface SsoService {

    String getSession(String sid);

    String checklogin(String sid) throws Exception;

}
