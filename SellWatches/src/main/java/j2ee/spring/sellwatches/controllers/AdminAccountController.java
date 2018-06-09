package j2ee.spring.sellwatches.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import j2ee.spring.sellwatches.common.CommonConstands;
import j2ee.spring.sellwatches.common.Encryptor;
import j2ee.spring.sellwatches.services.AccountService;
import j2ee.spring.sellwatches.viewmodel.AdminLoginViewModel;

@Controller
@Component
@SessionAttributes(CommonConstands.USER_SESSION)
public class AdminAccountController {
	
	@Autowired 
	AccountService service;
	
	@RequestMapping(value = "admin/login", method = RequestMethod.GET)
	public String index(Model model)
	{
		model.addAttribute("account", new AdminLoginViewModel());
		return "admin_login";
	}
	@RequestMapping(value = "admin/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("account") AdminLoginViewModel account)
	{
		// Lấy dữ liệu
		String userName = account.getUsername();
		String password = account.getPassword();
		// kiểm tra
		if(!userName.toLowerCase().equals("admin"))
		{
			account.setError("Tài khoản, mật khẩu không đúng!");
			return "admin_login";
		}
		// xữ lý
		boolean checkLogin = service.LoginAccount(userName, Encryptor.MD5Hash(password));
		if(!checkLogin)
		{
			account.setError("Tài khoản, mật khẩu không đúng!");
			return "admin_login";
		}
		return "redirect:home";
	}
}