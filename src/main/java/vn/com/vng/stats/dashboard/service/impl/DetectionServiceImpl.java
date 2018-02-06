package vn.com.vng.stats.dashboard.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vn.com.vng.stats.dashboard.dao.DetectionDao;
import vn.com.vng.stats.dashboard.model.NewGame;
import vn.com.vng.stats.dashboard.model.QcData;
import vn.com.vng.stats.dashboard.service.DetectionService;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Tanaye on 6/2/17.
 */
@Service("detectionServiceImpl")
public class DetectionServiceImpl implements DetectionService {

    private static final Logger logger = Logger.getLogger(DetectionServiceImpl.class.getName());

    @Autowired
    private DetectionDao detectionDao;

    @Override
    public List<NewGame> detectGame(String date) throws Exception {
        if (StringUtils.isEmpty(date)) {
            return detectionDao.getNewGameToday();
        } else {
            return detectionDao.getNewGameByDate(date);
        }
    }
}
