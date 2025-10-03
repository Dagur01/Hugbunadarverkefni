package is.hi.hbv501g.verkefni.Controllers;


import is.hi.hbv501g.verkefni.Persistence.Entities.User;
import is.hi.hbv501g.verkefni.Services.SignUpService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class SignUpController {

    SignUpService signUpService;

    // kóði úr dæmi

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUp() {
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPOST(User user, BindingResult result, Model model){
        if(result.hasErrors()){
            return "redirect:/signup";
        }
        User exists = SignUpService.findByUsername(user.getUsername());
        if(exists == null){
            signUpService.save(user);
        }
        return "redirect:/";
    }

}
