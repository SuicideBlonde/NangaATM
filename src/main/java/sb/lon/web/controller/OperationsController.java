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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by sblon on 11/05/2017.
 */
@Controller
public class OperationsController {

    private static final Logger logger = LoggerFactory.getLogger(OperationsController.class);

    @Autowired
    private CardDao cardDao;


    @RequestMapping(value = "/operations", method = RequestMethod.POST)
    public String operations(
            final @RequestParam(value = "cardNumber") Long cardNumber,
            final @RequestParam(value = "pin") Long pin,
            final HttpServletRequest request,
            final Model model) {

        Card card = cardDao.findCardByIdAndPin(cardNumber, pin);
        if (card == null) {
            cardDao.pinEnteredCount(cardNumber);
            return "redirect:/";
        }

        HttpSession session = request.getSession();
        session.setAttribute("cardIdNumber", cardNumber);

        model.addAttribute("cardIdNumber", cardNumber);
        return "operations";
    }

    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public String balance(final HttpServletRequest request, final HttpServletResponse response, final Model model) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/";
        }

        Long cardIdNumber = (Long) session.getAttribute("cardIdNumber");

        Long balance = cardDao.balance(cardIdNumber);
        model.addAttribute("balance", balance / 100); // show in USD

        return "operations-balance";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String withdraw(final @RequestParam(value = "amount") String amount, final HttpServletRequest request, final Model model) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/";
        }

        if (!amount.matches("\\d+")) {
            return "redirect:/operations";
        }

        Long cardIdNumber = (Long) session.getAttribute("cardIdNumber");

        cardDao.withdraw(cardIdNumber, Long.valueOf(amount));
        return "operations";
    }

    @RequestMapping(value = "/exit", method = RequestMethod.POST)
    public String exit(final HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}
