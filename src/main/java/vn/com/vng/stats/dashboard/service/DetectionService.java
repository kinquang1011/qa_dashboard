package vn.com.vng.stats.dashboard.service;

import vn.com.vng.stats.dashboard.model.NewGame;

import java.util.List;

/**
 * Created by Tanaye on 6/2/17.
 */
public interface DetectionService {
    List<NewGame> detectGame(String date) throws Exception;
}
