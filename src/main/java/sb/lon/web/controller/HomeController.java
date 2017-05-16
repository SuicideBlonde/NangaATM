package sb.lon.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sb.lon.dao.CardDao;
import sb.lon.model.Card;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by sblon on 11/05/2017.
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private CardDao cardDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(final HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        System.out.println(session);
        if (session != null) {
            session.getAttribute("cardIdNumber");
        }
        return "card-number-entry";
    }

    @RequestMapping(value = "/pin", method = RequestMethod.POST)
    public String pin(@RequestParam(value = "cardNumber") String cardNumberFormatted, final Model model) {

        cardNumberFormatted = cardNumberFormatted.replace("-", "");
        Long cardNumber = Long.valueOf(cardNumberFormatted);
        Card card = cardDao.findCardById(cardNumber);
        if (card == null) {
            return "redirect:/";
        }
        model.addAttribute("cardNumber", card.getId());
        return "pin-entry";
    }
}
