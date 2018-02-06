package vn.com.vng.stats.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.ResponseBody;
import vn.com.vng.stats.dashboard.service.QaDataService;

import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * Created by Tanaye on 5/25/17.
 */
@RequestMapping("/issues")
@Controller
public class IssuesController extends BaseController {

    private static final Logger logger = Logger.getLogger(IssuesController.class.getName());

    @Autowired
    private QaDataService qaDataService;

    @RequestMapping(value = {"", "/", "data"})
    public String index(@RequestParam(value = "noIssues", defaultValue = "false") String isNoIssues,
                        ModelMap map) throws Exception {

        map.put("isNoIssues", Boolean.parseBoolean(isNoIssues));
        return "issues/issues";
    }

    @RequestMapping(value = {"/refresh"})
    public String refreshData(@RequestParam(name = "rangeDate", required = false) String rangeDate) throws Exception {
        qaDataService.refreshData(rangeDate);
        return "redirect:/issues";
    }

    @RequestMapping(value = {"/firstRun"})
    public String firstRun() throws Exception {
        qaDataService.compareData();
        return "redirect:/issues";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ajax/issuesTable")
    public @ResponseBody void ajaxGetIssuesTable(HttpServletResponse response,
                                   @RequestParam(name = "rangeDate") String rangeDate,
                                   @RequestParam(name = "noIssues", defaultValue = "false") String isNoIssues) throws Exception {
        writeJsonResponse(response, qaDataService.getIssuesOfGame(rangeDate, Boolean.parseBoolean(isNoIssues)));
    }

}
