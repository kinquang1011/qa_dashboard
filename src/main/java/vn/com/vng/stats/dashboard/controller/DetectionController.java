package vn.com.vng.stats.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vn.com.vng.stats.dashboard.service.DetectionService;
import vn.com.vng.stats.dashboard.service.impl.DetectionServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * Created by Tanaye on 6/2/17.
 */

@RequestMapping("/detect")
@Controller
public class DetectionController extends BaseController {

    private static final Logger logger = Logger.getLogger(DetectionController.class.getName());

    @Autowired
    private DetectionService detectionService;

    @RequestMapping(value = {"", "/", "data"})
    public String index(ModelMap map) throws Exception {
        return "detect/detect";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ajax/detectTable")
    public void ajaxGetIssuesTable(HttpServletResponse response,
                                   @RequestParam(name = "date", defaultValue = "", required = false) String date) throws Exception {
        writeJsonResponse(response, detectionService.detectGame(date));
    }


}
