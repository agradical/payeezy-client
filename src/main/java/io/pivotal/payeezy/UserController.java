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
	
	@RequestMapping("/user")
    public List<User> greeting(@RequestParam(value="id", defaultValue="1") String id) {
        return userDAO.findById(Long.parseLong(id));
    }
	
	@RequestMapping("/create")
    public void create(@RequestParam(value="id", defaultValue="1") String id) {
        User u = new User();
        u.setId(Long.parseLong("1"));
        u.setUsername("Ankur");
		userDAO.save(u);
    }
	
}
