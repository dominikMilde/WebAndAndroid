/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.wpu.users;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
@RestController
public class UserDataController {
    @Autowired
    private UserDataService usersService;
    
    @RequestMapping(method = RequestMethod.GET, value = "/userdatas")
    public List<UserData> get() {
        return usersService.get();
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/userdatas")
    public void insert(@RequestBody UserData userData) {
        usersService.insert(userData);
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/userdatas/{uuid}")
    public void update(@RequestBody UserData userData, @PathVariable("uuid") String uuid) {
        usersService.update(userData, uuid);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, value = "/userdatas/{uuid}")
    public void delete(@PathVariable("uuid") String uuid) {
        usersService.delete(uuid);
    }
}
