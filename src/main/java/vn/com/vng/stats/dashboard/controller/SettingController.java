package vn.com.vng.stats.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vn.com.vng.stats.dashboard.form.GameConfigForm;
import vn.com.vng.stats.dashboard.form.UserForm;
import vn.com.vng.stats.dashboard.service.GameConfigService;
import vn.com.vng.stats.dashboard.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Tanaye on 6/7/17.
 */
@RequestMapping("/setting")
@Controller
public class SettingController extends BaseController {

    private static final Logger logger = Logger.getLogger(SettingController.class.getName());


    @Autowired
    private UserService userService;

    @Autowired
    private GameConfigService gameConfigService;

    @RequestMapping(value = {"", "/", "data"})
    public String index(ModelMap map) throws Exception {
        return "setting/setting";
    }

    /**
     * User Setting
     *
     * @param response
     * @param userForm
     * @throws IOException
     */
    @RequestMapping(value = "/ajax/user/add", method = RequestMethod.POST)
    public void ajaxAddUser(HttpServletResponse response,
                            @RequestParam(name = "isUpdate", defaultValue = "false") String isUpdate,
                            @ModelAttribute UserForm userForm) throws Exception {
        logger.info(userForm.toString());
        userForm.setUpdate(Boolean.parseBoolean(isUpdate));
        writeJsonResponse(response, userService.addUser(userForm));
    }

    @RequestMapping(value = "/ajax/user/remove", method = RequestMethod.POST)
    public void ajaxRemoveUser(HttpServletResponse response,
                               @ModelAttribute UserForm userForm) throws Exception {
        logger.info(userForm.toString());
        writeJsonResponse(response, userService.removeUser(userForm));
    }

    /**
     * User Setting
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/user/list", method = RequestMethod.GET)
    public void ajaxListUser(HttpServletResponse response) throws Exception {
        writeJsonResponse(response, userService.getUsers());
    }

    /**
     * Game Setting
     *
     * @param response
     * @param gameConfigForm
     * @throws Exception
     */

    @RequestMapping(value = "/ajax/gameconfig/add", method = RequestMethod.POST)
    public void ajaxAddGame(HttpServletResponse response,
                            @RequestParam(name = "isUpdate", defaultValue = "false") String isUpdate,
                            @ModelAttribute GameConfigForm gameConfigForm) throws Exception {
        gameConfigForm.setUpdate(Boolean.parseBoolean(isUpdate));
        writeJsonResponse(response, gameConfigService.addGame(gameConfigForm));
    }

    /**
     *
     * @param response
     * @param gameConfigForm
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/gameconfig/remove", method = RequestMethod.POST)
    public void ajaxRemoveGame(HttpServletResponse response,
                            @ModelAttribute GameConfigForm gameConfigForm) throws Exception {
        logger.info(gameConfigForm.toString());
        writeJsonResponse(response, gameConfigService.removeGame(gameConfigForm));
    }


    /**
     * Game Setting
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/ajax/gameconfig/list", method = RequestMethod.GET)
    public void ajaxListGame(HttpServletResponse response) throws Exception {
        writeJsonResponse(response, gameConfigService.getGames());
    }


}
