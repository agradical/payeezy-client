package io.pivotal.payeezy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired
	public UserDAO userDAO;
	
	@RequestMapping("/getuser")
    public List<User> getUser(@RequestParam(value="id", defaultValue="1") String id) {
        return userDAO.findById(Long.parseLong(id));
    }
	
	@RequestMapping("/createuser")
    public void create(@RequestParam(value="id") String id) {
        User u = new User();
        u.setId(Long.parseLong(id));
        u.setName("Ankur");
		userDAO.save(u);
    }
	
}
